package com.scottpreston.javarobot.chapter9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import com.scottpreston.javarobot.chapter6.GetFrame;

public class ImageTimer {

    // image capture class
    public GetFrame getFrame;
    // timer class
    private Timer timer;
    // seconds to take pictures
    private int seconds = 5;
    // filename
    private String fileName;
    // url of camera
    private String url = "vfw://0";

    public ImageTimer(String fname) throws Exception {
        // init frame grabber
        getFrame = new GetFrame(url);
        // open it (takes 2500ms)
        //getFrame.open();
        // set filename
        fileName = fname;
        // schedule pictures every 5 seconds
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    savePic(getFrame.getBufferedImage());
                } catch (Exception e) {
                }
            }
        }, 1000, seconds * 1000);
    }

    private void savePic(BufferedImage img) {
        try {
            // open file
            File file = new File(fileName);
            // write JPG
            ImageIO.write(img, "jpg", file);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        try {
            // since this takes a command argument do some error handeling
            if (args.length != 1) {
                System.out
                        .println("usage: java ImageTimer c://webroot/1.jpg");
                System.exit(1);
            }
            // create the class
            ImageTimer it = new ImageTimer(args[0]);
        } catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
        }
    }

}
