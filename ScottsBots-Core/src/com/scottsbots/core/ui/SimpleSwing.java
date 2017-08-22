/*
 * (C) Copyright 2000-2011, by Scott Preston and Preston Research LLC
 *
 *  Project Info:  http://www.scottsbots.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.scottsbots.core.ui;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

/**
 * Simple parent for swing based programs.
 * 
 * @author scott
 *
 */
public class SimpleSwing extends JFrame {

	public static final long serialVersionUID = 1;
	// constructor
	public SimpleSwing()  {
		// calls JFrame with title
		super("ScottBots.Com - Robotics Programming 101");
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
