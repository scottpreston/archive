package com.scottpreston.javarobot.chapter6;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

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
