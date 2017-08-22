/*
 * (C) Copyright 2000-2011, by Scott Preston and Preston Research LLC
 *
 *  Project Info:  http://www.scottsbots.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.scottsbots.core.comm;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.utils.Utils;

/**
 * A modified version of the serial port for use with robotic controllers.
 * 
 * @author scott
 * 
 */
public class StandardSerialPort implements SerialPortEventListener, JSerialPort {

	private Enumeration portList;

	private CommPortIdentifier portId;

	private SerialPort serialPort;

	private OutputStream outputStream;

	private InputStream inputStream;

	private byte[] readBuffer;

	private boolean dataIn = false;

	private byte[] currentWrite;

	private StringBuffer sBuff = new StringBuffer();
	
	private int inLength = 0;

	public StandardSerialPort(int id) throws Exception {
		init(id, 9600);
	}

	public StandardSerialPort(int id, int baud) throws Exception {
		init(id, baud);
	}

	private void init(int comID, int baud) {
		String comIdAsString = new Integer(comID).toString();
		try {
			portList = CommPortIdentifier.getPortIdentifiers();
			while (portList.hasMoreElements()) {
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					if (portId.getName().endsWith(comIdAsString)) {
						// create serial port
						serialPort = (SerialPort) portId.open(
								"StandardSerialPort", 3000);
						// set config parms
						System.out.println("setting serial parms");
						serialPort.setSerialPortParams(baud,
								SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
						serialPort
								.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
						Utils.pause(50);
						// config output stream
						outputStream = serialPort.getOutputStream();
						// config input stream
						inputStream = serialPort.getInputStream();
						// add events listener
						serialPort.setDTR(false);
						serialPort.setRTS(false);
						serialPort.addEventListener(this);
						serialPort.notifyOnDataAvailable(true);
						Thread.sleep(50); // waits till ports change state.
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	int data = 0; // nothing ready to read

	public byte[] read() {
		Utils.log("read start");
		while (dataIn == false) {
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
		Utils.log("exit read");
		data = 0;
		dataIn = false;
		return readBuffer;
	}

	public String readString() {
		String out = sBuff.substring(inLength);
		sBuff = new StringBuffer(); // clear the string buffer since it's been read.
		return out;

	}

	public void write(byte[] b) throws Exception {
		inLength = b.length;
		sBuff = new StringBuffer();
		currentWrite = b;
		outputStream.write(b);
		Utils.log("serial out = " + Utils.byteToString(b));
	}

	public void close() {
		serialPort.close();
	}

	public byte[] read(byte[] b) throws Exception {
		// not used
		return null;
	}

	public String readString(byte[] b) throws Exception {
		String out = sBuff.substring(inLength);
		return out;
	}

	public void serialEvent(SerialPortEvent event) {

		if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

			try {
				while (inputStream.available() > 0) {
					int in = inputStream.read();
					char c = (char) in;
					sBuff.append(new Character(c).toString());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void setTimeout(int timeout) {
		// not used
	}

	public void setDTR(boolean dtr) {
		serialPort.setDTR(dtr);
	}

	public String getName() {
		return serialPort.getName();
	}

}