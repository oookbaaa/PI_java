package tn.esprit.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.security.cert.PolicyNode;
import java.util.ResourceBundle;

public class SignIn implements Initializable {

    String password;
    private final UserService ps = new UserService();

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXTextField usernameField;
    private double xOffset=0;
    private double yOffset=0;
    @FXML
    private AnchorPane parent;

    @FXML
    private Pane parentpane;



    @FXML
    private ImageView reduceIcon;
    @FXML
    private ImageView lblopen;
    @FXML
    private ImageView lblclose;

    @FXML
    private Label LoginMessagelabel;



    @FXML
    private JFXTextField txtShowPassword;





public void HidePasswordOnAction(KeyEvent event) {
    password=passwordField.getText();
    txtShowPassword.setText(password);


}
    public void ShowPasswordOnAction(KeyEvent event) {
    password=txtShowPassword.getText();
    passwordField.setText(password);

    }

    public void Close_Eye_OnAction(MouseEvent event) {
      txtShowPassword.setVisible(true);
      lblopen.setVisible(true);
        lblclose.setVisible(false);
        passwordField.setVisible(false);

    }
    public void Open_Eye_OnAction(MouseEvent event) {
        txtShowPassword.setVisible(false);
        lblopen.setVisible(false);
        lblclose.setVisible(true);
        passwordField.setVisible(true);
    }


















    @FXML
    void signIn(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            LoginMessagelabel.setText("Please enter email and password.");
//            showAlert(Alert.AlertType.ERROR, "Error", "Please enter email and password.");
            return;
        }
        else if (!usernameField.getText().matches(emailPattern)) {
            // Afficher un message d'erreur indiquant que l'email est invalide
            LoginMessagelabel.setText("Please enter a valid email address.");

            return; // Sortir de la méthode si l'email est invalide
        }

        // Query the database to check if the user exists and the password is correct
        User user = ps.authenticateUser(username, password);

        if (user != null) {
            LoginMessagelabel.setText("Login successful!");
            System.out.println("ena houni shihhhhh");

           // openDashboard();
            openWelcomeWindow(user.getNom());


        } else {
            LoginMessagelabel.setText("Invalid email or password.");
//            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email or password.");
        }
    }


    // Method to open the Welcome.fxml interface
    private void openWelcomeWindow(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Welcome.fxml"));
            Parent root = loader.load();
            Welcome controller = loader.getController();
            controller.setUserName(username);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the sign-in window
            Stage signInStage = (Stage) usernameField.getScene().getWindow();
            signInStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






























































    public void makeStageDrageable(){
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }

        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                parent.getScene().getWindow().setX(event.getScreenX() - xOffset);
                parent.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }

        });
    }
    @FXML
    private void open_registration(MouseEvent event ) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/RegistrationUi.fxml")); // load the fxml file and return it()s)
        parentpane.getChildren().removeAll();
        parentpane.getChildren().setAll(fxml);
    }

    @FXML
    private void open_forget(MouseEvent event ) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/ForgetPassword.fxml"));
        parentpane.getChildren().removeAll();
        parentpane.getChildren().setAll(fxml);
    }


    @FXML
    private void reduceWindow(MouseEvent event) {
        Stage stage = (Stage) reduceIcon.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    private ImageView maximizeIcon;

    @FXML
    private void toggleMaximizeWindow(MouseEvent event) {
        // Get the stage from any node in the scene
        Stage stage = (Stage) maximizeIcon.getScene().getWindow();
        // Toggle between maximized and restored (normal) state
        if (stage.isMaximized()) {
            stage.setMaximized(false); // Restore window to normal size
        } else {
            stage.setMaximized(true); // Maximize window
        }
    }

    @FXML
    private void close_app(MouseEvent event){
        System.exit(0);

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeStageDrageable();
        txtShowPassword.setVisible(false);
        lblopen.setVisible(false);
    }
}
