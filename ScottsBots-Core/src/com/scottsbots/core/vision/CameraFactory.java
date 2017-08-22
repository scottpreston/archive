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

import com.scottsbots.core.JCamera;

/**
 * Factory class for creating a camera either in Windows (requiring JMF) or Ubuntu Linux (requiring linuxcamera.so)
 * 
 * @author scott
 *
 */

public class CameraFactory {

	public static final int JMF_WIN_CAMERA = 0;
	public static final int LINUX_CAMERA_VIDEO_0 = 1;
	public static final int LINUX_CAMERA_VIDEO_1 = 2;
	public static final int LINUX_CAMERA_VIDEO_2 = 3;
	public static final int MAC_CAMERA = 5;
	public static final String VFW_0 = "vfw://0";

	public static JCamera getInstance() {
		String os = System.getProperty("os.name").substring(0, 3);
		if (os.equalsIgnoreCase("win")) {
			return getInstance(JMF_WIN_CAMERA);
		} else if (os.equalsIgnoreCase("lin")) {
			return getInstance(LINUX_CAMERA_VIDEO_0);
		} else if (os.equalsIgnoreCase("lin")) {
			return getInstance(MAC_CAMERA);	
		} else {
			System.out.println("OS Not supported!!!");
			System.exit(1);
		}
		// will never get here but won't compile...
		return null;
	}

	public static JCamera getInstance(int camera) {
		try {
			if (camera == JMF_WIN_CAMERA) {
				return new JMFCamera(VFW_0);
			}
			if (camera == LINUX_CAMERA_VIDEO_0) {
				return new V4LCamera(V4LCamera.DEVICE_VIDEO_0);
			}
			if (camera == LINUX_CAMERA_VIDEO_1) {
				return new V4LCamera(V4LCamera.DEVICE_VIDEO_1);
			}
			if (camera == LINUX_CAMERA_VIDEO_2) {
				return new V4LCamera(V4LCamera.DEVICE_VIDEO_2);
			}
			if (camera == MAC_CAMERA) {
				//return new C
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

}
