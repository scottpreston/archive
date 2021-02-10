package com.scottpreston.javarobot.chapter9;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.scottpreston.javarobot.chapter2.Utils;

public class StereoVision{

    private String fname1;
    private String fname2;
    private String path;
    private CmdExec cmd1;
    private CmdExec cmd2;

    public StereoVision(String fname1, String fname2, String path) {
        this.fname1 = fname1;
        this.fname2 = fname2;
        this.path = path;
        cmd1 = new CmdExec();
        cmd2 = new CmdExec();
    }

    public void openCamera1() {
        // calls ant script to invoke since requires large class path
        cmd1.exe("c:/scripts/camera1.bat " + path + fname1);
    }

    public void openCamera2() {
        // calls ant script to invoke since requires large class path
        cmd2.exe("c:/scripts/camera2.bat " + path + fname2);
    }

    public void close() {
        // kills both processes
        cmd1.kill();
        cmd2.kill();
    }

    public static void main(String[] args) {
        try {
            // init class with two file names and path
            StereoVision sv = new StereoVision("1.jpg", "2.jpg",
                    "c:\\wwwroot\\");
            System.out.println("opening camera one...");
            sv.openCamera1();
            // wait 10 seconds
            Utils.pause(10000);
            System.out.println("opening camera two...");
            sv.openCamera2();
            System.out.println("ready... press ENTER key to exit");
            // takes system in as a parameter and waits till enter key
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    System.in));
            // reads new line
            String anyKey = br.readLine();
            // closes stereo vision killing processes
            sv.close();
        } catch (Exception e) {

        }
    }
}
