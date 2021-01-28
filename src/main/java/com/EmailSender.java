package com;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
      
      public static void notifyStarted() throws Exception {
    	  
    	  Properties properties = System.getProperties();

	      properties.put("mail.smtp.host", EmailInfo.getInstance().getServer());
	      properties.put("mail.smtp.port", EmailInfo.getInstance().getPort());
	      properties.put("mail.smtp.auth", "true");
	      properties.put("mail.smtp.starttls.enable", "true");
	      properties.put("mail.smtp.ssl.enable", "true");
	      properties.put("mail.smtp.quitwait", "false");
	      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      //properties.put("mail.debug", "true");

	      
	      String user = EmailInfo.getInstance().getUser();
	      String password = EmailInfo.getInstance().getPassword();
	      Session session = Session.getInstance(properties,
	    		  new javax.mail.Authenticator() {
	    		  protected PasswordAuthentication getPasswordAuthentication() {
	    			  return new PasswordAuthentication(user, password);
	    		  }
	    		  });
	      
	      try {
	         
	          MimeMessage message = new MimeMessage(session);
	          message.setFrom(new InternetAddress(EmailInfo.getInstance().getUser(), "Employees Spring Boot App"));
	          message.addRecipient(Message.RecipientType.TO, new InternetAddress(EmailInfo.getInstance().getTo()));
	          message.setSubject("App Started");
	          message.setText("The Employees Spring Boot application has started successfully at " + new Date());
	          Transport.send(message);
	          System.out.println("Notified by email successfully");
	          
	       } catch (MessagingException mex) {
	          mex.printStackTrace();
	       }
      }
}
