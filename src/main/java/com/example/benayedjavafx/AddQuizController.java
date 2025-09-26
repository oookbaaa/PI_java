package com.example.benayedjavafx;
import com.example.benayedjavafx.entities.Quiz;
import com.example.benayedjavafx.services.ServiceQuiz;
import com.example.benayedjavafx.services.IserviceQuiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class AddQuizController implements Initializable {

    @FXML
    private TextField Duree;

    @FXML
    private Button addbutton;

    @FXML
    private TextField id_foramation;

    @FXML
    private TextField id_quiz;

    @FXML
    private TextField nbquestions;

    @FXML
    private TextField titre;

@FXML
    void AfficherQuiz(ActionEvent event) {
    try {

        Node source = (Node) event.getSource();
        Stage currentStage = (Stage) source.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("afficheQuiz.fxml"));
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
    void addQuiz(ActionEvent event) {
        try {
            // Vérifier si les champs obligatoires sont vides
            if (id_quiz.getText().isEmpty() || Duree.getText().isEmpty() ||
                    id_foramation.getText().isEmpty() || nbquestions.getText().isEmpty() ||
                    titre.getText().isEmpty()) {
                // Afficher un message d'erreur si un champ est vide
                showAlert("Veuillez remplir tous les champs.");
                return; // Arrêter l'exécution de la méthode s'il y a des champs vides
            }

            // Conversion des valeurs des champs en entiers
            int idQuiz = Integer.parseInt(id_quiz.getText());
            int duree = Integer.parseInt(Duree.getText());
            int idFormation = Integer.parseInt(id_foramation.getText());
            int nbQuestions = Integer.parseInt(nbquestions.getText());

            // Création d'un nouvel objet Quiz
            Quiz Q = new Quiz(idQuiz, duree, idFormation, nbQuestions, titre.getText());

            // Ajouter le quiz à la base de données
            new ServiceQuiz().ajouter(Q);
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
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}


