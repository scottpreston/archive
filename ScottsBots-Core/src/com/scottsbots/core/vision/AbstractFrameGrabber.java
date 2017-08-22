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

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.control.FormatControl;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.VideoFormat;
import javax.media.util.BufferToImage;
/*
 * Frame grabber class for the Java Media Framework
 */
public abstract class AbstractFrameGrabber {

	private Player player;
	public static final String DEFAULT_URL = "vfw://0";
	public static final String RGB_ENCODING = "rgb";
	public static final String YUV_ENCODING = "yuv";

	public AbstractFrameGrabber() throws Exception {
		player = Manager.createRealizedPlayer(new MediaLocator(DEFAULT_URL));
		startPlayer(RGB_ENCODING, 320, 240);
	}

	public AbstractFrameGrabber(String url) throws Exception {
		player = Manager.createRealizedPlayer(new MediaLocator(url));
		startPlayer(RGB_ENCODING, 320, 240);
	}

	public AbstractFrameGrabber(String url, String encoding, int width, int height)
			throws Exception {
		player = Manager.createRealizedPlayer(new MediaLocator(url));
		startPlayer(encoding,width,height);
	}

	public AbstractFrameGrabber(String encoding, int width, int height) throws Exception {
		player = Manager.createRealizedPlayer((getMediaLocator(encoding, width,height)));
		startPlayer(encoding,width,height);
	}
	
	private void startPlayer(String encoding, int width, int height) throws Exception {
		System.out.println("starting player...");
		FormatControl formatControl = (FormatControl) player.getControl("javax.media.control.FormatControl");
		formatControl.setFormat(setFormat(encoding, width, height));
		player.start();
		System.out.println("waiting 3500ms....");
		Thread.sleep(3500);
	}

	private MediaLocator getMediaLocator(String encoding, int width, int height) {
		MediaLocator ml = null;
		// returns first video device
		try {
			Vector deviceList = CaptureDeviceManager.getDeviceList(null);
			System.out.println(deviceList.size());
			for (int i = 0; i < deviceList.size(); i++) {
				CaptureDeviceInfo devInfo = (CaptureDeviceInfo) deviceList
						.elementAt(i);
				String name = devInfo.getName();
				System.out.println("Device Name : " + name);
				ml = devInfo.getLocator();
				System.out.println("Media Locator : '" + ml.toString() + "'");
				Format[] formats = devInfo.getFormats();
				for (int j = 0; j < formats.length; j++) {
					Format aFormat = formats[j];
					if (aFormat instanceof VideoFormat) {
						Dimension dim = ((VideoFormat) aFormat).getSize();
						System.out.println("Video Format " + j + " : "
								+ aFormat.getEncoding() + ", " + dim.width
								+ " x " + dim.height);
						if (aFormat.getEncoding().equalsIgnoreCase(encoding)
								&& dim.width == width && dim.height == height) {
							return ml; // return now don't do anything else.
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// System.exit(0);
		}
		return ml;
	}

	public Image getAwtImage() throws Exception {
		FrameGrabbingControl frameGrabber = (FrameGrabbingControl) player
				.getControl("javax.media.control.FrameGrabbingControl");
		Buffer buf = frameGrabber.grabFrame();
		Image img = (new BufferToImage((VideoFormat) buf.getFormat())
				.createImage(buf));

		if (img == null) {
			// throw new Exception("Image Null");
			System.exit(1);
		}

		return img;
	}

	public Format setFormat(String encoding, int width, int height) {
		Vector deviceList = CaptureDeviceManager.getDeviceList(null);
		for (int i = 0; i < deviceList.size(); i++) {
			CaptureDeviceInfo devInfo = (CaptureDeviceInfo) deviceList
					.elementAt(i);
			Format[] formats = devInfo.getFormats();
			for (int j = 0; j < formats.length; j++) {
				Format aFormat = formats[j];
				if (aFormat instanceof VideoFormat) {
					Dimension dim = ((VideoFormat) aFormat).getSize();
					System.out.println("Video Format " + j + " : "
							+ formats[j].getEncoding() + ", " + dim.width
							+ " x " + dim.height);
					if (aFormat.getEncoding().equalsIgnoreCase(encoding)
							&& dim.width == width && dim.height == height) {
						return aFormat;
					}
				}
			}

		}
		return null;
	}

	public BufferedImage getBufferedImage() throws Exception {
		return (BufferedImage) getAwtImage();
	}

	public void close() throws Exception {
		player.close();
		player.deallocate();
	}
}