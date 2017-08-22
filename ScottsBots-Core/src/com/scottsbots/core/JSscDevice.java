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

package com.scottsbots.core;

/**
 * Interface for serial servo controllers. At this point I am just implementing the 3 byte protocol, but
 * newer devices like the SSC32 (By Lynxmotion) will have a separate device interface that a controller will
 * implement this or the other one.
 * 
 * @author scott
 *
 */
public interface JSscDevice {

    // maximum
	public static final byte MAX_255 = (byte) 255;
	public static final byte MAX_250 = (byte) 250;
	// neutral
	public static final byte NEUTRAL_127 = (byte)127;
	public static final byte NEUTRAL_125 = (byte)125;
	// minimum
	public static final byte MIN = (byte) 0;

	/**
	 * @param pin - connector on the MiniSSC 0-7, SSC32 0-31
	 * @param pos - byte from 0-255
	 */
	public void move(int pin, int pos) throws Exception;

}
