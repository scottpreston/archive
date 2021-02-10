package com.scottpreston.javarobot.chapter2;

public class SimpleStamp extends Controller {

    public static final int COMMAND_A = 'd';
    public static final int COMMAND_B = 'e';
    public static final int COMMAND_TERM = '!';

    public SimpleStamp(int id) throws Exception {
        super(SingleSerialPort.getInstance(id));
    }

    public String cmdA() throws Exception {
        byte[] a = new byte[] { (byte) COMMAND_A, (byte) COMMAND_TERM };
        return execute(a, 150);
    }

    public String cmdB() throws Exception {
        byte[] b = new byte[] { (byte) COMMAND_B, (byte) COMMAND_TERM };
        return execute(b, 150);

    }

    public static void main(String[] args) throws Exception {
        SimpleStamp t = new SimpleStamp(8);
        System.out.println(t.cmdA());
        System.out.println(t.cmdB());
        t.close();
    }
}
