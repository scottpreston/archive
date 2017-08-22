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

import java.util.HashMap;
import java.util.Map;

import com.scottsbots.books.robotics101.samples.section2.MicroControllerSample;
import com.scottsbots.books.robotics101.samples.section2.SonarSample;
import com.scottsbots.core.JDrive;
import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.JSonar;
import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.ServoController;
import com.scottsbots.core.navigation.BasicNavigation;

public class SonarAssistedSample  extends BasicNavigation{
	
	public SonarAssistedSample(JDrive drive, Map<String, JSonar> sonars) {
		super(drive, sonars);
	}

	public static void main(String[] args) throws Exception {
		JSerialPort serialPort = SingleSerialPort.getInstance(0);
		ServoController ssc = new ServoController(serialPort);
		BasicDiffDriveSample sampleDrive = new BasicDiffDriveSample(ssc);
		MicroControllerSample sampleMicro = new MicroControllerSample(
				SingleSerialPort.getInstance(1));
		String frontSonar = "frontSonar";
		JSonar sonar = new SonarSample(sampleMicro,frontSonar);
		Map<String, JSonar> map = new HashMap<String, JSonar>();
		map.put(frontSonar,sonar);
		SonarAssistedSample sonarAssistedSample = new SonarAssistedSample(sampleDrive, map);
		int safeDistance = 24; // inches
		sonarAssistedSample.forwardTilObstical(frontSonar, safeDistance);
	}

}
