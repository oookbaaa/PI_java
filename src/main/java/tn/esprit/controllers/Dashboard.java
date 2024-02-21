package tn.esprit.controllers;


import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tn.esprit.models.Status;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dashboard implements Initializable {
    User user = null ;
    String query = null ;
    private final UserService ps = new UserService();
    @FXML
    private TableColumn<User, String> statusc;
    @FXML
    private TableColumn<User, String> operationc;

    @FXML
    private TableColumn<User, String> adressec;

    @FXML
    private TableColumn<User, String> emailc;

    @FXML
    private TableColumn<User, Integer> idc;

    @FXML
    private TableColumn<User, String> nomc;
    @FXML
    private TableColumn<User, String> rolec;
    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Integer> telc;

    private double xOffset=0;
    private double yOffset=0;
    @FXML
    private AnchorPane parentd;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        makeStageDrageable();

        try {
            List<User> users = ps.recuperer();
            ObservableList<User> observableList = FXCollections.observableList(users);
            tableView.setItems(observableList);


            nomc.setCellValueFactory(new PropertyValueFactory<>("nom"));
            emailc.setCellValueFactory(new PropertyValueFactory<>("email"));
            telc.setCellValueFactory(new PropertyValueFactory<>("tel"));
            rolec.setCellValueFactory(new PropertyValueFactory<>("role"));
            idc.setCellValueFactory(new PropertyValueFactory<>("id"));
            adressec.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            statusc.setCellValueFactory(new PropertyValueFactory<>("status"));


        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


    }









    public void makeStageDrageable(){
        parentd.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();

                yOffset = event.getSceneY();
            }

        });
        parentd.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                parentd.getScene().getWindow().setX(event.getScreenX() - xOffset);
                parentd.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }

        });
    }
    @FXML
    private void reduceWindow(MouseEvent event) {
        Stage stage = (Stage) reduceIcon.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private ImageView reduceIcon;
    @FXML
    private ImageView maximizeIcon;











    private void blockUser(User user) {
        // Implement the logic to block the user
        // This might involve updating the user's status in the database
        // For example, if you have a UserService that handles user operations:
        try {
            UserService us = new UserService(); // Instantiate your UserService class
            user.setStatus(Status.INACTIVE); // Assuming you have a setStatus method in your User class
            us.modifier(user); // Update the user status in the database
            // Optionally, you can update the UI to reflect the user's blocked status
            // For example, if you have an ObservableList<User> backing your TableView:
            ObservableList<User> userList = tableView.getItems();
            userList.set(userList.indexOf(user), user); // Update the user in the list
            // Notify the TableView to refresh the view
            tableView.refresh();
            // Optionally, display a success message
            System.out.println("User " + user.getNom() + " blocked successfully.");
        } catch (Exception e) {
            // Handle any exceptions that occur during the blocking operation
            System.err.println("Error blocking user: " + e.getMessage());
        }
    }

}
