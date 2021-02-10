package com.scottpreston.javarobot.chapter5;

public class EchoTalk {

    private MicrosoftVoice voice;
    private MicrosoftSR ear;

    public EchoTalk() throws Exception {
        // generic constructor gets instance of two worker classes
        voice = MicrosoftVoice.getInstance();
        ear = MicrosoftSR.getInstance();
        // give user instructions
        voice.speak("I will repeat what you say. Say exit, to end program.");
    }
    
    public void start() throws Exception {
        String words = "";
        // tell user to begin talking.
        voice.speak("listening");
        // this will loop until it hears 'exit'
        while (words.equalsIgnoreCase("exit") == false) {
            // gets words heard.
            words = ear.listen();
            //prints this to system out (good for debugging)
            System.out.println("I heard --> " + words);
            // say the words
            voice.speak(words);
        }
        // last words spoken.
        voice.speak("goodbye");
    }

    public static void main(String[] args) {

        try {
            EchoTalk echo = new EchoTalk();
            echo.start();
        } catch (Exception e) {
            //print error and exit.
            e.printStackTrace();
            System.exit(1);
        }

    }
}