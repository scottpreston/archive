package com.scottpreston.javarobot.chapter7;

import java.io.Serializable;

public class SonarReadings implements Serializable {

    public int left = 0;
    public int center = 0;
    public int right = 0;
    public static final long serialVersionUID = 1;

    public SonarReadings() {
        // default
    }

    public SonarReadings(String readings) {
        // sample input "11~22~33"
        String[] values = readings.split("~");
        left = new Integer(values[0]).intValue();
        center = new Integer(values[1]).intValue();
        right = new Integer(values[2]).intValue();
    }

    public String toString() {
        return "left=" + left + ",center=" + center + ",right=" + right;
    }
}
