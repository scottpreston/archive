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

package com.scottsbots.core.vision;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;


import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;

import com.scottsbots.core.utils.BasicImageUtils;

/**
 * This class converts images to other images. So the input type is variable,
 * but the return type is always a buffered image.
 * 
 * This class is also functional in nature, all methods are static, and there's
 * no state kept within this class
 * 
 * @author Scott
 * 
 */
public class BasicImageProcessor {

	public static BufferedImage toGrayImage(BufferedImage colorImage) {
		BufferedImage image = new BufferedImage(colorImage.getWidth(),
				colorImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		Graphics g = image.getGraphics();
		g.drawImage(colorImage, 0, 0, null);
		g.dispose();
		return image;
	}
	
	public static BufferedImage imageSubtract(BufferedImage img1,
			BufferedImage img2, double threshhold) {

		double diffThresh = threshhold * 255;
		// System.out.println("diff threshold = " + diffThresh);

		int h = img1.getHeight();
		int w = img1.getWidth();

		// create dst image
		BufferedImage dstImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		int diffpixels = 0;
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				// get grey of image
				int srcPixel = BasicImageUtils.getGrey(img1.getRGB(x, y));
				// get color of last image
				int diffPixel = BasicImageUtils.getGrey(img2.getRGB(x, y));
				// calculate differnece
				int diff = Math.abs(srcPixel - diffPixel);
				// make difference color
				if (diff > diffThresh) {
					if (srcPixel < 250) {
						dstImg.setRGB(x, y, img1.getRGB(x, y));
						// dstImg.setRGB(x, y, Color.WHITE.getRGB());
						diffpixels++;
					}
					// System.out.println("src = "+ srcPixel + ",diff=" +
					// diffPixel);
				} else {
					dstImg.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		// System.out.println("diffpixels = " + diffpixels);
		return dstImg;
	}

	// gets threshold
	public static BufferedImage threshold(BufferedImage srcImg, int min,
			int max, boolean transparent) {

		// get h & w
		int h = srcImg.getHeight();
		int w = srcImg.getWidth();
		// new image for processing
		BufferedImage dstImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				// get color
				int srcPixel = srcImg.getRGB(x, y);
				// get grey of color
				int colorValue = BasicImageUtils.getGrey(srcPixel);
				// compare to threshhold & convert to binary
				if (colorValue >= min && colorValue <= max) {
					if (transparent) {
						dstImg.setRGB(x, y, srcPixel);
					} else {
						dstImg.setRGB(x, y, Color.WHITE.getRGB());
					}
				} else {
					dstImg.setRGB(x, y, Color.BLACK.getRGB());
				}

			}
		}
		return dstImg;
	}

	public static BufferedImage sobelGradMag(BufferedImage srcImg) {

		PlanarImage input = BasicImageUtils.bufferedToPlanar(srcImg);
		KernelJAI vert = KernelJAI.GRADIENT_MASK_SOBEL_VERTICAL;
		KernelJAI horz = KernelJAI.GRADIENT_MASK_SOBEL_HORIZONTAL;
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(input);
		pb.add(vert);
		pb.add(horz);
		PlanarImage output = JAI.create("GradientMagnitude", pb)
				.createInstance();
		return BasicImageUtils.planarToBuffered(output);
	}

	public static BufferedImage smooth(BufferedImage srcImg) {

		PlanarImage input = BasicImageUtils.bufferedToPlanar(srcImg);
		float ninth = 1.0f / 9.0f;
		float[] k = { ninth, ninth, ninth, ninth, ninth, ninth, ninth, ninth,
				ninth };
		KernelJAI kern = new KernelJAI(3, 3, k);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(input);
		pb.add(kern);
		PlanarImage output = JAI.create("Convolve", pb).createInstance();
		return BasicImageUtils.planarToBuffered(output);
	}
	
	public static BufferedImage sharpen(BufferedImage srcImg) {

		PlanarImage input = BasicImageUtils.bufferedToPlanar(srcImg);
		float[] k = { 0.0f, -1.0f, 0.0f, -1.0f, 5.0f, -1.0f, 0.0f, -1f, 0.0f };
		KernelJAI kern = new KernelJAI(3, 3, k);
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(input);
		pb.add(kern);
		PlanarImage output = JAI.create("Convolve", pb).createInstance();
		return BasicImageUtils.planarToBuffered(output);
	}

	public static BufferedImage resize(BufferedImage srcImg, int targetW,
			int targetH) {

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

	public static BufferedImage invert(BufferedImage srcImage) {
		PlanarImage output = JAI.create("invert", srcImage);
		return output.getAsBufferedImage();
	}

	public static BufferedImage normalize(BufferedImage srcImage, int height,
			int width) {
		return resize(srcImage, height, width);
	}

	public static BufferedImage getAverageImage(BufferedImage[] images) {

		int h = images[0].getHeight();
		int w = images[0].getWidth();
		BufferedImage dstImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);

		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				int redAvg = 0, greenAvg = 0, blueAvg = 0;
				for (int i = 0; i < images.length; i++) {
					int srcPixel = images[i].getRGB(x, y);
					Color srcColor = new Color(srcPixel);
					redAvg = redAvg + srcColor.getRed();
					greenAvg = greenAvg + srcColor.getGreen();
					blueAvg = blueAvg + srcColor.getBlue();
				}
				redAvg = redAvg / images.length;
				greenAvg = greenAvg / images.length;
				blueAvg = blueAvg / images.length;

				Color c = new Color(redAvg, greenAvg, blueAvg);
				dstImg.setRGB(x, y, c.getRGB());
			}
		}

		return dstImg;
	}

	public static BufferedImage combineImages(BufferedImage[] images) {

		int width = 800;// images[0].getWidth() * images.length;
		int height = images[0].getHeight();
		BufferedImage dest = new BufferedImage(width, height,
				images[0].getType());
		Graphics g = dest.createGraphics();
		int pos = 0;
		for (int i = 0; i < images.length; i++) {
			int segWidth = images[i].getWidth();
			System.out.println("segwidth" + segWidth);
			g.drawImage(images[i], pos, 0, null);
			pos = pos + segWidth;
		}
		g.dispose();
		return dest;
	}

	public static BufferedImage combine2Images(BufferedImage src1,
			BufferedImage src2) {

		BufferedImage dest = new BufferedImage(320, 240, src1.getType());
		Graphics g = dest.createGraphics();
		g.drawImage(src1, 0, 0, null);
		g.drawImage(src2, 160, 0, null);
		g.dispose();
		return dest;
	}

	public static BufferedImage overlaySubImage(BufferedImage image) {

		int width = image.getWidth();// images[0].getWidth() * images.length;
		int height = image.getHeight();
		BufferedImage dest = new BufferedImage(320, 240, image.getType());
		Graphics g = dest.createGraphics();
		int x = 160 - width / 2;
		int y = 120 - height / 2;
		g.drawImage(image, x, y, null);
		g.dispose();
		return dest;

	}
}
