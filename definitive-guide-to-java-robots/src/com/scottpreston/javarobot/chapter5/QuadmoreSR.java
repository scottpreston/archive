package com.scottpreston.javarobot.chapter5;

public class QuadmoreSR {

    // this is a DLL in system path QuadSR.dll
    static {
        System.loadLibrary("QuadSR");
    }
    // from native class
    public native String TakeDictation();

    // sample program
    public static void main(String args[]) {
        int i;
        i = 0;
        String strRecognizedText;
        System.out.println("Beginning speech recognition...");
        // create speech recognition class
        QuadmoreSR sr = new QuadmoreSR();
        // wait until four words are heard
        while (i < 4) {
            strRecognizedText = sr.TakeDictation();
            System.out.println("\n");
            System.out.println(strRecognizedText);
            i++;
        }
        System.out.println("Done.");
    }

}