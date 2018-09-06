package com.nuance.jmeter.sampler;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class TTSRequestSamplerGui extends AbstractSamplerGui {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TTSRequestSamplerGui.class);
    private TTSRequestSamplerGuiPanel settingsPanel;

    public TTSRequestSamplerGui() {
        super.setName(getLabelResource());
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        settingsPanel = new TTSRequestSamplerGuiPanel();
        add(settingsPanel, BorderLayout.CENTER);
    }

    @Override
    public String getStaticLabel() {
        return "Nuance TTS Sampler";
    }

    @Override
    public String getLabelResource() {
        return getStaticLabel();
    }

    @Override
    public TestElement createTestElement() {
        TTSRequestSampler element = new TTSRequestSampler();
        configureTestElement(element);
        return element;
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if (element instanceof TTSRequestSampler) {
            String url = element.getPropertyAsString(TTSRequestSampler.URL);
            logger.debug("URL:" + url);
            settingsPanel.getUrl().setText(url.trim().replace("\r","").replace("\n",""));

            String acceptType = element.getPropertyAsString(TTSRequestSampler.ACCEPT_TYPE);
            logger.debug("Accept Type:" + acceptType);
            settingsPanel.getAcceptType().setText(acceptType.trim().replace("\r","").replace("\n",""));

            String requestJson = element.getPropertyAsString(TTSRequestSampler.REQUEST_JSON);
            logger.debug("Request Json:" + requestJson);
            settingsPanel.getReqeustData().setText(requestJson.trim().replace("\r","").replace("\n",""));

            String textToRead = element.getPropertyAsString(TTSRequestSampler.TEXT_READ);
            logger.debug("Text To Read:" + textToRead);
            settingsPanel.getTextToRead().setText(textToRead.trim().replace("\r","").replace("\n",""));

            String goldenPath = element.getPropertyAsString(TTSRequestSampler.GOLDEN_PATH);
            logger.debug("Golden Path:" + goldenPath);
            settingsPanel.getGoldenPath().setText(goldenPath.trim().replace("\r","").replace("\n",""));

            String goldenFile = element.getPropertyAsString(TTSRequestSampler.GOLDEN_FILE);
            logger.debug("Golden File:" + goldenFile);
            settingsPanel.getGoldenFile().setText(goldenFile.trim().replace("\r","").replace("\n",""));

            String connTimeout = element.getPropertyAsString(TTSRequestSampler.CONN_TIMEOUT);
            logger.debug("Connection Timeout:" + connTimeout);
            settingsPanel.getConnectionTimeout().setText(connTimeout.trim().replace("\r","").replace("\n",""));

            String readTimeout = element.getPropertyAsString(TTSRequestSampler.READ_TIMEOUT);
            logger.debug("Read Timeout:" + readTimeout);
            settingsPanel.getReadTimeout().setText(readTimeout.trim().replace("\r","").replace("\n",""));

        }
    }

    @Override
    public void modifyTestElement(TestElement element) {
        super.configureTestElement(element);
        if (element instanceof TTSRequestSampler) {
            element.setProperty(TTSRequestSampler.URL, settingsPanel.getUrl().getText().trim().replace("\r","").replace("\n",""));
            element.setProperty(TTSRequestSampler.ACCEPT_TYPE, settingsPanel.getAcceptType().getText().trim().replace("\r","").replace("\n",""));
            element.setProperty(TTSRequestSampler.REQUEST_JSON, settingsPanel.getReqeustData().getText().trim().replace("\r","").replace("\n",""));
            element.setProperty(TTSRequestSampler.TEXT_READ, settingsPanel.getTextToRead().getText().trim().replace("\r","").replace("\n",""));
            element.setProperty(TTSRequestSampler.GOLDEN_PATH, settingsPanel.getGoldenPath().getText().trim().replace("\r","").replace("\n",""));
            element.setProperty(TTSRequestSampler.GOLDEN_FILE, settingsPanel.getGoldenFile().getText().trim().replace("\r","").replace("\n",""));
            element.setProperty(TTSRequestSampler.CONN_TIMEOUT, settingsPanel.getConnectionTimeout().getText().trim().replace("\r","").replace("\n",""));
            element.setProperty(TTSRequestSampler.READ_TIMEOUT, settingsPanel.getReadTimeout().getText().trim().replace("\r","").replace("\n",""));
        }
    }
}
