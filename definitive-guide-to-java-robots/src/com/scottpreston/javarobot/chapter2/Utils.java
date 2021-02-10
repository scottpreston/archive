package com.scottpreston.javarobot.chapter2;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static SimpleDateFormat formatter = new SimpleDateFormat(
            "MM/dd/yy - HH:mm:ss.SSS");

    public static void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void log(String s) {
        Date d = new Date();
        String dt = formatter.format(d);
        System.out.println(dt + " *** " + s);
    }

    public static String toAscii(byte[] b) {

        StringBuffer s = new StringBuffer();
        if (b == null) {
            return "";
        }
        for (int i = 0; i < b.length; i++) {
            if (b[i] != 0) {
                char in = (char) b[i];
                s.append(in);
            }
        }
        return s.toString();
    }
    public static String byteToString(byte[] b) {

        StringBuffer s = new StringBuffer();
        if (b == null) {
            return "";
        }
        for (int i = 0; i < b.length; i++) {
                s.append(b[i]+",");
            }
        return s.toString();
    }

    
    public static String replace(String str, String txt, String by) {

		if (isNull(str) == true || isNull(txt) == true) {
			return str;
		}

		int strLen = str.length();
		int txtLen = txt.length();
		int i = str.indexOf(txt);

		if (i == -1) {
			return str;
		}

		String newstr = str.substring(0, i) + by;

		if (i + txtLen < strLen) {
			newstr += replace(str.substring(i + txtLen, strLen), txt, by);
		}

		return newstr;
	}
    
    public static boolean isNull(String s) {

		if (String.valueOf(s).equals("null") || s.length() == 0) {
			return true;
		} else {
			return false;
		}

	}
}