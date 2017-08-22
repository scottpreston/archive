package com.scottsbots.core.comm;

import junit.framework.TestCase;

public class StandardSerialPortTest extends TestCase {
	
	public void testReadString() {
		MockSerialPort serialPort = new MockSerialPort();
		serialPort.setReadBuffer(new byte[]{97,98});
		assertEquals("ab", serialPort.readString());
	}
	

}
