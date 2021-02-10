package com.scottpreston.javarobot.chapter7;

public class DistanceVector extends MotionVector {

    public DistanceVector(int h, double inches) {
        super(h, inches);
    }

    public DistanceVector(String h, String inches) throws Exception {
        super(h, inches);
    }

    public String toString() {
        return "Heading: " + heading + " Inches: " + magintude;
    }
}
