package com.scottpreston.javarobot.chapter6;

import java.awt.Dimension;
import java.util.Vector;

import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.format.VideoFormat;

public class FindCamera {

	public static void main(String[] args) {

		try {
			Vector deviceList = CaptureDeviceManager.getDeviceList(null);
			for (int i = 0; i < deviceList.size(); i++) {
				CaptureDeviceInfo devInfo = (CaptureDeviceInfo) deviceList
						.elementAt(i);
				String name = devInfo.getName();
				System.out.println("Device Name : " + name);
				Format[] formats = devInfo.getFormats();
				for (int j = 0; j < formats.length; j++) {
					Format aFormat = formats[j];
					if (aFormat instanceof VideoFormat) {
						Dimension dim = ((VideoFormat) aFormat).getSize();
						System.out.println("Video Format " + j + " : "
								+ aFormat.getEncoding() + ", " + dim.width
								+ " x " + dim.height);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}

	}

}