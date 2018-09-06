package com.nuance.tts;

import com.nuance.util.ByteTokenizer;
import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResponseHandler {

    private static final byte[] CRLF = {0x0d, 0x0A};
    private static final byte[] DOUBLE_CRLF = {0x0d, 0x0A, 0x0d, 0x0A};

    byte[] data;
    @Getter
    List<byte[]> decodedData;
    @Getter
    byte[] boundary;
    @Getter
    List<HashMap<String, byte[]>> headerAndBody;

    public ResponseHandler(byte[] data) throws UnsupportedEncodingException {
        this.data = data;
        headerAndBody = new ArrayList<>();
        spliteByBoundary();
        handlerData();
    }

    public String getSessionID() {
        String sessionKey = "Nuance-Context: ";
        for (HashMap<String, byte[]> subData : headerAndBody) {
            for (String key : subData.keySet()) {
                if (key != null) {
                    for (String subString : key.split("\r\n")) {
                        if (subString.startsWith(sessionKey)) {
                            return subString.substring(subString.indexOf(sessionKey) + sessionKey.length()).trim();
                        }
                    }
                }
            }
        }
        return "";
    }

    public byte[] getAudio() {
        List<byte[]> audio = new ArrayList<>();
        for (HashMap<String, byte[]> subData : headerAndBody) {
            for (String key : subData.keySet()) {
                if(key!=null && key.contains("name=\"Audio\"")){
                    if(subData.get(key)!=null && subData.get(key).length>0)
                        audio.add(subData.get(key));
                }
            }
        }
        return toArray(audio);
    }

    private static byte[] toArray(List<byte[]> list) {
        if (list == null || list.size() == 0) {
            return new byte[0];
        }

        int size = 0;
        for (byte[] data : list) {
            size += data.length;
        }

        byte[] array = new byte[size];
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).length; j++) {
                array[index++] = list.get(i)[j];
            }
        }

        return array;

    }

    private byte[] scanBoundary() {
        if (data == null || data.length == 0)
            throw new NullPointerException();

        int n = 0;
        int j = 0;
        int end = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == CRLF[j]) {
                j++;
            } else {
                j = 0;
            }

            if (j >= CRLF.length) {
                n++;
                j = 0;
            }

            if (n == 2) {
                end = i;
                break;
            }
        }

        if (n != 2 || end <= 0) {
            throw new InvalidParameterException(null);
        }

        byte[] result = new byte[end + 1];
        for (int i = 0; i <= end; i++) {
            result[i] = data[i];
        }

        return result;
    }

    private void spliteByBoundary() {
        this.boundary = scanBoundary();
        ByteTokenizer tokenizer = new ByteTokenizer(data, boundary);
        decodedData = new ArrayList<>();
        while (tokenizer.hasMoreElements()) {
            byte[] temp = tokenizer.nextElement();
            if (temp.length > 4) {
                decodedData.add(temp);
            }
        }
    }

    private void handlerData() throws UnsupportedEncodingException {
        for (byte[] subData : decodedData) {
            ByteTokenizer tokenizer = new ByteTokenizer(subData, DOUBLE_CRLF);
            String header = null;
            byte[] body = null;
            while (tokenizer.hasMoreElements()) {
                byte[] temp = tokenizer.nextElement();
                if (header == null) {
                    header = new String(temp, "UTF-8");
                } else {
                    body = temp;
                }
            }
            if (header != null) {
                HashMap<String, byte[]> map = new HashMap<>();
                map.put(header, body);
                headerAndBody.add(map);
            }
        }
    }
}
