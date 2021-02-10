package com.scottpreston.javarobot.chapter7;

import com.scottpreston.javarobot.chapter2.Controller;
import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;

public class NavStamp extends Controller {

    // command bytes to microcontroller
    public static byte CMD_INIT = 100;
    public static byte CMD_COMPASS = 101;
    public static byte CMD_SONAR = 102;
    public static byte CMD_IR = 103;
    public static byte CMD_IR_SONAR = 104;
    public static byte CMD_GPS_LAT = 105;
    public static byte CMD_GPS_LON = 106;
    public static byte CMD_DIAG = 107;
    public static int PING_CYCLE_TIME = 200;

    public NavStamp(JSerialPort port) throws Exception {
        super(port);
    }

    // get compass reading
    public int getCompass() throws Exception {
        String heading = execute(new byte[] { CMD_INIT, CMD_COMPASS }, 175);
        String[] h2 = heading.split("~");
        String heading2 = "";
        for (int h = 0; h < h2.length; h++) {
            heading2 = heading2 + (char) new Integer(h2[h]).intValue();
        }
        return new Integer(heading2).intValue();
    }

    // get ir
    public IRReadings getIR() throws Exception {
        String readings = execute(new byte[] { CMD_INIT, CMD_IR }, 75);
        return new IRReadings(readings);
    }

    // get sonar
    public SonarReadings getSonar() throws Exception {
        String readings = execute(new byte[] { CMD_INIT, CMD_SONAR }, 75);
        return new SonarReadings(readings);
    }

    // get both ir and sonar
    public DistanceReadings getSonarIR() throws Exception {
        String readings = execute(new byte[] { CMD_INIT, CMD_IR_SONAR }, 200);
        return new DistanceReadings(readings);
    }

    // get gps longitude
    public String getGpsLongitude() throws Exception {
        byte[] readings = execute2(new byte[] { CMD_INIT, CMD_GPS_LON }, 5000);
        return Utils.toAscii(readings);
    }

    // get gps latitude
    public String getGpsLatitude() throws Exception {
        byte[] readings = execute2(new byte[] { CMD_INIT, CMD_GPS_LAT }, 5000);
        return Utils.toAscii(readings);
    }

    // get both lognitud and latitude
    public GpsReading getGps() throws Exception {
        String lon = getGpsLongitude();
        String lat = getGpsLatitude();
        return new GpsReading(lon, lat);
    }

    // get diagnostic singal
    public boolean diagnostic() throws Exception {
        String s = execute(new byte[] { CMD_INIT, CMD_DIAG }, 80);
        if (s.equals("1~2~3")) {
            return true;
        }
        return false;
    }

    // test all methods
    public static void main(String[] args) {
        try {
            WebSerialClient com = new WebSerialClient("10.10.10.99", "8080", "1");
            NavStamp s = new NavStamp(com);
            System.out.println("diag=" + s.diagnostic());
            Utils.pause(500);
            System.out.println("compass=" + s.getCompass());
            Utils.pause(500);
            System.out.println("ir=" + s.getIR().toString());
            Utils.pause(500);
            System.out.println("diag=" + s.getSonar().toString());
            Utils.pause(500);
            System.out.println("all dist=" + s.getSonarIR());
            s.close();
            System.out.println("done");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);

        }
    }

}
