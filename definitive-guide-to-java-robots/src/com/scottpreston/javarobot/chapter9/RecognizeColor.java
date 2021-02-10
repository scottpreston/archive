package com.scottpreston.javarobot.chapter9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter5.MicrosoftVoice;
import com.scottpreston.javarobot.chapter6.ColorGram;
import com.scottpreston.javarobot.chapter6.ColorGramCalibration;
import com.scottpreston.javarobot.chapter6.DoubleWebCamViewer;
import com.scottpreston.javarobot.chapter6.ImageProcessor;

public class RecognizeColor extends DoubleWebCamViewer {

    private MicrosoftVoice voice;
    private ArrayList colorObjects = new ArrayList();
    private ImageProcessor imageProcessor = new ImageProcessor();
    public static final long serialVersionUID = 1;

    public RecognizeColor() throws Exception {
        init();
        colorObjects.add(new ColorObject("coke", ColorGram.COKE));
        colorObjects.add(new ColorObject("7up", ColorGram.SEVEN_UP));
        colorObjects.add(new ColorObject("pepsi", ColorGram.PEPSI));
    }

    public RecognizeColor(String path) throws Exception {
        super();
        init();
        voice.speak("opening directory");
        // gets images from directory
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (int f = 0; f < files.length; f++) {
            // create object
            ColorObject co = new ColorObject();
            String file = files[f].getName();
            if (file.endsWith(".jpg")) {
                // calibrate for image
                ColorGramCalibration cgc = new ColorGramCalibration(path + file,
                        false);
                voice.speak("ColorGram optimization for " + file);
                // optmize
                cgc.optimize();
                // set to ColorObject
                co.colorGram = cgc.getBestColorGram();
                // get rid of extension
                co.name = file.substring(0, file.length() - 4);
                // add to list
                colorObjects.add(co);
            }
        }
        voice.speak("done optmizing colors");
    }

    private void init() throws Exception {
        setFps(1);
        voice = MicrosoftVoice.getInstance();

    }

    public BufferedImage doProcessing(BufferedImage src) {
        BufferedImage dstImage = null;
        String winner = "";
        int maxCount = 0;
        ColorGram cg = null;
        //while (colorMaps.)
        for (int i = 0; i < colorObjects.size(); i++) {
            // get Object[] from list
            ColorObject cObj = (ColorObject) colorObjects.get(i);
            // get pixel count
            int tmpCount = imageProcessor.colorRatioCount(src, cObj.colorGram);
            // get maximum
            if (tmpCount > maxCount) {
                maxCount = tmpCount;
                winner = cObj.name;
                cg = cObj.colorGram;
            }
            Utils.log(cObj.name + " = " + tmpCount);
        }

        dstImage = imageProcessor.colorRatio(src, cg);
        try {
            // speak the winner
            voice.speak(winner);
        } catch (Exception e) {
        }
        return dstImage;
    }

    public static void main(String[] args) {
        try {
            RecognizeColor rc = new RecognizeColor();
            //RecognizeColor rc = new RecognizeColor("sample_images//cans//");
            rc.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
