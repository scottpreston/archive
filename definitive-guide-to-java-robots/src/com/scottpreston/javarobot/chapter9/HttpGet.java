package com.scottpreston.javarobot.chapter9;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class HttpGet {
    
    // get image from internet via URL
    public static BufferedImage getImage(String urlString)throws Exception {
        // construct URL
        URL url = new URL(urlString);
        // get AWT image
        Image image = Toolkit.getDefaultToolkit().getImage(url);
        // cast to BufferedImage
        return (BufferedImage)image;
    }
    
    // get text from inernet via URL
    public static String getText(String urlString) throws Exception {
        // construct url
        URL url = new URL(urlString);
        // create input buffer for reading
        BufferedReader in = new BufferedReader(new InputStreamReader(url
                .openStream()));
        // temp string
        String str;
        // create buffer to put information in
        StringBuffer buffer = new StringBuffer();
        // read until end of file
        while ((str = in.readLine()) != null) {
            // since reading line add line feed.
            buffer.append(str+"\n");
        }
        // close buffer
        in.close();
        // return as string
        return buffer.toString();
    }

    public static void main(String[] args) {

        try {
            // open my publishers web site.
            System.out.println(HttpGet.getText("http://www.apress.com"));
        } catch (Exception e) {
            // print exception and exit
            e.printStackTrace();
            System.exit(1);
        }
    }
}