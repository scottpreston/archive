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

import com.scottsbots.core.JSerialPort;
import com.scottsbots.core.JSscDevice;
import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.ServoController;

/**
 * Generic class for a pan-tilt mechanism in robots.
 * @author scott
 * 
 */
public class PanTilt {

	private int panPos;
	private int tiltPos;
	private ServoConfig panConfig;
	private ServoConfig tiltConfig;

	// should set these to the best limits of your pan/tilt system
	// or if this class is extended, then you can set these manually. 

	// 3 millieconds at 9600 baud
	public static final int MIN_STEP_SIZE = 3;
	// 2 milliseconds for standard servo
	public static final int MIN_DELAY_SIZE = 2;

	// delay in milliseconds between move
	private int moveDelay = 50;
	
	// byte size of single step
	private int stepSize = MIN_STEP_SIZE;
	private int speed = 0;
	private JSscDevice ssc;
	private boolean inverted = false;

	public PanTilt(JSscDevice ssc, ServoConfig panConfig, ServoConfig tiltConfig) throws Exception {
		this.ssc = ssc;
		this.panConfig = panConfig;
		this.tiltConfig = tiltConfig;
	}


	public void setInverted(boolean invert) {
		this.inverted = invert;
	}


	// move both servos to positions
	private void move() throws Exception {
		pan(panPos);
		tilt(tiltPos);
	}

	// move both servos with input parameters
	// h = horizontal servo
	// v = vertical servo
	public void moveBoth(int h, int v) throws Exception {
		// set private fields
		panPos = h;
		tiltPos = v;
		// move
		move();
	}

	public void pan(int pos) throws Exception {
		// check to see if position within limits
		if (pos < panConfig.min || pos >  panConfig.max) {
			throw new Exception("Out of horizontal range.");
		}
		// set pos
		panPos = pos;
		// move
		ssc.move( panConfig.pin, pos);
	}

	public void panDegree(int angle) throws Exception {
		// check to see if angle is within limites of 0-180
		if (angle < 0 || angle > 180) {
			throw new Exception("Out of range, angle 0-180.");
		}
		// convert fraction of 255
		double theta = ((double) angle / 180) * 255.0;
		// move
		pan((int) theta);

	}

	public void tilt(int pos) throws Exception {
		// i.e. max down = 25 max up = 200
		if (inverted == false) {
			if (pos <  tiltConfig.min || pos >  tiltConfig.max) {
				throw new Exception("Out of vertical range. position = " + pos);
			}
		} else {
			if (pos >  tiltConfig.min || pos <  tiltConfig.max) {
				throw new Exception("Out of vertical range. position = " + pos);
			}
		}
		tiltPos = pos;
		ssc.move( tiltConfig.pin, pos);
	}

	public void tiltDegree(int angle) throws Exception {
		if (angle < 0 || angle > 180) {
			throw new Exception("Out of range, angle 0-180.");
		}
		double theta = ((double) angle / 180) * 255.0;
		tilt((int) theta);

	}

	// reset to neutral position
	public void reset() throws Exception {
		panPos =  panConfig.center;
		tiltPos =  tiltConfig.center;
		move();
	}

	// move right specific step size
	public void moveRight(int size) throws Exception {
		pan(panPos + size);
	}

	// move right current stepSize
	public void moveRight() throws Exception {
		moveRight(stepSize);
	}

	public void moveLeft(int size) throws Exception {
		pan(panPos - size);
	}

	public void moveLeft() throws Exception {
		moveLeft(stepSize);
	}

	public void moveUp(int size) throws Exception {
		tilt(tiltPos + size);
	}

	public void moveUp() throws Exception {
		moveUp(stepSize);
	}

	public void moveDown(int size) throws Exception {
		tilt(tiltPos - size);
	}

	public void moveDown() throws Exception {
		moveDown(stepSize);
	}
	public int getPanPos() {
		return panPos;
	}

	public void setPanPos(int panPos) {
		this.panPos = panPos;
	}

	public int getTiltPos() {
		return tiltPos;
	}

	public void setTiltPos(int tiltPos) {
		this.tiltPos = tiltPos;
	}

	

	public int getMoveDelay() {
		return moveDelay;
	}

	public void setMoveDelay(int moveDelay) {
		this.moveDelay = moveDelay;
	}

	public int getStepSize() {
		return stepSize;
	}

	public void setStepSize(int stepSize) {
		this.stepSize = stepSize;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isInverted() {
		return inverted;
	}
	
}
