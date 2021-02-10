package com.scottpreston.javarobot.chapter9;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CmdExec {
    
    // process invoking program will call
    private Process p;
    
    // empty constructor
    public CmdExec() {  }
    
    // execute command
    public void exe(String cmdline) {
        try {
            // string for system out
            String line;
            // create process
            p = Runtime.getRuntime().exec(cmdline);
            // capture output stream of program
            BufferedReader input = new BufferedReader(new InputStreamReader(p
                    .getInputStream()));
            // get all lines of output
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            // close input stream
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // ability to kill process
    public void kill() {
        p.destroy();
    }
}
