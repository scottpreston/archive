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

package com.scottsbots.books.robotics101.samples.section4;

import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.JSscDevice;
import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.ServoController;
import com.scottsbots.core.motion.BasicDiffDrive;
import com.scottsbots.core.motion.DriveConfiguration;
import com.scottsbots.core.utils.Utils;

public class BasicDiffDriveSample extends BasicDiffDrive {

	// drive cfg {pin,slowFwd,fastFwd,slowRev,fastRev
	public static DriveConfiguration LEFT_CFG =  new DriveConfiguration(0,110,100,140,150);
	public static DriveConfiguration RIGHT_CFG =  new DriveConfiguration(1,110,100,140,150);

	public BasicDiffDriveSample(JSscDevice ssc) throws Exception {
		super(ssc, LEFT_CFG, RIGHT_CFG);
	}
	
	public BasicDiffDriveSample(JSscDevice ssc, DriveConfiguration leftDrive,
			DriveConfiguration rightDrive) throws Exception {		
		super(ssc, leftDrive, rightDrive);
	}

	public static void main(String[] args) throws Exception {
		JSerialPort serialPort = SingleSerialPort.getInstance(0);
		ServoController ssc = new ServoController(serialPort);
		BasicDiffDriveSample sampleDrive = new BasicDiffDriveSample(ssc);
		sampleDrive.forward(1000);
		Utils.pause(1000);
		sampleDrive.pivotLeft(1000);
		Utils.pause(1000);
		sampleDrive.pivotRight(1000);
		Utils.pause(1000);
		sampleDrive.reverse(1000);
		Utils.pause(1000);
	}
	
}
