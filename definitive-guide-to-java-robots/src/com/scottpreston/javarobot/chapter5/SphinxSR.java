package com.scottpreston.javarobot.chapter5;

import java.net.URL;

import javax.speech.recognition.RuleGrammar;
import javax.speech.recognition.RuleParse;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.jsapi.JSGFGrammar;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;

public class SphinxSR implements JRecognizer {

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
        	System.out.println("rule name = " + rules[i]);	
        	System.out.println("rule = " +ruleGrammar.getRule(rules[i]).toString());;
        }
        // seperator
    	System.out.println("----");
    }

    // allocates resources
    public void open() {
        try {
            recognizer.allocate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // deallocates resources
    public void close() {
        recognizer.deallocate();
    }

    // start recording
    public void start() {
        // begins capturing audio data
        if (microphone.startRecording() == false) {
            recognizer.deallocate();
            System.exit(1);
        }
    }
    // stop capturing audio data
    public void stop() {
        microphone.stopRecording();
    }

    public String listen(){
        // gets recognition results from recognizer
        Result result = recognizer.recognize();
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
        		}             		
            }
        }
        // return rulename
        return ruleName;
    }

    // test class
    public static void main(String[] args) throws Exception {
        // this is the confiration file
        URL url = SphinxSR.class.getResource("notepad.config.xml");
        SphinxSR sr = new SphinxSR(url);
        System.out.println("Loading...");
        sr.open();
        sr.start();
        String rule = "";
        System.out.println("Listening...");
        while (true) {
        	rule = sr.listen();
            if (rule.equals("notepad")) {
                Runtime.getRuntime().exec("cmd /c notepad.exe");
            }
            if (rule.equals("exit")) {
                break;
            }
        }
        sr.stop();
        sr.close();
        System.out.println("done!");
    }

    /**
     * @return Returns the recognizer.
     */
    public Recognizer getRecognizer() {
        return recognizer;
    }
    /**
     * @param recognizer The recognizer to set.
     */
    public void setRecognizer(Recognizer recognizer) {
        this.recognizer = recognizer;
    }
    /**
     * @return Returns the ruleGrammar.
     */
    public RuleGrammar getRuleGrammar() {
        return ruleGrammar;
    }
    /**
     * @param ruleGrammar The ruleGrammar to set.
     */
    public void setRuleGrammar(RuleGrammar ruleGrammar) {
        this.ruleGrammar = ruleGrammar;
    }
}