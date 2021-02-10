package com.scottpreston.javarobot.chapter9;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class SliderFieldCombo extends Container {
	
	private ServoSlider servoSlider;
	private JTextField jTextField;
	private int id;
	
	public SliderFieldCombo(int i) {
	    id = i;
		servoSlider = new ServoSlider();
		jTextField = new JTextField(3);
		servoSlider.bind(jTextField);
		JLabel label = new  JLabel("Servo " + i);
		this.setLayout(new FlowLayout()); 
		this.add(label);
	    this.add(servoSlider);
	    this.add(jTextField);
	}
	
	public void moveServo(int value) {
	    SscPanel sscPanel = (SscPanel)this.getParent();
	    sscPanel.moveServo(id,value);
	}

	public JTextField getJTextField() {
		return jTextField;
	}
	public void setJTextField(JTextField textField) {
		jTextField = textField;
	}
	public ServoSlider getServoSlider() {
		return servoSlider;
	}
	public void setServoSlider(ServoSlider servoSlider) {
		this.servoSlider = servoSlider;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
