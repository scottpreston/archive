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

package com.scottsbots.core.utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Generic class for working with HTTP related resources either txt,xml, or images
 * 
 * @author scott
 *
 */
public class HttpGet {

	private String baseUrl = "";

	public HttpGet() {

	}

	public HttpGet(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String get(String urlString) {
		// construct url
		try {
			urlString = URLEncoder.encode(urlString);
			URL url = new URL(baseUrl + urlString);
			// create input buffer for reading
			BufferedReader in = new BufferedReader(new InputStreamReader(url
					.openStream()));
			// temp string
			String str;
			// create buffer to put information in
			StringBuffer buffer = new StringBuffer();
			// read until end of file
			while ((str = in.readLine()) != null) {
				// since reading line add line feed.
				buffer.append(str + "\n");
			}
			// close buffer
			in.close();
			// return as string
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	// get image from internet via URL
	public static BufferedImage getImage(String urlString) throws Exception {
		// construct URL
		URL url = new URL(urlString);
		// get AWT image
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		// cast to BufferedImage
		return (BufferedImage) image;
	}

	// get text from inernet via URL
	public static String getText(String urlString) throws Exception {
		// construct url
		URL url = new URL(urlString);
		// create input buffer for reading
		BufferedReader in = new BufferedReader(new InputStreamReader(url
				.openStream()));
		// temp string
		String str;
		// create buffer to put information in
		StringBuffer buffer = new StringBuffer();
		// read until end of file
		while ((str = in.readLine()) != null) {
			// since reading line add line feed.
			buffer.append(str + "\n");
		}
		// close buffer
		in.close();
		// return as string
		return buffer.toString();
	}

	public static void download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(address);
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
				numWritten += numRead;
			}
			Utils.logger(localFileName + "\t" + numWritten);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

}