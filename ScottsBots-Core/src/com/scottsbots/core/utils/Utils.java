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

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * Generic utility class
 * 
 * @author scott
 * 
 */
public class Utils {

	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"MM/dd/yy - HH:mm:ss.SSS");
	private static SimpleDateFormat filenameFormatter = new SimpleDateFormat(
			"MMddyyyy_HHmmss");

	public static void pause(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void logger(String s) {
		System.out.println(formatter.format(new Date()) + " *** " + s);
	}

	public static void log(String s) {
		Utils.logger(s);
	}

	public static void loggerErr(String s) {
		System.err.println(formatter.format(new Date()) + " *** " + s);
	}

	public static void loggerOld(String s) {
		// now write a file
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("snagr.log",
					true));
			Date d = new Date();
			String dt = formatter.format(d);
			String outTxt = dt + " *** " + s + "\n";
			System.out.print(outTxt);
			out.write(outTxt);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static String toAscii(byte[] b) {

		StringBuffer s = new StringBuffer();
		if (b == null) {
			return "";
		}
		for (int i = 0; i < b.length; i++) {
			if (b[i] != 0) {
				char in = (char) b[i];
				s.append(in);
			}
		}
		return s.toString();
	}

	public static String byteToString(byte[] b) {

		StringBuffer s = new StringBuffer();
		if (b == null) {
			return "";
		}
		for (int i = 0; i < b.length; i++) {
			s.append(b[i] + ",");
		}
		return s.toString();
	}

	public static String replace(String str, String txt, String by) {

		if (isNull(str) == true || isNull(txt) == true) {
			return str;
		}

		int strLen = str.length();
		int txtLen = txt.length();
		int i = str.indexOf(txt);

		if (i == -1) {
			return str;
		}

		String newstr = str.substring(0, i) + by;

		if (i + txtLen < strLen) {
			newstr += replace(str.substring(i + txtLen, strLen), txt, by);
		}

		return newstr;
	}

	public static boolean isNull(String s) {

		if (String.valueOf(s).equals("null") || s.length() == 0) {
			return true;
		} else {
			return false;
		}

	}

	// Copies src file to dst file.
	// If the dst file does not exist, it is created
	public static void copy(String src, String dst) throws IOException {
		InputStream in = new FileInputStream(new File(src));
		OutputStream out = new FileOutputStream(new File(dst));

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	public static String streamToString(InputStream is) {
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String str = "";
			while ((str = in.readLine()) != null) {
				buffer.append(str);
			}
		} catch (IOException e) {
		}
		return buffer.toString();
	}

	public static String getTempDir() {
		String os = System.getProperty("os.name").substring(0, 3);
		if (os.equalsIgnoreCase("win")) {
			return "c:/temp";
		} else {
			return "/tmp";
		}
	}

	public static void printJavaEnv() {
		System.out.println("java.home=" + System.getProperty("java.home"));
		System.out.println("java.library.path="
				+ System.getProperty("java.library.path"));
		System.out.println("os.name=" + System.getProperty("os.name"));
		System.out.println("java.class.path="
				+ System.getProperty("java.class.path"));
	}

	public static String getEnvVars(String name) {
		String value = null;

		try {
			Process p = null;
			Properties envVars = new Properties();
			Runtime r = Runtime.getRuntime();
			String OS = System.getProperty("os.name").toLowerCase();
			if (OS.indexOf("windows 9") > -1) {
				p = r.exec("command.com /c set");
			} else if ((OS.indexOf("nt") > -1)
					|| (OS.indexOf("windows 2000") > -1)
					|| (OS.indexOf("windows xp") > -1)) {
				// thanks to JuanFran for the xp fix!
				p = r.exec("cmd.exe /c set");
			} else {
				// our last hope, we assume Unix (thanks to H. Ware for the fix)
				p = r.exec("env");
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				int idx = line.indexOf('=');
				String key = line.substring(0, idx);
				String v = line.substring(idx + 1);
				envVars.setProperty(key, v);
			}
			value = envVars.getProperty(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static void main(String[] args) {
		Utils.printJavaEnv();
	}

	public static void savePic(BufferedImage img, String fileName) {
		try {
			// open file
			File file = new File(fileName);
			// write JPG
			ImageIO.write(img, "jpg", file);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static BufferedImage getBufferedImageFromFile(String fileName)
			throws Exception {
		return ImageIO.read(new File(fileName));
	}

}