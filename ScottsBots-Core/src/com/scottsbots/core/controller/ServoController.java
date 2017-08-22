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
import com.scottsbots.core.JSscDevice;

/**
 * Base class for a Serial Servo Controller, implementing a 3 byte protocol.
 * @author scott
 *
 */

public class ServoController extends AbstractController implements JSscDevice {

	// maximum possible for SSC32
	private int maxPin = 31;

	// takes JSerialPort
	public ServoController(JSerialPort serialPort) {
		super(serialPort);
	}

	/**
	 * @param pin - the pin the servo is connected
	 * @param pos - the position of the servo, either between 0 and 255 or 0 and 250
	 *              see the servo controller documentation
	 */
	
	public void move(int pin, int pos) throws Exception {
		// keep pos in valid range
		if (pos < 0 || pos > 255) {
			throw new Exception(
					"Position out of range, must be between 0 and 255. Value was "
							+ pos + ".");
		}
		// keep pin in valid range
		if (pin < 0 || pin > maxPin) {
			throw new Exception("Pin out of range, must be between 0 and "
					+ maxPin + ". Value was " + pin + ".");
		}
		// create byte[] for commands
		byte[] b = new byte[] { (byte) 255, (byte) pin, (byte) pos };
		// send those bytes to controller
		execute(b, 0);
	}

	// accessor
	public int getMaxPin() {
		return maxPin;
	}

	// setter
	public void setMaxPin(int maxPin) {
		this.maxPin = maxPin;
	}

	// this will reset all pins to 127
	public void reset() {
		try {
			for (int i = 0; i < maxPin + 1; i++) {
				move(i, 127);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
