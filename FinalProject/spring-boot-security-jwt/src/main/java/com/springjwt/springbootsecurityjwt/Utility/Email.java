package com.springjwt.springbootsecurityjwt.Utility;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class Email {
    public void sendmail(String recieverEmailId) throws AddressException, MessagingException, IOException {
        // email ID of Recipient.
        String recipient = recieverEmailId;

        // email ID of  Sender.
        String sender = "blogpostproject2022@gmail.com";

        // using host as localhost
        String host = "172.31.35.150";

        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("This is Subject");

            // set body of the email.
            message.setText("This is a test mail");

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }
}
