package com.scottpreston.javarobot.chapter2;

import java.util.Vector;

public class SingleSerialPort {

	private static Vector portsInUse = new Vector();
	
	private SingleSerialPort() {
		// prevents initialization
	}
	
	public static StandardSerialPort getInstance(int comid) throws Exception {
		return getInstance(comid,9600);
	}	
	
	public static StandardSerialPort getInstance(int comid, int baud) throws Exception {

		StandardSerialPort instance = null;
		String tmpComID = new Integer(comid).toString();

		// return a port in use if it exist.
		for (int i=0; i< portsInUse.size(); i++) {
			StandardSerialPort aPort = (StandardSerialPort)portsInUse.get(i);
			if (aPort.getName().endsWith(tmpComID)) {
				return aPort;
			}
		}
		// otherwise create the port if its in the list
		if (instance == null) {
	          instance = new StandardSerialPort(comid,baud);
	          portsInUse.add(instance);
		}
	    return instance;
	}
	
	public static void close(int comid) {
		
	    String tmpComID = new Integer(comid).toString();

		// return a port in use if it exist.
		for (int i=0; i< portsInUse.size(); i++) {
			StandardSerialPort aPort = (StandardSerialPort)portsInUse.get(i);
			if (aPort.getName().endsWith(tmpComID)) {
				aPort.close();
				portsInUse.remove(i);
			}
		}
	}

	public static void closeAll() {
		// cycle through all and close
		for (int i=0; i< portsInUse.size(); i++) {
			StandardSerialPort aPort = (StandardSerialPort)portsInUse.get(i);
			aPort.close();
			portsInUse.remove(i);
		}
	}
}
