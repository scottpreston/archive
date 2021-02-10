package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class PanTiltSpeed extends PanTilt {


	public PanTiltSpeed(JSerialPort sPort) throws Exception {
		super(sPort);
	}

	// move slowly horizontally
	public void horzSlow(int pos) throws Exception {
	    // pan left or right?
		if (pos > getHorzPos()) {
		    // move until at position
			while (getHorzPos() < pos) {			    
				if ((getHorzPos() + getStepSize()) > pos) {
					setHorzPos(pos);
				} else {
					setHorzPos(getHorzPos() + getStepSize());
				}
				horz(getHorzPos());
				Utils.pause(getMoveDelay());
			}
		// pan the other way
		} else {		    
			while (getHorzPos() > pos) {
				if ((getHorzPos() - getStepSize()) < pos) {
					setHorzPos(pos);
				} else {
					setHorzPos(getHorzPos() - getStepSize());
				}
				horz(getHorzPos());
				Utils.pause(getMoveDelay());
			}

		}
	}
	// move slowly vertically
	public void vertSlow(int pos) throws Exception {
	    // tilt up or down
		if (pos > getVertPos()) {
			while (getVertPos() < pos) {
				if ((getVertPos() + getStepSize()) > pos) {
					setVertPos(pos);
				} else {
					setVertPos(getVertPos() + getStepSize());
				}
				vert(getVertPos());
				Utils.pause(getMoveDelay());
			}
		// tilt the otherway
		} else {
			while (getVertPos() > pos) {
				if ((getVertPos() - getStepSize()) < pos) {
					setVertPos(pos);
				} else {
					setVertPos(getVertPos() - getStepSize());
				}
				vert(getVertPos());
				Utils.pause(getMoveDelay());
			}

		}
	}
	
	//	 sample program
	public static void main(String[] args) {
	    try {
		    // get instance of SingleSerialPort
		    JSerialPort sPort = (JSerialPort)SingleSerialPort.getInstance(1);
		    // create instance of PanTiltSpeed
			PanTiltSpeed pts = new PanTiltSpeed(sPort);
			// pan left
			pts.horzSlow(PanTilt.MAX_LEFT);
			// pan right
			pts.horzSlow(PanTilt.MAX_RIGHT);
			// reset
			pts.reset();
			// tilt up
			pts.vertSlow(PanTilt.MAX_UP);
			// tilt down
			pts.vertSlow(PanTilt.MAX_DOWN);
			// reset
			pts.reset();
			// close serial port
			sPort.close();
		} catch (Exception e) {
		    // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
		}
	}
	
}