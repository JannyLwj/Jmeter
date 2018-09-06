package com.nuance.jmeter.processor;


import org.apache.jmeter.processor.gui.AbstractPostProcessorGui;
import org.apache.jmeter.testelement.TestElement;

import java.awt.*;

public class TTSResponsePostProcessGui extends AbstractPostProcessorGui {
    private TTSResponsePostProcessGuiPanel settingPanel;

    public TTSResponsePostProcessGui() {
        init();
    }

    private void init() {
        super.setName(getLabelResource());
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        settingPanel = new TTSResponsePostProcessGuiPanel();
        add(settingPanel, BorderLayout.CENTER);
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if (element instanceof TTSResponsePostProcessor) {
            settingPanel.getGoldenPath().setText(element.getPropertyAsString(TTSResponsePostProcessor.GOLDEN_FOLDER));
            settingPanel.getGoldenFile().setText(element.getPropertyAsString(TTSResponsePostProcessor.GOLDEN_FILE));
        }
    }

    @Override
    public TestElement createTestElement() {
        TTSResponsePostProcessor element = new TTSResponsePostProcessor();
        configureTestElement(element);
        return element;
    }

    @Override
    public String getLabelResource() {
        return getStaticLabel();
    }


    @Override
    public void modifyTestElement(TestElement element) {
        super.configureTestElement(element);
        if(element instanceof TTSResponsePostProcessor){
            element.setProperty(TTSResponsePostProcessor.GOLDEN_FOLDER, settingPanel.getGoldenPath().getText());
            element.setProperty(TTSResponsePostProcessor.GOLDEN_FILE, settingPanel.getGoldenFile().getText());
        }
    }

    @Override
    public String getStaticLabel() {
        return "Nuance TTS Post Process";
    }
}