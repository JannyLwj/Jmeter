package com.nuance.tts;

import com.nuance.util.HTTPImpl;
import com.nuance.util.HttpResult;
import org.apache.jmeter.samplers.SampleResult;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class RequestHandler {
    private String requestData;
    private String requestInfo;
    private String accept;
    private String boundary;

    public RequestHandler(String requestData, String requestInfo, String accpet) {
        this.requestData = requestData;
        this.requestInfo = requestInfo;
        this.accept = accpet;
        this.boundary = getBoundary();
    }

    public static String getBoundary() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    public HashMap<String, String> getHTTPHeader() {
        HashMap<String, String> result = new HashMap<>();
        result.put("Connection", "Keep-Alive");
        result.put("Content-Type", "multipart/form-data; boundary=" + boundary);
        if (isNotBlank(accept)) {
            result.put("Accept", accept);
        }
        return result;
    }

    public String getHTTPBody() {
        StringBuilder sb = new StringBuilder();
        sb.append("--" + boundary + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"RequestData\"" + "\r\n");
        sb.append("Content-Type: application/json; charset=utf-8" + "\r\n");
        sb.append("\r\n");
        sb.append(requestData + "\r\n");


        sb.append("--" + boundary + "\r\n");
        sb.append("Content-Disposition: form-data; name=\"TtsParameter\"; paramName=\"TEXT_TO_READ\"" + "\r\n");
        sb.append("Content-Type: application/json; charset=utf-8" + "\r\n");
        sb.append("\r\n");
        sb.append(requestInfo + "\r\n");

        sb.append("--" + boundary + "--\r\n");


        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String s1 = "{\n" +
                "    \"appKey\": \"864a913262cdf87795e988eb87c596e724d18b53cfc1881fd3338a714f96bd708e876caaccbf7c3a8a5190cd9b968c7c31eba454e1e89b70d4175d3ac7786146\",\n" +
                "    \"appId\": \"AUDI_SDS_1ST_IT_20160212\",\n" +
                "    \"outCodec\": \"MP3_8KBPS\",\n" +
                "    \"cmdName\": \"NVC_TTS_CMD\",  \n" +
                "    \"cmdDict\": {\n" +
                "        \"tts_language\":\"de-de\",\n" +
                "        \"tts_voice\":\"Ina\" \n" +
                "\n" +
                "    }\n" +
                "}";

        String s2 = "{\n" +
                "    \"tts_type\": \"text\",\n" +
                "\t\"tts_input\":\"Streaming von Videos erfordert Safari 4 oder neuer auf einem Mac mit OS X 10.6 oder neuer.\"\n" +
                "}";
        s1=s1.replace("\n","");
        s2=s2.replace("\n","").replace("\t","");
        System.out.println(s1);
        System.out.println(s2);
        RequestHandler handler = new RequestHandler(s1, s2, "audio/x-wav;codec=pcm");
        URL url = new URL("http://10.33.61.73:22937/NmspServlet/");
        HTTPImpl hi = new HTTPImpl(url);
        HttpResult result = hi.sendPostData(handler.getHTTPHeader(), handler.getHTTPBody(), new SampleResult());
        String s = new String(result.getResult());
        int a = 1;
    }
}
