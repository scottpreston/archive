package com.scottpreston.javarobot.chapter3;

import java.util.ArrayList;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class ComplexArm{

    // servo positions for differnt servos
    // shoulder 1
	private ServoPosition2 s1;
	// shoulder 2
	private ServoPosition2 s2;
	// elbow
	private ServoPosition2 e;
	// wrist
	private ServoPosition2 w;
	// grip 1
	private ServoPosition2 g1;
	// grip 2
	private ServoPosition2 g2;
	// LM32 worker
	private LM32 lm32;
	// list of servos
	private ArrayList servos;

	public ComplexArm(JSerialPort serialPort) throws Exception {
		lm32 = new LM32(serialPort);
		// put in seperate method for cleanliness
		init();
	}

	private void init() throws Exception {

		// note the position pin is not used for the LM 32 because it remembers
		// the position
		s1 = new ServoPosition2(0);
		s2 = new ServoPosition2(1);
		e = new ServoPosition2(2);
		w = new ServoPosition2(3);
		g1 = new ServoPosition2(4);
		g2 = new ServoPosition2(5);
		// add to collection for easier checks
		servos.add(s1);
		servos.add(s2);
		servos.add(e);
		servos.add(w);
		servos.add(g1);
		servos.add(g2);
	}

	public void rest() throws Exception {
		for (int i = 0; i < servos.size(); i++) {
			ServoPosition2 tmpPos = (ServoPosition2) servos.get(i);
			lm32.sscCmd(tmpPos.pin, tmpPos.neutral);
		}
		lm32.move(1000);
	}

	// move to position A (experimentally determined)
	public void posA() throws Exception {
		lm32.sscCmd(s1.pin, 50);
		lm32.sscCmd(s2.pin, 135);
		lm32.sscCmd(e.pin, 75);
		lm32.sscCmd(w.pin, 200);
		lm32.sscCmd(g1.pin, 150);
		lm32.sscCmd(g2.pin, 255);
		lm32.move(1000); // move in 1 second
	}

	// move to position B (experimentally determined)
	public void posB() throws Exception {

		lm32.sscCmd(s1.pin, 220);
		lm32.sscCmd(s2.pin, 135);
		lm32.sscCmd(e.pin, 100);
		lm32.sscCmd(w.pin, 190);
		lm32.sscCmd(g1.pin, 130);
		lm32.sscCmd(g2.pin, 255);
		lm32.move(1000); // move in 1 second

	}




	public static void main(String[] args) {
		try {
		    // get single serial port instance
            JSerialPort sPort = (JSerialPort) SingleSerialPort.getInstance(1);
            // create new ComplexArm
			ComplexArm arm = new ComplexArm(sPort);
			arm.rest();
			arm.posA();
			arm.posB();
			sPort.close();
		} catch (Exception e) {
            // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
		}
	}
}