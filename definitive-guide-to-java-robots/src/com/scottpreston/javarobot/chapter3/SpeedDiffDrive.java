package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class SpeedDiffDrive extends TimedDiffDrive implements JMotion{

    // set initial speed
	private int speed = 25;

	// construct with JSerialPort
	public SpeedDiffDrive(JSerialPort serialPort) throws Exception{
		super(serialPort);
	}
	
	// accessor for speed
	public int getSpeed() {
		return speed;
	}
	 
	// setter for speed
	public void setSpeed(int speed) throws Exception {
	    // keep speed between min and max
		if (speed < 1 || speed > 100) {
			throw new Exception("Speed out of range 1-100.");
		}
		// set speed
		this.speed = speed;
		// get high for left
		setLeftHigh(getSpdHI());
		// get low for left
		setLeftLow(getSpdLO());
		// get high for right
		setRightHigh(getSpdHI());
		// get low for right
		setRightLow(getSpdLO());
	}

	// get speed as fraction of 127 (half of MiniSSC)
	private int getSpd() {
		double s = (double) 127 * (speed / 100.0);
		return (int) s;
	}

	// return high speed
	private int getSpdHI() {
		return getSpd() + 127;
	}

	// return low speed
	private int getSpdLO() {
		return 127 - getSpd();
	}

	// sample program
	public static void main(String[] args) {
		try {
		    // get instance of SingleSerialPort
		    JSerialPort sPort = (JSerialPort)SingleSerialPort.getInstance(1);
		    // create instance of SpeedDiffDrive
			SpeedDiffDrive diffDrive = new SpeedDiffDrive(sPort);
			// set speed to 5
			diffDrive.setSpeed(5);
			// move forward 2 seconds
			diffDrive.forward(2000);
			// close port
			sPort.close();
		} catch (Exception e) {
		    // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
		}
	}
	
	// for interface passthroughs 
    public void forward(int ms) throws Exception{
        super.forward(ms);
    }    
    public void reverse(int ms) throws Exception{
        super.reverse(ms);
    }
    public void pivotRight(int ms) throws Exception{
        super.pivotRight(ms);
    }
    public void pivotLeft(int ms) throws Exception{
        super.pivotLeft(ms);
    }
    
}