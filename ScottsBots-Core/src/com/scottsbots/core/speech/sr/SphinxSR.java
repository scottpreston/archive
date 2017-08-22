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

import java.net.URL;

import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleParse;

import com.scottsbots.core.JSpeechRecognizer;
import com.scottsbots.core.utils.Utils;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.jsapi.JSGFGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;


public class SphinxSR implements JSpeechRecognizer {

    private Recognizer recognizer;
    private Microphone microphone;
    private RuleGrammar ruleGrammar;

    public SphinxSR(URL url) throws Exception {
        //loads configuration data from XML based configuration file
        ConfigurationManager cm = new ConfigurationManager(url);
        // gets component by name
        recognizer = (Recognizer) cm.lookup("recognizer");
        microphone = (Microphone) cm.lookup("microphone");
        // get grammar file
        JSGFGrammar gram = (JSGFGrammar) cm.lookup("jsgfGrammar");
        // create the grammar
        gram.allocate();
        // get rules
        ruleGrammar = gram.getRuleGrammar();
        // get rule names
        String [] rules = ruleGrammar.listRuleNames();
        // display to console so you know what to speak.
        for (int i=0; i < rules.length;i++) {
        	Utils.log("rule name = " + rules[i]);
        	Utils.log("rule = " +ruleGrammar.getRule(rules[i]).toString());;
        }
    }

    // allocates resources
    public void open() {
        try {
            recognizer.allocate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        start();
    }

    // deallocates resources
    public void close() {
    	stop();
        recognizer.deallocate();
    }

    // start recording
    private void start() {
        // begins capturing audio data
        if (microphone.startRecording() == false) {
            recognizer.deallocate();
            System.exit(1);
        }
    }
    // stop capturing audio data
    private void stop() {
        microphone.stopRecording();
    }

    public String listen(){
        // gets recognition results from recognizer
    	Utils.log("start listen");
        Result result = recognizer.recognize();
        Utils.log("start listen2..");
        String ruleName = "";
        String resultText = "";
        if (result != null) {
            // gets best and final with no filler words
            resultText = result.getBestFinalResultNoFiller();
            // display text
            System.out.println("I heard --> " + resultText);
            String [] rules = ruleGrammar.listRuleNames();
        	for (int i=0; i < rules.length;i++) {
        		try {
       			// test rule name and execute
        		RuleParse rParse = ruleGrammar.parse(resultText,rules[i]);
        		// set rulename
        		ruleName = rParse.getRuleName().getRuleName();
        		} catch (Exception e) {
        			// do nothing
        			//e.printStackTrace();
        		}
            }
        }
        Utils.log("end listen");
        // return rulename
        return ruleName;
    }

    // test class
    public static void main(String[] args) throws Exception {
        // this is the confiration file
        URL url = SphinxSR.class.getResource("test.config.xml");
        SphinxSR sr = new SphinxSR(url);
        System.out.println("Loading...");
        sr.open(); 
        String rule = "";
        System.out.println("Listening...");
        while (true) {
        	System.out.println("Speak");
        	rule = sr.listen();
            System.out.println("rule >> "+rule);
        	if (rule.equals("notepad")) {
                Runtime.getRuntime().exec("cmd /c notepad.exe");
            }
            if (rule.equals("exit")) {
                break;
            }

        }
       
        sr.close();
        System.out.println("done!");
        System.exit(1);
    }

}