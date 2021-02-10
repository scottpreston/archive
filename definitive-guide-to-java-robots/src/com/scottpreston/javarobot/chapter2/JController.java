package com.scottpreston.javarobot.chapter2;

public interface JController {

    public String execute(byte[] cmd, int delay) throws Exception;
    public byte[] execute2(byte[] cmd, int delay) throws Exception;
    public void close();
}
