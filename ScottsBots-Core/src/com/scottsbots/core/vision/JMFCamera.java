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

package com.scottsbots.core.vision;

import java.awt.image.BufferedImage;

import com.scottsbots.core.JCamera;

/**
 * Wrapper for camera class using the Java Media Framework.
 * 
 * @author scott
 *
 */
public class JMFCamera extends AbstractFrameGrabber implements JCamera {

	public JMFCamera() throws Exception {
		super(DEFAULT_URL, RGB_ENCODING, 320, 240);
	}

	public JMFCamera(String url) throws Exception {
		super(url, RGB_ENCODING, 320, 240);
	}

	public JMFCamera(String url, String encoding, int width, int height)
			throws Exception {
		super(url, encoding, width, height);
	}

	public BufferedImage getImage() {
		try {
			return this.getBufferedImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
