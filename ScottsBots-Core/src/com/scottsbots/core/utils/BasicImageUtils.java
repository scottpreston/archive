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

package com.scottsbots.core.utils;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.media.jai.Histogram;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import com.scottsbots.core.utils.Utils;



/**
 * This class always return information from an image, so if it's a point, or
 * number then it's in the image utils class.
 * 
 * This class is also functional in nature, all methods are static, and there's
 * no state kept within this class
 * 
 * @author Scott
 * 
 */
public class BasicImageUtils {

	public static PlanarImage bufferedToPlanar(BufferedImage bImg) {
		Image awtImg = Toolkit.getDefaultToolkit()
				.createImage(bImg.getSource());
		return JAI.create("awtimage", awtImg);
	}

	public static BufferedImage planarToBuffered(PlanarImage pImg) {
		return pImg.getAsBufferedImage();
	}

	public static int[] getHistogram(BufferedImage bufImg) {

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
		int[] mean = new int[] { (int) hist.getMean()[0],
				(int) hist.getMean()[1], (int) hist.getMean()[2] };
		System.out.println("histogram2=" + mean[0] + "," + mean[1] + ","
				+ mean[2]);

		return mean;
	}

	public static Point getAvgPoint(BufferedImage srcImg) {
		return getAvgPoint(srcImg, 0);
	}

	public static Point getAvgPoint(BufferedImage srcImg, int threshhold) {

		int h = srcImg.getHeight();
		int w = srcImg.getWidth();
		// difference image

		double meanX = 0.0;
		double meanY = 0.0;
		int meanThresh = 10;
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
		// Utils.log("x=" + meanX + ",y=" + meanY + ",count=" + count);
		// adding threshhold
		Utils.log("count=" + count);
		Point pt = null;
		if (threshhold > 0) {
			if (count > threshhold) {
				pt = new Point(new Double(meanX).intValue(),
						new Double(meanY).intValue());
			} else {
				pt = new Point(w / 2, h / 2);
			}
		} else {
			pt = new Point(new Double(meanX).intValue(),
					new Double(meanY).intValue());
		}
		return pt;
	}

	/*
	 * this method will only count white images on black background.
	 */
	public static int getGrey255(int c) {
		Color color = new Color(c);
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int gray = (int) ((r + g + b) / 3.0);
		return gray;
	}

	public static Color getGrey(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int gray = (int) ((r + g + b) / 3.0);
		return new Color(gray, gray, gray);
	}

	/**
	 * @deprecated
	 **/

	public static int getGrey(int colorInt) {
		return getGrey255(colorInt);
	}

	public static int[] getMean(BufferedImage srcImg) {

		int h = srcImg.getHeight();
		int w = srcImg.getWidth();

		BufferedImage dstImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);

		double red = 0;
		double green = 0;
		double blue = 0;
		double count = 0.0;
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				int srcPixel = srcImg.getRGB(x, y);
				// System.out.print(srcPixel);
				int thisRed = new Color(srcPixel).getRed();
				int thisGreen = new Color(srcPixel).getGreen();
				int thisBlue = new Color(srcPixel).getBlue();

				if (thisRed > 0 || thisGreen > 0 || thisBlue > 0) {
					// tally total colors for 3 components
					red = red + thisRed;
					green = green + thisGreen;
					blue = blue + thisBlue;
					count++;
				}
			}
		}

		// get averages
		int redAvg = (int) Math.round(red / count);
		int greenAvg = (int) Math.round(green / count);
		int blueAvg = (int) Math.round(blue / count);
		// System.out.println("color mean=" + redAvg + "," + greenAvg + ","
		// + blueAvg + ",count=" + count);
		return new int[] { redAvg, greenAvg, blueAvg };
	}

	public static int colorCount(BufferedImage srcImg, Color targetColor) {

		int h = srcImg.getHeight();
		int w = srcImg.getWidth();
		int count = 0;
		BufferedImage dstImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				int srcPixel = srcImg.getRGB(x, y);
				Color c = new Color(srcPixel);
				if (c.getRGB() == targetColor.getRGB()) {
					count++;
				}
			}
		}
		return count;
	}

	public static double getImageDifference(BufferedImage img1,
			BufferedImage img2) {

		int h = img1.getHeight();
		int w = img1.getWidth();

		int total = h * w;
		int sum = 0;

		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				// get grey of image
				int srcPixel = getGrey255(img1.getRGB(x, y));
				// get color of last image
				int diffPixel = getGrey255(img2.getRGB(x, y));
				// calculate differnece
				int diff = Math.abs(srcPixel - diffPixel);
				// make difference color
				if (diff > 50) {// some %20 of total color range of 255
					sum++;
				}
			}
		}
		return (double) sum / (double) total;

	}

	public static int countImageDifference(BufferedImage img1,
			BufferedImage img2) {

		int h = img1.getHeight();
		int w = img1.getWidth();

		int total = h * w;
		int sum = 0;

		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				// get grey of image
				int srcPixel = getGrey255(img1.getRGB(x, y));
				// get color of last image
				int diffPixel = getGrey255(img2.getRGB(x, y));
				// calculate differnece
				int diff = Math.abs(srcPixel - diffPixel);
				sum = sum + diff;
			}
		}
		return sum;

	}

	public static ArrayList getColorsAsList(BufferedImage bimg) {

		int h = bimg.getHeight();
		int w = bimg.getWidth();

		ArrayList colors = new ArrayList();
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				colors.add(new Color(bimg.getRGB(x, y)));
			}
		}
		// System.out.println("color count = " + colors.size());
		return colors;

	}

	public static Color rgbToNormalRGB2(Color rgb) {
		int[] c = rgbToNormalRGB(rgb);
		return new Color(c[0], c[1], c[2]);
	}

	public static int[] rgbToNormalRGB(Color rgb) {
		double r = rgb.getRed();
		double g = rgb.getGreen();
		double b = rgb.getBlue();
		double sum = r + g + b;
		// this would be all values between 0 and 1, but rather it's normalized
		// by mult 255 to it
		// will get scale from 0-255 for each color
		int nR = (int) ((r / sum) * 255);
		int nG = (int) ((g / sum) * 255);
		int nB = (int) ((b / sum) * 255);
		return new int[] { nR, nG, nB };
	}

	public static int[] rgbToHSV(Color rgb) {
		double r = rgb.getRed();
		double g = rgb.getGreen();
		double b = rgb.getBlue();
		double max = Math.max(Math.max(r, g), b);
		double min = Math.min(Math.min(r, b), b);
		double h = 0;
		if (r == max) {
			h = ((g - b) / (max - min)) * 60;
		} else if (g == max) {
			h = (((b - r) / (max - min)) * 60) + 120;
		} else if (b == max) {
			h = (((r - g) / (max - min)) * 60) + 240;
		}
		return new int[] { (int) ((h / 360) * 240), (int) ((max - min) * 240),
				(int) (max * 240) };
	}

	public static boolean isSkin(Color color) {
		boolean skin = false;
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int rgDiff = Math.abs(r - g);
		int max1 = Math.max(r, g);
		int max2 = Math.max(g, b);
		int max = Math.max(max1, max2);
		int min1 = Math.min(r, g);
		int min2 = Math.min(g, b);
		int min = Math.min(min1, min2);
		int grey = getGrey(color.getRGB());
		if (grey > 140 && r > 95 && g > 40 && b > 20 && rgDiff > 30 && r > g
				&& r > b && ((max - min) > 15) && rgDiff < 140) {
			skin = true;
		}
		return skin;
	}

	public static boolean isSkin2(Color color) {
		boolean skin = false;
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();

		if ((-11 * r - 2 * g + 15 * b) < 0) {
			skin = true;
		}
		return skin;
	}

	public static boolean isSkin3(Color color) {
		boolean skin = false;
		double r = color.getRed();
		double g = color.getGreen();
		double b = color.getBlue();
		double rb = r * b / (r + g + b) * (r + g + b);
		double rg = r * g / (r + g + b) * (r + g + b);

		if ((r / g > 1.185) && rb > .107 && rg > .112) {
			skin = true;
		}
		return skin;
	}

	public static int[] getMean(ArrayList colorList) {

		double red = 0;
		double green = 0;
		double blue = 0;
		int count = 0;
		for (int i = 0; i < colorList.size(); i++) {
			Color c = (Color) colorList.get(i);
			int thisRed = c.getRed();
			int thisGreen = c.getGreen();
			int thisBlue = c.getBlue();
			// System.out.println(thisRed);
			if (thisRed > 0 || thisGreen > 0 || thisBlue > 0) {
				// tally total colors for 3 components
				red = red + thisRed;
				green = green + thisGreen;
				blue = blue + thisBlue;
				count++;
			}
		}

		int redAvg = (int) (red / count);
		int greenAvg = (int) (green / count);
		int blueAvg = (int) (blue / count);
		// System.out.println("color mean=" + redAvg + "," + greenAvg + ","
		// + blueAvg + ",count=" + count);
		return new int[] { redAvg, greenAvg, blueAvg };
	}

	// the function of this is to return a colorgram
	public static int[] getBracketedColor(BufferedImage srcImg, Color c,
			double threshhold) {

		int h = srcImg.getHeight();
		int w = srcImg.getWidth();
		int count = 0;
		BufferedImage dstImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		int bins[] = new int[256];
		int invertBins[] = new int[256];

		for (int i = 0; i < 256; i++) {
			bins[i] = 0;
		}
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				int srcPixel = srcImg.getRGB(x, y);
				Color srcColor = new Color(srcPixel);
				int red = srcColor.getRed();
				int green = srcColor.getGreen();
				int blue = srcColor.getBlue();

				if (c == Color.RED && red > 0) {
					bins[red] = bins[red] + 1; // red histogram
					count++;
				}
				if (c == Color.GREEN && green > 0) {
					bins[green] = bins[green] + 1; // green histogram
					count++;
				}
				if (c == Color.BLUE && blue > 0) {
					bins[blue] = bins[blue] + 1; // blue histogram
					count++;
				}
			}
		}

		int mean = count / 256;
		int min = 0;
		int max = 256;
		int maxBin = 0;

		invertBins = Arrays.copyOf(bins, bins.length);
		Arrays.sort(invertBins);
		int topBins[] = new int[50];
		int tops = 0;
		outer: for (int i = 0; i < 256; i++) {
			for (int k = 0; k < 256; k++) {
				if (invertBins[255 - i] == bins[k]) {
					if (tops >= 50) {
						break outer;
					}
					// System.out.println("k="+(255-k)+",inverted="+invertBins[255
					// -i]+", bins="+bins[k]);
					topBins[tops] = k; // this will be the bin number that has
					// highest values
					tops++;
				}
			}
		}
		int bin50Avg = 0;
		for (int i = 0; i < 50; i++) {
			bin50Avg = bin50Avg + topBins[i];
			// System.out.println("topbins="+topBins[i]);
		}
		bin50Avg = (bin50Avg / 50);
		// System.out.println("bin50Avg="+bin50Avg);
		// std dev of top 50
		double devSq50 = 0;
		for (int i = 0; i < 50; i++) {
			int dev50 = bin50Avg - topBins[i];
			int sq50 = dev50 * dev50;
			devSq50 = devSq50 + sq50;
		}
		devSq50 = (devSq50 / 50);
		devSq50 = Math.sqrt(devSq50);
		// System.out.println("std dev 50 = "+devSq50);
		// int maxStdDev = (int) (binAvg + (devSq*2));
		min = (int) (bin50Avg - devSq50 * 2);
		max = (int) (bin50Avg + devSq50 * 2);

		// standard deviation stuff
		// int binAvg = 0;
		// for (int i = 0; i < 256; i++) {
		// binAvg = binAvg + bins[i];
		// }
		// binAvg = (binAvg/256);
		// System.out.println("binAvg="+binAvg);

		// double devSq = 0;
		// for (int i = 0; i < 256; i++) {
		// int dev = binAvg - bins[i];
		// int sq = dev * dev;
		// devSq = devSq + sq;
		// }
		// devSq = (devSq/256);
		// devSq = Math.sqrt(devSq);
		// System.out.println("std dev="+devSq);
		// int maxStdDev = (int) (binAvg + (devSq*2));

		// for (int i = 0; i < 256; i++) {
		// if (c == Color.RED) {
		// System.out.println(i+","+bins[i]);
		// }
		// if (bins[i] > maxBin) {
		// maxBin = bins[i];
		// }
		// }

		// int maxThresh = (int)Math.round(maxBin * threshhold);

		// int maxThresh = mean;

		// for (int i = 1; i < 256; i++) {
		// // System.out.println(i+","+bins[i]+","+mean);
		// if (bins[i] < maxThresh) {
		// min = i;
		// } else {
		// break;
		// }
		// }
		// System.out.println("----");
		// for (int i = 254; i > 0; i--) {
		// // System.out.println(i+","+bins[i]+">"+mean);
		// if (bins[i] > maxThresh) {
		// max = i;
		// break;
		// }
		// }

		// System.out.println(c.toString() + ", min=" + min + ",max=" + max);
		return new int[] { min, max };

	}

	public static Color[] sortColors(int[] meanValues) {
		int redAvg = meanValues[0];
		int greenAvg = meanValues[1];
		int blueAvg = meanValues[2];
		Arrays.sort(meanValues);
		Color[] colors = new Color[3];
		// correct in case they are equal.
		if (meanValues[0] == meanValues[1]) {
			meanValues[1]++;
		}
		if (meanValues[0] == meanValues[2]) {
			meanValues[2]++;
		}
		if (meanValues[1] == meanValues[2]) {
			meanValues[2] = meanValues[2] + 1;
		}
		for (int i = 0; i < 3; i++) {
			if (meanValues[i] == redAvg) {
				colors[2 - i] = Color.RED;
				// System.out.println(meanValues[i] + ", RED is " + (2 - i));
			}
			if (meanValues[i] == greenAvg) {
				colors[2 - i] = Color.GREEN;
				// System.out.println(meanValues[i] + ", Green is " + (2 - i));
			}
			if (meanValues[i] == blueAvg) {
				colors[2 - i] = Color.BLUE;
				// System.out.println(meanValues[i] + ", Blue is " + (2 - i));
			}
		}
		return colors;
	}

	public static byte[] bufferedImageToByteArray(BufferedImage img)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( img, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;

	}

}
