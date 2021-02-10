package com.scottpreston.javarobot.chapter5;

public class QuadmoreTTS {

    // this is a DLL in system path QuadTTS.dll
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
        QuadmoreTTS v = new QuadmoreTTS();
        v.SpeakDarling("Java Robots Are Cool!");
        System.out.println("done!");
    }
}