package com.scottpreston.javarobot.chapter6;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;

import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

public class ImageProcessor {

    private static BufferedImage lastImage;
    
    public static final String FILTER_RGB_TO_GREY = "1";
    public static final String FILTER_MOTION = "2";
    public static final String FILTER_COLOR = "3";
    public static final String FILTER_THRESHHOLD = "4";
    public static final String FILTER_THRESHHOLD_COLOR = "5";
    public static final String FILTER_COLOR_RATIO = "6";
    public static final String FILTER_EDGE = "7";
    public static final String FILTER_SMOOTH = "8";
    public static final String FILTER_SHARP = "9";
    public static final String FILTER_RESIZE = "10";
    public static final String FILTER_HOUGH_LINES = "11";

    // generic method for processing
    public BufferedImage process(FilterParameters parms) {
        BufferedImage dstImg = null;
        if (parms.getName().equalsIgnoreCase(FILTER_RGB_TO_GREY)) {
            dstImg = rgbToGrey(parms.getImage());
        }
        if (parms.getName().equalsIgnoreCase(FILTER_MOTION)) {
            dstImg = backgroundSubtract(parms.getImage());
        }
        if (parms.getName().equalsIgnoreCase(FILTER_THRESHHOLD)) {
            int min = ((Integer) parms.getParameters().get(0)).intValue();
            int max = ((Integer) parms.getParameters().get(1)).intValue();
            Boolean transparent = Boolean.FALSE;
            if (parms.getParameters().get(2) != null) {
                transparent = (Boolean) parms.getParameters().get(2);
            }
            dstImg = threshold(parms.getImage(), min, max, transparent.booleanValue());
        }
        if (parms.getName().equalsIgnoreCase(FILTER_THRESHHOLD_COLOR)) {
            int min = ((Integer) parms.getParameters().get(0)).intValue();
            int max = ((Integer) parms.getParameters().get(1)).intValue();
            Color c = (Color) parms.getParameters().get(2);
            dstImg = thresholdColor(parms.getImage(), min, max, c);
        }
        if (parms.getName().equalsIgnoreCase(FILTER_COLOR)) {
            Color c = (Color) parms.getParameters().get(0);
            dstImg = filterColor(parms.getImage(), c);
        }
        if (parms.getName().equalsIgnoreCase(FILTER_COLOR_RATIO)) {
            ColorGram cg = (ColorGram) parms.getParameters().get(0);
            dstImg = colorRatio(parms.getImage(), cg);
        }
        if (parms.getName().equalsIgnoreCase(FILTER_EDGE)) {
            dstImg = sobelGradMag(parms.getImage());
        }
        if (parms.getName().equalsIgnoreCase(FILTER_SMOOTH)) {
            dstImg = smooth(parms.getImage());
        }
        if (parms.getName().equalsIgnoreCase(FILTER_SHARP)) {
            dstImg = sharpen(parms.getImage());
        }
        if (parms.getName().equalsIgnoreCase(FILTER_RESIZE)) {
            int w = ((Integer) parms.getParameters().get(0)).intValue();
            int h = ((Integer) parms.getParameters().get(1)).intValue();
            dstImg = resize(parms.getImage(), w, h);
        }
        if (parms.getName().equalsIgnoreCase(FILTER_HOUGH_LINES)) {
            dstImg = getHoughLines(parms.getImage());
        }

        return dstImg;
    }

    // to greyscale image
    public BufferedImage rgbToGrey(BufferedImage srcImg) {
        int h = srcImg.getHeight();
        int w = srcImg.getWidth();
        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int srcPixel = srcImg.getRGB(x, y);
                Color c = new Color(srcPixel);
                Color g = getGrey(c);
                dstImg.setRGB(x, y, g.getRGB());
            }
        }
        return dstImg;
    }

    // return grayscale equivalent of pixel as color
    public Color getGrey(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int gray = (int) ((r + g + b) / 3.0);
        return new Color(gray, gray, gray);
    }

    //return greyscale equivalent of pixel as int
    public int getGrey(int colorInt) {
        return getGrey(new Color(colorInt)).getRed();
    }

    // get motion by subtracting background between current old image
    public BufferedImage backgroundSubtract(BufferedImage srcImg) {

        int h = srcImg.getHeight();
        int w = srcImg.getWidth();

        // create dst image
        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        long sum = 0;
        if (lastImage != null) {
            for (int y = 0; y < h; ++y) {
                for (int x = 0; x < w; ++x) {
                    // get grey of image
                    int srcPixel = getGrey(srcImg.getRGB(x, y));
                    // get color of last image
                    int diffPixel = getGrey(lastImage.getRGB(x, y));
                    // calculate differnece
                    int diff = Math.abs(srcPixel - diffPixel);
                    // make difference color
                    if (diff>25 ){// 10% of total color range of 255
                    	sum = sum + 1;
                    	dstImg.setRGB(x, y, Color.WHITE.getRGB());
                    } else {
                    	dstImg.setRGB(x, y, Color.BLACK.getRGB());
                    }
                }
            }
        }
        // set last frame
        lastImage = srcImg;
        System.out.println("diff size="+sum);
        return dstImg;
    }

    // gets threshold
    public BufferedImage threshold(BufferedImage srcImg, int min, int max, boolean transparent) {

        // get h & w
        int h = srcImg.getHeight();
        int w = srcImg.getWidth();
        // new image for processing
        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                // get color
                int srcPixel = srcImg.getRGB(x, y);
                // get grey of color
                int colorValue = getGrey(srcPixel);
                // compair to threshhold & convert to binary
                if (colorValue >= min && colorValue <= max) {
                    if (transparent) {
                        dstImg.setRGB(x, y, srcPixel);                        
                    } else{
                        dstImg.setRGB(x, y, Color.WHITE.getRGB());    
                    }
                } else {
                    dstImg.setRGB(x, y, Color.BLACK.getRGB());
                }

            }
        }
        return dstImg;
    }

    public BufferedImage thresholdColor(BufferedImage srcImg, int min, int max,
            Color c) {
        // get h & w
        int h = srcImg.getHeight();
        int w = srcImg.getWidth();
        //destination image
        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        // get pixels
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                // get color
                int srcPixel = srcImg.getRGB(x, y);
                int colorValue = 0;
                // get color values for color sent
                if (c == null) {
                    colorValue = getGrey(srcPixel);
                } else if (c == Color.RED) {
                    colorValue = new Color(srcPixel).getRed();
                } else if (c == Color.GREEN) {
                    colorValue = new Color(srcPixel).getGreen();
                } else if (c == Color.BLUE) {
                    colorValue = new Color(srcPixel).getBlue();
                }
                // filter for color in range
                if (colorValue >= min && colorValue <= max) {
                    dstImg.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    dstImg.setRGB(x, y, Color.BLACK.getRGB());
                }

            }
        }
        // return image
        return dstImg;
    }

    public BufferedImage filterColor(BufferedImage srcImg, Color c) {

        int h = srcImg.getHeight();
        int w = srcImg.getWidth();

        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int srcPixel = srcImg.getRGB(x, y);
                int colorValue = 0;
                // gets colors
                if (c == null) {
                    colorValue = getGrey(srcPixel);
                } else if (c == Color.RED) {
                    colorValue = new Color(srcPixel).getRed();
                } else if (c == Color.GREEN) {
                    colorValue = new Color(srcPixel).getGreen();
                } else if (c == Color.BLUE) {
                    colorValue = new Color(srcPixel).getBlue();
                }
                // set that color as grey version
                dstImg.setRGB(x, y, new Color(colorValue, colorValue, colorValue)
                        .getRGB());
            }
        }
        // return image
        return dstImg;
    }

    public BufferedImage colorRatio(BufferedImage srcImg, ColorGram cg) {


        int h = srcImg.getHeight();
        int w = srcImg.getWidth();

        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        if (srcImg != null) {
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int srcPixel = srcImg.getRGB(x, y);
                Color c = new Color(srcPixel);
                // calls hard work done here.
                if (cg.isMatch(c)) {
                    // for real color
                    dstImg.setRGB(x, y, c.getRGB());
                    // for binary color
                    //dstImg.setRGB(x, y, Color.BLACK.getRGB());
                } else {
                    dstImg.setRGB(x, y, Color.BLACK.getRGB());
                }

            }
        }
        }
        return dstImg;
        
    }

    public int[] getMean(BufferedImage srcImg) {

        int h = srcImg.getHeight();
        int w = srcImg.getWidth();

        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        double red = 0;
        double green = 0;
        double blue = 0;

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int srcPixel = srcImg.getRGB(x, y);
                // tally total colors for 3 components
                red = red + new Color(srcPixel).getRed();
                green = green + new Color(srcPixel).getGreen();
                blue = blue + new Color(srcPixel).getBlue();
            }
        }
        // get averages
        int redAvg = (int) (red / (h * w));
        int greenAvg = (int) (green / (h * w));
        int blueAvg = (int) (blue / (h * w));
        System.out.println("color mean=" + redAvg + "," + greenAvg + "," + blueAvg);
        return new int[] { redAvg, greenAvg, blueAvg };
    }

    public int colorRatioCount(BufferedImage srcImg, ColorGram cg) {

        int h = srcImg.getHeight();
        int w = srcImg.getWidth();
        int count = 0;
        BufferedImage dstImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {
                int srcPixel = srcImg.getRGB(x, y);
                Color c = new Color(srcPixel);
                if (cg.isMatch(c)) {
                    count++;
                }
            }
        }
        return count;
    }

    public BufferedImage sobelGradMag(BufferedImage srcImg) {

        PlanarImage input = bufferedToPlanar(srcImg);
        KernelJAI vert = KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL;
        KernelJAI horz = KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL;
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(input);
        pb.add(vert);
        pb.add(horz);
        PlanarImage output = JAI.create("GradientMagnitude", pb).createInstance();
        return planarToBuffered(output);
    }

    public BufferedImage smooth(BufferedImage srcImg) {

        PlanarImage input = bufferedToPlanar(srcImg);
        float ninth = 1.0f / 9.0f;
        float[] k = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth };
        KernelJAI kern = new KernelJAI(3, 3, k);
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(input);
        pb.add(kern);
        PlanarImage output = JAI.create("Convolve", pb).createInstance();
        return planarToBuffered(output);
    }

    public BufferedImage sharpen(BufferedImage srcImg) {

        PlanarImage input = bufferedToPlanar(srcImg);
        float[] k = { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1f, 0.0f };
        KernelJAI kern = new KernelJAI(3, 3, k);
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(input);
        pb.add(kern);
        PlanarImage output = JAI.create("Convolve", pb).createInstance();
        return planarToBuffered(output);
    }

    public BufferedImage resize(BufferedImage srcImg, int targetW, int targetH) {
        
        // create new bufferedImage
        BufferedImage dstImg = new BufferedImage(targetW, targetH,
                BufferedImage.TYPE_INT_RGB);

        // create new canvas
        Graphics2D g = dstImg.createGraphics();
        g.setBackground(Color.BLACK);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        double sx = (double) targetW / srcImg.getWidth();
        double sy = (double) targetH / srcImg.getHeight();
        // draw src image on new object
        g.drawRenderedImage(srcImg, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        // return new image
        return dstImg;

    }

    private PlanarImage bufferedToPlanar(BufferedImage bImg) {
        Image awtImg = Toolkit.getDefaultToolkit().createImage(bImg.getSource());
        return JAI.create("awtimage", awtImg);

    }

    private BufferedImage planarToBuffered(PlanarImage pImg) {
        return pImg.getAsBufferedImage();
    }

    public int[] getHistogram(BufferedImage bufImg) {
        // Set up the parameters for the Histogram object.
        int[] bins = { 256, 256, 256 }; // The number of bins.
        double[] low = { 0.0D, 0.0D, 0.0D }; // The low value.
        double[] high = { 256.0D, 256.0D, 256.0D }; // The high value.

        // Construct the Histogram object.
        Histogram hist = new Histogram(bins, low, high);

        // Create the parameter block.
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(bufImg); // Specify the source image
        pb.add(null); // No ROI
        pb.add(1); // Sampling
        pb.add(1); // periods

        // Perform the histogram operation.
        PlanarImage output = (PlanarImage) JAI.create("histogram", pb, null);

        // Retrieve the histogram data.
        hist = (Histogram) output.getProperty("histogram");

        // Print 3-band histogram.
        int[] mean = new int[] {(int)hist.getMean()[0],
                (int)hist.getMean()[1],(int)hist.getMean()[2]};
        System.out.println("histogram2=" +  mean[0] + "," + mean[1] + ","
                +  mean[2]);
        return mean;
    }

    public BufferedImage getHoughLines(BufferedImage srcImg) {
    	
        double hough_thresh = .25;
        // since all points being traversed most lines will be found by 
        // only moving through 90 degrees
        // also i only care about grid lines
        int angle_range = 90;
        // angular resolution
        int aRes = 1; 
        
        int h = srcImg.getHeight()-1;
        int w = srcImg.getWidth()-1;

        // maximum radius of image is diagnal
        int pMax = (int) Math.sqrt((w * w) + (h * h)); 

        int[][] acc = new int[pMax][angle_range]; // create accumulator
        // pre-process image
        srcImg = smooth(srcImg);
        srcImg = sobelGradMag(srcImg);
        srcImg = threshold(srcImg, 127, 255, false);
        int maxPoints = 0;
        double totalPoints = 0;
        // move through image row by row form top to bototm
     
        for (int y = 1; y < h; ++y) {
            for (int x = 1; x < w; ++x) {
                int srcPixel = srcImg.getRGB(x, y);
                Color c = new Color(srcPixel);
                // build accumulator image
                // this will get the grey value of the image
                // even though i get red here, they are all same value.
                int colorValue = getGrey(c).getRed();
                // if color is white, then we want to move through all lines at this point
                if (colorValue == 255) {
                    // moving through each line from zero to max angle at resolution defined.
                    for (int theta = 0; theta < angle_range; theta = theta + aRes) {
                        // get the angle 0-90
                        double radians = (theta / 180.0) * Math.PI ;
                        // get potential line
                        // p = radius
                        // radians = angle
                        // x = x-coordinate
                        // y = y-coordinate
                        int p = (int) (Math.cos(radians) * x + Math
                                .sin(radians)
                                * y);
                        // get absolute radius
                        p = Math.abs(p);
                        // add the accumulator at this angle and radius
                        acc[p][theta] = acc[p][theta] + 1;
                        // want to add the total points accumulated
                        totalPoints = totalPoints + acc[p][theta];
                        // get the maximum number of points acummulated for a particular bin
                        if (acc[p][theta] > maxPoints) {
                            maxPoints = acc[p][theta];
                        }
                    }
                }
            }
        }
        // now work with paramters space of the accumulator to find the x,y 
        // cordinats of the lines
        // a = normalized to width
        // b = normalized height
        for (int b = 0; b < pMax; b++) { // all pixels 
            for (int a = 0; a < angle_range; a = a + aRes) { // all angles
                // created x cordinate from angles and distances
                double xx = (a / (double)angle_range) * (double) w;
                // created y cordinate from angles and distances
                double yy = (b / (double) pMax) * (double) h;
                // look at threshold of lines relative to max value of the
                if (acc[b][a] > (hough_thresh * maxPoints)) {
                    // now find tangent lines
                    drawHoughLines(srcImg, b, a);
                }
            }
        }

        return srcImg;

    }

    private void drawHoughLines(BufferedImage img, int p, int theta) {

        // h & w of image
        int h = img.getHeight();
        int w = img.getWidth();

        double radians = (theta / 360.0) * Math.PI * 2;
        // get line coordinates
        int x = (int) (p * Math.cos(radians));
        int y = (int) (p * Math.sin(radians));

        double x1 = (double) x;
        double y1 = (double) y;
        double x2 = x;
        double y2 = y;
        //double tx = Math.cos(radians);
        //double ty = Math.sin(radians);

        // add all points on line in one direction
        while (y1 > 0 && x1 < w && y1 < h && x1 > 0) {
            x1 = (x1 + Math.sin(radians));
            y1 = (y1 - Math.cos(radians));
        }

        // add allpoints on line in the other direction
        while (y2 > 0 && x2 < w && y2 < h && x2 > 0) {
            x2 = (x2 - Math.sin(radians));
            y2 = (y2 + Math.cos(radians));
        }
        // draw line from end of direction one, to end of direction 2
        Graphics2D g = img.createGraphics();
        g.setColor(Color.GREEN);
        g.drawLine((int)x1,(int) y1, (int) x2, (int) y2);
        
    }
    
    public Point getAvgPoint(BufferedImage srcImg) {

		int h = srcImg.getHeight();
		int w = srcImg.getWidth();
		// difference image

		int meanX = 0;
		int meanY = 0;
		int meanThresh = 100;
		int count = 0;

		for (int y = 0; y < h; ++y) {
			int rowY = 0;
			for (int x = 0; x < w; ++x) {
				int srcPixel = getGrey(srcImg.getRGB(x, y));
				if (srcPixel > meanThresh) {
					rowY = rowY + srcPixel;
					meanY = meanY + y;
					count++;
				}
			}
		}
		if (count > 0) {
			meanY = meanY / count;
		}
		count = 0;
		for (int x = 0; x < w; ++x) {
			int rowX = 0;
			for (int y = 0; y < h; ++y) {
				int srcPixel = getGrey(srcImg.getRGB(x, y));
			
				if (srcPixel > meanThresh) {
					rowX = rowX + srcPixel;
					meanX = meanX + x;
					count++;
				}
			}
		}

		if (count > 0) {
			meanX = meanX / count;
		}
		return new Point(meanX, meanY);
	}
}
