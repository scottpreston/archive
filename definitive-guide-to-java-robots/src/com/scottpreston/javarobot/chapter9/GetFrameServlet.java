package com.scottpreston.javarobot.chapter9;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.awt.image.codec.JPEGImageEncoderImpl;

import com.scottpreston.javarobot.chapter6.GetFrame;

// requires servlet-api.jar
public class GetFrameServlet extends HttpServlet {

    private GetFrame getFrame;
    public static final long serialVersionUID = 1;

    // happens when servlet loads
    public void init() {

        try {
            getFrame = new GetFrame("vfw://0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // when there is a request via HTTP GET
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        /* Handle a GET in the same way as we handle a POST */
        doPost(request, response);
    }

    // when there is a request via HTTP POST
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        /* If there is no GetFrame do nothing */
        if (getFrame == null)
            return;

        BufferedImage bImage = null;
        try {// get frame
            if ((bImage = getFrame.getBufferedImage()) == null) {
                return;
            }
            // set output MIME type
            response.setContentType("image/jpeg");
            // get output stream
            ServletOutputStream out = response.getOutputStream();
            // write image to stream
            JPEGImageEncoderImpl encoder = new JPEGImageEncoderImpl(out);
            // encode the image as a JPEG
            encoder.encode(bImage);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
