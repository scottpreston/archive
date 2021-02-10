package com.scottpreston.javarobot.chapter6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.Arrays;

import javax.swing.JFrame;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class ColorGramCalibration extends JFrame {

    // image to calibrate
    private BufferedImage currentImage;
    // panel to hold image
    private ImagePanel imagePanel;
    // count of colors
    private int maxCount = 0;
    // values of histogram mean values
    private int[] meanValues;
    // current best ColorGram
    private ColorGram bestColorGram = new ColorGram();
    // mean values for color components
    private int redAvg = 0;
    private int greenAvg = 0;
    private int blueAvg = 0;
    // initial threshhold
    private double threshhold = .95;
    private ImageProcessor imageProcessor = new ImageProcessor();
    
    public static final long serialVersionUID = 1;
    
    public ColorGramCalibration(String fileName) throws Exception {
        init(fileName, true);
    }
    
    public ColorGramCalibration(String fileName, boolean gui) throws Exception {
        init(fileName, gui);
    }
    
    private void init(String fileName, boolean toShow) throws Exception{
        setTitle("ColorGram Calibration");
        FileInputStream fis = new FileInputStream(fileName);
        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
        currentImage = decoder.decodeAsBufferedImage();
        // get imporant part of image, not background which is white
        currentImage = imageProcessor.threshold(currentImage, 0, 150, true);
        // gets mean values
        meanValues = imageProcessor.getMean(currentImage);
        // used later
        redAvg = meanValues[0];
        greenAvg = meanValues[1];
        blueAvg = meanValues[2];

        // init panel
        imagePanel = new ImagePanel(currentImage.getWidth(), currentImage.getHeight());

        // set frame properties
        WindowUtilities.setNativeLookAndFeel();
        addWindowListener(new ExitListener());
        setBackground(Color.BLACK); // gets image
        setSize(currentImage.getWidth() + 8, currentImage.getHeight() + 30);
        getContentPane().add(imagePanel, BorderLayout.CENTER);
        if (toShow) {
            setVisible(true);
        }
    }
    
    // processing called from optimize methods
    private void doProcessing(ColorGram cg) {
        // get maximum color ratio count for image and colorgram passed
        int max = imageProcessor.colorRatioCount(currentImage, cg);
        // if zero initialize count
        if (maxCount == 0) {
            maxCount = max;
        }
        // get threshold for colors to be counted
        double maxThresh = maxCount * threshhold;
        // if current color count greater than threshhold, set as best colorgram
        if (max > maxThresh) {
            currentImage = imageProcessor.colorRatio(currentImage, cg);
            // since cg is changing and by reference
            bestColorGram = (ColorGram) cg.clone();
        }
        // set image
        imagePanel.setImage(currentImage);

    }

    
    // move primary color minumum value up
    public void optimizeMin(Color color) {
        int min = 0;
        for (min = 0; min < 256; min++) {
            ColorGram tempColorGram = new ColorGram();
            int[] rgb = null;
            // checks to see what color is primary
            if (color.equals(Color.RED)) {
                rgb = new int[] { min, 0, 0 };
            }
            if (color.equals(Color.GREEN)) {
                rgb = new int[] { 0, min, 0 };
            }
            if (color.equals(Color.BLUE)) {
                rgb = new int[] { 0, 0, min };
            }
            // adjust colorgram
            tempColorGram.setMins(rgb);
            // process colorgram in image
            doProcessing(tempColorGram);
        }
    }

    // move maximum of primary color down to threshold
    public void optimizeMax(Color color) {

        // reset max count
        maxCount = 0;
        // make sure I start with copy of current best colorgram (getting min
        // value)
        ColorGram tempColorGram = (ColorGram) getBestColorGram().clone();
        int max = 255;
        for (max = 255; max > 0; max--) {
            int[] rgb = null;
            if (color.equals(Color.RED)) {
                rgb = new int[] { max, 255, 255 };
            }
            if (color.equals(Color.GREEN)) {
                rgb = new int[] { 255, max, 255 };
            }
            if (color.equals(Color.BLUE)) {
                rgb = new int[] { 255, 255, max };
            }
            tempColorGram.setMaxs(rgb);
            doProcessing(tempColorGram);
        }
    }

    // get ratio of two colors
    public void optmizeRatio(Color primaryColor, Color secondaryColor) {
        // get copy of current best colorgram
        ColorGram tempColorGram = (ColorGram) getBestColorGram().clone();
        // value of ratio
        int value = 0;
        // what color (r,g,b)
        int column = 0;
        // what min/max value of component r,g,b
        int row = 0;
        // move values from 0 to 255
        for (value = 0; value < 255; value++) {
            if (primaryColor.equals(Color.RED)) {
                column = 1;
            }
            if (primaryColor.equals(Color.GREEN)) {
                column = 2;
            }
            if (primaryColor.equals(Color.BLUE)) {
                column = 3;
            }
            if (secondaryColor.equals(Color.RED)) {
                row = 2;
            }
            if (secondaryColor.equals(Color.GREEN)) {
                row = 4;
            }
            if (secondaryColor.equals(Color.BLUE)) {
                row = 6;
            }
            tempColorGram.setRatio(column, row, -value);
            doProcessing(tempColorGram);
        }
    }

    // optimization
    public void optimize() {
        // sort values getting order of most color, 2nd and 3rd
        Arrays.sort(meanValues);
        Color[] colors = new Color[3];
        // correct in case they are equal.
        if (meanValues[0] == meanValues[1]) {
            meanValues[1]++;
        }
        if (meanValues[0] == meanValues[2]) {
            meanValues[2]++;
        }
        if (meanValues[1] == meanValues[2]) {
            meanValues[2]++;
        }
        
        for (int i = 0; i < 3; i++) {
            if (meanValues[i] == redAvg) {
                colors[2 - i] = Color.RED;
            }
            if (meanValues[i] == greenAvg) {
                colors[2 - i] = Color.GREEN;
            }
            if (meanValues[i] == blueAvg) {
                colors[2 - i] = Color.BLUE;
            }
        }
        // go in this order
        // i want most of primary color
        threshhold = .95;
        optimizeMin(colors[0]);
        System.out.println("done min");
        optimizeMax(colors[0]);
        System.out.println("done max");
        // i don't want much of 2nd and 3rd colors
        threshhold = .5;
        optmizeRatio(colors[0], colors[1]);
        System.out.println("done ratio 1");
        optmizeRatio(colors[0], colors[2]);
        System.out.println("done ratio 2");
    }

    public ColorGram getBestColorGram() {
        return bestColorGram;
    }

    // sample program
    public static void main(String[] args) {
        try {
            // load image
            ColorGramCalibration cg2 = new ColorGramCalibration(
                    "sample_images//coke.jpg", true);
            // optmize it
            cg2.optimize();
            // print colorgram for cut-paste if needed
            System.out.println(cg2.getBestColorGram().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}