package com.nuance.jmeter.processor;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;

public class TTSResponsePostProcessGuiPanel extends JPanel {
    public TTSResponsePostProcessGuiPanel() {
        init();
    }

    JLabel goldenPathLabel, goldenFileLabel;
    @Getter
    JTextField goldenPath, goldenFile;

    private void init() {
        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, Y_AXIS));
        boxPanel.setBorder(BorderFactory.createTitledBorder("Save Golden Files"));
        boxPanel.add(Box.createVerticalStrut(3));

        JPanel pathPanel = new JPanel();
        pathPanel.setLayout(new BoxLayout(pathPanel, X_AXIS));
        goldenPathLabel = new JLabel("Golden Folder Path: ");
        goldenPathLabel.setPreferredSize(new Dimension(150, 80));
        pathPanel.add(goldenPathLabel);
        goldenPath = new JTextField();
        goldenPath.setColumns(30);
        goldenPath.setMaximumSize(new Dimension(Integer.MAX_VALUE, goldenPath.getMinimumSize().height));
        pathPanel.add(goldenPath);
        boxPanel.add(pathPanel);

        JPanel filePanel=new JPanel();
        filePanel.setLayout(new BoxLayout(filePanel, X_AXIS));
        goldenFileLabel =new JLabel("Golden File Name: ");
        goldenFileLabel.setPreferredSize(new Dimension(150, 80));
        filePanel.add(goldenFileLabel);
        goldenFile =new JTextField();
        goldenFile.setColumns(30);
        goldenFile.setMaximumSize(new Dimension(Integer.MAX_VALUE, goldenFile.getMinimumSize().height));
        filePanel.add(goldenFile);
        boxPanel.add(filePanel);

        add(boxPanel, BorderLayout.NORTH);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.getContentPane().add(new TTSResponsePostProcessGuiPanel());
        frame.setVisible(true);
    }
}
