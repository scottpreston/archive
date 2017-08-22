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

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.util.Enumeration;

import com.scottsbots.core.utils.Utils;

/**
 * This test the installation of the Java Communications API.
 * 
 * @author scott
 * 
 */
public class ListOpenPorts {

	private CommPortIdentifier portId;
	private Enumeration portList;

	public ListOpenPorts() {
		portList = CommPortIdentifier.getPortIdentifiers();
	}

	public void list() throws InterruptedException, PortInUseException {
		while (portList.hasMoreElements()) {
			try {
				portId = (CommPortIdentifier) portList.nextElement();
				if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
					System.out.print("Serial Port = ");
				}
				if (portId.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
					System.out.print("Parallel Port = ");
				}
				System.out.print(portId.getName() + ", ");

				CommPort port = portId.open("OpenTest", 20);
				Thread.sleep(250);
				System.out.println("Opened." + portId.getCurrentOwner());
				port.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void test() {
		Utils.printJavaEnv();
		try {
			ListOpenPorts openPorts = new ListOpenPorts();
			openPorts.list();
			System.out.println("done!");
		} catch (InterruptedException ie) {
			System.out.println("Unable to sleep.");
		} catch (PortInUseException pe) {
			System.out.println("Failed. Port In Use.");
		}

	}

	public static void main(String[] args) {
		test();
	}
}