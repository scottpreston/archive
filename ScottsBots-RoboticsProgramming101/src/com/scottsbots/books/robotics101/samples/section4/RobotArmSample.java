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
import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.ServoController;
import com.scottsbots.core.motion.ServoConfig;
import com.scottsbots.core.utils.Utils;

public class RobotArmSample {

	private ServoController ssc;
	
	public static ServoConfig SHOULDER = new ServoConfig(4,0,127,255);
	public static ServoConfig ELBOW = new ServoConfig(5,0,127,255);
	public static ServoConfig WRIST = new ServoConfig(6,0,127,255);
	public static ServoConfig LEFT_PINCHER = new ServoConfig(7,0,127,255);
	public static ServoConfig RIGHT_PINCHER = new ServoConfig(7,0,127,255);
	
	public RobotArmSample(ServoController ssc) {
		this.ssc = ssc;
	}
	
	public void move(ServoConfig servo, int pos) throws Exception {
		ssc.move(servo.pin, pos);
	}
	
	public static void main(String[] args) throws Exception{
		JSerialPort serialPort = SingleSerialPort.getInstance(0);
		ServoController ssc = new ServoController(serialPort);
		RobotArmSample arm = new RobotArmSample(ssc);
		arm.move(SHOULDER, 100);
		Utils.pause(1000);
		arm.move(ELBOW, 100);
		Utils.pause(1000);
		arm.move(LEFT_PINCHER, 30);
		arm.move(RIGHT_PINCHER, 30);		
	}
}
