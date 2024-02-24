package tn.esprit.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.Status;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private TableColumn<User, String> nomc;
    @FXML
    private TableColumn<User, String> rolec;
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> telc;
    @FXML
    private AnchorPane parentd;

    private double xOffset=0;
    private double yOffset=0;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeStageDrageable();

        try {
            List<User> users = ps.recuperer();
            users = users.stream()
                    .filter(user -> !user.getRole().equals("ADMIN"))
                    .collect(Collectors.toList());
            ObservableList<User> observableList = FXCollections.observableList(users);
            tableView.setItems(observableList);

            nomc.setCellValueFactory(new PropertyValueFactory<>("nom"));
            emailc.setCellValueFactory(new PropertyValueFactory<>("email"));
            telc.setCellValueFactory(new PropertyValueFactory<>("tel"));
            rolec.setCellValueFactory(new PropertyValueFactory<>("role"));
            adressec.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            statusc.setCellValueFactory(new PropertyValueFactory<>("status"));

            operationc.setCellFactory(cell -> new TableCell<>() {
                private final JFXButton blockButton = new JFXButton("Block");
                private final JFXButton unblockButton = new JFXButton("Unblock");
                {
                blockButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-radius: 5px; -fx-cursor: hand;");
                unblockButton.setStyle("-fx-background-color: red; -fx-text-fill: black; -fx-border-radius: 5px; -fx-cursor: hand;");


                    blockButton.setOnAction(event -> {
                        User user = getTableRow().getItem();
                        if (user != null) {
                            blockUser(user);
                        }
                    });

                    unblockButton.setOnAction(event -> {
                        User user = getTableRow().getItem();
                        if (user != null) {
                            unblockUser(user);
                        }
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        User user = getTableRow().getItem();
                        if (user != null && user.getStatus() == Status.ACTIVE) {
                            setGraphic(blockButton);
                        } else if (user != null && user.getStatus() == Status.INACTIVE) {
                            setGraphic(unblockButton);
                        }
                    }
                }
            });

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


    private void blockUser(User user) {
        try {
            UserService us = new UserService(); // Instantiate your UserService class
            user.setStatus(Status.INACTIVE); // Assuming you have a setStatus method in your User class
            us.modifier(user); // Update the user status in the database
            ObservableList<User> userList = tableView.getItems();
            userList.set(userList.indexOf(user), user);
            tableView.refresh();

            System.out.println("User " + user.getNom() + " blocked successfully.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("blocked successfully");
            alert.setHeaderText(null);
            alert.setContentText("User " + user.getNom() + " blocked successfully.");
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Error blocking user: " + e.getMessage());
        }
    }


    private void unblockUser(User user) {

        try {
            UserService us = new UserService(); // Instantiate your UserService class
            user.setStatus(Status.ACTIVE); // Assuming you have a setStatus method in your User class
            us.modifier(user); // Update the user status in the database
            ObservableList<User> userList = tableView.getItems();
            userList.set(userList.indexOf(user), user); // Update the user in the list
            tableView.refresh();
            System.out.println("User " + user.getNom() + " unblocked successfully.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("unblocked successfully");
            alert.setHeaderText(null);
            alert.setContentText("User " + user.getNom() + " unblocked successfully.");
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Error blocking user: " + e.getMessage());
        }
    }

}
