package com.scottpreston.javarobot.chapter6;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class ImageViewer extends SimpleSwing {

	// to hold image
	private BufferedImage currentImage;
	public static final long serialVersionUID = 1;
	
	// constructor for buffered image
	public ImageViewer(BufferedImage bimg) {
		setTitle("ImageViewer");
		currentImage = bimg;
		init();
	}
	// constructor for filename
    public ImageViewer(String fileName) throws Exception{
        setTitle("ImageViewer - " + fileName);
        // get file
        FileInputStream fis = new FileInputStream(fileName);
        // convert jpec to buffered image
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
        currentImage = decoder.decodeAsBufferedImage();
        init();
    }
    
    public void init() {

        int w = currentImage.getWidth();
        int h = currentImage.getHeight();
        ImagePanel imagePanel =  new ImagePanel(w,h);
        
        // set size of the window
        setSize(w + 8, h+35);
        // add imagePanel
        getContentPane().add(imagePanel,BorderLayout.CENTER);
        // make visible
        setVisible(true);
        // in case this is overloaded later
        imagePanel.setImage(currentImage);
    	
    }

    public static void main(String[] args) {
    	try {
    		ImageViewer imageViewer = new ImageViewer("sample_images/stonehenge.jpg");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
}