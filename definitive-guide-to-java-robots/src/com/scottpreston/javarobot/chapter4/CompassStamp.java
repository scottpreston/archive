package com.scottpreston.javarobot.chapter4;

import com.scottpreston.javarobot.chapter2.Controller;
import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class CompassStamp extends Controller {

    // commands set in basic stmap program
    public static final int CMD_INIT = 100;
    public static final int CMD_DISMORE = 101;
    public static final int CMD_DEVANTECH = 102;
    public static final int CMD_VECTOR = 103;

    // default reading for compass
    private int compass = CMD_DEVANTECH;

    // constructor
    public CompassStamp(JSerialPort sPort) throws Exception {
        super(sPort);
    }

    public int getDismore() throws Exception{
        return getHeading(CMD_DISMORE);
    }
    public int getDevantech() throws Exception{
        return getHeading(CMD_DEVANTECH);
    }
    public int getVector() throws Exception{
        return getHeading(CMD_VECTOR);
    }
    
    public int getHeading(int compass) throws Exception {
        setCompass(compass);
        return getHeading();
    }

    // get heading method
    public int getHeading() throws Exception {
        // calling super execute() method
        String heading = execute(new byte[] { CMD_INIT, (byte) compass },
                getCompassDelay());
        // since returning heading as one, two or three bytes
        String[] h2 = heading.split("~");
        String heading2 = "";
        for (int h = 0; h < h2.length; h++) {
            // convert each byte to char which I append to create single number
            heading2 = heading2 + (char) new Integer(h2[h]).intValue();
        }
        // return 3 chars like '123' which is 123 degrees
        return new Integer(heading2).intValue();
    }

    // since differnt delay for each compass
    private int getCompassDelay() {
        int delay = 0;
        if (compass == CMD_DISMORE) {
            delay = 50;
        }
        if (compass == CMD_DEVANTECH) {
            delay = 150;
        }
        if (compass == CMD_VECTOR) {
            delay = 250;
        }
        return delay;
    }

    public int getCompass() {
        return compass;
    }

    public void setCompass(int compass) {
        this.compass = compass;
    }

    public static void main(String[] args) {
        try {
            // since i am testing at my desk and not on my robot
            CompassStamp s = new CompassStamp(SingleSerialPort.getInstance(1));
            // since devantech is default
            System.out.println("Devantech Heading = " + s.getHeading());
            // getting specific heading
            System.out
                    .println("Vector Heading = " + s.getHeading(CMD_VECTOR));
            // using a setter
            s.setCompass(CompassStamp.CMD_DISMORE);
            // getting dinsmore heading
            System.out.println("Dinsmore Heading = " + s.getHeading());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        }
    }
}
