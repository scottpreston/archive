package com.scottpreston.javarobot.chapter2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebSerialClient implements JSerialPort {

    private SimpleDateFormat formatter = new SimpleDateFormat(
            "MM/dd/yy - HH:mm:ss.SSS");
    private String url;
    private boolean dtr = false;
    private int timeout = 0;
    public static final int MAX_DELAY = 500;

    public WebSerialClient(String server, String tcpPort, String portId) {
        this.url = "http://" + server + ":" + tcpPort 
        + "/" + WebSerialPort.COMM_JSP + "?portid=" + portId;
    }

    public byte[] read() {
        return readString().getBytes();
    }

    public String readString() {
        return request(WebSerialPort.READ_ONLY, JSerialPort.READ_COMMAND);
    }

    public void write(byte[] b) throws Exception {
        String out = request(b, JSerialPort.WRITE_COMMAND);
        if (out.equalsIgnoreCase(WebSerialPort.CMD_ACK) == false) {
            throw new Exception("WebClient Write Failure: " + out);
        }
    }

    // added in case where user wants to read after they send commands
    // this is specific to the webcom.jsp
    public byte[] read(byte[] b) {
        return readString(b).getBytes();
    }

    public String readString(byte[] b) {
        return request(b, JSerialPort.WRITE_READ_COMMAND);
    }

    public void close() {
        // do nothing since having more than one port
    }

    public void setDTR(boolean dtr) {
        this.dtr = dtr;
    }

    boolean timedOut = false;
    
    private String request(byte[] commands, String cmdType) {
        // convert byte to string
        String cmdString = byteArrayToString(commands);

        log("WebClient: cmds=" + cmdString + ", cmdType=" + cmdType
                + ", timeout=" + timeout);

        String out = "";
        try {
            String urlString = url 
            	+ "&action=" + cmdType 
            	+ "&commands=" + cmdString
                + "&timeout=" + timeout 
                + "&dtr=" + dtr;

            URL myurl = new URL(urlString);
            log(urlString);
            //Timer t = new Timer();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    myurl.openStream()));
            String str = null;
            while ((str = in.readLine()) != null || timedOut) {
                // str is one line of text; readLine() strips the newline
                // character(s)
                if (str != null) {
                    out = str;
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out = out.trim();
        log("WebClient: out=" + out);
        return out;
    }

    private String byteArrayToString(byte[] b) {
        String s = "";
        for (int x = 0; x < b.length; x++) {
            s = s + b[x] + ",";
        }
        s = s.substring(0, s.length() - 1);
        return s;
    }

    private void log(String s) {
        Date d = new Date();
        String dt = formatter.format(d);
        System.out.println(dt + " *** " + s);
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public static void main(String[] args) {
    }

}