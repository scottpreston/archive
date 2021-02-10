package com.scottpreston.javarobot.chapter9;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// requires mail.jar & activation .jar
public class SendMailClient {

    private Session mailSession;

    public SendMailClient(String host, String port) {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "false");
        properties.put("mail.smtp.port", port);
        mailSession = Session.getInstance(properties, null);
    }

    public void send(String to, String from, String subj, String msg) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            message.setSubject(subj);
            message.setContent(msg, "text/plain");
            message.saveChanges();
            Transport transport = mailSession.getTransport("smtp");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try {
            SendMailClient sendMail = new SendMailClient("localhost","25");            
            String from = "feynman@scottsbots.com";
            String to = "info@scottsbots.com";
            String subj = "Test E-Mail";
            String msg = "Java Robots Are Cool!";
            sendMail.send(to,from,subj,msg);
            System.out.println("Email Message Sent");
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}