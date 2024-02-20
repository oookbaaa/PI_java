package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.util.Random;

public class ForgetPassword {
    @FXML
    private ImageView reduceIcon;
    @FXML
    private JFXButton reset;
    @FXML
    private TextField emailverif;
    @FXML
//    private void envoyer_mdp(ActionEvent event) throws SQLException, IOException {
//        try {
//            UserService usr = new UserService();
//
//            User user = usr.getUserByEmail(email.getText());
//            if (user != null) {
//                String verificationCode = generateVerificationCode();
//                String subject = "Verification code for changing password";
//                String message = "Dear " + user.getNom() + ",\n\n"
//                        + "Here is your verification code to change your password: " + verificationCode + "\n\n"
//                        + "Please enter this code in the application to proceed with changing your password.\n\n"
//                        + "Best regards,\n"
//                        + "The application team";
//                sendVerificationEmail(user.getEmail(), message);
//                SmsSender smsSender = new SmsSender();
//                smsSender.sendSms("+21658992538",message);
//
//                AnimationType type = AnimationType.POPUP;
//                TrayNotification tr = new TrayNotification();
//
//                tr.setAnimationType(type);
//                tr.setTitle("code de vérification");
//                tr.setMessage("code envoyé par mail et sms");
//                tr.setNotificationType(NotificationType.SUCCESS);
//                tr.showAndDismiss(Duration.millis(5000));
//                TextInputDialog verificationDialog = new TextInputDialog();
//                verificationDialog.setTitle("Changer le mot de passe");
//                verificationDialog.setHeaderText(null);
//                verificationDialog.setContentText("Code de vérification envoyé par e-mail:");
//                Optional<String> enteredVerificationCode = verificationDialog.showAndWait();
//                TextInputDialog passwordDialog = new TextInputDialog();
//                passwordDialog.setTitle("Changer le mot de passe");
//                passwordDialog.setHeaderText(null);
//                passwordDialog.setContentText("Nouveau mot de passe:");
//                Optional<String> newPassword = passwordDialog.showAndWait();
//
//                if (newPassword.isPresent()) {
//                    TextInputDialog confirmDialog = new TextInputDialog();
//                    confirmDialog.setTitle("Changer le mot de passe");
//                    confirmDialog.setHeaderText(null);
//                    confirmDialog.setContentText("Confirmer le mot de passe:");
//                    Optional<String> confirmedPassword = confirmDialog.showAndWait();
//
//                    if (confirmedPassword.isPresent() && newPassword.get().equals(confirmedPassword.get())) {
//
//                        if (enteredVerificationCode.isPresent() && enteredVerificationCode.get().equals(verificationCode)) {
//                            usr.changePassword(newPassword.get(), email.getText());
//                            Mailing m = new Mailing();
//                            String body = "Bonjour Mme/mr " + user.getNom() + "\n"
//                                    + "Votre mot de passe a été changé avec succès.";
//                            m.sendEmail(email.getText(), "Changement de mot de passe", body);
//                            email.setVisible(false);
//                            System.out.println("Mot de passe changé avec succès");
//
//                            tr.setAnimationType(type);
//                            tr.setTitle("changer mot de passe");
//                            tr.setMessage("Mot de passe changé avec succès");
//                            tr.setNotificationType(NotificationType.SUCCESS);
//                            tr.showAndDismiss(Duration.millis(5000));
//                            GuiNavigate nav = new GuiNavigate();
//                            nav.navigate(event, "GestionUser", "/gestionuser/utils/login.fxml");
//                        } else {
//                            System.out.println("Les mots de passe ne correspondent pas");
//
//                            tr.setAnimationType(type);
//                            tr.setTitle("Erreur");
//                            tr.setMessage("Mot de passe non changé");
//                            tr.setNotificationType(NotificationType.ERROR);
//                            tr.showAndDismiss(Duration.millis(5000));
//                        }
//                    }
//                } else {
//                    System.out.println("Utilisateur introuvable");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private String generateVerificationCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 6;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

//    private void sendVerificationEmail(String email, String code) {
//        String subject = "Verification code for password reset";
//        String body = "Hello,\n\n"
//                + "You have requested to reset your password. Please use the following verification code to proceed:\n\n"
//                + code + "\n\n"
//                + "If you did not request a password reset, please ignore this message.\n\n"
//                + "Best regards,\n"
//                + "The Password Reset Team";
//        Mailing mailing = new Mailing();
//        mailing.sendEmail(email, subject, body);
//    }




































































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

}
