package com.scottpreston.javarobot.chapter3;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;

public class PanTilt{


    // connected to pin 6 of MinSSC-II
	public static final int HORZ_SERVO = 6;
	// connected to pin 7 of MinSSC-II
	public static final int VERT_SERVO = 7;	
	private int horzPos = SSCProtocol.NEUTRAL;
	private int vertPos = SSCProtocol.NEUTRAL;

	// should set these to the best limits of your pan/tilt system
	public static final int MAX_UP = 145;
	public static final int MAX_DOWN = 45;
	public static final int MAX_RIGHT = 235;
	public static final int MAX_LEFT = 25;
	public static final int VERT_NEUTRAL = 95;
	public static final int HORZ_NEUTRAL = 140;
	// 3 millieconds at 9600 baud
	public static final int MIN_STEP_SIZE = 3;
	// 2 milliseconds for standard servo
	public static final int MIN_DELAY_SIZE = 2;

	// delay in milliseconds between move
	private int moveDelay = 50;
	// byte size of single step
	private int  stepSize = MIN_STEP_SIZE;
	private int speed = 0;

	// MiniSSC doing work
	private MiniSsc ssc;
	
	// constructor takes JSerialPort
	public PanTilt(JSerialPort sPort) throws Exception{
		 ssc = new MiniSsc(sPort);
	}

	// move both servos to positions
	private void move() throws Exception {
		horz(horzPos);
		vert(vertPos);
	}
	// move both servos with input parameters
	// h = horizontal servo
	// v = vertical servo
	public void moveBoth(int h, int v) throws Exception{
	    // set private fields
	    horzPos = h;
	    vertPos = v;
	    // move
	    move();
	}
	public void horz(int pos)  throws Exception{
	    // check to see if position within limits
		if (pos < MAX_LEFT || pos > MAX_RIGHT ) {
			throw new Exception("Out of horizontal range.");
		}
		// set pos
		horzPos = pos;
		// move
		ssc.move(HORZ_SERVO,pos);
	}
	
	public void horzDegree(int angle)  throws Exception{
	    // check to see if angle is within limites of 0-180
		if (angle <0 || angle > 180) {
			throw new Exception("Out of range, angle 0-180.");
		}
		// convert fraction of 255
		double theta = ((double)angle/180 ) * 255.0 ;
		// move
		horz((int)theta);
		
	}

	public void vert(int pos)  throws Exception{
		if (pos < MAX_DOWN || pos > MAX_UP ) {
			throw new Exception("Out of vertical range.");
		}
		vertPos = pos;
		ssc.move(VERT_SERVO,pos);
	}
	
	public void vertDegree(int angle)  throws Exception{
		if (angle <0 || angle > 180) {
			throw new Exception("Out of range, angle 0-180.");
		}
		double theta = ((double)angle/180 ) * 255.0 ;
		vert((int)theta);
		
	}
	
	// reset to neutral position
	public void reset( ) throws Exception{
		horzPos = HORZ_NEUTRAL;
		vertPos = VERT_NEUTRAL;
		move();
	}
	
	// move right specific step size
	public void moveRight(int size) throws Exception{
		horz(horzPos+size);
	}
	// move right current stepSize
	public void moveRight()throws Exception {
		moveRight(stepSize);
	}

	public void moveLeft(int size) throws Exception{
		horz(horzPos-size);
	}
	public void moveLeft()throws Exception {
		moveLeft(stepSize);
	}
	
	public void moveUp(int size) throws Exception{
		vert(vertPos+size);
	}
	public void moveUp()throws Exception {
		moveUp(stepSize);
	}
	public void moveDown(int size) throws Exception{
		vert(vertPos-size);
	}
	public void moveDown()throws Exception {
		moveDown(stepSize);
	}
	
	// accessor
	public int getHorzPos() {
		return horzPos;
	}
	// setter
	public void setHorzPos(int horzPos) {
		this.horzPos = horzPos;
	}
	// accessor
	public int getVertPos() {
		return vertPos;
	}
	// setter
	public void setVertPos(int vertPos) {
		this.vertPos = vertPos;
	}
	// accessor
	public int getSpeed() {
		return speed;
	}
	// setter
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	// servo timing setter
	// stepSize = size of the step as long as it's not minimim step size
	// moveDelay = timing delay between steps
	public void setServoTiming(int stepSize, int moveDelay) throws Exception {
	    // ensure will work
		if (stepSize < MIN_STEP_SIZE) {
			throw new Exception("Step size not possible at 9600 baud.");
		}
		if (moveDelay < (stepSize * MIN_DELAY_SIZE)) {
			throw new Exception("Move delay not practicle for given step size.");
		}
		this.stepSize = stepSize;
		this.moveDelay = moveDelay;		
	}

	public int getMoveDelay() {
		return moveDelay;
	}

	public int getStepSize() {
		return stepSize;
	}
	
	// sample program
	public static void main(String[] args) {
	    try {
		    // get instance of SingleSerialPort
		    JSerialPort sPort = (JSerialPort)SingleSerialPort.getInstance(1);
		    // create instance of PanTilt
			PanTilt pt = new PanTilt(sPort);
			// pan left until exception is thrown
			while (true) {
			    try {
			    pt.moveLeft();
			    } catch (Exception e) {
			        break;
			    }
			}
			// pan right
			while (true) {
			    try {
			    pt.moveRight();
			    } catch (Exception e) {
			        break;
			    }
			}
			// reset head
			pt.reset();
			// tilt up
			while (true) {
			    try {
			    pt.moveUp();
			    } catch (Exception e) {
			        break;
			    }
			}
			// tilt down
			while (true) {
			    try {
			    pt.moveDown();
			    } catch (Exception e) {
			        break;
			    }
			}
			// reset head
			pt.reset();	
			// close serial port
			sPort.close();
		} catch (Exception e) {
		    // print stack trace and exit
            e.printStackTrace();
            System.exit(1);
		}
	}
}
