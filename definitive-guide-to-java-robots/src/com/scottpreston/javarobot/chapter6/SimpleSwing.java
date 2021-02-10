package com.scottpreston.javarobot.chapter6;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

public class SimpleSwing extends JFrame {

	public static final long serialVersionUID = 1;
	// constructor
	public SimpleSwing()  {
		// calls JFrame with title
		super("Java Robots Are Cool!");
		// set look & feel
	    WindowUtilities.setNativeLookAndFeel();
	    // closes
	    addWindowListener(new ExitListener());
	    // sets size
	    setSize(320, 240);
	    // sets pane of content
	    Container content = getContentPane();
	    // sets color to white
	    content.setBackground(Color.white);
	    // shows frame
	    setVisible(true);
	}

	  public static void main(String[] args) throws Exception{
	  	SimpleSwing test = new SimpleSwing();	   
	    
	  }
}
