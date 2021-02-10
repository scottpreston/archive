package com.scottpreston.javarobot.chapter6;

import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;

public class WebCamViewer extends SimpleSwing {

    private Timer timer = new Timer();
	private GetFrame getFrame;
    private ImagePanel imagePanel;
    private int fps = 15;
    public static final String DEFAULT_CAMERA = "vfw://0";
    public static final long serialVersionUID = 1;
    
    public WebCamViewer() throws Exception {
        init(DEFAULT_CAMERA, GetFrame.RGB_ENCODING,320,240);
    }

    public WebCamViewer(String camera) throws Exception{
        init(camera, GetFrame.RGB_ENCODING,320,240);
    }
    public WebCamViewer(String camera, String encoding, int width, int height) throws Exception{
        init(camera,encoding, width,height);
    }
    private void init(String camera, String encoding, int w, int h) throws Exception{
        setTitle("WebCamViewer");
    	// creates frame grabber
        getFrame = new GetFrame(camera,encoding,w,h);
        imagePanel =  new ImagePanel(w,h);        
        // set size of the window
        setSize(w + 8, h+35);
        // add imagePanel
        getContentPane().add(imagePanel,BorderLayout.CENTER);
        // make visible
        setVisible(true);
    }

    // start the camera frame capture
    public void start() {
        
        timer.schedule(new TimerTask() {
            public void run() {
                getPic();
            }
        }, 200, (int)(1000 / fps));
    }
    
    // stop the camera frame capture
    public void stop() throws Exception{
        timer.cancel();
    }
    
    // get frame from GetFrame
    public void getPic() {
        try {
            // set to image panel and repaint called from ImagePanel
            imagePanel.setImage(getFrame.getBufferedImage());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    // get the framegrabber
    public GetFrame getGetFrame() {
        return getFrame;
    }
    // get frames per second
    public int getFps() {
        return fps;
    }
    //sets frames per second
    public void setFps(int fps) {
        this.fps = fps;
    }

    
    public static void main(String[] args) {
        try {

            WebCamViewer webcam = new WebCamViewer(WebCamViewer.DEFAULT_CAMERA,GetFrame.RGB_ENCODING,640,480);
            webcam.start();
            //Utils.pause(2000);
            //webcam.stop();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
