package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class ArmTest1 {

    private BasicArm arm;

    public ArmTest1(JSerialPort sPort) throws Exception {
        arm = new BasicArm(sPort);
        arm.rest();
    }

    // to position a
    public void toA() throws Exception {
        arm.shoulder(50);
        Utils.pause(1000);
        arm.elbow(200);
    }

    // to position b
    public void toB() throws Exception {
        arm.shoulder(150);
        Utils.pause(1000);
        arm.elbow(50);

    }

    // sample program
    public static void main(String[] args) {
        try {
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            ArmTest1 arm1 = new ArmTest1(sPort);
            arm1.toA();
            arm1.toB();
            arm1.toA();
            sPort.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
