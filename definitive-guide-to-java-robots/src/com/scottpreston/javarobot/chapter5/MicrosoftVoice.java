package com.scottpreston.javarobot.chapter5;

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
    public static MicrosoftVoice getInstance() throws Exception {
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
    
    //speak otherwise throw exception
    public void speak(String s) throws Exception {
        if (!voice.SpeakDarling(s)) {
            throw new Exception("Unable to speak");
        }
    }

    // sample usage
    public static void main(String args[]) {
        try {
            MicrosoftVoice v = MicrosoftVoice.getInstance();
            v.speak("Java Robots Are Cool!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("done!");
    }

}