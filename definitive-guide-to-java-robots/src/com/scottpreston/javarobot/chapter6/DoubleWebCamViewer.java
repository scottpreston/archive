package com.scottpreston.javarobot.chapter6;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class DoubleWebCamViewer extends WebCamViewer {

    // source / original image
    private ImagePanel srcPanel = new ImagePanel();
    // destination image
    private ImagePanel dstPanel = new ImagePanel();
    // filters (list of FilterParameters)
    private ArrayList filters = new ArrayList();
    public static final long serialVersionUID = 1;
    // construcor with no camera
    public DoubleWebCamViewer() throws Exception {
        super(DEFAULT_CAMERA);
        // seperate init method
        init2();
    }

    // constructor with camera name
    public DoubleWebCamViewer(String camera) throws Exception {
        super(camera);
        // seperate init method
        init2();
    }

    // common initialization block
    public void init2() throws Exception {
        setTitle("Double Webcam Viewer");
        // set frame properties
        this.setSize(648, 270);
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(srcPanel);
        box.add(dstPanel);
        // clear contents added in parent
        this.getContentPane().removeAll();
        // add new panels
        this.getContentPane().add(box);
        // show 
        setVisible(true);
    }

    // get picture where two panels are set and processing is called
    public void getPic() {
        try {
            BufferedImage bimg = getGetFrame().getBufferedImage();
            // image to left panel
            srcPanel.setImage(bimg);
            // image to right panel
            dstPanel.setImage(doProcessing(bimg));
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // add filters
    public void addFilter(FilterParameters filter) {
        filters.add(filter);
    }

    // processes all filters
    public BufferedImage doProcessing(BufferedImage bimg) {
        ImageProcessor imageProcessor = new ImageProcessor();
        for (int f = 0; f < filters.size(); f++) {
            FilterParameters parms = (FilterParameters) filters.get(f);
            parms.setImage(bimg);
            bimg = imageProcessor.process(parms);
        }
        return bimg;
    }

    // sample program with two filters
    public static void main(String[] args) {
        try {
            DoubleWebCamViewer webcam = new DoubleWebCamViewer();
            //FilterParameters resizeFilter = new FilterParameters(ImageProcessor.FILTER_RESIZE);
            //resizeFilter.addParameters(new Integer(160));
            //resizeFilter.addParameters(new Integer(120));
            //webcam.addFilter(resizeFilter);
            webcam.setFps(5);
            webcam.addFilter(new FilterParameters(ImageProcessor.FILTER_MOTION));
            webcam.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
