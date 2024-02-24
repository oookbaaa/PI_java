package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.models.Role;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.utils.SessionManager;

import javax.mail.internet.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class Dash implements Initializable {

    private double xOffset=0;
    private double yOffset=0;
    @FXML
    private HBox root;
    @FXML
    private JFXButton btn_logout;
    @FXML
    private BorderPane borderpane;
    @FXML
    private ImageView reduceIcon;
    @FXML
    private JFXButton btn_list;
    @FXML
    private ImageView pdf;
    @FXML
    private JFXButton btn_report;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        makeStageDrageable();

    }
    @FXML
    private void btn_list_view(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
        borderpane.setCenter(view);
    }

    @FXML
    private void btn_Mailing(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/Mail.fxml"));
        borderpane.setCenter(view);
    }

    public void makeStageDrageable(){
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                root.getScene().getWindow().setX(event.getScreenX() - xOffset);
                root.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }

        });
    }
    @FXML
    private void reduceWindow(MouseEvent event) {
        Stage stage = (Stage) reduceIcon.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void close_app(MouseEvent event){
        System.exit(0);

    }
    public String sessionId;


    @FXML
    private void logout(ActionEvent event) throws IOException {

        SessionManager.endSession();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainUI.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



}
