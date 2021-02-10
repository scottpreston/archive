package com.scottpreston.javarobot.chapter9;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;
import com.scottpreston.javarobot.chapter5.MicrosoftVoice;
import com.scottpreston.javarobot.chapter7.DistanceReadings;
import com.scottpreston.javarobot.chapter7.NavStamp;

public class SpeakSensors {

    private MicrosoftVoice voice;
    private NavStamp stamp;

    public SpeakSensors(JSerialPort sPort) throws Exception {
        stamp = new NavStamp(sPort);
        voice = MicrosoftVoice.getInstance();
    }

    public void readSensorData() throws Exception {
        int heading = stamp.getCompass();
        DistanceReadings readings = stamp.getSonarIR();
        voice.speak("heading is " + heading + " degrees.");
        voice.speak("left infrared is " + readings.ir.left + " degrees.");
        voice.speak("right infrared is " + readings.ir.right + " degrees.");
        voice.speak("left sonar is " + readings.sonar.left + " inches.");
        voice.speak("center sonar is " + readings.sonar.center + " inches.");
        voice.speak("right sonar is " + readings.sonar.right + " inches.");
    }

    public static void main(String[] args) {
        try {
            WebSerialClient sPort = new WebSerialClient("10.10.10.99",
                    "8080", "1");
            SpeakSensors ss = new SpeakSensors(sPort);
            int checks = 50;
            for (int x=0; x < checks; x++) {
                ss.readSensorData();
                Utils.pause(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
