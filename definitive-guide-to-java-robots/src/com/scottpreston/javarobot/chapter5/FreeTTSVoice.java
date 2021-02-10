package com.scottpreston.javarobot.chapter5;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class FreeTTSVoice implements JVoice {

    // create these for use in constructor
    public static final String VOICE_ALAN = "alan";
    public static final String VOICE_KEVIN = "kevin";
    public static final String VOICE_KEVIN_16 = "kevin16";

    private Voice voice;

    // creates with name
    public FreeTTSVoice(String voiceName) {
        voice = VoiceManager.getInstance().getVoice(voiceName);
    }
    
    // speaks
    public void speak(String msg) {
        voice.speak(msg);
    }
    // deallocates and frees resources
    public void close() {
        voice.deallocate();
    }
    // allocates and opens reseources
    public void open() {
        voice.allocate();
    }
    // sample program
    public static void main(String[] args) {
        FreeTTSVoice me = new FreeTTSVoice(FreeTTSVoice.VOICE_KEVIN_16);
        me.open();
        me.speak("Java Robots Are Cool.");
        me.close();
    }
}