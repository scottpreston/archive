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
import com.scottsbots.core.speech.QuadmoreTTS;

/**
 * A wrapper class for voice using Windows. Note: HKEY_USERS\.DEFAULT|Software\Microsoft\Speech\Voices 
 * then change DefaultTokenId to the voice you want for the system.
 */

public class MicrosoftVoice implements JVoice {

	// worker class for voice
	private QuadmoreTTS voice;
	// private instance to ensure only one is active
	private static MicrosoftVoice instance;

	// private constructor prevents inititalization
	// called by getInstance
	private MicrosoftVoice() {
		voice = new QuadmoreTTS();
	}

	// static methods ensures one instance per class
	public static MicrosoftVoice getInstance() {
		if (instance == null) {
			// returns self
			instance = new MicrosoftVoice();
		}
		return instance;
	}

	public void open() {
		// do nothing
	}

	public void close() {
		// do nothing
	}

	// speak otherwise throw exception
	public void speak(String s) {
		if (!voice.SpeakDarling(s)) {
			System.out.println("unable to speak");
		}
		System.out.println("speaks: " + s);
	}

	// sample usage
	public static void main(String args[]) {
		try {
			MicrosoftVoice v = MicrosoftVoice.getInstance();
			v.speak("hello scott, welcome back, HOW ARE YOU doing?");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("done!");
	}

}