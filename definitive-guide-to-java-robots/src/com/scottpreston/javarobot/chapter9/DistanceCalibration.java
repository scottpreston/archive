package com.scottpreston.javarobot.chapter9;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;
import com.scottpreston.javarobot.chapter7.NavStamp;
import com.scottpreston.javarobot.chapter7.Navigation;
import com.scottpreston.javarobot.chapter7.SonarReadings;

public class DistanceCalibration {
    
    // navigation class
    private Navigation sNav;
    // stamp class
    private NavStamp stamp;
    
    public DistanceCalibration(JSerialPort sPort) throws Exception{
        sNav = new Navigation(sPort);
        stamp = new NavStamp(sPort);
    }
    
    public void calibrate() throws Exception {
        // default 3 times
        calibrate(3);
    }
    
    public void calibrate(int times) throws Exception{
        // avg fwd dist per second
        int count = 0;
        int interval = 1000;
        SonarReadings sr;
        int startDist;
        // total distance to summ
        int totalDistF = 0;
        int totalDistR = 0;
        int totalTime = 0;
        int dist;
        int speed  = 25;
        while (count < times) {
            // get forward readings & distance
            sr = stamp.getSonar();
            startDist = sr.center;
            Utils.pause(250);
            // face north
            sNav.changeHeading(0);
            sNav.setSpeed(speed);
            // move forward
            sNav.moveRaw(Navigation.RAW_FWD,count*interval);
            Utils.pause(250);
            // take new sonar reading
            sr = stamp.getSonar();
            dist = startDist - sr.center;
            totalDistF = totalDistF + dist;
            // get reverse readings & distance
            startDist = sr.center;
            Utils.pause(250);
            sNav.changeHeading(0);
            sNav.setSpeed(speed);
            // move reverse
            sNav.moveRaw(Navigation.RAW_REV,count*interval);
            Utils.pause(250);
            // take sonar
            sr = stamp.getSonar();
            dist = sr.center - startDist ;
            totalDistR = totalDistR + dist;
            totalTime = totalTime + count*interval;
            count++;
        }
        System.out.println("avg fwd:" + totalDistF / (double)totalTime);
        System.out.println("avg rev:" + totalDistR / (double)totalTime);        
    }

    public static void main(String[] args) {
        
        try {
            WebSerialClient com = new WebSerialClient("10.10.10.99", "8080",
             "1");
            DistanceCalibration cal = new DistanceCalibration(com);
            cal.calibrate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        }

    }
}
