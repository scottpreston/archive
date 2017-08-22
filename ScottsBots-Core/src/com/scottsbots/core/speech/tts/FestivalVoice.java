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

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import com.scottsbots.core.JVoice;

/**
 * Class for using the Festival Voice in Ubuntu Linux
 * 
 * @author scott
 *
 */

public class FestivalVoice implements JVoice {

	public void speak(String txt) {
		try {
			Runtime rtime = Runtime.getRuntime();

			// start unix shell
			Process child = rtime.exec("/bin/sh");
			// or "/bin/sh set -t" to auto-exit after 1 command

			BufferedWriter outCommand = new BufferedWriter(
					new OutputStreamWriter(child.getOutputStream()));

			// run a command
			outCommand.write("echo '" + txt + "' | festival --tts\n");
			outCommand.flush();

			// exit the unix shell
			outCommand.write("exit" + "\n");
			outCommand.flush();

			int wait = child.waitFor();
			System.out.println("exit code: " + wait);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() {
	}

	public void close() {
	}

	public static void main(String[] args) {
		try {
			FestivalVoice v = new FestivalVoice();
			v.speak("hello scott, welcome back, HOW ARE YOU doing?");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("done!");

	}

}
