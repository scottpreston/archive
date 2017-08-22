package com.scottsbots.core.vision;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import au.edu.jcu.v4l4j.CaptureCallback;
import au.edu.jcu.v4l4j.FrameGrabber;
import au.edu.jcu.v4l4j.V4L4JConstants;
import au.edu.jcu.v4l4j.VideoDevice;
import au.edu.jcu.v4l4j.VideoFrame;
import au.edu.jcu.v4l4j.exceptions.V4L4JException;

import com.scottsbots.core.JCamera;

public class V4LCamera implements JCamera, CaptureCallback {

	public static Dimension DIM_320_240 = new Dimension(320, 240);
	public static Dimension DIM_640_480 = new Dimension(640, 480);

	public static String DEVICE_VIDEO_0 = "/dev/video0";
	public static String DEVICE_VIDEO_1 = "/dev/video1";
	public static String DEVICE_VIDEO_2 = "/dev/video2";
	private static int std = V4L4JConstants.STANDARD_WEBCAM;
	private static int channel = 1;

	private VideoDevice videoDevice;
	private FrameGrabber frameGrabber;
	private BufferedImage img;

	public V4LCamera() {
		init(DEVICE_VIDEO_0, DIM_320_240);
	}

	public V4LCamera(String device) {
		init(device, DIM_320_240);
	}

	public V4LCamera(String device, Dimension d) {
		init(device, d);
	}

	private void init(String device, Dimension d) {
		try {
			videoDevice = new VideoDevice(device);
			frameGrabber = videoDevice.getJPEGFrameGrabber(d.width, d.height,
					channel, std, 95);
			frameGrabber.setCaptureCallback(this);
			frameGrabber.startCapture();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage() {
		try {
			while (img == null) {
				Thread.currentThread().sleep(10);
			}
		} catch (Exception e) {

		}
		return img;
	}

	public void nextFrame(VideoFrame frame) {
		img = frame.getBufferedImage();
	}

	@Override
	public void exceptionReceived(V4L4JException e) {
		e.printStackTrace();
	}

}
