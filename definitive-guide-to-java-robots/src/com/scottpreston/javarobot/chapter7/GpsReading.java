package com.scottpreston.javarobot.chapter7;

import java.io.Serializable;

public class GpsReading implements Serializable {

    public boolean status = false;
    public double longitude = 0.0;
    public double latitude = 0.0;
    public String lon_raw = null;
    public String lat_raw = null;
    
    public static final long serialVersionUID = 1;

    // gives an equivalent of about 7 inches.
    public static final double LAT_LON_CONV = (24902.0 // miles around earth
            * 5280.0 // feet in a mile
            * 12 // inches per feet
            ) / (360.0 // degrees in a circle
            * 60.0 // minutes in a degree
            * 10000 // resolution of minutes
            ); // inches in a foot

    public GpsReading(String lon, String lat) throws Exception {
        lon_raw = lon;
        lat_raw = lat;
        setValues();
    }

    private void setValues() throws Exception {
        if (lon_raw.startsWith("A") && lon_raw.startsWith("A")) {
            status = true;
        }
        String lonHr = lon_raw.substring(2, 5);
        String latHr = lat_raw.substring(2, 5);
        String lonMn = lon_raw.substring(6, 12);
        String latMn = lat_raw.substring(6, 12);

        longitude = new Double(lonHr).doubleValue() * 60
                + (new Double(lonMn).doubleValue());
        latitude = new Double(latHr).doubleValue() * 60
                + (new Double(latMn).doubleValue());

    }

    public String toString() {
        return "GpsReading = {longitude = " + lon_raw + " = " + longitude
                + ", latitude = " + lat_raw + " = " + latitude;
    }

}
