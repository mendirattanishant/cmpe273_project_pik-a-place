package Gmail;




import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import Database.FilteredUserPhotoPlace;
import Database.User;

public class Mail {
    
	public void gmail(List<FilteredUserPhotoPlace> filteredUserPhotoPlace)
    {
        
		List<String> usernames = new ArrayList<String>();
		
		//List<String> emailIds = new ArrayList<String>();
		User uTemp = new User();
		try {
			usernames = uTemp.getUserNames(filteredUserPhotoPlace);
            //aSet = new HashSet<String>(usernames);
			
			
			//emailIds = uTemp.getEmailIds(usernames);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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
			
			
			
			for(int i = 0; i < usernames.size(); i++)
			{
				//System.out.println("1");
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(usernames.get(i)));
				System.out.println(usernames.get(i));
			}
			
			
			
			
			
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
