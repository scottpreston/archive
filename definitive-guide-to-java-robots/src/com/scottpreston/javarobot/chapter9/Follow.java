package com.scottpreston.javarobot.chapter9;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter3.PanTilt;
import com.scottpreston.javarobot.chapter6.ColorGram;
import com.scottpreston.javarobot.chapter6.DoubleWebCamViewer;
import com.scottpreston.javarobot.chapter6.ImageProcessor;

public class Follow extends DoubleWebCamViewer {

    private int hPos = PanTilt.HORZ_NEUTRAL;
    private int vPos = PanTilt.VERT_NEUTRAL;

    private PanTilt head;
    private ImageProcessor imageProcessor = new ImageProcessor();
    
    public static final long serialVersionUID = 1;

    public Follow(JSerialPort sPort) throws Exception {
        super();
        head = new PanTilt(sPort);
        setTitle("Follow Color");
    }


    public BufferedImage doProcessing(BufferedImage bimg) {
        // get cologram of coke
        bimg = imageProcessor.colorRatio(bimg,ColorGram.COKE);
        // get avg point of coke
        Point pt = imageProcessor.getAvgPoint(bimg);
        // mvoe head
        moveHead(pt);
        // display point to system.out
        Utils.log(pt.toString());
        return bimg;
    }

    private void moveHead(Point pt) {

        double x = pt.x;
        double y = pt.y;
        //x
        if (x < 50) {
            hPos = hPos - 5;
        }
        if (x > 270) {
            hPos = hPos + 5;
        }
        if (x < 100 && x >= 50) {
            hPos = hPos - 3;
        }
        if (x > 220 && x <= 270) {
            hPos = hPos + 3;
        }
        if (x < 220 && x > 190) {
            hPos = hPos + 1;
        }
        if (x > 100 && x < 130) {
            hPos = hPos - 1;
        }
        // y
        if (y < 30) {
            vPos = vPos + 5;
        }
        if (y > 210) {
            vPos = vPos - 5;
        }
        if (y < 60 && y >= 30) {
            vPos = vPos + 3;
        }
        if (y > 180 && y <= 210) {
            vPos = vPos - 3;
        }
        if (y < 180 && y > 150) {
            vPos = vPos - 1;
        }
        if (y > 60 && y < 90) {
            vPos = vPos + 1;
        }

        // this is where robot will turn
        if (hPos > 255) {
            hPos = 255;
        }
        if (hPos < 0) {
            hPos = 0;
        }
        if (vPos > 255) {
            vPos = 255;
        }
        if (vPos < 0) {
            vPos = 0;
        }
        try {
            head.moveBoth(hPos, vPos);
        } catch (Exception e) {
            // don't do anything since could just move out of bounds
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        try {
            Follow fc = new Follow(SingleSerialPort.getInstance(1));
            fc.setFps(5);
            fc.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
