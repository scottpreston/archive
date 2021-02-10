package com.scottpreston.javarobot.chapter4;

import com.scottpreston.javarobot.chapter2.Controller;
import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class SwitchStamp extends Controller {
    
    // commands set in basic stmap program
    public static final int CMD_INIT = 100;
    public static final int CMD_SINGLE = 101;
    public static final int CMD_MULTI = 102;
    // sensors
    public static final int SINGLE_LINE_SENSOR1 = 0;
    public static final int SINGLR_LINE_SENSOR2 = 1;
    public static final int PROXIMITY_SENSOR = 2;
    

    public SwitchStamp(JSerialPort sPort) throws Exception{
        super(sPort);
    }
    
    public boolean getSingle() throws Exception{
        // read single reading
        String h = execute(new byte[] {CMD_INIT,CMD_SINGLE},25);
		if (h.equalsIgnoreCase("1")) {
			return true;
		} else {
			return false;
		}
    }
    
    public String getMulti() throws Exception {
        String r = execute(new byte[] {CMD_INIT,CMD_SINGLE},25);
        String[] r2 = r.split("~");
        String readings = "";
        for (int i = 0; i < r2.length; i++) {
            // convert each byte to char which I append to create single number
            readings = readings + (char) new Integer(r2[i]).intValue();
        }
        return readings;
    }
    
    public boolean getMulti(int index) throws Exception{
        String i = getMulti().substring(index);
        if (i.equalsIgnoreCase("1")) {
			return true;
		} else {
			return false;
		}
    }

    public static void main(String[] args) {
        try {
            // since i am testing at my desk and not on my robot
            SwitchStamp s = new SwitchStamp(SingleSerialPort.getInstance(1));
           	// get single switch
            System.out.println("Single Switch = " + s.getSingle());
            // get multiple readings
            System.out.println("Multiple Switches = " + s.getMulti());
            // get proximity switch from multiple readings
            System.out.println("Proximity Sensor = " + s.getMulti(SwitchStamp.PROXIMITY_SENSOR));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        }
    }
}
