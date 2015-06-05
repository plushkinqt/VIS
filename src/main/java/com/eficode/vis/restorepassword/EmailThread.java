package com.eficode.vis.restorepassword;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class EmailThread extends Thread{
    
    EmailSender sender;

    public EmailThread(String email, String password) {
        this.sender = new EmailSender(email, password);
    }
    
    
    @Override
    public void run(){
        try {
            sender.send();
        } catch (MessagingException ex) {
            Logger.getLogger(EmailThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
