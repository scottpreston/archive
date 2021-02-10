package com.scottpreston.javarobot.chapter6;

import java.awt.Color;

public class ColorGram implements Cloneable{
	
    private double[] colorGram;
    
	public static ColorGram COKE = new ColorGram (new double[] {
	        0.0,0.0,0.0,104.0,
	        0.0,0.0,0.0,158.0,
	        0.0,0.0,0.0,0.0,
	        1.0,0.0,0.0,-22.0,
	        0.0,0.0,0.0,0.0,
	        1.0,0.0,0.0,-31.0
	        });
    
	public static ColorGram SEVEN_UP = new ColorGram (new double[] {
	        0.0,0.0,0.0,0.0,
	        0.0,1.0,0.0,-38.0,
	        0.0,0.0,0.0,90.0,
	        0.0,0.0,0.0,147.0,
	        0.0,0.0,0.0,0.0,
	        0.0,1.0,0.0,-6.0
			});
	
	public static ColorGram PEPSI = new ColorGram (new double[] {
	        0.0,0.0,0.0,0.0,
	        0.0,0.0,1.0,-26.0,
	        0.0,0.0,0.0,0.0,
	        0.0,0.0,1.0,-19.0,
	        0.0,0.0,0.0,87.0,
	        0.0,0.0,0.0,162.0
			});
    
	public ColorGram() {
	// blank
		colorGram = new double[] { 
	    		0, 0, 0, 0, // min
	            0, 0, 0, 255, // max red color
	            0, 0, 0, 0, // min green color
	            0, 0, 0, 255, // max green color
	            0, 0, 0, 0, // min blue color
	            0, 0, 0, 255 };
	}
	


    public ColorGram(double[] cg) {
        colorGram = cg;
    }

    public int getRedMin(Color c) {
        return getColor(c, 0);
    }

    public int getRedMax(Color c) {
        return getColor(c, 4);
    }

    public int getGreenMin(Color c) {
        return getColor(c, 8);
    }

    public int getGreenMax(Color c) {
        return getColor(c, 12);
    }

    public int getBlueMin(Color c) {
        return getColor(c, 16);
    }

    public int getBlueMax(Color c) {
        return getColor(c, 20);
    }
    
    public double getIndex(int index) {
    	return colorGram[index];
    }
    public void setMins(int[] mins) {
    	colorGram[3] = mins[0]; // red
    	colorGram[3+8] = mins[1]; // green
    	colorGram[3+16] = mins[2]; // blue    	
    }
    
    public void setMaxs(int[] maxs) {
    	colorGram[7] = maxs[0]; // red
    	colorGram[7+8] = maxs[1]; // green
    	colorGram[7+16] = maxs[2]; // blue    	
    }
    
    // column == r,g,b if primary color
    // row = r,g,b of secondary color
    // constant value
    public void setRatio(int column, int row, double value) {
    	// rows will be 2,4,6
    	colorGram[(column + ((row-1)*4))-1] = 1;
    	colorGram[(row*4) -1] = value;    	    	
    }


    private int getColor(Color c, int index) {
        int out = (int) (c.getRed() * colorGram[index] 
				+ c.getGreen() * colorGram[index + 1] 
                + c.getBlue() * colorGram[index + 2] 
                + colorGram[index + 3]);
        if (out > 255)
            out = 255;
        if (out < 0)
            out = 0;

        return out;
    }

    public String toString() {
        StringBuffer out = new StringBuffer("ColorGram =  {\n");
        for (int x = 0; x < colorGram.length; x++) {
            out.append(colorGram[x]);
            if ((x % 4) == 3) {
                if (x == colorGram.length) {
                    out.append("\n");
                } else {
                    out.append(",\n");
                }
            } else {
                out.append(",");
            }
        }
        out.append("}");
        return out.toString();
    }

    public boolean isMatch(Color c) {

        boolean hit = false;
        int count = 0;
        // eliminate black since it's 0
        if (c.getRed() > getRedMin(c) && c.getRed() <= getRedMax(c)) {
            count++;
        }
        if (c.getGreen() > getGreenMin(c) && c.getGreen() <= getGreenMax(c)) {
            count++;
        }
        if (c.getBlue() > getBlueMin(c) && c.getBlue() <= getBlueMax(c)) {
            count++;
        }
        if (count > 2) {
            hit = true;
        }
        return hit;
    }
    
	public double[] getColorGram() {
		return colorGram;
	}
	public void setColorGram(double[] colorGram) {
		this.colorGram = colorGram;
	}
	
	public Object clone() {
		double[] newCg = new double[24];
		for (int d=0;d<24;d++) {
			newCg[d] = colorGram[d];
		}
		return new ColorGram(newCg);
	}
}