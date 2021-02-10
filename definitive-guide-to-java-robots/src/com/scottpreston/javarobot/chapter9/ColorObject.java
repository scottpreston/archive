package com.scottpreston.javarobot.chapter9;

import com.scottpreston.javarobot.chapter6.ColorGram;


public class ColorObject {

    public ColorGram colorGram;
    public String name;

    public ColorObject(){
        // dafault
    }
    public ColorObject(String nm ,ColorGram cg) {
        name = nm;
        colorGram = cg;
    }
    
}
