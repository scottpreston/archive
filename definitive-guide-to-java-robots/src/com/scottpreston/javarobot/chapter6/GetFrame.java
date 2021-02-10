package com.scottpreston.javarobot.chapter6;

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

public class GetFrame {

    private Player player;
    public static final String RGB_ENCODING = "rgb";
    public static final String YUV_ENCODING = "yuv";

    public GetFrame(String url) throws Exception{
    	init(url,RGB_ENCODING,320,240);
    }
    public GetFrame(String url, String encoding, int width, int height) throws Exception{
    	init(url,encoding,width,height);
    }

    private void init(String url, String encoding, int width, int height) throws Exception {
        player = Manager.createRealizedPlayer( new MediaLocator(url));
        // sets to larger format
        FormatControl formatControl = (FormatControl)player.getControl ( "javax.media.control.FormatControl" );
        formatControl.setFormat(setFormat(encoding,width,height));
        player.start();
        // Wait a few seconds for camera to initialise (otherwise img==null)
        Thread.sleep(2500);

    }
    public Image getAwtImage() throws Exception {
        FrameGrabbingControl frameGrabber = (FrameGrabbingControl) player
                .getControl("javax.media.control.FrameGrabbingControl");
        Buffer buf = frameGrabber.grabFrame();
        Image img = (new BufferToImage((VideoFormat) buf.getFormat())
                .createImage(buf));
        
        
        if (img == null) {
            //throw new Exception("Image Null");
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
					if (aFormat.getEncoding().equalsIgnoreCase(encoding) &&
							dim.width == width && dim.height == height) {
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

    public static void main(String[] args) {
        try {
            GetFrame getFrame = new GetFrame("vfw://0");
            BufferedImage img = getFrame.getBufferedImage();
            getFrame.close();
            ImageViewer viewer = new ImageViewer(img);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}