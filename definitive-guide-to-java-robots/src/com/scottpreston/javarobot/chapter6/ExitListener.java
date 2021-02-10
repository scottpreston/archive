package com.scottpreston.javarobot.chapter6;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ExitListener extends WindowAdapter {
    public void windowClosing(WindowEvent event) {
      System.exit(0);
    }
  }