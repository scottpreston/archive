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

package com.scottsbots.core.navigation;

public class MotionVector {

	public int heading = 0;
	public double magintude = 0;

	public MotionVector(int h, double seconds) {
		heading = h;
		magintude = seconds;
	}

	public MotionVector(String h, String seconds) throws Exception {
		heading = new Integer(h).intValue();
		magintude = new Double(seconds).doubleValue();
	}

	public String toString() {
		return "Heading: " + heading + " Seconds: " + magintude;
	}
}
