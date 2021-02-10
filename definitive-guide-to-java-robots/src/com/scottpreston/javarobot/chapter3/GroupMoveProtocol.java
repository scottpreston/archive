package com.scottpreston.javarobot.chapter3;

public interface GroupMoveProtocol {
	
	public static final String CMD_TERM = "\r";
	
	/**
	 * This is the SSC Constructor Mode w/500 default speed
	 * @param ch - channel 0-31
	 * @param pos - position 0-255
	 */
	
	public void sscCmd(int ch, int pos) throws Exception;
	
	/**
	 * This is the native constructor mode.
	 * @param ch - channel 0-31
	 * @param pos - position 0-255
	 * @param spd - speed in ms
	 * @param tm = time to move in milliseconds
	 * 
	 */
	public void cmd(int ch, int pos, int spd) throws Exception;
	
	/**
	 * 
	 * @param time - length in milliseconds to move
	 * @throws Exception
	 */
	
	public void move(int time) throws Exception;

}
 