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
 * Interface for multiple serial ports, though I am just implementing one at this point, I've
 * later used it for web and network enabled ports.
 * 
 * @author scott
 *
 */
public interface JSerialPort{

    public byte[] read();
	public String readString();
	public void write(byte[] b) throws Exception;
	public void close();
	public void setDTR(boolean dtr);
	public void setTimeout(int tOut);
	// added for web
    public static final String READ_COMMAND = "r";
    public static final String WRITE_COMMAND = "w";
    public static final String WRITE_READ_COMMAND = "wr";
    public byte[] read(byte[] b) throws Exception;
    public String readString(byte[] b) throws Exception;

}
