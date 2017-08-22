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

import java.util.Set;

import com.ibm.media.codec.video.h263.ReadStream;
import com.scottsbots.core.JMicroController;
import com.scottsbots.core.JSerialPort;

public class MicroController extends AbstractController implements JMicroController{

	private Set<String> commandSet;

	public MicroController(JSerialPort serialPort) {
		super(serialPort);
	}
	
	public void addCommand(String command) {
		commandSet.add(command);
	}


	public void setCommands(Set<String> commands) {
		this.commandSet = commands;
	}
	
	public Set getCommands() {
		return this.commandSet;
	}

	public String getData(String command, int delay) throws Exception {
		if (commandSet.contains(command)) {
			return exec(command, delay);
		} else {
			throw new Exception("Command " + command + " Not Found...");
		}
	}
	
	private String exec(String command, int delay) throws Exception {
		byte [] cmds = stringToByte(command);
		return execute(cmds,delay);
	}

	private byte [] stringToByte(String s) {
		String [] commands = s.split(COMMAND_DELIM);
		byte[] bytes = new byte[commands.length];
		int i = 0;
		for (String cmd: commands) {
			bytes[i] = new Byte(cmd).byteValue();
			i++;
		}
		return bytes;
	}

}
