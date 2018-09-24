package com.pilot.pswrest.action;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pilot.pswrest.config.AppInfoProperties;
import com.pilot.pswrest.config.PBKDF2;

public class ResetPasswordAction {
	private String emailAddress;
	
	public String execute() throws AddressException, MessagingException {
		// Recipient's email ID needs to be mentioned.
	    String to = getEmailAddress();

		/**
		 * Generate Secure Hash Value
		 */
		String securedHash = PBKDF2.hashPassword("abced");
		
		sendResetPasswordLink("nanpwintshweyiphu", AppInfoProperties.MAIL_USERNAME, securedHash);
		
	      // Sender's email ID needs to be mentioned
	      String from = "sender@gmail.com";//change accordingly
	      final String username = "youmail@gmail.com";//change accordingly
	      final String password = "xxxx";//change accordingly

	      // Assuming you are sending email through relay.jangosmtp.net
	      String host = "smtp.gmail.com";

	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");

	      // Get the Session object.
	      Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	         protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	         }
	      });

	      try {
	         // Create a default MimeMessage object.
	         Message message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.setRecipients(Message.RecipientType.TO,
	         InternetAddress.parse(to));

	         // Set Subject: header field
	         message.setSubject("Testing Subject");

	         // Now set the actual message
	         message.setText("Hello, this is sample for to check send "
	            + "email using JavaMailAPI" + "Have a nice day!!!!!!!!!!! ");

	         // Send message
	         Transport.send(message);

	         System.out.println("Sent message successfully....");

	      } catch (MessagingException e) {
	            throw new RuntimeException(e);
	      }
		
		return "success";
		
	}
   private void sendResetPasswordLink(String to, String mailUsername, String securedHash) throws AddressException, MessagingException {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", AppInfoProperties.MAIL_SMTP_HOST);
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(AppInfoProperties.MAIL_USERNAME, AppInfoProperties.MAIL_PASSWORD);
			}
		  });

		String link = AppInfoProperties.MAIL_REGISTRATION_SITE_LINK+"?scope=resetPassword&userId="+to+"&hash="+securedHash;
		
		  StringBuilder bodyText = new StringBuilder(); 
			bodyText.append("<div>")
			     .append("  Dear User<br/><br/>")
			     .append("  We got your reset password request, Find below link to reset password <br/>")
			     .append("  Please click <a href=\""+link+"\">here</a> or open below link in browser<br/>")
			     .append("  <a href=\""+link+"\">"+link+"</a>")
			     .append("  <br/><br/>")
			     .append("  Thanks,<br/>")
			     .append("  contact@RecKaigi")
			     .append("</div>");
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(AppInfoProperties.MAIL_USERNAME));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailUsername));
			message.setSubject("Reset Password");
			message.setContent(bodyText.toString(), "text/html; charset=utf-8");
			Transport.send(message);
		
	}
public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String email) {
		this.emailAddress = email;
	}
}
