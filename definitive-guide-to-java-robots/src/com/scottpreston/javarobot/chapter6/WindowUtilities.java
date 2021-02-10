package com.scottpreston.javarobot.chapter6;

import javax.swing.UIManager;

public class WindowUtilities {

	// used in all SWING examples
    public static void setNativeLookAndFeel() {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
          System.out.println("Error setting native LAF: " + e);
        }
      }

      public static void setJavaLookAndFeel() {
        try {
          UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e) {
          System.out.println("Error setting Java LAF: " + e);
        }
      }

       public static void setMotifLookAndFeel() {
        try {
          UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch(Exception e) {
          System.out.println("Error setting Motif LAF: " + e);
        }
      }
}
