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

package com.scottsbots.core.controller;

import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.utils.Utils;

/**
 * This is a generic controller that implements serial communication to either a
 * microcontroller, or serial controller.
 * 
 * @author scott
 * 
 */

public abstract class AbstractController{

	private JSerialPort serialPort;

	/**
	 * 
	 * @param sPort
	 *            - serial port
	 */
	public AbstractController(JSerialPort sPort) {
		serialPort = sPort;
		serialPort.setDTR(false);
		Utils.pause(125);
	}
		
	/**
	 * @return A string response from the microcontroller
	 * @param cmd
	 *            - takes a byte[] input for commands
	 * @param delay
	 *            - a delay in MS to await a response, if 0, then no delay
	 */

	public String execute(byte[] cmd, int delay) throws Exception {
		String out = null;

		if (delay == 0) {
			serialPort.write(cmd);
		} else {
			serialPort.write(cmd);
			Utils.pause(delay);
			out = serialPort.readString();
		}

		return out;
	}

	/**
	 * @return A byte[] response from the microcontroller
	 * @param cmd
	 *            - takes a byte[] input for commands
	 * @param delay
	 *            - a delay in MS to await a response, if 0, then no delay
	 */

	public byte[] execute2(byte[] cmd, int delay) throws Exception {
		byte[] out = null;

		if (delay == 0) {
			serialPort.write(cmd);
		} else {
			serialPort.write(cmd);
			Utils.pause(delay);
			out = serialPort.read();
		}

		return out;
	}
	
	public String readData() throws Exception {
		return serialPort.readString();
	}
}
