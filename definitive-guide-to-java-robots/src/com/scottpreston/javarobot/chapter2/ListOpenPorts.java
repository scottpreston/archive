package com.scottpreston.javarobot.chapter2;

import java.util.Enumeration;

import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;

public class ListOpenPorts {

    private CommPortIdentifier portId;
    private Enumeration portList;

    public ListOpenPorts() {
        portList = CommPortIdentifier.getPortIdentifiers();
    }

    public void list() throws InterruptedException, PortInUseException {
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.print("Serial Port = ");
            }
            if (portId.getPortType() == CommPortIdentifier.PORT_PARALLEL) {
                System.out.print("Parallel Port = ");
            }
            System.out.print(portId.getName() + ", ");

            CommPort port = portId.open("OpenTest", 20);
            Thread.sleep(250);
            System.out.println("Opened." + portId.getCurrentOwner());
            port.close();
        }
    }

    public static void main(String[] args) {
        try {
            ListOpenPorts openPorts = new ListOpenPorts();
            openPorts.list();
        } catch (InterruptedException ie) {
            System.out.println("Unable to sleep.");
        } catch (PortInUseException pe) {
            System.out.println("Failed. Port In Use.");
        }

    }
}