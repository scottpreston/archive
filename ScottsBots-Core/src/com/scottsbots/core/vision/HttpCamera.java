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
import java.util.Date;

import com.scottsbots.core.JCamera;
import com.scottsbots.core.utils.Benchmark;
import com.scottsbots.core.utils.HttpGet;
import com.scottsbots.core.utils.Utils;

public class HttpCamera implements JCamera {

	private String imageUrl;

	public HttpCamera(String url) {
		imageUrl = url;
	}

	public BufferedImage getImage() {
		BufferedImage img = null;
		try {
			return  HttpGet.getImage(imageUrl + "?" + new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return img;

	}

	public static void main(String[] args) {
		HttpCamera cam = new HttpCamera("http://192.168.1.22/IMAGE.JPG");
		for (int i=0;i<25;i++) {
			Benchmark bench = new Benchmark();
			String testimg = "c:\\temp\\temp_" + i + ".jpg";
			bench.start();
			Utils.savePic(cam.getImage(), testimg);
			bench.end("done getting image");
			Utils.pause(200);
		}
	}

}
