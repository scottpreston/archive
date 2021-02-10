package com.scottpreston.javarobot.chapter5;

import java.util.Locale;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class JavaVoice implements JVoice {

    private Synthesizer synth;
    
    public JavaVoice() throws Exception {
        // constructs synthesizer for US English
        
        synth = Central.createSynthesizer(new SynthesizerModeDesc(
                null, // engine name
                "general", // mode name 
                Locale.US, // local 
                null, // Boolean, running
                null)); // Voices[]
    }
    
    // allocates synthesizer resources, puts engine in state ALLOCATED..
    public void open() {
        try {
            synth.allocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // deallocates synthesizer resources, puts engine in state DEALLOCATED..
    public void close() {
        try {
            synth.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // speaks
    public void speak(String words) throws Exception {
        // removes from paused state as set by allocate
        synth.resume();
        // speaks plain text and text is not interpreted by Java Speech Markup Language JSML
        synth.speakPlainText(words, null);
        // waits until que is empty
        synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
    }

    // sample program
    public static void main(String[] args) {
        try {

            JavaVoice voice = new JavaVoice();
            voice.open();
            voice.speak("Java Robots Are Cool!");
            voice.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("done");
    }
}