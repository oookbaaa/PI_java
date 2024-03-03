package tn.esprit.controllers;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

public class ForgetPassword {
    @FXML
    private JFXButton DONE;
    public JFXButton reset;
    public JFXTextField confirmPasswordField;
    public JFXTextField newPasswordField;
    public JFXTextField verificationCodeField;
    public ImageView iconemail;
    @FXML
    private JFXButton smsButton;

    @FXML
    private JFXButton emailButton;

    @FXML
    private ImageView reduceIcon;

    @FXML
    private TextField emailverif;

    private UserService userService = new UserService();

    private String correctVerificationCode; // Store the correct verification code here

    @FXML
    private void envoyer_mdp(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
        User user = userService.getUserByEmail(emailverif.getText());
        System.out.println("user : " + user);
        if (emailverif.getText().isEmpty()) {
            // Show warning if email is empty
            showAlert("Attention", null, "Veuillez saisir l'email.");
            return;
        } else if (user == null) {
            // Show error if no account is registered with the email
            showAlert("Attention", null, "Aucun compte n'est enrigistré avec ce mail!");
            return;
        } else {
            // Generate verification code
            correctVerificationCode = generateVerificationCode();

            // Show the verification code field
            // Hide other fields
            newPasswordField.setVisible(false);
            confirmPasswordField.setVisible(false);
            verificationCodeField.setVisible(false);
            emailverif.setVisible(false);
            iconemail.setVisible(false);
            // Hide reset button and show SMS and Email buttons
            reset.setVisible(false);
            smsButton.setVisible(true);
            emailButton.setVisible(true);
        }
    }

    @FXML
    private void sendSMS(ActionEvent event) {
        try {
            // Retrieve user information
            User user = userService.getUserByEmail(emailverif.getText());

            // Generate verification code
            String verificationCode = generateVerificationCode();
            String messagesms = verificationCode + " : est votre code de verification pour changer votre mot de passe.";

            // Send verification code via SMS
            String number = String.valueOf(user.getTel());
            SmsSender.sendSms(number, messagesms);

            // Store the correct verification code
            correctVerificationCode = verificationCode;
            smsButton.setVisible(false);
            emailButton.setVisible(false);
            verificationCodeField.setVisible(true);

            // Show success message
            showAlert("Success", null, "Verification code sent via SMS.");
        } catch (IOException e) {
            showAlert("Error", null, "Failed to send SMS.");
        }
    }

    @FXML
    private void sendEmail(ActionEvent event) {
        try {
            // Retrieve user information
            User user = userService.getUserByEmail(emailverif.getText());

            // Generate verification code
            String verificationCode = generateVerificationCode();
            String subject = "Verification code for changing password :";
            String message = verificationCode;

            // Send verification code via email
            sendVerificationEmail(user.getEmail(), message);

            // Store the correct verification code
            correctVerificationCode = verificationCode;
            smsButton.setVisible(false);
            emailButton.setVisible(false);
            verificationCodeField.setVisible(true);

            // Show success message
            showAlert("Success", null, "Verification code sent via email.");
        } catch (IOException | SQLException | NoSuchAlgorithmException e) {
            showAlert("Error", null, "Failed to send email.");
        }
    }

    @FXML
    private void verifyCode(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            String enteredVerificationCode = verificationCodeField.getText();
            // Check if the verification code field is empty
            if (enteredVerificationCode.isEmpty()) {
                // Show a warning message to fill the verification code
                showAlert("Attention", null, "Veuillez saisir le code de vérification.");
            } else {
                // Call the verifyCode method to proceed with verification
                verifyCode();
            }
        }
    }

    private void verifyCode() {
        String enteredVerificationCode = verificationCodeField.getText();
        // Validate entered verification code
        if (enteredVerificationCode.equals(correctVerificationCode)) {
            // Show the new password and confirm password fields
            newPasswordField.setVisible(true);
            confirmPasswordField.setVisible(true);
            DONE.setVisible(true);
            // Hide the verification code field
            verificationCodeField.setVisible(false);
            smsButton.setVisible(false);
            emailButton.setVisible(false);
        } else {
            verificationCodeField.setStyle("-fx-border-color: red;");
        }
    }

    @FXML
    private void changePassword(KeyEvent event) throws SQLException, NoSuchAlgorithmException, IOException {
        if (event.getCode() == KeyCode.ENTER) {

            String newPassword = newPasswordField.getText();
            String confirmedPassword = confirmPasswordField.getText();
            // Check if new password and confirmed password match
            if ((newPassword.equals(confirmedPassword))&&(newPassword.length() > 8) && (newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$"))) {
                // Change the password and redirect to the login interface
                userService.changePassword(newPassword, emailverif.getText());
                // Redirect to the login interface
                // For example:
                Parent root = FXMLLoader.load(getClass().getResource("/MainUi.fxml"));
                emailverif.getScene().setRoot(root);
            } else {
                confirmPasswordField.setStyle("-fx-border-color: red;");
            }
        }
    }

    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void back_to_menu(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainUI.fxml"));
        emailverif.getScene().setRoot(root);
    }

    @FXML
    private void reduceWindow(MouseEvent event) {
        // Get the stage from any node in the scene
        Stage stage = (Stage) reduceIcon.getScene().getWindow();
        // Minimize the stage
        stage.setIconified(true);
    }

    private String generateVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int length = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void sendVerificationEmail(String email, String code) throws IOException, SQLException, NoSuchAlgorithmException {
        User user = userService.getUserByEmail(email);
        String subject = "Verification code for password reset :";
        String body = "<html><head><style>" +
                "body { font-family: 'Arial', sans-serif; color: #333; background-color: #f9f9f9; margin: 0; padding: 0; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".header { background-color: #008000; color: #fff; padding: 20px; text-align: center; }" +
                ".content { background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                ".verification-code { background-color: #f2f2f2; padding: 10px; text-align: center; font-size: 20px; border-radius: 5px; }" +
                ".footer { background-color: #f9f9f9; padding: 20px; text-align: center; }" +
                "</style></head><body>" +
                "<div class='container'>" +
                "<div class='header'><h2>Password Reset Verification</h2></div>" +
                "<div class='content'>" +
                "<h3>Dear "+user.getNom()+" :"+"</h3>" +

                "<p>You have requested to reset your password. Please use the following verification code to proceed:</p>" +
                "<div class='verification-code'>" + code + "</div>" +
                "<p>If you did not request a password reset, please ignore this message.</p>" +
                "</div>" +
                "<div class='footer'>Best regards,<br/>The E.V.H Team</div>" +
                "</div></body></html>";
        Mailing mailing = new Mailing();
        mailing.sendMailex(email, subject, body);
    }
}
