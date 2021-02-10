package com.scottpreston.javarobot.chapter9;

import javax.swing.JPanel;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter3.MiniSsc;

public class SscPanel extends JPanel {

    private MiniSsc ssc;
    public static final long serialVersionUID = 1;
    
    public SscPanel(JSerialPort sPort) throws Exception{
        ssc = new MiniSsc(sPort);
    }

    public void moveServo(int pin, int pos) {
        try {
            ssc.move(pin, pos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
