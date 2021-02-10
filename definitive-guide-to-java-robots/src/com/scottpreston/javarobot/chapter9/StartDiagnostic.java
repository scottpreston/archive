package com.scottpreston.javarobot.chapter9;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter5.MicrosoftVoice;
import com.scottpreston.javarobot.chapter7.DistanceReadings;
import com.scottpreston.javarobot.chapter7.MotionVector;
import com.scottpreston.javarobot.chapter7.NavStamp;
import com.scottpreston.javarobot.chapter7.Navigation;
import com.scottpreston.javarobot.chapter7.SonarReadings;

public class StartDiagnostic {

    private MicrosoftVoice voice;

    public StartDiagnostic() throws Exception {
        voice = MicrosoftVoice.getInstance();
        speak("starting diagnostic");
    }

    public void speak(String txt) {
        Utils.log(txt);
        try {
            voice.speak(txt);
        } catch (Exception e) {
            Utils.log(e.getMessage());
        }
    }

    public void testInternet() throws Exception {
        speak("testing internet connection");
        testUrl("http://www.apress.com");
        speak("connected to internet");
    }

    public void testTomcat() throws Exception {
        speak("testing tom cat");
        testUrl("http://localhost:8080/test.txt");
        speak("connected to tom cat");
    }

    private void testUrl(String url) throws Exception {

        int i = 0;
        while (HttpGet.getText(url) != null && i < 10) {
            speak("testing");
            Utils.pause(1000);
            i++;
        }

    }

    public void testStamp(JSerialPort sPort) throws Exception {
        speak("testing stamp connection");
        NavStamp stamp = new NavStamp(sPort);
        if (stamp.diagnostic()) {
            speak("stamp return is good");
        } else {
            speak("stamp return is bad");
            throw new Exception("unable to connect to stamp");
        }
    }

    public void testHeading(JSerialPort sPort) throws Exception {
        speak("heading is " + new NavStamp(sPort).getCompass());
    }

    public void testSensors(JSerialPort sPort) throws Exception {
        speak("testing sensors");
        DistanceReadings readings = new NavStamp(sPort).getSonarIR();
        speak("left infrared sensor is " + readings.ir.left);
        speak("right infrared sensor is " + readings.ir.right);
        speak("left sonar is " + readings.sonar.left + " inches");
        speak("center sonar is " + readings.sonar.center + " inches");
        speak("right sonar is " + readings.sonar.right + " inches");
    }

    public void testNavigation(JSerialPort sPort) throws Exception {
        speak("testing navigation, facing north");
        Navigation simpleNav = new Navigation(sPort);
        simpleNav.changeHeading(Navigation.REL_NORTH);
        speak("facing north now");
    }

    public void testMotion(JSerialPort sPort) throws Exception {
        speak("testing navigation, moving north");
        Navigation simpleNav = new Navigation(sPort);
        NavStamp stamp = new NavStamp(sPort);
        SonarReadings readings = stamp.getSonar();
        Utils.pause(250);
        int startReading = readings.center;
        simpleNav.move(new MotionVector(Navigation.REL_NORTH, 1000));
        Utils.pause(250);
        readings = stamp.getSonar();
        int endReading = readings.center;
        if (endReading < startReading) {
            speak("moved north " + (startReading - endReading) + " inches");
        } else {
            speak("did not move north");
            throw new Exception("unable to move north");
        }
    }

    public void testCamera() {
        try {
            BufferedImage img = HttpGet.getImage("http://localhost:8080/getimage");
            // open file
            File file = new File("%temp%//start.jpg");
            ImageIO.write(img, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testAll(JSerialPort sPort) {
        try {
            testInternet();
            testTomcat();
            testStamp(sPort);
            testHeading(sPort);
            testSensors(sPort);
            testHeading(sPort);
            testNavigation(sPort);
            testMotion(sPort);
            testCamera();
            speak("completed diagnostic succesfully");
        } catch (Exception e) {
            speak("error occured during diagnostic");
        }
    }

    public static void main(String[] args) {
        try {
            StartDiagnostic diagnostic = new StartDiagnostic();
            diagnostic.testAll(SingleSerialPort.getInstance(1));
            SingleSerialPort.close(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
