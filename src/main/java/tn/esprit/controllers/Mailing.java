package tn.esprit.controllers;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.utils.SessionManager;
import java.io.File;
import java.util.Properties;

public class Mailing {

    @FXML
    private TextArea txtBody;

    @FXML
    private TextField txtRecipientEmail;

    @FXML
    private TextField txtSubject;

    private static File attachmentFile;

    @FXML
    private void initialize() {
        attachmentFile = null;
    }

    @FXML
    private void addAttachment(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Attachment File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            attachmentFile = selectedFile;
        }
    }

    public static void sendMail(String from, String password, String to, String subject, String body, File attachment) {
        // Mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String sessionId = SessionManager.getLastSessionId();
        User user = UserService.getUserFromSession(sessionId);
        String username = user.getEmail();
        System.out.println("Sender email: " + username);
        from = username;

        // Create session with authentication
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create MimeMessage object
            Message message = new MimeMessage(session);

            // Set sender's email address
            message.setFrom(new InternetAddress(from));

            // Set recipient's email address
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set email subject
            message.setSubject(subject);

            // Create a multipart message
            Multipart multipart = new MimeMultipart();

            // Create the message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");
            multipart.addBodyPart(messageBodyPart);

            // Attach the file
            if (attachment != null) {
                MimeBodyPart attachmentBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                attachmentBodyPart.setDataHandler(new DataHandler(source));
                attachmentBodyPart.setFileName(attachment.getName());
                multipart.addBodyPart(attachmentBodyPart);
            }

            // Set the multipart as the message content
            message.setContent(multipart);

            // Send email
            Transport.send(message);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mail sent successfully");
            alert.setHeaderText(null);
            alert.setContentText("Mail sent successfully to " + to);
            alert.show();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> alert.hide()));
            timeline.play();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void sendMailex(String toEmail, String subject, String body) {
        String fromEmail = "evh0hve@gmail.com";
        String password = "cffw brbd rtcv cxal";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void sendEmail(MouseEvent event) {
        // Sender's email and password
        String sessionId = SessionManager.getLastSessionId();
        User user = UserService.getUserFromSession(sessionId);
        String senderEmail = user.getEmail();
        String senderPassword = "cffw brbd rtcv cxal"; // Your email password
        System.out.println(senderPassword);

        // Recipient's email address
        String recipientEmail = txtRecipientEmail.getText();
        String subject = txtSubject.getText();
        String body = txtBody.getText();

        // Call the sendMail function
        Mailing.sendMail(senderEmail, senderPassword, recipientEmail, subject, body, attachmentFile);
        clearFields();
    }

    private void clearFields() {
        txtBody.clear();
        txtRecipientEmail.clear();
        txtSubject.clear();
        attachmentFile = null;
    }
}
