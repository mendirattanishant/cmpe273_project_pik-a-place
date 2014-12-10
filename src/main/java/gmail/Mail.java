package gmail;



import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mail {
 
	public void gmail(String user_name)
	   {    
 
		final String username = "proj273@gmail.com";
		final String password = "sjsucmpe";
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("proj273@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("aparnakansal@gmail.com"));
			message.setSubject("Recommendation Needed");
			message.setText("Hi,"
				+ "\n\n You visited San Jose. I was Planning to visit next month. Can you please recommend and rate this place. Thank You");
 
			
			
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			

			throw new RuntimeException(e);
		}
	}
}
