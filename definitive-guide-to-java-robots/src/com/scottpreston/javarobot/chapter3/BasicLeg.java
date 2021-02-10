package com.scottpreston.javarobot.chapter3;

public class BasicLeg {

    // each leg has 2 servos
	private ServoPosition2 vertServo;
	private ServoPosition2 horzServo;
	
	// generic constructor just taking pins
	public BasicLeg(int vPin, int hPin)throws Exception {
	    vertServo = new ServoPosition2(vPin);
	    horzServo = new ServoPosition2(hPin);
	}
	// constructors with ServoPosition2's
	public BasicLeg(ServoPosition2 vertServo, ServoPosition2 horzServo) {
		this.vertServo = vertServo;
		this.horzServo = horzServo;
	}	

	// move leg up
	public String up() {
		return LM32.createCmd(vertServo.pin,vertServo.max,LM32.DEFAULT_SPEED);
	}
	// move leg down
	public String down() {
		return LM32.createCmd(vertServo.pin,vertServo.min,LM32.DEFAULT_SPEED);
	}
	// move leg forward
	public String forward() {
		return LM32.createCmd(horzServo.pin,horzServo.max,LM32.DEFAULT_SPEED);
	}
	// move leg backward
	public String backward() {
		return LM32.createCmd(horzServo.pin,horzServo.min,LM32.DEFAULT_SPEED);
	}
	// reset horz servo
	public String neutralHorz() {		
		return LM32.createCmd(horzServo.pin,horzServo.neutral,LM32.DEFAULT_SPEED);
	}
	
	// reset vert servo
	public String neutralVert(){		
		return LM32.createCmd(vertServo.pin,vertServo.neutral,LM32.DEFAULT_SPEED);
	}
	
	// reset both servos
	public String neutral() {
		return neutralVert() + neutralHorz();
	}
	
}
