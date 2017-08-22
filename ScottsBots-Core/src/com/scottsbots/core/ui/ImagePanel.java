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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;
/**
 * Used as a container for the image so it can be refreshed vs. 
 * ImageIcon or something else.
 * 
 * @author scott
 *
 */
public class ImagePanel extends JPanel {
    
    public Image image = null;

    public ImagePanel(){
    	init(320,240);
    }
    public ImagePanel(int w,int h) {
        init(w,h);
    }

    private void init(int w,int h) {
    	setSize(w, h);
        setMinimumSize(new Dimension(w, h));
        setMaximumSize(new Dimension(w, h));
    }
    public void setImage(Image img) {
        image = img;
        repaint();
    }

    public void paint(Graphics g) {
        if (image != null) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0,this.getWidth(),getHeight());
            g.drawImage(image, 0, 0, this);
        }
    }
}
