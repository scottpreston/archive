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

package com.scottsbots.core.speech.sr;

import com.scottsbots.core.JSpeechRecognizer;
import com.scottsbots.core.speech.QuadmoreSR;

public class MicrosoftSR implements JSpeechRecognizer {

    // class used for recognizer
    private QuadmoreSR quadmoreSr;

    // holds single instance of recognizer
    private static MicrosoftSR instance;

    // private constructor prevents initialization
    // called by getInstance()
    private MicrosoftSR() {
    	quadmoreSr = new QuadmoreSR();
    }

    public static MicrosoftSR getInstance(){
        if (instance == null) {
            instance = new MicrosoftSR();
        }
        return instance;
    }

    public void close() {
    } 

    public void open() {
    } 

    public String listen() {
        return quadmoreSr.TakeDictation();
    }
    
    // sample usage
    public static void main(String[] args) {
        try {
            // gets instance
            MicrosoftSR sr = MicrosoftSR.getInstance();
            String words = "";
            System.out.println("Listening...");
            // loops until hears exit
            while (words.equalsIgnoreCase("exit") == false) {
                words = sr.listen();
                System.out.println("I heard --> " + words);
                // if it hears note then it opens notepad
                if (words.equalsIgnoreCase("note")) {
                    Runtime.getRuntime().exec("cmd /c notepad.exe");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("done");
    }
}