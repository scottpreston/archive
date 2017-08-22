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

package com.scottsbots.books.robotics101.samples.section3;

import java.awt.image.BufferedImage;

import com.scottsbots.core.JCamera;
import com.scottsbots.core.utils.Utils;
import com.scottsbots.core.vision.BasicImageProcessor;
import com.scottsbots.core.vision.HttpCamera;

public class SharpEdgeSample {

	public static void main(String[] args) {
		JCamera camera = new  HttpCamera("http://192.168.1.xxx/IMAGE.JPG");
		BufferedImage img = camera.getImage();
		img = BasicImageProcessor.sharpen(img);
		img = BasicImageProcessor.sobelGradMag(img);
		Utils.savePic(camera.getImage(), "sharpedge.jpg");
	}

}
