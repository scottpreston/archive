package com.scottpreston.javarobot.chapter7;

import java.awt.Point;

public class NavPoint extends Point {

    public static final String START_POINT = "start";
    public static final String EXIT_POINT = "exit";
    public static final String CENTER_POINT = "center";
    public static final long serialVersionUID = 1;
    
    public String name = null;

    
    public NavPoint(String name) {
        super();
        this.name = name;
    }

    public NavPoint(String name, int x, int y) {
        super(x, y);
        this.name = name;
    }

    public NavPoint(String name, Point p) {
        super(p);
        this.name = name;
    }
}
