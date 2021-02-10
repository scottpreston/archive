package com.scottpreston.javarobot.chapter7;

import java.awt.Point;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;

public class Localization extends Navigation {

    private SonarServos sonarServos;

    public static final int ROBOT_RADIUS = 12;

    public Localization(JSerialPort serialPort) throws Exception {
        super(serialPort);
        sonarServos = new SonarServos(serialPort);
    }

    // calculate vector from 2 points.
    public static DistanceVector getDistanceVector(Point a, Point b) throws Exception {

        int d;
        int dx = a.x - b.x;
        int dy = a.y - b.y;
        // get distance
        double mag =  Math.sqrt(dx * dx + dy * dy);
        // get angle
        if ((dx) == 0) {
            d = 90;
        } else {
            double slope = (double) (dy) / (double) (dx);
            d = (int) Math.toDegrees(Math.atan(slope));
        }
        // adjust angle to coordinate system of N,E,S,W
        if (a.y <= b.y) { // if 1st point(Y) higher
            if (a.x > b.x) { // if 1st point(X) is more to right
                d = 360 - (90 + d);
            } else {
                d = 90 - d;
            }
        } else {
            if (a.x < b.x) {
                d = 90 - d;
            } else {
                d = 180 + (90 - d);
            }
        }
        return new DistanceVector(d, mag);
    }

    // this uses sonarServos, add your own sensors here if needed
    public NavPoint getStart() throws Exception {

        int[] nesw = getFourCoordinates();
        return new NavPoint(NavPoint.START_POINT, nesw[3], nesw[2]);
    }

    public int[] getFourCoordinates() throws Exception {
        // first face north.
        changeHeading(0);
        sonarServos.lookSide();
        Utils.pause(500);
        SonarReadings sonarReadings = getNavStamp().getSonar();
        int north = sonarReadings.center;
        int east = sonarReadings.right - ROBOT_RADIUS;
        int west = sonarReadings.left + ROBOT_RADIUS;
        sonarServos.lookAft();
        Utils.pause(500);
        sonarReadings = getNavStamp().getSonar();
        // average of two readings
        int south = (int) ((sonarReadings.left + sonarReadings.right) / 2.0);
        return new int[] {north,east,south,west};
    }
    
    // this uses sonarServos, add your own sensors here if needed
    public NavPoint getStart2() throws Exception {

        int heading = getNavStamp().getCompass();
        int north = 0, south = 0, east = 0, west = 0;
        int eastPos = 90 - heading;
        int southPos = 180 - heading;
        int westPos = 270 - heading;
        int northPos = 360 - heading;
        SonarReadings sonarReadings = null;

        int bestReadings[] = null; // order x,y
        if (heading >= 0 && heading < 91) { //1st quad
            sonarServos.moveLeft(westPos);
            sonarServos.moveRight(southPos);
            Utils.pause(500);
            sonarReadings = getNavStamp().getSonar();
            west = sonarReadings.left;
            south = sonarReadings.right;
            bestReadings = new int[] { REL_WEST, REL_SOUTH };
        } else if (heading > 90 && heading < 181) {
            sonarServos.moveLeft(northPos);
            sonarServos.moveRight(westPos);
            Utils.pause(500);
            sonarReadings = getNavStamp().getSonar();
            north = sonarReadings.left;
            west = sonarReadings.right;
            bestReadings = new int[] { REL_WEST, REL_NORTH };
        } else if (heading > 180 && heading < 271) {
            sonarServos.moveLeft(eastPos);
            sonarServos.moveRight(northPos);
            Utils.pause(500);
            sonarReadings = getNavStamp().getSonar();
            east = sonarReadings.left;
            north = sonarReadings.right;
            bestReadings = new int[] { REL_EAST, REL_NORTH };
        } else if (heading > 270 && heading < 360) {
            sonarServos.moveLeft(southPos);
            sonarServos.moveRight(eastPos);
            Utils.pause(500);
            sonarReadings = getNavStamp().getSonar();
            south = sonarReadings.left;
            east = sonarReadings.right;
            bestReadings = new int[] { REL_EAST, REL_SOUTH };
        }

        NavPoint navPoint = new NavPoint(NavPoint.START_POINT, 0, 0);
        int xOffset = 0;
        int yOffset = 0;
        if (bestReadings[0] == REL_EAST) {
            xOffset = (int)(ROBOT_RADIUS * Math.cos(Math.toRadians(eastPos)));
            navPoint.x = 100 - east;
        } else {
            xOffset = (int)(ROBOT_RADIUS * Math.cos(Math.toRadians(westPos)));
            navPoint.x = west;
        }
        if (bestReadings[1] == REL_NORTH) {
            yOffset = (int)(ROBOT_RADIUS * Math.sin(Math.toRadians(northPos)));
            navPoint.y = 100 - north;
        } else {
            yOffset = (int)(ROBOT_RADIUS * Math.sin(Math.toRadians(southPos)));
            navPoint.y = south ;
        }
        navPoint.x = navPoint.x + xOffset;
        navPoint.y = navPoint.y + yOffset;
        return navPoint;
    }

    // move from a to b
    public void move(Point a, Point b) throws Exception {
        MotionVector v = getDistanceVector(a, b);
        move(v);
    }

    public void move(Point b) throws Exception {
        move(getStart(), b);
    }
    
    public SonarServos getSonarServos() {
        return sonarServos;
    }
    
    public static void main(String[] args) {
        try {
            WebSerialClient sPort = new WebSerialClient("10.10.10.99", "8080", "1");
            Localization local = new Localization(sPort);
            local.move(new Point(36, 36));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    
}
