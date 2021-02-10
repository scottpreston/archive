
package com.scottpreston.javarobot.chapter3;

public class ServoPosition2 extends ServoPosition {

    // minimum position of arm
	public int min;
	// maximum position of arm
	public int max;	
	// neutral position of arm
	public int neutral;
	
	public ServoPosition2(int pin) throws Exception {
	    super(pin,SSCProtocol.NEUTRAL);
	    min = SSCProtocol.MIN;
	    max = SSCProtocol.MAX;
	    neutral = SSCProtocol.NEUTRAL;
	}
	public ServoPosition2(int pin, int pos, int min, int max) throws Exception{
		super(pin,pos);
		if (min > 255 || min < 0) {
			throw new Exception("Minimum out of range, 0-255 only.");
		}
		if (max > 255 || max < 0) {
			throw new Exception("Maximum out of range, 0-255 only.");
		}
		this.min = min;
		this.max = max;		
		this.neutral = pos;
	}
	
}
