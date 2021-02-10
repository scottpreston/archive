package com.scottpreston.javarobot.chapter3;

import java.util.ArrayList;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class Hexapod implements JMotion {

    private LM32 lm32;

    private BasicLeg leg1; // left front
    private BasicLeg leg2; // left middle
    private BasicLeg leg3; // left back
    private BasicLeg leg4; // right front
    private BasicLeg leg5; // right middle
    private BasicLeg leg6; // right back

    private ArrayList legGroup1 = new ArrayList();
    private ArrayList legGroup2 = new ArrayList();

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int FORWARD = 2;
    private static final int BACKWARD = 3;
    private static final int NEUTRAL = 4;
    
    private int speed = 5;
    private static final int MIN_SPEED = 250;

    public Hexapod(JSerialPort serialPort) throws Exception {

        lm32 = new LM32(serialPort);
        // two methods for clean constructor
        init(); // init all legs
        setLegGroups(); // set legs in groups

    }

    // create legs
    private void init() throws Exception {
        // 1st position vertical servo (up/down)
        // 2nd position horzontal servo (forward/backward)
        leg1 = new BasicLeg(new ServoPosition2(0, 127, 50, 200),
                new ServoPosition2(1, 127, 50, 200));
        leg2 = new BasicLeg(new ServoPosition2(4, 127, 50, 200),
                new ServoPosition2(5, 127, 100, 150));
        leg3 = new BasicLeg(new ServoPosition2(8, 127, 50, 200),
                new ServoPosition2(9, 127, 50, 200));
        leg4 = new BasicLeg(new ServoPosition2(16, 127, 200, 50),
                new ServoPosition2(17, 127, 200, 50));
        leg5 = new BasicLeg(new ServoPosition2(20, 127, 200, 50),
                new ServoPosition2(21, 127, 150, 100));
        leg6 = new BasicLeg(new ServoPosition2(24, 127, 200, 50),
                new ServoPosition2(25, 127, 200, 50));

    }

    // put legs into walking groups
    private void setLegGroups() throws Exception {
        legGroup1.add(leg1);
        legGroup1.add(leg3);
        legGroup1.add(leg5);
        legGroup2.add(leg2);
        legGroup2.add(leg4);
        legGroup2.add(leg6);
    }

    // this will create an enture string of commands for all legs
    public String getTotalMove(ArrayList legs, int cmd) throws Exception {

        StringBuffer cmds = new StringBuffer();
        for (int i = 0; i < legs.size(); i++) {
            BasicLeg tmpLeg = (BasicLeg) legs.get(i);
            if (cmd == UP) {
                cmds.append(tmpLeg.up());
            }
            if (cmd == DOWN) {
                cmds.append(tmpLeg.down());
            }
            if (cmd == FORWARD) {
                cmds.append(tmpLeg.forward());
            }
            if (cmd == BACKWARD) {
                cmds.append(tmpLeg.backward());
            }
            if (cmd == NEUTRAL) {
                cmds.append(tmpLeg.neutral());
            }
        }
        return cmds.toString();
    }

    // sample to move forward gate
    public void forward() throws Exception {
        lm32.setRawCommand(getTotalMove(legGroup1, DOWN));
        lm32.move(getSpeedInMs());
        lm32.setRawCommand(getTotalMove(legGroup2, UP));
        lm32.move(getSpeedInMs());
        lm32.setRawCommand(getTotalMove(legGroup2, FORWARD));
        lm32.move(getSpeedInMs());
        lm32.setRawCommand(getTotalMove(legGroup1, BACKWARD));
        lm32.move(getSpeedInMs());
        lm32.setRawCommand(getTotalMove(legGroup2, DOWN));
        lm32.move(getSpeedInMs());
        lm32.setRawCommand(getTotalMove(legGroup1, UP));
        lm32.move(getSpeedInMs());
        lm32.setRawCommand(getTotalMove(legGroup1, FORWARD));
        lm32.move(getSpeedInMs());
        lm32.setRawCommand(getTotalMove(legGroup2, BACKWARD));
        lm32.move(getSpeedInMs());
    }

    public static void main(String[] args) {
        try {
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            Hexapod hex = new Hexapod(sPort);
            hex.forward();
            hex.forward();
            sPort.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void forward(int ms) throws Exception {
        int remaining = ms;
        if (getSpeedInMs() * 8 < ms) {
            throw new Exception("Speed requested is less than minimum speed.");
        }
        while (remaining > getSpeedInMs() * 8) {
            forward();
            remaining = remaining - getSpeedInMs() * 8;
        }
        Utils.pause(remaining);
    }

    public void stop() throws Exception {
        lm32.setRawCommand(getTotalMove(legGroup1, DOWN));
        lm32.setRawCommand(getTotalMove(legGroup2, DOWN));
        lm32.move(getSpeedInMs());
    }

    public void reverse() throws Exception {
    }

    public void pivotRight() throws Exception {
    }

    public void pivotLeft() throws Exception {
    }

    public void reverse(int ms) throws Exception {
    }

    public void pivotRight(int ms) throws Exception {
    }

    public void pivotLeft(int ms) throws Exception {
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    private int getSpeedInMs() {
        return  (MIN_SPEED* 10) - (MIN_SPEED * speed) + 250;
    }
}
