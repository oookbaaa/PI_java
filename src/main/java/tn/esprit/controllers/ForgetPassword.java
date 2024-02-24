package tn.esprit.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

public class ForgetPassword {

    @FXML
    private ImageView reduceIcon;

    @FXML
    private TextField emailverif;

    private UserService userService = new UserService();



    @FXML
    private void envoyer_mdp(ActionEvent event) throws IOException {
        try {


            User user = userService.getUserByEmail(emailverif.getText());
            System.out.println("user : " + user);
            if (emailverif.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez saisir l'email.");
                alert.showAndWait();
                return;
            } else if (user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention");
                alert.setHeaderText(null);
                alert.setContentText("Aucun compte n'est enrigistré avec ce mail!");
                alert.showAndWait();

            } else {
                String verificationCode = generateVerificationCode();
                String subject = "Verification code for changing password :";
                String message = verificationCode ;
                String messagesms = verificationCode + " : est votre code de verification pour changer votre mot de passe.";



                // Send verification code to user
                String number = String.valueOf(user.getTel());
                SmsSender.sendSms(number, messagesms);
                System.out.println(number);
                System.out.println(messagesms);

                sendVerificationEmail(user.getEmail(), message);


                TextInputDialog verificationDialog = new TextInputDialog();
                verificationDialog.setTitle("Changer le mot de passe");
                verificationDialog.setHeaderText(null);
                verificationDialog.setContentText("Code de vérification envoyé par e-mail/sms:");
                Optional<String> enteredVerificationCode = verificationDialog.showAndWait();

                if (!enteredVerificationCode.isPresent() || enteredVerificationCode.get().isEmpty()) {
                    // Show a warning if verification code is empty
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Attention");
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez saisir le code de vérification.");
                    alert.showAndWait();
                } else if (!enteredVerificationCode.get().equals(verificationCode)) {
                    // Show a warning if verification code does not match
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Attention");
                    alert.setHeaderText(null);
                    alert.setContentText("Code de vérification incorrect.");
                    alert.showAndWait();
                } else {
                    // Continue if verification code is correct
                    TextInputDialog passwordDialog = new TextInputDialog();
                    passwordDialog.setTitle("Changer le mot de passe");
                    passwordDialog.setHeaderText(null);
                    passwordDialog.setContentText("Nouveau mot de passe:");
                    Optional<String> newPassword = passwordDialog.showAndWait();

                    if (newPassword.isPresent()) {
                        String newPasswordValue = newPassword.get();
                        // Validate password criteria
                        if (newPasswordValue.length() < 8 || !newPasswordValue.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
                            // Show a warning if password does not meet criteria
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Attention");
                            alert.setHeaderText(null);
                            alert.setContentText("Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre.");
                            alert.showAndWait();
                            return;
                        } else {
                            // Continue if password criteria are met
                            TextInputDialog confirmDialog = new TextInputDialog();
                            confirmDialog.setTitle("Changer le mot de passe");
                            confirmDialog.setHeaderText(null);
                            confirmDialog.setContentText("Confirmer le mot de passe:");
                            Optional<String> confirmedPassword = confirmDialog.showAndWait();

                            if (confirmedPassword.isPresent() && newPasswordValue.equals(confirmedPassword.get())) {
                                // Change password if confirmed password matches
                                userService.changePassword(newPasswordValue, emailverif.getText());

                                String body = "Good morning Mr/Ms " + user.getNom() + " ; "
                                        + "Your password has been changed Successfully.";
                                Mailing mailing = new Mailing();
                                mailing.sendMailex(user.getEmail(), subject, body);


                                SmsSender.sendSms(String.valueOf(user.getTel()), body);

                                emailverif.setVisible(false);
                                System.out.println("Mot de passe changé avec succès");
                                showAlert("Attention", null, "Mot de passe changé avec succès");


                                Parent root = FXMLLoader.load(getClass().getResource("/MainUI.fxml"));
                                emailverif.getScene().setRoot(root);
                            } else {
                                showAlert("Attention", null, "Les mots de passe ne correspondent pas");
                                System.out.println("Les mots de passe ne correspondent pas");
                            }
                        }
                    } else {
                        System.out.println("Utilisateur introuvable");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }




    private void showAlert(String title, String header, String content) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle(title);
                    alert.setHeaderText(header);
                    alert.setContentText(content);
                    alert.showAndWait();
                }



    private void sendVerificationEmail(String email, String code) {
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























    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }
    @FXML
    private void back_to_menu (MouseEvent event) throws IOException {
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
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        String code = sb.toString();
        return code;
    }

}