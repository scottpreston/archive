package com.scottpreston.javarobot.chapter7;

import java.io.Serializable;

public class DistanceReadings implements Serializable {

    public SonarReadings sonar = new SonarReadings();
    public IRReadings ir = new IRReadings();
    public static final long serialVersionUID = 1;
    
    public DistanceReadings(String readings) throws Exception {

        String[] values = readings.split("~");
        ir.left = new Integer(values[0]).intValue();
        ir.right = new Integer(values[1]).intValue();
        sonar.left = new Integer(values[2]).intValue();
        sonar.center = new Integer(values[3]).intValue();
        sonar.right = new Integer(values[4]).intValue();
    }

    public String toString() {
        return ir.toString() + "," + sonar.toString();
    }

}
