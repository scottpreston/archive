package com.scottpreston.javarobot.chapter7;

public class MotionVector extends Edge{

    public int heading = 0;
    public double magintude = 0;

    public MotionVector(int h, double seconds) {
        heading = h;
        magintude = seconds;
        weight= (int)seconds;
    }

    public MotionVector(String h, String seconds) throws Exception {
        heading = new Integer(h).intValue();
        magintude = new Double(seconds).doubleValue();
        weight= (int)magintude;
    }

    public String toString() {
        return "Heading: " + heading + " Seconds: " + magintude;
    }
}
