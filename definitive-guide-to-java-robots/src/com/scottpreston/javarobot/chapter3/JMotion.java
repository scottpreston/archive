package com.scottpreston.javarobot.chapter3;

public interface JMotion {
    
    // forward
    public void forward() throws Exception;
    // reverse
    public void reverse() throws Exception;
    // pivot right
    public void pivotRight() throws Exception;
    // picot left
    public void pivotLeft() throws Exception;
    // stop
    public void stop() throws Exception;
    // forward
    public void forward(int ms) throws Exception;
    // reverse
    public void reverse(int ms) throws Exception;
    // pivot right
    public void pivotRight(int ms) throws Exception;
    // picot left
    public void pivotLeft(int ms) throws Exception;
    // setting speed of robot
    public void setSpeed(int speed)throws Exception ;
    // get speed of robot
    public int getSpeed();
    

}
