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

import com.scottsbots.core.JSscDevice;

public class ServoConfig {

	public int pin;
	public int min = JSscDevice.MIN;
	public int center = JSscDevice.NEUTRAL_127;
	public int max = JSscDevice.MAX_255;
	
	public ServoConfig(int pin) {
		this.pin = pin;
	}
	
	public ServoConfig(int pin, int min, int center, int max) {
		this.pin = pin;
		this.min = min;
		this.center = center;
		this.max = max;		
	}

}
