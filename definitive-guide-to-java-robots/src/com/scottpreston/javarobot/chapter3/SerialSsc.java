package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.StandardSerialPort;

public class SerialSsc {

	public static void main(String[] args) {

		try {
		    // create serial port
			StandardSerialPort serialPort = new StandardSerialPort(1);
			// increment position by 5 each time in loop
			for (int pos = 0; pos < 255; pos = pos + 5) {
			    // create byte array for ssc commands
				byte[] sscCmd = new byte[] { (byte) 255, 0, (byte) pos };
				// send byte array to serial port
				serialPort.write(sscCmd);
				// pause between commands
				Thread.sleep(100);
			}
			// close serail port
			serialPort.close();
		} catch (Exception e) {
		    // print stack trace and exit.
			e.printStackTrace();
			System.exit(1);
		}
	}
}