package com.scottpreston.javarobot.chapter7;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;

public class ObstacleNavigation extends Localization {

    
    private double offsetDistance = 0; 
    private boolean inBypass = false;
    
    public ObstacleNavigation(JSerialPort serialPort) throws Exception {
        super(serialPort);
        offsetDistance = Math.sin(Math.toRadians(45)) * ROBOT_RADIUS * 2;

    }

    public void move(MotionVector vect) throws Exception {
        Utils.log("MV=" + vect.toString());
        if (vect.magintude < 0) {
            throw new Exception("Only avoids obstacles in forward direction");
        }
        changeHeading(vect.heading);

        // get total time in MS for motion (vector length)
        int totalMS = (int) Math.abs(vect.magintude) * 1000;
        int offsetTime = (int) getSurfaceRate(offsetDistance) * 1000;
        // this will be minimum bypass distance
        // get number of sonar scans for range of motion
        int sweeps = (int) (totalMS / NavStamp.PING_CYCLE_TIME);
        // this will start motion
        getSonarServos().lookFore();
        Utils.pause(2000); // time to move sonar
        getDrive().forward();
        int count = 0;
        boolean obstacle = false;
        while (count < sweeps) {
            // moves until it hits something or is done.
            if (isobstacleFwd()) {
                Utils.log("***fwd obstacle***");
                getDrive().stop();
                obstacle = true;
                break;
            }
            count++;
        }
        getDrive().stop();
        // get remaining time in vector
        int remainingMS = totalMS - (count * NavStamp.PING_CYCLE_TIME);
        if (obstacle) {
            if (inBypass) {
                throw new Exception("Already in bypass find another route.");
            }
            Utils.pause(1000); // so not rough change of direction
            moveRaw(RAW_REV, 1000);
            remainingMS = remainingMS + 1000;
            // since both an obstacle and it can be bypassed
            if (remainingMS > offsetTime) {
                inBypass = true;
                moveBypass(new MotionVector(vect.heading, remainingMS), offsetTime);
                inBypass = false;
            }
        } else {
            // since can't detect this distance anyway
            getDrive().forward(remainingMS);
        }
    }

    private void moveBypass(MotionVector remainingVect, int offsetTime) throws Exception {

        // since readings in milliseconds
        remainingVect.magintude = remainingVect.magintude;
        DistanceReadings readings = getNavStamp().getSonarIR();
        // to move around obstacle to the left or to the right
        int newHeading = remainingVect.heading;

        double leftProb = 0;
        double rightProb = 0;

        // ir is more important use this first
        // ir high means close, low means far
        if (readings.ir.left - 20 > readings.ir.right) {
            //  since something closer on left, then turn right
            leftProb = leftProb + 0.15;
            // if so close turning will cause hit
            if (readings.ir.left > 100)
                leftProb = leftProb + 0.1;
        } else {
            rightProb = rightProb + 0.15;
            // if so close not turning will cause hit
            if (readings.ir.right > 120)
                rightProb = rightProb + 0.1;
        }
        // checking sonar if left < right more room to right so turn right by
        // increasing prob.
        if (readings.sonar.left < readings.sonar.right) {
            leftProb = leftProb + 0.1;
            // if close
            if (readings.sonar.left < 24)
                leftProb = leftProb + 0.1;
            // if so close not turning will cause hit
            if (readings.sonar.left < 12)
                leftProb = leftProb + 0.1;
        } else {
            rightProb = rightProb + 0.1;
            if (readings.sonar.right < 24)
                rightProb = rightProb + 0.1;
            if (readings.sonar.right < 12)
                rightProb = rightProb + 0.1;
        }
        int headingOne = 0;
        int headingTwo = 0;
        // int offset distance
        double offsetAdjacent = Math.cos(Math.toRadians(45)) * offsetDistance;
        double offsetOpposite = Math.sin(Math.toRadians(45)) * offsetDistance;
        // remaining time for original heading
        double remainingTime = remainingVect.magintude - offsetAdjacent;
        int finalAngle = (int) Math.toDegrees(Math.atan(offsetOpposite / remainingTime));
        double finalMagnitude = Math.sqrt(offsetAdjacent * offsetAdjacent + remainingTime
                * remainingTime);
        Utils.log("obstacle prob=" + rightProb + "," + leftProb);
        if (rightProb < leftProb) {
            // turn right
            headingOne = newHeading + 45;
            headingTwo = newHeading - finalAngle;
        } else {
            headingOne = newHeading - 45;
            headingTwo = newHeading + finalAngle;
        }

        MotionVector bypassOne = new DistanceVector(headingOne, offsetTime);
        move(bypassOne);
        MotionVector bypassTwo = new MotionVector(headingTwo, finalMagnitude);
        move(bypassTwo);
    }

    private boolean isobstacleFwd() throws Exception {
        DistanceReadings dist = getNavStamp().getSonarIR();
        if (dist.ir.left > 100 || dist.ir.right > 120 || dist.sonar.left < 12
                || dist.sonar.center < 12 || dist.sonar.right < 12) {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {
        try {
            WebSerialClient com = new WebSerialClient("10.10.10.99", "8080", "1");
            ObstacleNavigation nav = new ObstacleNavigation(com);
            // in seconds
            MotionVector[] v = new MotionVector[] { new MotionVector(90, 10) };
            nav.move(v);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
