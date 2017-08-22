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

package com.scottsbots.books.robotics101.samples.section2;

import com.scottsbots.core.comm.SingleSerialPort;
import com.scottsbots.core.controller.ServoController;
import com.scottsbots.core.utils.Utils;

public class Lm32MovementSample {

	public static void main(String[] args) throws Exception {
		ServoController lm32 = new ServoController(SingleSerialPort.getInstance(1));
		lm32.move(0, 127);
		Utils.pause(1000);
		lm32.move(0, 0);
		Utils.pause(1000);
		lm32.move(0, 127);
	}

}
