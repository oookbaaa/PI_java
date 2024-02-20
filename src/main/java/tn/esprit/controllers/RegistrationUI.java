package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import tn.esprit.models.Role;
import tn.esprit.models.User;
import tn.esprit.services.UserService;


public class RegistrationUI implements Initializable {
    private final UserService ps = new UserService();


    private ObservableList<String> list = FXCollections.observableArrayList();
    @FXML
    private JFXTextField adressTF;

    @FXML
    private JFXButton choosefileBT;

    @FXML
    private JFXPasswordField cmdpPF;

    @FXML
    private JFXTextField emailTF;

    @FXML
    private JFXPasswordField mdpPF;

    @FXML
    private JFXTextField nomTF;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXComboBox<String> roleCB;

    @FXML
    private JFXTextField telTF;

    @FXML
    private ImageView reduceIcon;

    @FXML
    private Pane signUpPane;

    @FXML
    private Label registrationlabel;

    public Pane getParentPane() {
        return signUpPane;
    }

    @FXML
    private ImageView userPhoto;

    @FXML
    void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(userPhoto.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            userPhoto.setImage(image);
        }
    }






    private boolean validateFields() {
        String telPattern = "\\d{8}";
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        String password = mdpPF.getText();
        String confirmPassword = cmdpPF.getText();

        if (!nomTF.getText().isEmpty() && !emailTF.getText().isEmpty() && roleCB.getValue() != null &&
                !telTF.getText().isEmpty() && !adressTF.getText().isEmpty() && !mdpPF.getText().isEmpty() && !cmdpPF.getText().isEmpty()) {
            if (!telTF.getText().matches(telPattern)) {
                registrationlabel.setText("Please enter a valid phone number (8 digits).");
                return false;
            } else if (!emailTF.getText().matches(emailPattern)) {
                registrationlabel.setText("Please enter a valid email address.");
                return false;
            } else if (password.length() < 8 || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
                registrationlabel.setText("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter");
                return false;
            } else if (confirmPassword.length() < 8 || !confirmPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
                registrationlabel.setText("Confirm Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter");
                return false;
            } else if (!password.equals(confirmPassword)) {
                registrationlabel.setText("Password and Confirm Password do not match.");
                return false;
            }
            registrationlabel.setText("");
            // If all fields are valid, proceed with registration
            addUserToDatabase();
        } else {
            registrationlabel.setText("Please fill all necessary fields ");
        }
        return false;
    }

    private void addUserToDatabase() {
        try {
            String photo = userPhoto.getImage().getUrl();
            String nom = nomTF.getText();
            String email = emailTF.getText();
            int tel = Integer.parseInt(telTF.getText());
            String address = adressTF.getText();
            String mdp = mdpPF.getText();
            String role = roleCB.getValue();

            User user = new User(tel, nom, email, mdp, role, address, photo);
            ps.ajouter(user);
            clearFields();
            registrationlabel.setText("User added successfully");
        } catch (Exception e) {
            registrationlabel.setText("An error occurred: " + e.getMessage());
        }
    }




















































    @FXML
    void AddUser(ActionEvent event) {



       validateFields();
//        String telPattern = "\\d{8}";
//        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Expression régulière pour un email valide
//        String password = mdpPF.getText();
//        String confirmPassword = cmdpPF.getText();
//        if (!nomTF.getText().isEmpty() || !emailTF.getText().isEmpty() || roleCB.getValue()!=null || !telTF.getText().isEmpty() || !adressTF.getText().isEmpty() || !mdpPF.getText().isEmpty() || !cmdpPF.getText().isEmpty()) {
//
//            if (!telTF.getText().matches(telPattern)) {
//                // Afficher un message d'erreur indiquant que le numéro de téléphone est invalide
//                registrationlabel.setText("Please enter a valid phone number (8 digits).");
////
//                return; // Sortir de la méthode si le numéro de téléphone est invalide
//            }
//            else if (!emailTF.getText().matches(emailPattern)) {
//                // Afficher un message d'erreur indiquant que l'email est invalide
//                registrationlabel.setText("Please enter a valid email address.");
////
//                return; // Sortir de la méthode si l'email est invalide
//            }
//            else if (password.length() < 8 || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).{8,}$")) {
//                // Afficher un message d'erreur indiquant que le mot de passe est invalide
//                registrationlabel.setText("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
////
//                return; // Sortir de la méthode si le mot de passe est invalide
//            }
//            else if (confirmPassword.length() < 8 || !confirmPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d).{8,}$")) {
//                // Afficher un message d'erreur indiquant que le mot de passe est invalide
//                registrationlabel.setText("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter");
////
//                return; // Sortir de la méthode si le mot de passe est invalide
//            }
//
//            try {
//                // Check if passwords match
//                if (!mdpPF.getText().equals(cmdpPF.getText())) {
//                    registrationlabel.setText("Password Mismatch");
////
//                    return; // Exit method if passwords don't match
//                }
//
//
//                String photo = userPhoto.getImage().getUrl(); // Convert userPhoto.getImage() to byte[]
//                String nom = nomTF.getText();
//                String email = emailTF.getText();
//                int tel = Integer.parseInt(telTF.getText());
//                String address = adressTF.getText();
//                String mdp = mdpPF.getText();
//                String role = roleCB.getValue();
//
//                // Create a new User object with the retrieved information
//                User user = new User(tel, nom, email, mdp, role, address, photo);
//
//                // Add the user to the database
//                ps.ajouter(user);
//                clearFields();
//                // Optionally, display a success message
//                registrationlabel.setText("User added successfully");
//
//
//            } catch (Exception e) {
//                // Handle any exceptions that occur (e.g., invalid input, database errors)
//            }
//        } else {
//            registrationlabel.setText("Please fill all necessary fields ");
//        }


    }



    @FXML
    private void close_app(MouseEvent event) {
        System.exit(0);
    }
    @FXML
    private void back_to_menu (MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/MainUI.fxml"));
        emailTF.getScene().setRoot(root);
    }


    public void selectRole(ObservableList<String> list) {
        list.add(Role.USER.toString());
        list.add(Role.FORMATEUR.toString());
        list.add(Role.EMPLOYE.toString());
    }
    @FXML
    private void reduceWindow(MouseEvent event) {
        // Get the stage from any node in the scene
        Stage stage = (Stage) reduceIcon.getScene().getWindow();
        // Minimize the stage
        stage.setIconified(true);
    }

    private void clearFields() {
        nomTF.clear();
        emailTF.clear();
        telTF.clear();
        adressTF.clear();
        mdpPF.clear();
        cmdpPF.clear();
        roleCB.getSelectionModel().clearSelection();
        choosefileBT.setText("");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectRole(list);
        roleCB.setItems(list);

        // Add event listeners to input fields for live validation
        nomTF.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        emailTF.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        telTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telTF.setText(newValue.replaceAll("[^\\d]", ""));
            }

            // Limiter la longueur du texte à 8 caractères
            if (telTF.getText().length() > 8) {
                String limitedText = telTF.getText().substring(0, 8);
                telTF.setText(limitedText);
            }
        });
        adressTF.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        mdpPF.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        cmdpPF.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        roleCB.valueProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }
    }
