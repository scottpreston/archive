package com.scottpreston.javarobot.chapter7;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;
import com.scottpreston.javarobot.chapter3.MiniSsc;

public class SonarServos {

    public static final int LEFT_SONAR = 2;
    public static final int RIGHT_SONAR = 3;
    public static final int LEFT_AFT = 60;
    public static final int LEFT_NEUTRAL = 150;
    public static final int RIGHT_NEUTRAL = 110;
    public static final int LEFT_FORE = 245;
    public static final int RIGHT_AFT = 200;
    public static final int RIGHT_FORE = 20;

    private MiniSsc ssc;

    public SonarServos(JSerialPort serialPort) throws Exception {
        ssc = new MiniSsc(serialPort);
    }

    public void move(int left, int right) throws Exception {
        Utils.pause(250); // wait for servo settle
        ssc.move(LEFT_SONAR, left, RIGHT_SONAR, right);
        Utils.pause(250); // wait for servo settle
    }

    //  this will be from 180 to 360 of the robot.
    public void moveLeft(int angle) throws Exception {
        if (angle > 360) {
            angle = angle - 360;
        }
        if (angle < 0) {
            angle = angle + 360;
        }
        double thirdQuad = (LEFT_FORE - LEFT_NEUTRAL); // > 127
        double fourthQuad = (LEFT_NEUTRAL - LEFT_AFT); // < 127
        int pos = LEFT_NEUTRAL;
        if (angle < 270 && angle > 180) {
            angle = 270 - angle;
            pos = (int) ((angle / 90.0) * thirdQuad) + LEFT_NEUTRAL;
        } else if (angle > 270) {
            angle = 360 - angle;
            pos = LEFT_NEUTRAL - (int) ((angle / 90.0) * fourthQuad);
        } else if (angle < 180) {
            pos = LEFT_AFT;
        }
        ssc.move(LEFT_SONAR, pos);
    }

    //     this will be from 0 to 180 of the robot.
    public void moveRight(int angle) throws Exception {
        if (angle > 360) {
            angle = angle - 360;
        }
        if (angle < 0) {
            angle = angle + 360;
        }
        double firstQuad = (RIGHT_NEUTRAL - RIGHT_FORE); // < 127
        double secondQuad = (RIGHT_AFT - RIGHT_NEUTRAL); // > 127
        int pos = RIGHT_NEUTRAL;
        if (angle < 90) {
            pos = RIGHT_NEUTRAL - (int) ((angle / 90.0) * firstQuad);
        } else if (angle > 90 && angle > 180) {
            angle = 180 - angle;
            pos = (int) ((angle / 90.0) * secondQuad) + RIGHT_NEUTRAL;
        } else if (angle > 180) {
            pos = RIGHT_AFT;
        }
        ssc.move(RIGHT_SONAR, pos);
    }

    public void lookSide() throws Exception {
        move(LEFT_NEUTRAL, RIGHT_NEUTRAL);
    }

    public void lookFore() throws Exception {
        move(LEFT_FORE, RIGHT_FORE);
    }

    public void lookAft() throws Exception {
        move(LEFT_AFT, RIGHT_AFT);
    }

    public static void main(String[] args) throws Exception {
        try {
            WebSerialClient com = new WebSerialClient("10.10.10.99", "8080", "1");
            SonarServos ss = new SonarServos(com);
            ss.lookFore();
            Utils.pause(1000);
            ss.lookAft();
            Utils.pause(1000);
            ss.lookSide();
            // get 360 readings from sonar
            for (int a = 0; a < 360; a = a + 10) {
                ss.moveLeft(a);
                ss.moveRight(a);
                Utils.pause(1000);
            }
            com.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
