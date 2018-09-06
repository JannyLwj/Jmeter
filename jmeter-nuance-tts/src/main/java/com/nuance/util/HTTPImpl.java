package com.nuance.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.util.SSLManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.HashMap;

import static org.apache.http.HttpHeaders.CONTENT_LENGTH;

public class HTTPImpl {
    private static final String HTTPS = "https";
    private static final String ENCODING = StandardCharsets.UTF_8.name();

    URL url;
    int connectionTimeout;
    int readTimeout;

    public HTTPImpl(URL u) {
        this(u, 0, 0);
    }

    public HTTPImpl(URL u, int connectionTimeout, int readTimeout) {
        this.url = u;
        this.connectionTimeout = connectionTimeout;
        this.readTimeout = readTimeout;
    }

    private HttpURLConnection setupConnection() throws IOException {
        SSLManager sslManager = null;
        if (url.getProtocol().equalsIgnoreCase(HTTPS)) {
            sslManager = SSLManager.getInstance();
        }

        final HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setInstanceFollowRedirects(true);

        if (connectionTimeout > 0)
            httpConn.setConnectTimeout(connectionTimeout);

        if (readTimeout > 0)
            httpConn.setReadTimeout(readTimeout);

        if (url.getProtocol().equalsIgnoreCase(HTTPS)) {
            sslManager.setContext(httpConn);
        }
        return httpConn;
    }


    public HttpResult sendPostData(HashMap<String, String> header, String body, SampleResult result) throws IOException {
        return sentPostData(header, body, ENCODING, result);
    }

    public HttpResult sentPostData(HashMap<String, String> header, String body, String contentEncoding, SampleResult result) throws IOException {
        if (body == null) {
            throw new InvalidParameterException("The body is empty");
        }

        if (StringUtils.isBlank(contentEncoding)) {
            contentEncoding = ENCODING;
        }
        result.sampleStart();

        HttpURLConnection conn = setupConnection();
        result.connectEnd();

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");

        if (header != null) {
            for (String key : header.keySet()) {
                conn.setRequestProperty(key, header.get(key));
            }
        }

        byte[] data = body.getBytes(StandardCharsets.UTF_8);
        conn.setRequestProperty(CONTENT_LENGTH, Integer.toString(data.length));

        OutputStream out = conn.getOutputStream();
        out.write(body.getBytes(StandardCharsets.UTF_8));

        out.flush();

        int code = conn.getResponseCode();
        String message = conn.getResponseMessage();
        InputStream is = conn.getInputStream();

        byte[] temp = new byte[1024];
        ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        int bytesRead = 0;
        boolean isFirstPackage = true;
        while ((bytesRead = is.read(temp)) > 0) {
            responseBody.write(temp, 0, bytesRead);
            if(isFirstPackage){
                result.latencyEnd();
                isFirstPackage=false;
            }
        }
        result.sampleEnd();
        return HttpResult.builder().httpMessage(message).httpCode(code).result(responseBody.toByteArray()).build();
    }

}
