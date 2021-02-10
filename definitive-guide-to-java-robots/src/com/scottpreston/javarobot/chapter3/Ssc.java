package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.Controller;
import com.scottpreston.javarobot.chapter2.JSerialPort;

public abstract class Ssc extends Controller implements SSCProtocol{

    // maximum possible for LM32
	private int maxPin = 31;
	
	// takes JSerialPort
	public Ssc(JSerialPort serialPort )throws Exception {
		super(serialPort);
	}
	
	// move will send signal to pin (0-7) and pos (0-255)
	public void move(int pin, int pos) throws Exception{
	    // keep pos in valid range
		if (pos < 0 || pos >255) {
			throw new Exception("Position out of range, must be between 0 and 255. Value was " + pos + ".");
		}
		// keep pin in valid range
		if (pin < 0 || pin > maxPin) {
			throw new Exception("Pin out of range, must be between 0 and " 
					+ maxPin + ". Value was " + pin + ".");
		}
		// create byte[] for commands
		byte [] b = new byte[] {(byte)255,(byte)pin,(byte)pos};
		// send those bytes to controller
		execute(b,0);
	}

	public void move(int pin1, int pos1, int pin2, int pos2) throws Exception{
		byte [] b = new byte[] {(byte)255,(byte)pin1,(byte)pos1,(byte)255,(byte)pin2,(byte)pos2};
		// send those bytes to controller
		execute(b,0);
	}
	// accessor
	public int getMaxPin() {
		return maxPin;
	}
	// setter
	public void setMaxPin(int maxPin) {
		this.maxPin = maxPin;
	}
	
}
