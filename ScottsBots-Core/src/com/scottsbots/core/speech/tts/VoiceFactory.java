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

package com.scottsbots.core.speech.tts;

import com.scottsbots.core.JVoice;

public class VoiceFactory {

	private static JVoice instance;

	/**
	 * Used as factory to create voices in either Windows or Ubuntu Linux or Mac.
	 * 
	 * @return
	 */
	public static JVoice getInstance() {
		try {
			if (instance != null) {
				return instance;
			}
			String os = System.getProperty("os.name").substring(0, 3);
			if (os.equalsIgnoreCase("win")) {
				instance = MicrosoftVoice.getInstance();
			} else if (os.equalsIgnoreCase("lin")) {
				instance = new FestivalVoice();
			} else if (os.equalsIgnoreCase("mac")) {
				instance = new FreeTTSVoice();
			} else {
				System.out.println("OS Not supported!!!");
				System.exit(1);
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// will never get here but won't compile...
		return null;

	}

	public static void main(String[] args) {
		JVoice voice = VoiceFactory.getInstance();
		voice.open();
		voice.speak("this is cool.");
		voice.close();
	}

}
