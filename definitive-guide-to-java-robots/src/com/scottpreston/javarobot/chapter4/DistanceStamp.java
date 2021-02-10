package com.scottpreston.javarobot.chapter4;

import com.scottpreston.javarobot.chapter2.Controller;
import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class DistanceStamp extends Controller {

    // commands set in basic stmap program
    public static final int CMD_INIT = 100;
    public static final int CMD_IR = 101;
    public static final int CMD_SRF = 102;
    public static final int CMD_6500 = 103;
    
    private int distSensor = CMD_SRF;

    // constructor
    public DistanceStamp(JSerialPort sPort) throws Exception {
        super(sPort);
    }

    public int ping(int distSensor) throws Exception {
        setDistSensor(distSensor);
        return ping();
    }

    // get distance method
    public int ping() throws Exception {
        // calling super execute() method
        String heading = execute(new byte[] { CMD_INIT, (byte) distSensor },
                getSonarDelay());
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
    
    public int getIR() throws Exception {
        return ping(CMD_IR);
    }
    public int getSRF() throws Exception {
        return ping(CMD_IR);
    }
    public int get6500() throws Exception {
        return ping(CMD_IR);
    }
    
    // since differnt delay for each compass
    private int getSonarDelay() {
        int delay = 0;
        if (distSensor == CMD_IR) {
            delay = 50;
        }
        if (distSensor == CMD_SRF) {
            delay = 150;
        }
        if (distSensor == CMD_6500) {
            delay = 250;
        }
        return delay;
    }
    public int getDistSensor() {
        return distSensor;
    }
    public void setDistSensor(int distSensor) {
        this.distSensor = distSensor;
    }

    public static void main(String[] args) {
        try {
            // since i am testing at my desk and not on my robot
            DistanceStamp s = new DistanceStamp(SingleSerialPort.getInstance(1));
            System.out.println("Sharp IR Reading = " + s.getIR());
            System.out.println("SRF04 reading = " + s.getSRF());
            System.out.println("6500 reading = " + s.get6500());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
