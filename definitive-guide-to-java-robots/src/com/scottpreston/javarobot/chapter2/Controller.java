package com.scottpreston.javarobot.chapter2;

public abstract class Controller implements JController {
    
    private JSerialPort serialPort;

    public Controller(JSerialPort sPort) throws Exception {
        serialPort = sPort;
        serialPort.setDTR(false);
        Utils.pause(250);
    }

    public String execute(byte[] cmd, int delay) throws Exception {
        String out = null;
        if ((serialPort instanceof WebSerialClient)
                && delay <= WebSerialClient.MAX_DELAY) {
            serialPort.setTimeout(delay);
            if (delay == 0) {
                serialPort.write(cmd);
            } else {
                out = serialPort.readString(cmd);
            }
        } else {
            if (delay == 0) {
                serialPort.write(cmd);
            } else {
                serialPort.write(cmd);
                Utils.pause(delay);
                out = serialPort.readString();
            }

        }
        return out;
    }

    public byte[] execute2(byte[] cmd, int delay) throws Exception {
        byte[] out = null;
        if ((serialPort instanceof WebSerialClient)
                && delay <= WebSerialClient.MAX_DELAY) {
            serialPort.setTimeout(delay);
            if (delay == 0) {
                serialPort.write(cmd);
            } else {
                out = serialPort.read(cmd);
            }
        } else {
            if (delay == 0) {
                serialPort.write(cmd);
            } else {
                serialPort.write(cmd);
                Utils.pause(delay);
                out = serialPort.read();
            }
        }
        return out;
    }

    
    public void close() {
        serialPort.close();
    }
}
