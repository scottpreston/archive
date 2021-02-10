package com.scottpreston.javarobot.chapter2;

public class StampSerialTest {

    private StandardSerialPort sPort;

    public StampSerialTest() throws Exception {
        sPort = new StandardSerialPort(1);
        sPort.setDTR(false);
        Utils.pause(125);

    }

    public String test(byte something) throws Exception {
        byte[] a = { something };
        sPort.write(a);
        Utils.pause(100);
        return sPort.readString();
    }

    public void close() {
        sPort.close();
    }

    public static void main(String[] args) {
        try {
            StampSerialTest sst = new StampSerialTest();
            System.out.println("From Stamp:" + sst.test((byte)101));
            System.out.println("From Stamp:" + sst.test((byte)102));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Done.");
    }

}