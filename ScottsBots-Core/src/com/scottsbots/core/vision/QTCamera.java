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

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;

import quicktime.QTRuntimeException;
import quicktime.QTRuntimeHandler;
import quicktime.QTSession;
import quicktime.qd.PixMap;
import quicktime.qd.QDGraphics;
import quicktime.qd.QDRect;
import quicktime.std.StdQTConstants;
import quicktime.std.sg.SGVideoChannel;
import quicktime.std.sg.SequenceGrabber;
import quicktime.util.RawEncodedImage;

import com.scottsbots.core.JCamera;
import com.scottsbots.core.utils.Utils;

public class QTCamera implements JCamera {

	private SequenceGrabber grabber;
	private SGVideoChannel channel;
	private RawEncodedImage rowEncodedImage;

	private int width;
	private int height;
	private int videoWidth;

	private int[] pixels;
	private BufferedImage image;
	private WritableRaster raster;
	
	public QTCamera() {
		init(320,240);
	}

	public void init(int height, int width) {
		this.width = width;
        this.height = height;
        try {
            QTSession.open();
            QDRect bounds = new QDRect(width, height);
            QDGraphics graphics = new QDGraphics(bounds);
            grabber = new SequenceGrabber();
            grabber.setGWorld(graphics, null);
            channel = new SGVideoChannel(grabber);
            channel.setBounds(bounds);
            channel.setUsage(StdQTConstants.seqGrabPreview);
            channel.settingsDialog();
            grabber.prepare(true, false);
            grabber.startPreview();
            PixMap pixMap = graphics.getPixMap();
            rowEncodedImage = pixMap.getPixelData();

            videoWidth = width + (rowEncodedImage.getRowBytes() - width * 4) / 4;
            pixels = new int[videoWidth * height];
            image = new BufferedImage(
                videoWidth, height, BufferedImage.TYPE_INT_RGB);
            raster = WritableRaster.createPackedRaster(DataBuffer.TYPE_INT,
                videoWidth, height,
                new int[] { 0x00ff0000, 0x0000ff00, 0x000000ff }, null);
            raster.setDataElements(0, 0, videoWidth, height, pixels);
            image.setData(raster);
            QTRuntimeException.registerHandler(new QTRuntimeHandler() {
                public void exceptionOccurred(
                        QTRuntimeException e, Object eGenerator,
                        String methodNameIfKnown, boolean unrecoverableFlag) {
                    System.out.println("what should i do?");
                }
            });
        } catch (Exception e) {
        	e.printStackTrace();
            QTSession.close();
           // throw e;
        }
	}
	 public void dispose() {
	        try {
	            grabber.stop();
	            grabber.release();
	            grabber.disposeChannel(channel);
	            image.flush();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            QTSession.close();
	        }
	    }

	    public int getWidth() {
	        return width;
	    }

	    public int getHeight() {
	        return height;
	    }

	    public int getVideoWidth() {
	        return videoWidth;
	    }

	    public int getVideoHeight() {
	        return height;
	    }

	    public void getNextPixels(int[] pixels) throws Exception {
	        grabber.idle();
	        rowEncodedImage.copyToArray(0, pixels, 0, pixels.length);
	    }

	    public Image getNextImage() throws Exception {
	        grabber.idle();
	        rowEncodedImage.copyToArray(0, pixels, 0, pixels.length);
	        raster.setDataElements(0, 0, videoWidth, height, pixels);
	        image.setData(raster);
	        return image;
	    }
	
	public BufferedImage getImage() {
		
		BufferedImage bImg = null;
		try {
		bImg = (BufferedImage)getNextImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bImg;
	}

	public static void main(String[] args) {
		QTCamera camera = new QTCamera();
		Utils.savePic(camera.getImage(), "/Users/scott/temp/test.jpg");

	}

}
