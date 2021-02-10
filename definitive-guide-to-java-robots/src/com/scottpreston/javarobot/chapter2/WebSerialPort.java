package com.scottpreston.javarobot.chapter2;

public class WebSerialPort{

    JSerialPort com;

    public static final String CMD_ACK = "ok";
    public static final String COMM_JSP = "webcom.jsp";
    public static final byte[] READ_ONLY = new byte[] { (byte) 0 };
    public static final int DEFAULT_TIMEOUT = 0;
    private int timeout;

    public WebSerialPort(String comId) throws Exception {
        int pId = new Integer(comId).intValue();
        com = SingleSerialPort.getInstance(pId);
    }

    public String execute(String action, String cmds, String timeout,String dtr) throws Exception{
        if (action == null) {
            throw new Exception("Action is null");
        }
        if (cmds == null) {
            throw new Exception("Commands are null");
        }
        if (timeout == null) {
            throw new Exception("Timeout is null");
        }
        if (dtr == null) {
            throw new Exception("DTR is null");
        }
        int tOut = new Integer(timeout).intValue();
        this.timeout = tOut;

        if (dtr.equalsIgnoreCase("true")) {
            com.setDTR(true);
        }
        if (dtr.equalsIgnoreCase("false")) {
            com.setDTR(false);
        } 
        if (action.equalsIgnoreCase(JSerialPort.READ_COMMAND)) {
            return read();
        } else if (action.equalsIgnoreCase(JSerialPort.WRITE_READ_COMMAND)) {
            return read(cmds);
        } else if (action.equalsIgnoreCase(JSerialPort.WRITE_COMMAND)) {
            return write(cmds);
        } else {
            return null;
        }
    }
    /**
     * 
     * @param cmd
     *            this will be comma delimited seq of cmds
     */
    private String write(String cmd) throws Exception {
        com.write(urlCmdsToBytes(cmd));
        return CMD_ACK;
    }

    private String read(String cmd) throws Exception {
        write(cmd);
        Utils.pause(timeout);
        return read();
    }

    private String read() {
        return com.readString();
    }

    public void close() {
        com.close();
    }
    private byte[] urlCmdsToBytes(String command) {
        String[] commands = command.split(",");
        byte[] cmds = new byte[commands.length];
        for (int x = 0; x < commands.length; x++) {
            int i = new Integer(commands[x]).intValue();
            cmds[x] = (byte) i;
        }
        return cmds;
    }
    
    
}