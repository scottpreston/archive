package com.scottpreston.javarobot.chapter9;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.SingleSerialPort;
import com.scottpreston.javarobot.chapter6.ExitListener;
import com.scottpreston.javarobot.chapter6.WindowUtilities;

public class ServoControlClient extends JFrame {

    private JMenuBar myMenuBar;
    private JSerialPort sPort;
    private PrefFrame prefFrame;
    private int id = 1;
    private int baud = 9600;
    public static final long serialVersionUID = 1;

    public ServoControlClient() {
        // set caption
        super("Java Robot - Servo Controller");
        // get current look and feel
        WindowUtilities.setNativeLookAndFeel();
        // set size
        setSize(640, 480);
        // create panel
        SscPanel content = null;
        // add serial port to panel
        try {
            // sets the serial port
            setSerialPort();
            // adds to panel
            content = new SscPanel(sPort);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        // sets panel as content pane
        setContentPane(content);
        // set background color
        content.setBackground(Color.white);
        // set grid layout as 2 rows, 4 columns
        content.setLayout(new GridLayout(2, 4));
        // create new comos with loop
        for (int x = 0; x < 8; x++) {
            SliderFieldCombo slider = new SliderFieldCombo(x);
            content.add(slider);
        }
        // create menu bar
        makeMenuBar();
        // set menu bar
        setJMenuBar(myMenuBar);
        // set frame
        prefFrame = new PrefFrame(this);
        // set visible = false
        prefFrame.setVisible(false);
        // add exit listener
        addWindowListener(new ExitListener());
        // pack this for display
        pack();
        // display frame
        setVisible(true);

    }

    public void makeMenuBar() {
        myMenuBar = new JMenuBar();
        // creates first one
        JMenu fileMenu = new JMenu("File");
        // adds items
        String[] fileItems = new String[] { "Preferences", "Exit" };
        // create shortcut
        char[] fileShortcuts = { 'P', 'X' };

        ActionListener printListener = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                actionFactory(event.getActionCommand());
            }
        };

        for (int i = 0; i < fileItems.length; i++) {
            JMenuItem item = new JMenuItem(fileItems[i], fileShortcuts[i]);
            item.addActionListener(printListener);
            fileMenu.add(item);
            // add seperator between preferences and exit 
            if (fileItems[i].equalsIgnoreCase("Preferences")) {
                fileMenu.addSeparator();
            }
        }
        // add shortcut key
        fileMenu.setMnemonic('F');
        // add to menu bar
        myMenuBar.add(fileMenu);
        // help
        JMenu helpMenu = new JMenu("Help");
        String[] fileItems2 = new String[] { "Help Contents", "About" };
        char[] fileShortcuts2 = { 'C', 'A' };

        ActionListener printListener2 = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                actionFactory(event.getActionCommand());
            }

        };

        for (int i = 0; i < fileItems.length; i++) {
            JMenuItem item = new JMenuItem(fileItems2[i], fileShortcuts2[i]);
            item.addActionListener(printListener);
            helpMenu.add(item);
            if (fileItems[i].equalsIgnoreCase("Help Contents")) {
                helpMenu.addSeparator();
            }
        }

        helpMenu.setMnemonic('H');
        myMenuBar.add(helpMenu);

    }
    // shows about dialog
    private void showAbout() {
        String msg = "Simple Servo Controller\n" + "Version 1.0\n"
                + "Updates can be found at www.scottsbots.com";
        JOptionPane.showMessageDialog(null, msg,
                "About - Simple Servo Controller",
                JOptionPane.INFORMATION_MESSAGE, null);
    }

    // show pref frame
    private void showPrefs() {

        ExitListener closeListener = new ExitListener() {
            public void windowClosing(WindowEvent event) {
                prefFrame.setVisible(false);
            }
        };

        prefFrame.setVisible(true);
        prefFrame.addWindowListener(closeListener);

    }
    // shows different dialogs
    private void actionFactory(String s) {

        if (s.equalsIgnoreCase("preferences")) {
            showPrefs();
        }

        if (s.equalsIgnoreCase("about")) {
            showAbout();
        }
        if (s.equalsIgnoreCase("exit")) {
            System.exit(0);
        }
    }
    // called from child
    public void setSerialPort() throws Exception {
        sPort = SingleSerialPort.getInstance(id, baud);
        System.out.println("seral port id is " + id);
    }

    public int getBaud() {
        return baud;
    }

    public void setBaud(int baud) throws Exception {
        this.baud = baud;
        setSerialPort();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws Exception {
        this.id = id;
        setSerialPort();
    }

    public void setSerialPort(int id, int baud) throws Exception {
        this.id = id;
        this.baud = baud;
        setSerialPort();
    }
    
    public static void main(String[] args) {
        ServoControlClient scc = new ServoControlClient();
    }

}