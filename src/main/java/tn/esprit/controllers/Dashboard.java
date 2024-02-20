package tn.esprit.controllers;


import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    private final UserService ps = new UserService();

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
    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    @FXML
    private ImageView Exit;

    @FXML
    private AnchorPane slider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        makeStageDrageable();
        Exit.setOnMouseClicked(event -> {
            System.exit(0);
        });
        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose .setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });
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
}
