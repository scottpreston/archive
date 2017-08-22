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

package com.scottsbots.books.robotics101.samples.section3;

import java.net.URL;

import com.scottsbots.core.speech.sr.SphinxSR;
import com.scottsbots.core.utils.Utils;

/**
 * A sample program to output speech recognized by the Sphinx4 Speech Recognizer
 * to command line.
 * 
 * @author scott
 *
 */

public class SphinxSrSample {

	public static void main(String[] args) throws Exception {
		URL url = SphinxSR.class.getResource("test.config.xml");
		SphinxSR sr = new SphinxSR(url);
		sr.open();
		String words;
		while (true) {
			words = sr.listen();
			Utils.log(words);
			if (words.equals("exit")) {
				break;
			}

		}
		sr.close();
		System.exit(1);
	}

}
