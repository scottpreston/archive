package com.scottpreston.javarobot.chapter9;

import java.net.URL;

import com.scottpreston.javarobot.chapter5.MicrosoftVoice;
import com.scottpreston.javarobot.chapter5.SphinxSR;

public class VoiceControl{

    private SphinxSR ear;
    private MicrosoftVoice voice;

    public VoiceControl() throws Exception {
        URL url = VoiceControl.class.getResource("commands.config.xml");
        ear = new SphinxSR(url);
        System.out.println("Opening...");
        ear.open();
        System.out.println("Starting...");
        ear.start();
        voice = MicrosoftVoice.getInstance();
        System.out.println("speak");
        voice.speak("ready to listen");
        listen();
    }

    public void listen() throws Exception {
        String words;
        boolean heading = false;
        boolean time = false;
        boolean cmdDone = false;
        StringBuffer headingString = new StringBuffer();
        String timeString = null;
        while (true) {
            words = ear.listen();
            System.out.println("words="+words);
            if (words.indexOf("move") >= 0) {
                voice.speak("enter direction");
                heading = true;
                words = null;
            }
            // expect heading
            if (heading && words != null) {    
                voice.speak(words);
                headingString.append(wordsToNumber(words));
            }
            if (heading && headingString.length() == 3) {
                voice.speak("heading is, " + headingString.toString());
                heading = false;
                time = true;
                voice.speak("enter seconds");
                words = null;
            }
            if (time && words != null) {
                timeString = wordsToNumber(words);
                time = false;
                cmdDone = true;
            }            
            if (cmdDone) {
                move(headingString.toString(),timeString);
            }
            if (words != null && words.indexOf("exit") >= 0) {
                break;
            }
        }
        ear.stop();
        ear.close();

    }
    
    private void move(String heading, String time) throws Exception{
        voice.speak("moving direction equal to " + heading 
                + " degrees. time will be  " + time + " seconds.");
        // need on new thread
        //cmd.exe("c:\\commands\\move.bat " + heading + " " + time);
    }
   
    private String wordsToNumber(String word) {
        String out = "";
        if (word.equalsIgnoreCase("zero")) {
            out = "0";
        }
        if (word.equalsIgnoreCase("one")) {
            out = "1";
        }
        if (word.equalsIgnoreCase("two")) {
            out = "2";
        }
        if (word.equalsIgnoreCase("three")) {
            out = "3";
        }
        if (word.equalsIgnoreCase("four")) {
            out = "4";
        }
        if (word.equalsIgnoreCase("five")) {
            out = "5";
        }
        if (word.equalsIgnoreCase("six")) {
            out = "6";
        }
        if (word.equalsIgnoreCase("seven")) {
            out = "7";
        }
        if (word.equalsIgnoreCase("eight")) {
            out = "8";
        }
        if (word.equalsIgnoreCase("nine")) {
            out = "9";
        }        
        return out;
    }

    public static void main(String[] args) {
        try {
            VoiceControl vc = new VoiceControl();            
            System.out.println("done!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
