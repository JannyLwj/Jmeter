package com.nuance.tts;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestResponseHandler {
    @Test
    public void test() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File rawData = new File(classLoader.getResource("contents.bin").getFile());
        File audioData = new File(classLoader.getResource("audio.ogg").getFile());
        if (rawData.exists() && audioData.exists()) {
            FileInputStream fi = new FileInputStream(rawData);
            byte[] data = new byte[fi.available()];
            fi.read(data);

            ResponseHandler handler = new ResponseHandler(data);
            String sessionID = handler.getSessionID();
            Assert.assertEquals(sessionID, "8d670549-20b2-4044-bd46-58a88dbe6e9d");


            fi = new FileInputStream(audioData);
            byte[] audioFromFile = new byte[fi.available()];
            fi.read(audioFromFile);
            byte[] audioFromHandler = handler.getAudio();
            Assert.assertArrayEquals(audioFromFile, audioFromHandler);

        } else {
            throw new FileNotFoundException();
        }
    }
}
