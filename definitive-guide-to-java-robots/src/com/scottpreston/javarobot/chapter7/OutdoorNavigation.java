package com.scottpreston.javarobot.chapter7;

import java.awt.Point;

import com.scottpreston.javarobot.chapter2.JSerialPort;

public class OutdoorNavigation extends Navigation {

    public static double HOUSE_LAT = 2395.7362;
    public static double HOUSE_LON = 4989.2237;

    public static double MAILBOX_LAT = 2395.7402;
    public static double MAILBOX_LON = 4989.2307;

    public OutdoorNavigation(JSerialPort serialPort) throws Exception {
        super(serialPort);
    }

    public void move(GpsReading end) throws Exception {
        GpsReading start = getNavStamp().getGps();
        Point startPoint = new Point(lonToInch(start.longitude),
                lonToInch(start.latitude));
        Point endPoint = new Point(lonToInch(end.longitude), lonToInch(end.latitude));
        DistanceVector v = Localization.getDistanceVector(startPoint,
                endPoint);
        // convert to inches
        v.magintude = v.magintude * GpsReading.LAT_LON_CONV;
        move(v);
    }

    public static int lonToInch(double d) {
        double conv = Math.cos(Math.toRadians(HOUSE_LAT/60));
        d = conv * d;
        return (int) (d * 10000);
    }

    public static int latToInch(double d) {
        return (int) (d * 10000);
    }

    public static void main(String[] args) {
        try {
            System.out.println("inches per point of resolution =" + GpsReading.LAT_LON_CONV);
            System.out.println("house lon to inches=" + lonToInch(HOUSE_LON));
            System.out.println("house lat to inches=" + latToInch(HOUSE_LAT));
            System.out.println("mail lon to inches=" + lonToInch(MAILBOX_LON));
            System.out.println("mail lat to inches=" + latToInch(MAILBOX_LAT));
            Point startPoint = new Point(lonToInch(HOUSE_LON), lonToInch(HOUSE_LAT));
            Point endPoint = new Point(lonToInch(MAILBOX_LON), lonToInch(MAILBOX_LAT));
            DistanceVector v = Localization.getDistanceVector(startPoint,
                    endPoint);
            v.magintude = v.magintude * GpsReading.LAT_LON_CONV;
            System.out.println(v.toString());
        } catch (Exception e) {
        }
    }
}
