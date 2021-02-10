package com.scottpreston.javarobot.chapter7;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;
import com.scottpreston.javarobot.chapter3.JMotion;
import com.scottpreston.javarobot.chapter3.SpeedDiffDrive;

public class Navigation {

    // movement constants for raw movement
    public static final int RAW_FWD = 0;
    public static final int RAW_REV = 1;
    public static final int RAW_RGT = 2;
    public static final int RAW_LFT = 3;
    // relative readings for 4 coordinate axis
    public static final int REL_NORTH = 40;
    public static final int REL_EAST = 100;
    public static final int REL_SOUTH = 160;
    public static final int REL_WEST = 255;
    // surface constants
    public static final int SURFACE_CEMENT = 1;
    public static final int SURFACE_CARPET = 2;
    // default speed
    public static final int DEFAULT_SPEED = 25;

    // instance variables
    public int surface = SURFACE_CEMENT;
    private JMotion drive;
    private NavStamp navStamp;

    public Navigation(JSerialPort serialPort) throws Exception {
        // drive with default speed
        drive = new SpeedDiffDrive(serialPort);
        drive.setSpeed(DEFAULT_SPEED);
        // stamp for sensors
        navStamp = new NavStamp(serialPort);
    }

    // change heading
    public void changeHeading(int newHeading) throws Exception {
        // this will calculate real angle from relative measure from coord axis.
        newHeading = getRealAngle(newHeading);
        int accuracy = 2; // degrees
        // auto adjust speed depending on the surface
        if (surface == SURFACE_CEMENT) {
            // slow so don't overshoot 15 degree at 1sec intervals
            drive.setSpeed(12);
        } else {
            // moves slower on carpet
            drive.setSpeed(20);
        }
        // used to record lats turn
        int lastTurn = 0;
        boolean toggle = false;
        int turnSize = 1000;
        while (true) {
            // get compass
            int currentHeading = navStamp.getCompass();
            // get relative heading from compass to where want to go
            int relHeading = currentHeading - newHeading;

            // adjust for negative
            if (relHeading < 0) {
                relHeading = 360 + relHeading;
            }
            // if within bounds, stop
            if (relHeading <= accuracy || relHeading >= 360 - accuracy) {
                drive.stop();
                break;
            }
            // in case it overshoots direction twice
            if (toggle) {
                // reset
                toggle = false;
                // reduce turn time by 250ms
                turnSize = turnSize - 250;
            }
            // turn for a second left
            if (relHeading < 180 && relHeading > 15) {
                if (lastTurn == 'R') {
                    toggle = true;
                }
                drive.pivotLeft(turnSize);
                // record what turn
                lastTurn = 'L';
                // turn for a second right
            } else if (relHeading >= 180 && relHeading < 345) {
                // records toggle
                if (lastTurn == 'L') {
                    toggle = true;
                }
                drive.pivotRight(turnSize);
                lastTurn = 'R';
            } else if (relHeading >= 345) {
                drive.pivotRight(250);
            } else if (relHeading <= 15) {
                drive.pivotLeft(250);
            }
        }
        // set back to default speed
        drive.setSpeed(DEFAULT_SPEED);
    }

    // adjust for angle measured to absolute angle
    public static int getRealAngle(int theta) {

        int phi = 0;
        // if in 1st quadrant
        if (theta > 0 && theta < 90) {
            // 1. get % of the total range
            // 2. get range
            // 3. multiply range by percentage, add it to current north reading.
            phi = (int) ((theta / 90.0) * (REL_EAST - REL_NORTH)) + REL_NORTH;
        }
        if (theta > 90 && theta < 180) {
            theta = theta - 90;
            phi = (int) ((theta / 90.0) * (REL_SOUTH - REL_EAST)) + REL_EAST;
        }
        if (theta > 180 && theta < 270) {
            theta = theta - 180;
            phi = (int) ((theta / 90.0) * (REL_WEST - REL_SOUTH)) + REL_SOUTH;
        }
        if (theta > 270 && theta < 360) {
            theta = theta - 270;
            phi = (int) ((theta / 90.0) * ((360 + REL_NORTH) - REL_WEST)) + REL_WEST;
        }
        // in case actual directions
        if (theta == 0) {
            phi = REL_NORTH;
        }
        if (theta == 90) {
            phi = REL_EAST;
        }
        if (theta == 180) {
            phi = REL_SOUTH;
        }
        if (theta == 270) {
            phi = REL_WEST;
        }
        if (phi > 360) {
            phi = phi - 360;
        }
        return phi;

    }

    // setter for drive speed
    public void setSpeed(int s) throws Exception {
        drive.setSpeed(s);
    }

    // getter for drive speed
    public int getSpeed() {
        return drive.getSpeed();
    }

    //  distance vector is in inches
    public void move(DistanceVector dVect) throws Exception {
        // convert since in inches
        dVect.magintude = getSurfaceRate(dVect.magintude);
        // converted to MotionVector
        move(dVect);
    }

    // motion vector is in inches
    public void move(MotionVector vect) throws Exception {
        // change heading
        Utils.log("MV=" + vect.toString());
        changeHeading(vect.heading);
        // move fwd or reverse
        if (vect.magintude > 0) {
            drive.forward((int) (vect.magintude * 1000));
        } else if (vect.magintude < 0) {
            drive.reverse((int) (-vect.magintude * 1000));
        }
    }

    public void moveRaw(int dir, int ms) throws Exception {
        if (dir == RAW_FWD) {
            drive.forward(ms);
        }
        if (dir == RAW_REV) {
            drive.reverse(ms);
        }
        if (dir == RAW_RGT) {
            drive.pivotRight(ms);
        }
        if (dir == RAW_LFT) {
            drive.pivotLeft(ms);
        }
    }

    // surface rate when adjusting inches to seconds
    public int getSurfaceRate(double inches) {
        if (surface == SURFACE_CARPET) {
            return getMillisecondsCarpet(inches);
        }
        if (surface == SURFACE_CEMENT) {
            return getMillisecondsCement(inches);
        }
        return 0;
    }

    //  surface rate when adjusting inches to seconds
    private int getMillisecondsCement(double inches) {

        double convFactor = 0.0; // this will be second/inches
        switch (drive.getSpeed()) {
        case 10:
            convFactor = 1 / 4.0;
            break;
        case 20:
            convFactor = 1 / 7.0;
            break;
        case DEFAULT_SPEED:
            convFactor = 1 / 14.0;
            break;
        case 30:
            convFactor = 1 / 20.0;
            break;
        }
        // will return seconds
        return (int) (inches * convFactor);

    }

    //  surface rate when adjusting inches to seconds
    private int getMillisecondsCarpet(double inches) {

        double convFactor = 0.0; // this will be second/inches
        switch (drive.getSpeed()) {
        case 10:
            convFactor = 1 / 16.0;
        case 20:
            convFactor = 1 / 36.0;
        case 30:
            convFactor = 1 / 48.0;
        }
        return (int) (inches * convFactor);

    }

    // call to stop since in case of emergency
    public void stop() throws Exception {
        drive.stop();
    }

    // move for multiple vectors
    public void move(MotionVector[] path) throws Exception {
        for (int i = 0; i < path.length; i++) {
            move(path[i]);
        }
    }

    public JMotion getDrive() {
        return drive;
    }

    public NavStamp getNavStamp() {
        return navStamp;
    }

    public static void main(String[] args) {

        try {
            WebSerialClient sPort = new WebSerialClient("10.10.10.99", "8080", "1");
            Navigation nav = new Navigation(sPort);
            // move east 36 inches
            nav.move(new DistanceVector(90, 36));
            // move north 36 inches
            nav.move(new DistanceVector(0, 36));
            // move west 36 inches
            nav.move(new DistanceVector(270, 36));
            // move south 36 inches
            nav.move(new DistanceVector(180, 36));

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
