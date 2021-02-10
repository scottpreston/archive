package com.scottpreston.javarobot.chapter3;

public interface SSCProtocol {

    // maximum
	public static final byte MAX = (byte) 255;
	// neutral
	public static final byte NEUTRAL = (byte)127;
	// minimum
	public static final byte MIN = (byte) 0;

	/**
	 * @param pin - connector on the MiniSSC 0-7
	 * @param pos - byte from 0-255
	 */
	public void move(int pin, int pos) throws Exception;

}
