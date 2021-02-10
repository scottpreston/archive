package com.scottpreston.javarobot.chapter4;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class Compass{

	private CompassStamp cStamp;
	
	public Compass(JSerialPort sPort) throws Exception{
	    cStamp = new CompassStamp(sPort);
	}
	
	public int getHeading() throws Exception{		
		return cStamp.getHeading(CompassStamp.CMD_DEVANTECH);
	}
	
	public static void main(String[] args) throws Exception{
	    try {
            // since i am testing at my desk and not on my robot
            Compass compass = new Compass(SingleSerialPort.getInstance(1));
            System.out.println("Compass Heading = " + compass.getHeading());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        }
		
	}
}