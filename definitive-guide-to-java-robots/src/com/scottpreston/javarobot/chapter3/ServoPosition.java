package com.scottpreston.javarobot.chapter3;

public class ServoPosition {
	
	public int pin;
	public int pos;

	public ServoPosition (int pin, int pos) throws Exception{
		if (pos > 255 || pos < 0) {
			throw new Exception("Position out of range, 0-255 only.");
		}
		this.pin = pin;
		this.pos = pos;
	}
}
