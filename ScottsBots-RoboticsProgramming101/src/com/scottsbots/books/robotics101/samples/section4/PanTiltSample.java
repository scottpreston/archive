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
import com.scottsbots.core.motion.PanTilt;
import com.scottsbots.core.motion.ServoConfig;
import com.scottsbots.core.utils.Utils;

public class PanTiltSample extends PanTilt{

	public PanTiltSample(JSscDevice ssc, ServoConfig panCfg, ServoConfig tiltCfg)
			throws Exception {
		super(ssc, panCfg, tiltCfg);
	}

	public static void main(String[] args) throws Exception{
		JSerialPort serialPort = SingleSerialPort.getInstance(0);
		ServoController ssc = new ServoController(serialPort);
		// Pan Tilt2 Configuration {servo,min,center,max}
		ServoConfig panCfg = new ServoConfig(2,10,125,250);
		ServoConfig tiltCfg = new ServoConfig(3,10,125,250);
		PanTiltSample panTiltSample = new PanTiltSample(ssc, panCfg, tiltCfg);
		panTiltSample.reset();
		Utils.pause(1000);
		panTiltSample.moveDown();
		Utils.pause(1000);
		panTiltSample.moveUp();
		Utils.pause(1000);
		panTiltSample.reset();
		Utils.pause(1000);
		panTiltSample.moveLeft();
		Utils.pause(1000);
		panTiltSample.moveRight();
		Utils.pause(1000);
		panTiltSample.reset();
	}
}
