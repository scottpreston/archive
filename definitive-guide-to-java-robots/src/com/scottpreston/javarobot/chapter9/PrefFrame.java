package com.scottpreston.javarobot.chapter9;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PrefFrame extends JFrame implements ActionListener {
    
    private JTextField comTxt;
    private JTextField baudTxt;
    private ServoControlClient scc;
    public static final long serialVersionUID = 1;

    public PrefFrame(ServoControlClient parent) {
        super("Preferences");
        scc = parent;
        this.setSize(400, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridLayout(3, 2));
        JPanel pan1 = new JPanel();
        pan1.add(new JLabel("Com :"));
        comTxt = new JTextField(2);
        comTxt.setText(scc.getId() + "");
        baudTxt = new JTextField(4);
        baudTxt.setText(scc.getBaud() + "");
        pan1.add(comTxt);
        JPanel pan2 = new JPanel();
        pan2.add(new JLabel("Baud :"));
        pan2.add(baudTxt);
        JPanel pan3 = new JPanel();
        JButton saveButton = new JButton("Save");
        pan3.add(saveButton);
        content.add(pan1);
        content.add(pan2);
        content.add(pan3);
        this.pack();
        saveButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent event) {
        //Object source = event.getSource();
        int id = new Integer(comTxt.getText()).intValue();
        int baud = new Integer(baudTxt.getText()).intValue();
        setVisible(false);
        try {
            scc.setSerialPort(id,baud);
        } catch (Exception e) {
            e.printStackTrace();}

    }
}