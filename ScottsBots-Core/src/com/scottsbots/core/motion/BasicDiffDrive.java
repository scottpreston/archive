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

package com.scottsbots.core.motion;

import com.scottsbots.core.JDrive;
import com.scottsbots.core.JSscDevice;
import com.scottsbots.core.utils.Utils;

/**
 * Class used for differential drive robots. It has two speeds, fast and slow.
 * 
 * @author scott
 * 
 */

public class BasicDiffDrive implements JDrive {
	
	public static final int SPEED_FAST = 1;
	public static final int SPEED_SLOW = 2;

	private JSscDevice ssc;

	// default position
	private int currentRight = JSscDevice.NEUTRAL_127;
	private int currentLeft = JSscDevice.NEUTRAL_127;
	private int speed = SPEED_SLOW;
	
	private DriveConfiguration leftDrive;
	private DriveConfiguration rightDrive;

	public BasicDiffDrive(JSscDevice ssc, DriveConfiguration leftDrive, DriveConfiguration rightDrive) throws Exception {
		this.ssc = ssc;
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
	}

	public void setSpeedMode(int speed) {
		this.speed = speed;
	}
	
	private void move() throws Exception {
		Utils.log("l="+currentLeft + ",r=" + currentRight);
		ssc.move(leftDrive.servoPin, currentLeft);
		ssc.move(rightDrive.servoPin, currentRight);
	}

	public void move(int left, int right) {
		
		try {
			this.currentLeft = left;
			this.currentRight = right;
			move();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		move(leftDrive.neutral, rightDrive.neutral);
	}

	public void forward() {
		if (speed == SPEED_SLOW) {
			move(leftDrive.slowFwd, rightDrive.slowFwd);
		} else {
			move(leftDrive.fastFwd, rightDrive.fastFwd);
		}
	}

	public void forward(int ms) {
		forward();
		Utils.pause(ms);
		stop();
	}

	public void reverse() {
		if (speed == SPEED_SLOW) {
			move(leftDrive.slowRev, rightDrive.slowRev);
		} else {
			move(leftDrive.fastRev, rightDrive.fastRev);
		}
	}

	public void reverse(int ms) {
		reverse();
		Utils.pause(ms);
		stop();
	}

	public void pivotLeft() {
		move(leftDrive.slowRev, rightDrive.slowFwd);
	}

	public void pivotLeft(int ms) {
		pivotLeft();
		Utils.pause(ms);
		stop();
	}

	public void pivotRight() {
		move(leftDrive.slowFwd, rightDrive.slowRev);
	}

	public void pivotRight(int ms) {
		pivotRight();
		Utils.pause(ms);
		stop();
	}

	public void leftFwd() {
		move(leftDrive.slowFwd,rightDrive.neutral);
	}
	
	public void leftFwd(int ms) {
		leftFwd();
		Utils.pause(ms);
		stop();
	}

	public void leftRev() {
		move(leftDrive.slowRev,rightDrive.neutral);
	}
	
	public void leftRev(int ms) {
		leftRev();
		Utils.pause(ms);
		stop();
	}

	public void rightFwd() {
		move(leftDrive.neutral,rightDrive.slowFwd);
	}
	
	public void rightFwd(int ms) {
		rightFwd();
		Utils.pause(ms);
		stop();
	}

	public void rightRev() {
		move(leftDrive.neutral,rightDrive.slowRev);
	}
	
	public void rightRev(int ms) {
		rightRev();
		Utils.pause(ms);
		stop();
	}
	public int getCurrentRight() {
		return currentRight;
	}

	public void setCurrentRight(int currentRight) {
		this.currentRight = currentRight;
	}

	public int getCurrentLeft() {
		return currentLeft;
	}

	public void setCurrentLeft(int currentLeft) {
		this.currentLeft = currentLeft;
	}

	public DriveConfiguration getRightMotorConfig() {
		// TODO Auto-generated method stub
		return null;
	}

}