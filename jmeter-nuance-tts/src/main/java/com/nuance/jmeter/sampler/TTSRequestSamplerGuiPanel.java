package com.nuance.jmeter.sampler;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;

public class TTSRequestSamplerGuiPanel extends JPanel {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TTSRequestSamplerGuiPanel.class);
    public TTSRequestSamplerGuiPanel() {
        init();
    }

    JLabel urlLabel, requestDataLabel, acceptTypeLable, textToReadLabel, goldenFileLabel, goldenPathLabel, connectionTimeoutLabel, readTimeoutLabel;

    @Getter
    JTextField url, reqeustData, acceptType, textToRead, goldenFile, goldenPath, connectionTimeout, readTimeout;
    JPanel boxPanel;

    private void init() {
        boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, Y_AXIS));
        boxPanel.setBorder(BorderFactory.createTitledBorder("Parameters"));
        boxPanel.add(Box.createVerticalStrut(3));

        createURL();
        createAcceptType();
        createRequestJson();
        createTextToRead();
        createGoldenFolder();
        createGoldenFile();
        createConnectionTimeout();
        createReadTimeout();

        this.setLayout(new BorderLayout());
        add(boxPanel, BorderLayout.NORTH);
    }

    private void createURL(){
        JPanel urlPanel = new JPanel();
        urlPanel.setLayout(new BoxLayout(urlPanel, X_AXIS));
        urlLabel = new JLabel("URL:");
        urlLabel.setPreferredSize(new Dimension(150, 80));
        urlPanel.add(urlLabel);
        url = new JTextField();
        url.setColumns(30);
        url.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        urlPanel.add(url);
        boxPanel.add(urlPanel);
    }

    private void createAcceptType(){
        JPanel acceptTypePanel = new JPanel();
        acceptTypePanel.setLayout(new BoxLayout(acceptTypePanel, X_AXIS));
        acceptTypeLable = new JLabel("Accept Type:");
        acceptTypeLable.setPreferredSize(new Dimension(150, 80));
        acceptTypePanel.add(acceptTypeLable);
        acceptType = new JTextField();
        acceptType.setColumns(30);
        acceptType.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        acceptTypePanel.add(acceptType);
        boxPanel.add(acceptTypePanel);
    }

    private void createRequestJson(){
        JPanel requestJsonPanel = new JPanel();
        requestJsonPanel.setLayout(new BoxLayout(requestJsonPanel, X_AXIS));
        requestDataLabel = new JLabel("Request JSON:");
        requestDataLabel.setPreferredSize(new Dimension(150, 80));
        requestJsonPanel.add(requestDataLabel);
        reqeustData = new JTextField();
        reqeustData.setColumns(30);
        reqeustData.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        requestJsonPanel.add(reqeustData);
        boxPanel.add(requestJsonPanel);
    }

    private void createGUI(JLabel label, JTextField field, String labelName) {
        logger.info("init"+labelName);
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, X_AXIS));
        label = new JLabel(labelName);
        label.setPreferredSize(new Dimension(150, 80));
        temp.add(label);
        field = new JTextField();
        field.setColumns(30);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getMinimumSize().height));
        temp.add(field);
        boxPanel.add(temp);
    }

    private void createTextToRead(){
        JPanel textToReadPanel = new JPanel();
        textToReadPanel.setLayout(new BoxLayout(textToReadPanel, X_AXIS));
        textToReadLabel = new JLabel("TextToRead JSON:");
        textToReadLabel.setPreferredSize(new Dimension(150, 80));
        textToReadPanel.add(textToReadLabel);
        textToRead = new JTextField();
        textToRead.setColumns(30);
        textToRead.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        textToReadPanel.add(textToRead);
        boxPanel.add(textToReadPanel);
    }

    private void createGoldenFolder(){
        JPanel godlenFolderPanel = new JPanel();
        godlenFolderPanel.setLayout(new BoxLayout(godlenFolderPanel, X_AXIS));
        goldenPathLabel = new JLabel("Golden Folder:");
        goldenPathLabel.setPreferredSize(new Dimension(150, 80));
        godlenFolderPanel.add(goldenPathLabel);
        goldenPath = new JTextField();
        goldenPath.setColumns(30);
        goldenPath.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        godlenFolderPanel.add(goldenPath);
        boxPanel.add(godlenFolderPanel);
    }

    private void createGoldenFile(){
        JPanel goldenFilePanel = new JPanel();
        goldenFilePanel.setLayout(new BoxLayout(goldenFilePanel, X_AXIS));
        goldenFileLabel = new JLabel("Golden File Name:");
        goldenFileLabel.setPreferredSize(new Dimension(150, 80));
        goldenFilePanel.add(goldenFileLabel);
        goldenFile = new JTextField();
        goldenFile.setColumns(30);
        goldenFile.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        goldenFilePanel.add(goldenFile);
        boxPanel.add(goldenFilePanel);
    }

    private void createConnectionTimeout(){
        JPanel connectionTimeoutPanel = new JPanel();
        connectionTimeoutPanel.setLayout(new BoxLayout(connectionTimeoutPanel, X_AXIS));
        connectionTimeoutLabel = new JLabel("Connection Timeout (ms):");
        connectionTimeoutLabel.setPreferredSize(new Dimension(150, 80));
        connectionTimeoutPanel.add(connectionTimeoutLabel);
        connectionTimeout = new JTextField();
        connectionTimeout.setColumns(30);
        connectionTimeout.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        connectionTimeoutPanel.add(connectionTimeout);
        boxPanel.add(connectionTimeoutPanel);
    }

    private void createReadTimeout(){
        JPanel connectionTimeoutPanel = new JPanel();
        connectionTimeoutPanel.setLayout(new BoxLayout(connectionTimeoutPanel, X_AXIS));
        readTimeoutLabel = new JLabel("Read Timeout (ms):");
        readTimeoutLabel.setPreferredSize(new Dimension(150, 80));
        connectionTimeoutPanel.add(readTimeoutLabel);
        readTimeout = new JTextField();
        readTimeout.setColumns(30);
        readTimeout.setMaximumSize(new Dimension(Integer.MAX_VALUE, url.getMinimumSize().height));
        connectionTimeoutPanel.add(readTimeout);
        boxPanel.add(connectionTimeoutPanel);
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.getContentPane().add(new TTSRequestSamplerGuiPanel());
        frame.setVisible(true);
    }
}
