package com.scottpreston.javarobot.chapter5;

public interface JVoice {

    // speaks
    public void speak(String words) throws Exception;
    // opens or allocates voice engine
    public void open();
    // closes or deallocates voice engine
    public void close();

}