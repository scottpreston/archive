package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class BasicDiffDrive {

    // drive will use MiniSSC
    private MiniSsc ssc;

    // left wheel hooked to pin 0
    public static final int LEFT_WHEEL = 0;
    // right wheel hooked to pin 1
    public static final int RIGHT_WHEEL = 1;
    // set all to neutral values
    private int right = SSCProtocol.NEUTRAL;;
    private int left = SSCProtocol.NEUTRAL;
    private int rightHigh = SSCProtocol.MAX;
    private int rightLow = SSCProtocol.MIN;
    private int leftHigh = SSCProtocol.MAX;
    private int leftLow = SSCProtocol.MIN;

    // right will always be the one inverted can change this
    private boolean motorsInverted = false;

    // constructor takes JSerialPort
    public BasicDiffDrive(JSerialPort serialPort) throws Exception {
        // create MiniSSC
        ssc = new MiniSsc(serialPort);
    }

    // setting motor values
    public void setMotors(int left, int right) {
        this.left = left;
        this.right = right;
    }

    // actually moving the motors
    private void move() throws Exception {
        // left wheel
        ssc.move(LEFT_WHEEL, left, RIGHT_WHEEL, right);
        // right wheel
        //ssc.move(RIGHT_WHEEL, right);
    }

    // move in reverse
    public void reverse() throws Exception {
        // if inverted move motors opposite or same.
        if (motorsInverted) {
            // opposite direction
            setMotors(leftHigh, rightLow);
        } else {
            // same direction
            setMotors(leftHigh, rightHigh);
        }
        // move motors
        move();
    }

    // move forward
    public void forward() throws Exception {
        if (motorsInverted) {
            setMotors(leftLow, rightHigh);
        } else {
            setMotors(leftLow, rightLow);
        }

        move();
    }
    
    // pivot on axis right
    public void pivotRight() throws Exception {
        if (motorsInverted) {
            setMotors(leftLow, rightLow);
        } else {
            setMotors(leftLow, rightHigh);
        }
        move();
    }

    // pivot on axis left
    public void pivotLeft() throws Exception {
        if (motorsInverted) {
            setMotors(leftHigh, rightHigh);
        } else {
            setMotors(leftHigh, rightLow);
        }
        move();
    }

    // stop the motion
    public void stop() throws Exception {
        // set both motors to same value
        setMotors(SSCProtocol.NEUTRAL, SSCProtocol.NEUTRAL);
        move();
    }

    // accessor
    public boolean isMotorsInverted() {
        return motorsInverted;
    }

    // setter
    public void setMotorsInverted(boolean motorsInverted) {
        this.motorsInverted = motorsInverted;
    }

    // accessor
    public int getLeftHigh() {
        return leftHigh;
    }

    // setter
    public void setLeftHigh(int leftHigh) {
        this.leftHigh = leftHigh;
    }

    // accessor
    public int getLeftLow() {
        return leftLow;
    }

    // setter
    public void setLeftLow(int leftLow) {
        this.leftLow = leftLow;
    }

    // accessor
    public int getRightHigh() {
        return rightHigh;
    }

    // setter
    public void setRightHigh(int rightHigh) {
        this.rightHigh = rightHigh;
    }

    // accessor
    public int getRightLow() {
        return rightLow;
    }

    // setter
    public void setRightLow(int rightLow) {
        this.rightLow = rightLow;
    }

    // sample program
    public static void main(String[] args) {
        try {
            // get instance of SingleSerialPort
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            // create instnace of BasicDiffDrive
            BasicDiffDrive diffDrive = new BasicDiffDrive(sPort);
            // move forward
            diffDrive.forward();
            // pause 2 seconds
            Utils.pause(2000);
            // stop
            diffDrive.stop();
            // close serial port
            sPort.close();
        } catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
        }
    }

}