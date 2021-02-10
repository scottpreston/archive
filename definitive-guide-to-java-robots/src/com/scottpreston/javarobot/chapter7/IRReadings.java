package com.scottpreston.javarobot.chapter7;

import java.io.Serializable;

public class IRReadings implements Serializable {

	public static final long serialVersionUID = 1;
	
    public int left = 0;
    public int right = 0;

    public IRReadings() {
        // default
    }

    public IRReadings(String readings) {
        String[] values = readings.split("~");
        left = new Integer(values[0]).intValue();
        right = new Integer(values[1]).intValue();
    }

    public String toString() {
        return "left=" + left + ",right=" + right;
    }
}
