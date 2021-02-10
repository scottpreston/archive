package com.scottpreston.javarobot.chapter9;

import com.scottpreston.javarobot.chapter2.JSerialPort;
import com.scottpreston.javarobot.chapter2.Utils;
import com.scottpreston.javarobot.chapter2.WebSerialClient;
import com.scottpreston.javarobot.chapter5.MicrosoftVoice;
import com.scottpreston.javarobot.chapter7.Navigation;

public class FourDirections {

    private Navigation nav;
    private MicrosoftVoice voice;

    public FourDirections(JSerialPort sPort) throws Exception {
        nav = new Navigation(sPort);
        voice = MicrosoftVoice.getInstance();
        voice.speak("ready to move");
    }

    public void turn() throws Exception {
        nav.changeHeading(Navigation.REL_NORTH);
        voice.speak("facing north now");
        Utils.pause(3000);
        nav.changeHeading(Navigation.REL_EAST);
        voice.speak("facing east now");
        Utils.pause(3000);
        nav.changeHeading(Navigation.REL_SOUTH);
        voice.speak("facing south now");
        Utils.pause(3000);
        nav.changeHeading(Navigation.REL_WEST);
        voice.speak("facing west now");
        Utils.pause(3000);
        voice.speak("done");
    }

    public static void main(String[] args) {
        try {
            WebSerialClient sPort = new WebSerialClient("10.10.10.99", "8080", "1");
            FourDirections me = new FourDirections(sPort);
            me.turn();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
