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

import com.scottsbots.core.JDrive;
import com.scottsbots.core.JDrive.Direction;
import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.ServoController;
import com.scottsbots.core.navigation.BasicNavigation;

public class DeadReckoningSample extends BasicNavigation {

	public DeadReckoningSample(JDrive drive) {
		super(drive);
	}

	public static void main(String[] args) throws Exception {
		JSerialPort serialPort = SingleSerialPort.getInstance(0);
		ServoController ssc = new ServoController(serialPort);
		BasicDiffDriveSample sampleDrive = new BasicDiffDriveSample(ssc);
		DeadReckoningSample deadReckon = new DeadReckoningSample(sampleDrive);
		
		int oneMeter = 2500;
		int nintyDegrees = 800;
		
		deadReckon.rawMove(Direction.FORWARD,oneMeter);
		deadReckon.rawMove(Direction.PIVOT_RIGHT,nintyDegrees);
		deadReckon.rawMove(Direction.FORWARD,oneMeter);
		deadReckon.rawMove(Direction.PIVOT_RIGHT,nintyDegrees);
		deadReckon.rawMove(Direction.FORWARD,oneMeter);
		deadReckon.rawMove(Direction.PIVOT_RIGHT,nintyDegrees);
		deadReckon.rawMove(Direction.FORWARD,oneMeter);
	}

}
