package com.scottpreston.javarobot.chapter5;

public class MicrosoftSR implements JRecognizer {

    // class used for recognizer
    private QuadmoreSR ear;

    // holds single instance of recognizer
    private static MicrosoftSR instance;

    // private constructor prevents initialization
    // called by getInstance()
    private MicrosoftSR() {
        ear = new QuadmoreSR();
    }

    // gets single instance of speech recognizer.
    public static MicrosoftSR getInstance() throws Exception {
        if (instance == null) {
            instance = new MicrosoftSR();
        }
        return instance;
    }

    public void start() {
    } // do nothing

    public void stop() {
    } // do nothing

    public void open() {
    } // do nothing

    public void close() {
    } // do nothing

    // starts listening and returning strings of spoken text
    public String listen() {
        return ear.TakeDictation();
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