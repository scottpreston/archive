package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class MiniSsc extends Ssc implements SSCProtocol {

    // calls super and sets max pin to 7
    public MiniSsc(JSerialPort serialPort) throws Exception {
        super(serialPort);
        setMaxPin(7);
    }

    // sample program
    public static void main(String[] args) {
        try {
            // get single serial port instance
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            // create new miniSSc
            MiniSsc ssc = new MiniSsc(sPort);
            // move from position 0 to 255, 5 per 100 ms
            for (int pos = 0; pos < 255; pos = pos + 5) {
                // move
                ssc.move(0, pos);
                // wait 100 milliseconds
                Utils.pause(100);
            }
            // close serial port
            sPort.close();
        } catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
        }
    }

}
