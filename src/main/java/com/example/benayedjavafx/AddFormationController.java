package com.example.benayedjavafx;
import com.example.benayedjavafx.entities.formation;
import com.example.benayedjavafx.services.service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;


import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;

public class AddFormationController {

    @FXML
    private DatePicker DDField;

    @FXML
    private DatePicker DFField;

    @FXML
    private TextField DescriptionField;

    @FXML
    private TextField DureeField;

    @FXML
    private TextField FormateurField;

    @FXML
    private TextField LieuField;

    @FXML
    private TextField TitreField;

    @FXML
    private Button addButton;
    private boolean NoDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate myDate = DDField.getValue();
        LocalDate myDate1 = DFField.getValue();
        int comparisonResult = myDate.compareTo(currentDate);
        int comparisonResult1 = myDate1.compareTo(currentDate);
        boolean test = true;
        if (comparisonResult < 0) {
            // myDate est antérieure à currentDate
            test = true;
        } else if (comparisonResult > 0) {
            // myDate est postérieure à currentDate
            test = false;
        }
        if (comparisonResult1 < 0) {
            // myDate est antérieure à currentDate
            test = true;
        } else if (comparisonResult1 > 0) {
            // myDate est postérieure à currentDate
            test = false;
        }
        return test;
    }


    @FXML
    void Afficher(ActionEvent event) {
        try {

            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficheFormation.fxml"));
            Parent root = loader.load();



            Stage addStage = new Stage();
            addStage.setScene(new Scene(root));
            addStage.show();

            // Close the current page (Affiche page)
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                System.err.println("Root cause: " + cause.getMessage());
            }
        }

    }

    @FXML
    void addFormation(ActionEvent event) {
        try {
            // Vérifier si les champs obligatoires sont vides
            if (DureeField.getText().isEmpty() || TitreField.getText().isEmpty() ||
                    DescriptionField.getText().isEmpty() || LieuField.getText().isEmpty() ||
                    FormateurField.getText().isEmpty() || DDField.getValue() == null ||
                    DFField.getValue() == null) {
                // Afficher un message d'erreur si un champ est vide
                showAlert("Veuillez remplir tous les champs.");
                return; // Arrêter l'exécution de la méthode s'il y a des champs vides
            }

            // Vérifier si la date de début est après la date d'aujourd'hui
            if (NoDate()) {
                showAlert("La date de début doit être après la date d'aujourd'hui.");
                return; // Arrêter l'exécution de la méthode si la date n'est pas valide
            }

            // Conversion des valeurs des champs en entiers
            int duree = Integer.parseInt(DureeField.getText());

            // Conversion des valeurs des champs en dates
            LocalDate dateD = DDField.getValue();
            LocalDate dateF = DFField.getValue();

            // Vérifier si dateF est au moins 30 jours après dateD
            if (dateF.isBefore(dateD.plusDays(30))) {
                showAlert("La date de fin doit être au moins 30 jours après la date de début.");
                return;
            }

            // Vérifier si les deux dates ne sont pas dans le passé
            if (dateD.isBefore(LocalDate.now()) || dateF.isBefore(LocalDate.now())) {
                showAlert("Les dates ne peuvent pas être dans le passé.");
                return;
            }

            // Création d'un nouvel objet formation
            formation f = new formation(0,
                    Integer.parseInt(DureeField.getText()),
                    TitreField.getText(),
                    DescriptionField.getText(),
                    LieuField.getText(),
                    FormateurField.getText(),
                    Date.valueOf(dateD),
                    Date.valueOf(dateF));


            // Ajouter la formation à la base de données
            new service().ajouter(f);
            showSuccessAlert("Ajout réussi");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            showAlert("Veuillez saisir des valeurs valides pour les champs numériques.");
        } catch (Exception e) {
            e.printStackTrace();
            Throwable cause = e.getCause();
            if (cause != null) {
                System.err.println("Root cause: " + cause.getMessage());
            }
        }
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout réussi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        Notifications.create().title("Success").text("La formation est ajouté avec succes").showInformation();

    }
    @Override
    public void start(Stage primaryStage) {
        // Chemin vers le flux vidéo de la caméra
        String videoPath = "http://path/to/your/camera/stream";

        // Créer le lecteur média avec le flux vidéo
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // Créer la vue média
        MediaView mediaView = new MediaView(mediaPlayer);

        StackPane root = new StackPane();
        root.getChildren().add(mediaView);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Camera App");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Commencer la lecture du flux vidéo
        mediaPlayer.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    }






