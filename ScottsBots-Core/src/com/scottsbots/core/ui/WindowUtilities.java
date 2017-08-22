/*
 * (C) Copyright 2000-2011, by Scott Preston and Preston Research LLC
 *
 *  Project Info:  http://www.scottsbots.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.scottsbots.core.ui;

import javax.swing.UIManager;

/**
 * For using Windows and getting look and feel and other GUI functions.
 * 
 * @author scott
 *
 */
public class WindowUtilities {

	// used in all SWING examples
    public static void setNativeLookAndFeel() {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
          System.out.println(UIManager.getSystemLookAndFeelClassName());
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
