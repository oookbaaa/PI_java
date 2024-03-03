package tn.esprit.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.models.Status;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.controllers.Home;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userDashboardItem implements Initializable {

    @FXML
    private AnchorPane doneAction;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Button banButton;
    @FXML
    private Circle imageCircle;
    @FXML
    private Label roleLabel;
    private User user;

    public void setFeedBackData(User user){
        imageCircle.setFill(new ImagePattern(
                new Image(user.getPhoto().replace("\\","/"))
        ));
        fullNameLabel.setText(user.getNom());
        roleLabel.setText(user.getRole());
        this.user = user;
        banButton.setOnAction(event ->
        {
            try {
                banUser(user.getId(),false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    public void onDetailsClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OperationAdmin.fxml"));
        Parent root = fxmlLoader.load();
        OperationAdmin controller = fxmlLoader.getController();
        controller.showUserDetails(user);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  // Get the current stage
        stage.setScene(scene);
        stage.show();



    }
    @FXML
    public void banUser(int id,boolean b) throws SQLException {

        UserService usr = new UserService();
        Status currentUserState = user.getStatus();

        if (currentUserState.equals(Status.ACTIVE)) {
            // User is currently active, so ban the user
            user.setStatus(Status.INACTIVE);
            usr.modifier(user);
            doneAction.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), e -> doneAction.setVisible(false)));
            timeline.setCycleCount(1);
            timeline.play();
            System.out.println("User banned");
        } else {
            // User is currently banned, so unban the user
            user.setStatus(Status.ACTIVE);
            usr.modifier(user);
            doneAction.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), e -> doneAction.setVisible(false)));
            timeline.setCycleCount(1);
            timeline.play();
            System.out.println("User unbanned");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doneAction.setVisible(false);


    }
}