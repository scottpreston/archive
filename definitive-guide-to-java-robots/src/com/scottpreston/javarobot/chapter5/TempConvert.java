package com.scottpreston.javarobot.chapter5;

public class TempConvert {

    //  this is a DLL in system path QuadTTS.dll
    static {
        System.loadLibrary("SimpleJNI");
    }

    // native method
    public native float CtoF(float c);

    // native method
    public native float FtoC(float f);

    // sample program
    public static void main(String args[]) {
        TempConvert tc = new TempConvert();
        for (int c = 0; c < 101; c++) {
            System.out.println("c=" + c + ",f=" + tc.CtoF(c));
        }
    }
}
