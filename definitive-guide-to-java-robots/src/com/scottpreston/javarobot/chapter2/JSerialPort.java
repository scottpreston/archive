package com.scottpreston.javarobot.chapter2;

public interface JSerialPort{
	
    public byte[] read();
	public String readString();
	public void write(byte[] b) throws Exception;
	public void close();
	public void setDTR(boolean dtr);
	public void setTimeout(int tOut);
	// added for web
    public static final String READ_COMMAND = "r";
    public static final String WRITE_COMMAND = "w";
    public static final String WRITE_READ_COMMAND = "wr";
    public byte[] read(byte[] b) throws Exception;
    public String readString(byte[] b) throws Exception;

}
