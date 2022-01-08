package com.sajjadamin.randomface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI extends JFrame {
    JLabel dialogLabel, progressLabel;
    JButton chooseButton, startButton;
    JTextField destinationField;
    JComboBox<String> amountChooser;
    String destination = "";
    int amount;
    boolean should_stop = false, stop_now = false;
    People people;
    GUI(){
        initComponents();
        people = new People();
    }

    private void initComponents() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        ImageIcon icon = new ImageIcon(this.getClass().getResource("image/icon.png"));
        Container container = this.getContentPane();
        container.setLayout(null);

        //define frame parameters
        this.setSize(400, 150);
        this.setTitle("Random Face Generator");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        //Destination
        destinationField = new JTextField();
        destinationField.setEditable(false);
        destinationField.setHorizontalAlignment(JTextField.LEFT);
        destinationField.setBounds(15,15,240,30);
        container.add(destinationField);
        //Choose Button
        chooseButton = new JButton("choose folder");
        chooseButton.setBounds(270,15,100,30);
        container.add(chooseButton);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setMultiSelectionEnabled(false);
                if(chooser.showOpenDialog(GUI.this) == JFileChooser.APPROVE_OPTION){
                    destination = chooser.getSelectedFile().toString();
                    destinationField.setText(destination);
                    startButton.setEnabled(true);
                } else {
                    destination = "";
                    destinationField.setText(destination);
                    startButton.setEnabled(false);
                }
            }
        });
        //Start Button
        startButton = new JButton("Start");
        startButton.setBounds(270,55,100,30);
        if(destination.equals("")) startButton.setEnabled(false);
        container.add(startButton);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fLocation = destination+File.separator;
                amount = Integer.parseInt(amountChooser.getSelectedItem().toString());
                if(should_stop){
                    stop_now = true;
                    should_stop = false;
                    startButton.setText("Start");
                }else {
                    stop_now = false;
                    should_stop = true;
                    startButton.setText("Stop");
                    progressLabel.setText("");
                    startFaceCreating(amount,fLocation);
                }
            }
        });
        //Dialog Label
        dialogLabel = new JLabel("How much face you need?");
        dialogLabel.setBounds(15,55,160,20);
        container.add(dialogLabel);
        //Amount Chooser
        String[] amountNumber = new String[100];
        for(int i = 0; i < 100; i++){
            amountNumber[i] = Integer.toString(i+1);
        }
        amountChooser = new JComboBox<>(amountNumber);
        amountChooser.setBounds(180,55,70,30);
        container.add(amountChooser);
        //Progress Label
        progressLabel = new JLabel();
        progressLabel.setBounds(15, 75, 160,30);
        container.add(progressLabel);
    }
    private void startFaceCreating (int amount, String location){
        new Thread(new Runnable() {
            int count = 0;
            @Override
            public void run() {
                while (!stop_now && count != amount){
                    if(people.getFace(destination)){
                        count++;
                        progressLabel.setText(count+" faces has been created!");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                should_stop = true;
                stop_now = false;
                startButton.setText("Start");
            }
        }).start();
    }
    void start(){
        this.setVisible(true);
    }
}
