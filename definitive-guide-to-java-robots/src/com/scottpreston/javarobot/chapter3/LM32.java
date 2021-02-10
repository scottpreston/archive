package com.scottpreston.javarobot.chapter3;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class LM32 extends Ssc implements SSCProtocol, GroupMoveProtocol {

    // buffer to store commands
    private StringBuffer cmds = new StringBuffer();
    // default speed in milliseconds
    public static int DEFAULT_SPEED = 500;
    // busy or not
    private boolean busy = false;

    // constructor taking JSerialPort
    public LM32(JSerialPort sPort) throws Exception {
        super(sPort);
        super.setMaxPin(32);

    }

    // move command with parameter of milliseconds
    public void move(int ms) throws Exception {
        // this will pause the current thread for the arm move until
        // it is finished completing it's action
        while (busy) {
            Utils.pause(2);
        }
        // set the object status to busy
        setBusy(ms);
        // append final command
        String cmd = cmds.append("T" + ms + CMD_TERM).toString();
        // send bytes to LM32
        execute(cmd.getBytes(), 0);
        // clear command string
        cmds = new StringBuffer(); //resets the string buffer for new set of
                                   // commands
    }

    // override current SSC command
    public void sscCmd(int ch, int pos) throws Exception {
        cmd(ch, pos, DEFAULT_SPEED);
    }

    /**
     * @param ch - channel 0-31
     * @param pos - position 0-255
     * @param spd - speed in milliseconds
     */
    public void cmd(int ch, int pos, int spd) throws Exception {
        // ensure position is valid
        if (pos < 0 || pos > 255) {
            throw new Exception("position out of bounds");
        }
        // call createCmd then append to string buffer
        cmds.append(createCmd(ch, pos, spd));
    }

    // allows for raw command string to be sent
    public void setRawCommand(String rawCmd) {
        cmds.append(rawCmd);
    }

    // this is the protocol for the command string for the LM32
    public static String createCmd(int ch, int pos, int spd) {

        String out = "#" + ch + "P" + getPw(pos) + "S" + spd;
        return out;
    }

    // sets the LM32 busy for specific milliseconds
    private void setBusy(long ms) {
        // the set busy function
        busy = true;
        // gets time when it' should be done
        Date timeToRun = new Date(System.currentTimeMillis() + ms);
        Timer timer = new Timer();
        // schedules time to be run so busy can be set to false
        timer.schedule(new TimerTask() {
            public void run() {
                busy = false;
            }
        }, timeToRun);

    }

    // accessor
    public boolean isBusy() {
        return busy;
    }

    // static utility method
    public static int getPw(int pos) {
        int pulsewidth;
        double percent = (double) pos / 255;
        double pwfactor = percent * 1500;
        // sets pulsewidth as function of btye size
        pulsewidth = 750 + (int) pwfactor;
        return pulsewidth;

    }

    // same program
    public static void main(String[] args) {
        try {
            // get single serial port instance
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            // create new LM32
            LM32 lm32 = new LM32(sPort);
            // sets position for servo at pin 0
            lm32.sscCmd(0, 100);
            // sets position for servo at pin 1
            lm32.sscCmd(1, 200);
            // tells the servos to move there in 1 second.            
            lm32.move(1000);
            // close serial port
            sPort.close();
        } catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
        }

    }

}