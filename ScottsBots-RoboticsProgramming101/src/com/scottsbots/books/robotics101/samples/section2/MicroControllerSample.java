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

package com.scottsbots.books.robotics101.samples.section2;

import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.MicroController;
import com.scottsbots.core.utils.Utils;

public class MicroControllerSample extends MicroController {

	public static String CMD_COMPASS = "100" + COMMAND_DELIM + "101";
	public static String CMD_SONAR = "100" + COMMAND_DELIM + "102";
	public static String CMD_HELLO = "100" + COMMAND_DELIM + "103";

	public MicroControllerSample(JSerialPort serialPort) {
		super(serialPort);
		this.addCommand(CMD_COMPASS);
		this.addCommand(CMD_SONAR);
		this.addCommand(CMD_HELLO);
	}

	public int getCompass() throws Exception {
		return new Integer(getData(CMD_COMPASS, 175)).intValue();
	}

	public int getSonar() throws Exception {
		return new Integer(getData(CMD_SONAR, 100)).intValue();
	}
	
	public String hello() throws Exception {
		return getData(CMD_HELLO, 100);
	}
	
	public static void main(String[] args) throws Exception {
		MicroControllerSample microSample = new MicroControllerSample(SingleSerialPort.getInstance(1));
		Utils.log("hello = " + microSample.hello());
		Utils.log("sonar = " + microSample.getSonar());
		Utils.log("compass = " + microSample.getCompass());		
	}
}
