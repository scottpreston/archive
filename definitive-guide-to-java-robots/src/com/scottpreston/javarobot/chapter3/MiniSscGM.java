package com.scottpreston.javarobot.chapter3;

import java.util.ArrayList;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;

public class MiniSscGM extends Ssc implements SSCProtocol, GroupMoveProtocol {

    // store commands in list
	private ArrayList commands = new ArrayList();
	// store servos in list
	private ArrayList servos = new ArrayList();

	// constructor takes JSerialPort as parameter
	public MiniSscGM(JSerialPort jSerialPort) throws Exception {
		super(jSerialPort);
		setMaxPin(7);
		// create servos
		createServos();
	}
	// add servos to list
	private void createServos() throws Exception{
		for (int i = 0; i < getMaxPin() + 1; i++) {
			ServoPosition svo = new ServoPosition(i, SSCProtocol.NEUTRAL);
			// index will be same as id.
			servos.add(svo); 
		}
	}

	public void sscCmd(int ch, int pos) throws Exception {
	    // calls overridden move method later in this class
		move(ch, pos);
	}

	public void cmd(int ch, int pos, int spd) throws Exception {
		// not going to implement the spd variable for the MiniSSC-II
		ServoPosition svoPos = new ServoPosition(ch, pos);
		commands.add(svoPos);
	}

	public void move(int time) throws Exception {
		// all servo moves will have a minimum step-size of 3

		/*
		 * gets maximum difference between current positions and new position
		 */
		int maxDiff = 0;
		for (int i = 0; i < commands.size(); i++) {
			ServoPosition newPos = (ServoPosition) commands.get(i);
			ServoPosition curPos = (ServoPosition) servos.get(newPos.pin);
			int tmpDiff = Math.abs(newPos.pos - curPos.pos);
			if (tmpDiff > maxDiff) {
				maxDiff = tmpDiff;
			}
		}
		// total steps since 3 is min size.
		double totalSteps = ((double) maxDiff / 3.0);
		// calculate pause time
		// total time of move divded by total steps
		int pauseTime = (int) ((double) time / totalSteps);

		// loop until total differnce between all servos 
		// current position and goal position is zero
		while (getTotalDiff() > 0) {

			for (int i = 0; i < commands.size(); i++) {
				ServoPosition newPos = (ServoPosition) commands.get(i);
				ServoPosition curPos = (ServoPosition) servos.get(newPos.pin);
				int tmpDiff = Math.abs(newPos.pos - curPos.pos);
				if (newPos.pos > curPos.pos) {
					if (tmpDiff > 2) {
						curPos.pos = curPos.pos + 3;
					} else {
						curPos.pos = newPos.pos;
					}
				} else if (newPos.pos < curPos.pos) {
					if (tmpDiff > 2) {
						curPos.pos = curPos.pos - 3;
					} else {
						curPos.pos = newPos.pos;
					}
				}
				// move current servo position plus or minus 3
				move(curPos.pin, curPos.pos);
				Utils.pause(pauseTime);
			}
		}
		// resets commands list.
		commands = new ArrayList();
	}

	// helper method to get difference
	private int getTotalDiff() {
		int totalDiff = 0;
		for (int i = 0; i < commands.size(); i++) {
			ServoPosition newPos = (ServoPosition) commands.get(i);
			ServoPosition curPos = (ServoPosition) servos.get(newPos.pin);
			int tmpDiff = Math.abs(newPos.pos - curPos.pos);
			totalDiff = totalDiff + tmpDiff;
		}
		return totalDiff;
	}


	// sample program same as LM32
	public static void main(String[] args) {
	    try {
            // get single serial port instance
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            // create new LM32
            MiniSscGM miniSscGM = new MiniSscGM(sPort);
            // sets position for servo at pin 0
            miniSscGM.sscCmd(0, 100);
            // sets position for servo at pin 1
            miniSscGM.sscCmd(1, 200);
            // tells the servos to move there in 1 second.            
            miniSscGM.move(1000);
            // close serial port
            sPort.close();
        } catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
        }
	    
	}
}