package tn.esprit.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tn.esprit.models.Role;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.IOException;

public class Welcome {
    @FXML
    private Label welcomeLabel;

    // Method to set the username and display it in the label
    public void setUserName(String username) {
            welcomeLabel.setText("Welcome back," + username);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(6), e -> {
            try {
                closeWelcomeWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));
        timeline.play();
    }



    private void closeWelcomeWindow() throws IOException {
        // Get the stage of the Welcome.fxml interface
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.close();

        // Load and display the dashboard.fxml interface
        try {
            User user = UserService.getCurrentUser(); // Assuming you have a method like this in your UserService class
            if (user != null) {
                Role userRole = Role.valueOf(user.getRole());
                String username = user.getNom(); // Assuming this is the username

                if (userRole != Role.ADMIN) {
                    System.out.println("User role: " + userRole);
                    Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
                    Stage homeStage = new Stage();
                    homeStage.setScene(new Scene(root));
                    homeStage.show();
                } else {
                    System.out.println("User role: " + userRole);
                    Parent root = FXMLLoader.load(getClass().getResource("/Dashboard.fxml"));
                    Stage dashboardStage = new Stage();
                    dashboardStage.initStyle(StageStyle.UNDECORATED);
                    dashboardStage.setScene(new Scene(root));
                    dashboardStage.show();

                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }





        // Your sign in logic here...

        private void showDashboardOrHome(User user) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                Parent root;

                if (Role.ADMIN.equals(Role.valueOf(user.getRole()))) {
                    root = loader.load();
                    Dashboard controller = loader.getController();

                } else {
                    loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                    root = loader.load();
                    Home controller = loader.getController();

                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                // Close the sign in window if needed
                // Stage signInStage = (Stage) signInButton.getScene().getWindow();
                // signInStage.close();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any exceptions
            }
        }
}
