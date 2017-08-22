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

import java.util.Map;

import com.scottsbots.core.JCompass;
import com.scottsbots.core.JDrive;
import com.scottsbots.core.JDrive.Direction;
import com.scottsbots.core.JSonar;
import com.scottsbots.core.utils.Utils;

public class BasicNavigation {

	private JDrive drive;
	private JCompass compass;
	private Map<String, JSonar> sonars;

	public BasicNavigation(JDrive drive) {
		this.drive = drive;
	}

	public BasicNavigation(JDrive drive, JCompass compass) {
		this.drive = drive;
		this.compass = compass;
	}
	
	public BasicNavigation(JDrive drive, Map<String, JSonar> sonars) {
		this.drive = drive;
		this.sonars = sonars;
	}

	public BasicNavigation(JDrive drive, JCompass compass,
			Map<String, JSonar> sonars) {
		this.drive = drive;
		this.compass = compass;
		this.sonars = sonars;
	}

	public void rawMove(Direction dir) {
		if (dir == Direction.FORWARD)
			drive.forward();
		if (dir == Direction.REVERSE)
			drive.reverse();
		if (dir == Direction.PIVOT_LEFT)
			drive.pivotLeft();
		if (dir == Direction.PIVOT_RIGHT)
			drive.pivotRight();
		if (dir == Direction.STOP)
			drive.stop();
	}

	public void rawMove(Direction dir, int duration) {
		if (dir == Direction.FORWARD)
			drive.forward(duration);
		if (dir == Direction.REVERSE)
			drive.reverse(duration);
		if (dir == Direction.PIVOT_LEFT)
			drive.pivotLeft(duration);
		if (dir == Direction.PIVOT_RIGHT)
			drive.pivotRight(duration);
		if (dir == Direction.STOP)
			drive.stop();
	}

	public void changeHeading(int newHeading) throws Exception {
		if (compass == null) {
			throw new Exception ("Compass is not defined.");
		}

		Utils.log("new heading is " + newHeading);

		int accuracy = 5;
		int turnSize = 1000;

		while (true) {
			// get compass
			Utils.pause(50);
			int currentHeading = -1;
			try {
				currentHeading = compass.getHeading();
			} catch (Exception e) {
				Utils.log("unable to obtain heading....");
				break;
			}
			// get relative heading from compass to where want to go
			int relHeading = currentHeading - newHeading;

			// adjust for negative
			if (relHeading < 0) {
				relHeading = 360 + relHeading;
			}
			Utils.log("relative heading is " + relHeading);

			// if within bounds, stop
			if (relHeading <= accuracy || relHeading >= 360 - accuracy) {
				drive.stop();
				break;
			}

			// turn for a second left
			if (relHeading < 180 && relHeading > 15) {
				drive.pivotLeft(turnSize);
			} else if (relHeading >= 180 && relHeading < 345) {
				drive.pivotRight(turnSize);
			} else if (relHeading >= 345) {
				// turn less when you are close
				drive.pivotRight(250);
			} else if (relHeading <= 15) {
				drive.pivotLeft(250);
			}
		}
	}

	// motion vector is in seconds
	public void move(MotionVector vect) throws Exception {
		// change heading
		Utils.log("MV=" + vect.toString());
		changeHeading(vect.heading);
		// move fwd or reverse
		if (vect.magintude > 0) {
			drive.forward((int) (vect.magintude * 1000));
		} else if (vect.magintude < 0) {
			drive.reverse((int) (-vect.magintude * 1000));
		}
	}

	public void forwardTilObstical(String sonarName, int distance) throws Exception {
		drive.forward();
		Utils.pause(50); // just pause enough time for serial signals to
		while (!sonarHit(sonarName, distance)) {
			Utils.pause(50);
		}
		drive.stop();
	}
	
	
	private boolean sonarHit(String sonarName, int distance) throws Exception {
		if (sonars == null || !sonars.containsKey(sonarName)) {
			throw new Exception("Sonar does not exist for key {" + sonarName + "}");
		}
		try {
			if (sonars.get(sonarName).ping() < distance) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true; // assume if an exception something is in the way

	}

}
