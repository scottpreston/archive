package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class TimedDiffDrive extends BasicDiffDrive {

    // construct with JSerialPort
    public TimedDiffDrive(JSerialPort serialPort) throws Exception {
        super(serialPort);
    }

    // forward
    public void forward(long ms) throws Exception {
        // calls super
        forward();
        // pause
        Utils.pause(ms);
        // stop
        stop();
    }

    // reverse
    public void reverse(long ms) throws Exception {
        reverse();
        Utils.pause(ms);
        stop();
    }

    // pivot left
    public void pivotLeft(long ms) throws Exception {
        pivotLeft();
        Utils.pause(ms);
        stop();
    }
    // pivot right
    public void pivotRight(long ms) throws Exception {
        pivotRight();
        Utils.pause(ms);
        stop();
    }

    // sample program
    public static void main(String[] args) {
        try {
            // get instance of SingleSerialPort
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            // create instnace of TimedDiffDrive
            TimedDiffDrive diffDrive = new TimedDiffDrive(sPort);
            // move forwrd 2 seconds
            diffDrive.forward(2000);
            // close serial port
            sPort.close();
        } catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
        }
    }
}