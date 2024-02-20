package tn.esprit.controllers;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;


public class Mailing {

//    public void sendEmail(String email, String subject, String body) {
//
//        // Add your email sending logic here
//        try {
//            String host = "smtp.gmail.com";
//            String user = "bedhiafi00@gmail.com";
//            String pass = "Ayoubokba12345";
//            String from = "bedhiafi00@gmail.com";
//            boolean sessionDebug = false;
//
//
//            Properties props = System.getProperties();
//
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.host", host);
//            props.put("mail.smtp.port", "587");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.starttls.required", "true");
//            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//
//
//            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//            Session mailSession = Session.getDefaultInstance(props, null);
//            mailSession.setDebug(sessionDebug);
//            Message msg = new MimeMessage(mailSession);
//            msg.setFrom(new InternetAddress(from));
//            InternetAddress[] address = {new InternetAddress(toEmail)};
//
//            msg.setRecipients(Message.RecipientType.TO, address);
//            //msg.setSentDate(new Date());
//            msg.setSubject(subject);
//            javax.mail.internet.MimeBodyPart messageBodyPart = new javax.mail.internet.MimeBodyPart();
//            Multipart multipart = new MimeMultipart();
//            messageBodyPart = new javax.mail.internet.MimeBodyPart();
//            messageBodyPart.setText(body);
//            multipart.addBodyPart(messageBodyPart);
//            msg.setContent(multipart);
//
//            Transport transport = mailSession.getTransport("smtp");
//            transport.connect(host, user, pass);
//            transport.sendMessage(msg, msg.getAllRecipients());
//            transport.close();
//            System.out.println("Email envoyé");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
