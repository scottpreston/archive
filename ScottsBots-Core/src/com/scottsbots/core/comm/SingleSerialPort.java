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

import java.util.HashMap;
import java.util.Map;

import com.scottsbots.core.JSerialPort;

/**
 * Singleton of the serial port, to ensure only one copy is ever retrieved.
 * 
 * @author scott
 * 
 */
public class SingleSerialPort {

	private static Map<Integer, JSerialPort> portMap;

	private SingleSerialPort() {
		// prevents initialization
	}

	/**
	 * 
	 * @param comid
	 *            - this can either be COM1,ttyS0,ttyUSBS0, change the comid to
	 *            the port id
	 * @return StandardSerialPort
	 */

	public static JSerialPort getInstance(int comid) throws Exception {
		if (portMap == null) portMap = new HashMap<Integer,JSerialPort>();
		if (!portMap.containsKey(new Integer(comid))) {
			portMap.put(new Integer(comid), new StandardSerialPort(comid)); // Lazy
																			// initialization
		}
		return portMap.get(new Integer(comid));
	}

}
