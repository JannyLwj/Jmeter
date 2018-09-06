package com.nuance.jmeter.sampler;

import com.nuance.tts.RequestHandler;
import com.nuance.tts.ResponseHandler;
import com.nuance.util.ByteCompare;
import com.nuance.util.HTTPImpl;
import com.nuance.util.HttpResult;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.ThreadListener;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class TTSRequestSampler extends AbstractSampler implements ThreadListener {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TTSRequestSampler.class);
    public final static String URL = "url";
    public final static String ACCEPT_TYPE = "accept_type";
    public final static String REQUEST_JSON = "request_json";
    public final static String TEXT_READ = "text_read";
    public final static String GOLDEN_PATH = "golden_path";
    public final static String GOLDEN_FILE = "golden_name";
    public final static String CONN_TIMEOUT = "conn_timeout";
    public final static String READ_TIMEOUT = "read_timeout";


    @Override
    public SampleResult sample(Entry entry) {
        SampleResult result = new SampleResult();
        result.setSampleLabel("TTS Request");
        String url = getPropertyAsString(URL);
        if (isBlank(url)) {
            logger.info("URL is null");
            result.setSuccessful(false);
            result.setResponseCode("NO URL");
            result.setResponseMessage("URL is null");
            return result;
        }

        try {
            //Start HTTP POST request
            logger.info("URL is " + url);
            java.net.URL gwURL = new URL(url);
            int connTimeout = parseInt(getPropertyAsString(CONN_TIMEOUT));
            int readTimeout = parseInt(getPropertyAsString(READ_TIMEOUT));

            HTTPImpl http = new HTTPImpl(gwURL, connTimeout, readTimeout);

            String requestJson = getPropertyAsString(REQUEST_JSON);
            logger.debug("Request Json is " + requestJson);
            logger.info("Request Json Length is" + requestJson.length());

            String textToRead = getPropertyAsString(TEXT_READ);
            logger.debug("Text To Read is " + textToRead);
            logger.info("Text To Read Length is" + textToRead.length());

            String acceptType = getPropertyAsString(ACCEPT_TYPE);
            logger.info("Accept Type is " + acceptType);

            RequestHandler handler = new RequestHandler(requestJson, textToRead, acceptType);

            HttpResult httpResult = http.sendPostData(handler.getHTTPHeader(), handler.getHTTPBody(), result);
            if (httpResult.getHttpCode() != 200) {
                result.setSuccessful(false);
                result.setResponseCode(String.valueOf(httpResult.getHttpCode()));
                result.setResponseMessage(httpResult.getHttpMessage());
                return result;
            }

            //End HTTP

            //Start get audio
            logger.info("Response Byte Length is " + httpResult.getResult().length);
            result.setResponseData(httpResult.getResult());

            ResponseHandler responseHandler = new ResponseHandler(httpResult.getResult());
            logger.info("Session id is " + responseHandler.getSessionID());

            byte[] audioFromRequest = responseHandler.getAudio();
            logger.info("Audio from request Length is " + audioFromRequest.length);
            //End audio


            //Start compare golden
            String goldenPath = getPropertyAsString(GOLDEN_PATH);
            logger.info("Golden Path is " + goldenPath);
            String goldenFile = getPropertyAsString(GOLDEN_FILE);
            logger.info("Golden File is " + goldenFile);

            File golden = new File(goldenPath + goldenFile);
            if (!golden.exists()) {
                result.setSuccessful(false);
                result.setResponseCode("No Golden File");
                result.setResponseMessage("Can't find Golden:" + golden.getAbsolutePath());
                return result;
            }
            byte[] goldenByte = Files.readAllBytes(golden.toPath());

            boolean isSamle = ByteCompare.compare(audioFromRequest, goldenByte);
            if (isSamle) {
                result.setSuccessful(true);
                result.setResponseCode("200");
                result.setResponseMessage("Audio is same with Golden, size is " + goldenByte.length);
            } else {
                result.setSuccessful(false);
                result.setResponseCode("Compare Fail");
                result.setResponseMessage("Compare Fail, Golden size is" + goldenByte.length + " response audio size is " + audioFromRequest.length);
            }
            return result;
            //End golden


        } catch (Exception ex) {
            logger.error("Exception", ex);
            result.setSuccessful(false);
            result.setResponseCode("EXCEPTION");
            result.setResponseMessage(ex.getMessage());
            return result;
        }

    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public void threadStarted() {

    }

    @Override
    public void threadFinished() {

    }

}
