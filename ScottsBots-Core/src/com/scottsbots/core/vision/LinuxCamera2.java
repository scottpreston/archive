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
import java.awt.image.WritableRaster;

import com.scottsbots.core.JCamera;


public class LinuxCamera2 implements JCamera {

	 static {
		 System.load("/usr/lib/linuxcamera2.so");
		 System.out.println("loaded linuxcamera2.so");
	 }

	public LinuxCamera2(String device) {
		//System.load("/lib/linuxcamera_" + device + ".so");
		init("/dev/" + device);
	}

	public LinuxCamera2() {		
		init("/dev/video1");
	}

	public native byte[] getNativeImage();

	public native void init(String device);

	public BufferedImage getImage() {
		return createBufferedImage(getNativeImage());
	}

	private BufferedImage createBufferedImage(byte[] bytes) {
		int height = 240;
		int width = 320;
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = img.getRaster();
		int[] a = new int[bytes.length];
		for (int b = 0; b < bytes.length; b++) {
			a[b] = (int) bytes[b];
		}
		raster.setPixels(0, 0, width, height, a);
		img.setData(raster);
		return img;
	}

	public static void main(String[] args) {

	}

}
