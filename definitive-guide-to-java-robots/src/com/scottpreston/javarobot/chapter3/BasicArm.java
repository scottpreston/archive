package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class BasicArm {

    private MiniSsc ssc;
    // set shoulder and elbow parameters
    public static final int SHOULDER_PIN = 0;
    public static final int SHOULDER_MAX = SSCProtocol.MAX;
    public static final int SHOULDER_MIN = SSCProtocol.MIN;
    public static final int SHOULDER_REST = 55;
    public static final int ELBOW_PIN = 1;
    public static final int ELBOW_MAX = SSCProtocol.MAX;
    public static final int ELBOW_MIN = SSCProtocol.MIN;
    public static final int ELBOW_REST = 65;

    //constructor taking JSerialPort as parameter
    public BasicArm(JSerialPort sPort) throws Exception {
        ssc = new MiniSsc(sPort);
    }

    // passthough to ssc
    private void move(int pin, int pos) throws Exception {
        ssc.move(pin, pos);
    }

    // move the shoulder
    public void shoulder(int pos) throws Exception {
        if (pos < SHOULDER_MIN || pos > SHOULDER_MAX) {
            throw new Exception("Out of shoulder range.");
        }
        move(SHOULDER_PIN, pos);
    }

    // move the elbow
    public void elbow(int pos) throws Exception {
        if (pos < ELBOW_MIN || pos > ELBOW_MAX) {
            throw new Exception("Out of elbow range.");
        }
        move(ELBOW_PIN, pos);
    }

    public void rest() throws Exception {
        shoulder(SHOULDER_REST);
        elbow(ELBOW_REST);
    }

    public static void main(String[] args) {
        try {
            // get single serial port instance
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            // create new BasicArm
            BasicArm arm = new BasicArm(sPort);
            // move to rest position
            arm.rest();
            // move elbow to 150
            arm.elbow(150);
            // move shoulder to 200
            arm.shoulder(200);
            // close serial port
            sPort.close();
        } catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
        }
    }

}
