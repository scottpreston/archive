package com.scottpreston.javarobot.chapter5;

public interface JRecognizer {

    // opens device or allocates device
    public void open();
    // closes device or deallocates it
    public void close();
    // starts recognizer engine
    public void start();
    // stops recognizer engine
    public void stop();
    // starts listening
    public String listen();
    
}