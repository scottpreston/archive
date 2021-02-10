package com.scottpreston.javarobot.chapter9;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ServoSlider extends JSlider {

	private JTextField textField;
	public static final long serialVersionUID = 1;
	
	public ServoSlider() {
		super(JSlider.VERTICAL, 0, 255, 128);
		this.setMajorTickSpacing(50);
		this.setMinorTickSpacing(1);
		this.setPaintTicks(true);
		this.setPaintLabels(true);
		this.setLabelTable(this.createStandardLabels(50));		
		this.addChangeListener(new ChangeListener() {
	    	// This method is called whenever the slider's value is changed
	        public void stateChanged(ChangeEvent evt) {
	            JSlider slider = (JSlider)evt.getSource();		    
	            if (!slider.getValueIsAdjusting()) {	
	                // Get new value
	                int value = slider.getValue();
	                textField.setText(value+"");
	                SliderFieldCombo sfc = (SliderFieldCombo)getParent();
	                sfc.moveServo(value);	            
	            }
	        }
	    });

	}
	
	public void bind(JTextField tf) {
		textField = tf;
		textField.setText(this.getValue()+"");
	}

}