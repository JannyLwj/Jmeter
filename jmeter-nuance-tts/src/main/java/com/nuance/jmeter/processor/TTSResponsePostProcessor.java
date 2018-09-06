package com.nuance.jmeter.processor;


import com.nuance.tts.ResponseHandler;
import org.apache.jmeter.processor.PostProcessor;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.AbstractScopedTestElement;
import org.apache.jmeter.threads.JMeterContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class TTSResponsePostProcessor extends AbstractScopedTestElement implements PostProcessor {
    private static final Logger log = LoggerFactory.getLogger(TTSResponsePostProcessor.class);
    public final static String GOLDEN_FILE = "golden_file";
    public final static String GOLDEN_FOLDER = "golden_folder";

    @Override
    public void process() {
        JMeterContext context = getThreadContext();
        SampleResult previousResult = context.getPreviousResult();

        if (previousResult == null) {
            log.info("Can't get Previous Result");
            return;
        }


        byte[] data = previousResult.getResponseData();
        log.info("Get Previous Result {} bytes", data.length);

        try {
            ResponseHandler responseHandler = new ResponseHandler(data);
            log.info("Boundary is {}", responseHandler.getBoundary());

            String id = responseHandler.getSessionID();
            log.info("Get Session ID is {}", id);

            byte[] audio = responseHandler.getAudio();
            log.info("Get audio {} bytes", audio.length);

            if (id == null && id.trim() == "") {
                log.error("Can't get Session ID");
                return;
            }
            String fileNameFromGUI = getPropertyAsString(GOLDEN_FILE);
            String fileName;
            if (isBlank(fileNameFromGUI)) {
                fileName = id;
            } else {
                fileName = fileNameFromGUI;
            }

            String folderName = getPropertyAsString(GOLDEN_FOLDER);

            String fileFullName = folderName + fileName;
            log.info("Full Name is {}", fileFullName);
            File folder = new File(folderName);
            if (!folder.exists()) {
                log.info("Create the folder {}", folder.getName());
                folder.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(fileFullName);
            fos.write(audio);
            fos.flush();
            fos.close();

        } catch (Exception ex) {
            log.error("Exception happens");
            log.info(ex.getMessage());
        }
    }


}
