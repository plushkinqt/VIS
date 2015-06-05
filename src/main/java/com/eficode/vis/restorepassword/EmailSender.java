package com.eficode.vis.restorepassword;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender extends Thread{
    
    private final String receiver;
    private static final String sender = "email address here";
    private static final String host = "smtp.gmail.com";
    private final Properties properties;
    private final Session session;
    private static final String USER_NAME = "User name here";
    private static final String PASSWORD = "Your password here";
    private final String password;


    public EmailSender(String email, String password) {
        this.receiver = email;
        this.password = password;
        this.properties = System.getProperties();
        this.properties.put("mail.smtp.starttls.enable", "true");
        this.properties.put("mail.smtp.host", host);
        this.properties.put("mail.smtp.user", USER_NAME);
        this.properties.put("mail.smtp.password", PASSWORD);
        this.properties.put("mail.smtp.port", "587");
        this.properties.put("mail.smtp.auth", "true");
        this.session = Session.getDefaultInstance(properties); 
    }
      
    public void send() throws MessagingException{
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject("Automatic password restore");
        message.setText("This message was generated automaticly, plaese do not reply. \n Your password is " + password +
                ". If you do not requested for password just ignore this message or contact support. Oh yeah, we don't have support.");
        Transport transport = session.getTransport("smtp");
        transport.connect(host, USER_NAME, PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
      
}
