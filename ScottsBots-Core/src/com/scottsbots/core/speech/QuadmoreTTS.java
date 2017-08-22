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

package com.scottsbots.core.speech;

import com.scottsbots.core.utils.Utils;

/**
 * Class container for Microsoft Voice. Giving credit for this code located at 
 * http://www.quadmore.com
 * 
 * @author scott
 *
 */

public class QuadmoreTTS {

    // this is a DLL in system path QuadTTS.dll
	// put this in java home/bin
	// install both c+= distribution packs from microsoft
	// http://www.microsoft.com/downloads/en/confirmation.aspx?familyId=a5c84275-3b97-4ab7-a40d-3802b2af5fc2&displayLang=en
	
	static {
    	System.loadLibrary("QuadTTS");
    }
    
    // native method
    public native boolean SpeakDarling(String strInput);
    // native method
    public native boolean setVoiceToken(String s);
    // native method
    public native String getVoiceToken();

    // sample program
    public static void main(String args[]) {
    	Utils.printJavaEnv();
        QuadmoreTTS v = new QuadmoreTTS();
        v.SpeakDarling("I love the robots at Scott's. Bots. dot com");
        System.out.println("done!");
    }
}