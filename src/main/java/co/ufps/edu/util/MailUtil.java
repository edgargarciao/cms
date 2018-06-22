package co.ufps.edu.util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
  private String USER_NAME;
  private String PASSWORD;

  public MailUtil() {
    USER_NAME = "edgar.yesid.garcia.ortiz@gmail.com";  // GMail user name (just the part before "@gmail.com")
    PASSWORD = "94100209440"; // GMail password
  }
  
 

  public String sendFromGMail(String[] to, String subject, String body) {
      Properties props = System.getProperties();
      String host = "smtp.gmail.com";
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.user", USER_NAME);
      props.put("mail.smtp.password", PASSWORD);
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

      Session session = Session.getDefaultInstance(props);
      MimeMessage message = new MimeMessage(session);

      try {
          message.setFrom(new InternetAddress(USER_NAME));
          InternetAddress[] toAddress = new InternetAddress[to.length];

          // To get the array of addresses
          for( int i = 0; i < to.length; i++ ) {
              toAddress[i] = new InternetAddress(to[i]);
          }

          for( int i = 0; i < toAddress.length; i++) {
              message.addRecipient(Message.RecipientType.TO, toAddress[i]);
          }

          message.setSubject(subject);
          message.setText(body);
          Transport transport = session.getTransport("smtp");
          transport.connect(host, USER_NAME, PASSWORD);
          transport.sendMessage(message, message.getAllRecipients());
          transport.close();   
          return "Actualizacion";
      }
      catch (AddressException ae) {
          ae.printStackTrace();
          return "no envio";
      }
      catch (MessagingException me) {
          me.printStackTrace();
          return "no envio";
      }
   
  }

  
  public static void main(String[] args) {
    MailUtil mailUtil = new MailUtil();
    mailUtil.sendFromGMail(new String[] {"edgar.yesid.garcia.ortiz@gmail.com"}, "sdsdsd", "<p>asasa</p>");
  }
}
