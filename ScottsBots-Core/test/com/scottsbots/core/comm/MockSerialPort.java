package com.scottsbots.core.comm;

import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.utils.Utils;

public class MockSerialPort implements JSerialPort {

	private byte[] readBuffer;
	private byte[] writeBuffer;
	
	public void setReadBuffer(byte[] readBuffer) {
		this.readBuffer = readBuffer;
	}

	public void setWriteBuffer(byte[] writeBuffer) {
		this.writeBuffer = writeBuffer;
	}

	public byte[] read() {
		return readBuffer;
	}

	public String readString() {
		return Utils.toAscii(readBuffer);
	}

	public void write(byte[] b) throws Exception {

	}

	public void close() {

	}

	public void setDTR(boolean dtr) {

	}

	public void setTimeout(int tOut) {

	}

	public byte[] read(byte[] b) throws Exception {
		return read();
	}

	public String readString(byte[] b) throws Exception {
		return readString();
	}

}
