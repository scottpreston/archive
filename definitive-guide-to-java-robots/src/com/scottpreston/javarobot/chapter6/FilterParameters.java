package com.scottpreston.javarobot.chapter6;

import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class FilterParameters {
	
	// name to identify
	private String name;
	// image to work with
	private BufferedImage image;
	// paramters
	private ArrayList parameters = new ArrayList();

	// constructor
	public FilterParameters(String n) {
		name = n;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public ArrayList getParameters() {
		return parameters;
	}
	
	public void addParameters(Object parameter) {
		parameters.add(parameter);
	}
	
	public String getName() {
		return name;
	}
}
