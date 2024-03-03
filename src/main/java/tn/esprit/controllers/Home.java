package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.models.User;
import tn.esprit.utils.SessionManager;

import java.io.IOException;

public class Home {
    @FXML
    private JFXTextField txt_search;
    @FXML
    private Label dashusername;
    @FXML
    private BorderPane borderpane;

    @FXML
    private JFXButton btn_profile;

    @FXML
    private JFXButton btn_contact_us;

    @FXML
    private JFXButton btn_logout;

    @FXML
    private ImageView reduceIcon;

    @FXML
    private Circle profileImg;

    @FXML
    private ImageView profile;

    @FXML
    private Pane paneshow;


    User loggedInUser = SessionManager.getSession(SessionManager.getLastSessionId());

    @FXML
    private void initialize() {


        dashusername.setText(loggedInUser.getNom());
if (loggedInUser.getPhoto() != null) {
    User loggedInUser = SessionManager.getSession(SessionManager.getLastSessionId());
    profileImg.setFill(new ImagePattern(new Image(loggedInUser.getPhoto())));
}
else {
    profileImg.setVisible(false);
}

    }

    @FXML
    private void btn_view_profile(MouseEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OperationUser.fxml"));
        Parent root = loader.load();
        OperationUser controller = loader.getController();
        controller.showUserDetails(loggedInUser);
        paneshow.getChildren().setAll(root);

    }

    @FXML
    private void btn_contact_us() {
        // Placeholder: Open a contact form or provide contact information for customer support
        showAlert("Contact Us", "For support, please email support@example.com");
    }

    @FXML
    private void logoutuser(ActionEvent event) throws IOException {

        SessionManager.endSession();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainUI.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void close_app() {
        System.exit(0);
    }

    @FXML
    private void reduceWindow() {
        Stage stage = (Stage) reduceIcon.getScene().getWindow();
        stage.setIconified(true);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




}
