package com.scottpreston.javarobot.chapter2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;

public class StandardSerialPort implements SerialPortEventListener,
        JSerialPort {

    private Enumeration portList;
    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private OutputStream outputStream;
    private InputStream inputStream;
    private byte[] readBuffer;
    private boolean dataIn = false;
    private byte[] currentWrite;

    public StandardSerialPort(int id) throws Exception {
        init(id, 9600);
    }

    public StandardSerialPort(int id, int baud) throws Exception {
        init(id, baud);
    }

    private void init(int comID, int baud) {
        String comIdAsString = new Integer(comID).toString();
        try {
            portList = CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    if (portId.getName().endsWith(comIdAsString)) {
                        // create serial port
                        serialPort = (SerialPort) portId.open(
                                "StandardSerialPort", 3000);
                        // set config parms
                        serialPort.setSerialPortParams(baud,
                                SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                        serialPort
                                .setFlowControlMode(javax.comm.SerialPort.FLOWCONTROL_NONE);
                        Utils.pause(50);
                        // config output stream
                        outputStream = serialPort.getOutputStream();
                        // config input stream
                        inputStream = serialPort.getInputStream();
                        // add events listener
                        serialPort.addEventListener(this);
                        serialPort.notifyOnDataAvailable(true);
                        Thread.sleep(50); // waits till ports change state.
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    int data = 0; // nothing ready to read
    
    public byte[] read() {
        //data = 1;
        Utils.log("read start");
        while (dataIn == false) {
         //   Utils.log("data="+data);
            try {
                Thread.sleep(1);
            } catch (Exception e) {
            }
        }
        Utils.log("exit read");
        data = 0;
        dataIn = false;
        return readBuffer;
    }

    public String readString() {
        byte[] b = read();
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            if (b[i] != 0) {
                int in = (int) b[i];
                if (in < 0) {
                    in = in + 256;
                }
                s.append(in);
                s.append("~");
            }
        }
        s.deleteCharAt(s.length() - 1);
        return s.toString();
    }

    public void write(byte[] b) throws Exception {
        currentWrite = b;
        outputStream.write(b);
    }

    public void close() {
        serialPort.close();
    }

    public byte[] read(byte[] b) throws Exception {
        // not used
        return null;
    }

    public String readString(byte[] b) throws Exception {
        //      not used
        return null;

    }

    public void serialEvent(SerialPortEvent event) {

                
        if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            readBuffer = new byte[32];
            try {
                while (inputStream.available() > 0) {
                    inputStream.read(readBuffer);
                }
                Utils.log("bytein="+Utils.byteToString(readBuffer));
                int byteCount = 0;
                for (int i = 0; i < currentWrite.length; i++) {
                   if (currentWrite[i] == readBuffer[i]) {
                        byteCount++;
                    }
                }
                if (byteCount != currentWrite.length) {
                    dataIn = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setTimeout(int timeout) {
        // not used
    }

    public void setDTR(boolean dtr) {
        serialPort.setDTR(dtr);
    }

    public String getName() {
        return serialPort.getName();
    }

}