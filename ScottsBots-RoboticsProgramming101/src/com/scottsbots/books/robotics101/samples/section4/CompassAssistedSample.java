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

import com.scottsbots.books.robotics101.samples.section2.CompassSample;
import com.scottsbots.books.robotics101.samples.section2.MicroControllerSample;
import com.scottsbots.core.JCompass;
import com.scottsbots.core.JDrive;
import com.scottsbots.core.JDrive.Direction;
import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.ServoController;
import com.scottsbots.core.navigation.BasicNavigation;

public class CompassAssistedSample extends BasicNavigation {

	public CompassAssistedSample(JDrive drive, JCompass compass) {
		super(drive, compass);
	}

	public static void main(String[] args) throws Exception {
		JSerialPort serialPort = SingleSerialPort.getInstance(0);
		ServoController ssc = new ServoController(serialPort);
		BasicDiffDriveSample sampleDrive = new BasicDiffDriveSample(ssc);
		MicroControllerSample sampleMicro = new MicroControllerSample(
				SingleSerialPort.getInstance(1));
		JCompass compass = new CompassSample(sampleMicro);
		CompassAssistedSample compassAssist = new CompassAssistedSample(
				sampleDrive, compass);

		int oneMeter = 2500;

		compassAssist.changeHeading(0);
		compassAssist.rawMove(Direction.FORWARD, oneMeter);
		compassAssist.changeHeading(90);
		compassAssist.rawMove(Direction.FORWARD, oneMeter);
		compassAssist.changeHeading(180);
		compassAssist.rawMove(Direction.FORWARD, oneMeter);
		compassAssist.changeHeading(270);
		compassAssist.rawMove(Direction.FORWARD, oneMeter);
	}

}
