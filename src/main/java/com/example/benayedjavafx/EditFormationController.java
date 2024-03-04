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

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditFormationController {

    @FXML
    private DatePicker DD;

    @FXML
    private DatePicker DF;

    @FXML
    private TextField Description;

    @FXML
    private TextField Duree;

    @FXML
    private Button EditButton;

    @FXML
    private TextField Formateur;

    @FXML
    private TextField id_formation;

    @FXML
    private TextField Lieu;

    @FXML
    private TextField Titre;



    @FXML
    void Edit(ActionEvent event) throws SQLException {
        try {
            // Vérifier si les champs obligatoires sont vides
            if (Duree.getText().isEmpty() || Titre.getText().isEmpty() ||
                    Description.getText().isEmpty() || Lieu.getText().isEmpty() ||
                    Formateur.getText().isEmpty() || DD.getValue() == null ||
                    DF.getValue() == null) {
                // Afficher un message d'erreur si un champ est vide
                showAlert("Veuillez remplir tous les champs.");
                return; // Arrêter l'exécution de la méthode s'il y a des champs vides
            }

            // Conversion des valeurs des champs en entiers
            int id = Integer.parseInt(id_formation.getText());
            int duree = Integer.parseInt(Duree.getText());

            // Conversion des valeurs des champs en dates
            LocalDate dateD = DD.getValue();
            LocalDate dateF = DF.getValue();

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
            formation f = new formation(id, duree, Titre.getText(), Description.getText(),
                    Lieu.getText(), Formateur.getText(),
                    Date.valueOf(dateD), Date.valueOf(dateF));

            // Modifier la formation dans la base de données
            new service().Modifier(f);

            // Charger l'interface FXML d'affichage des formations
            try {
                // Assuming 'Affiche' is the source of the event (e.g., a button)
                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();

                // Load the new page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("afficheFormation.fxml"));
                Parent root = loader.load();

                // Show the new page
                Stage newStage = new Stage();
                newStage.setScene(new Scene(root));
                newStage.show();

                // Close the current page
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    public void initDonnees(formation formationAModifier) {
        if (id_formation != null && formationAModifier != null) {
            id_formation.setText(String.valueOf(formationAModifier.getId_formation()));
            Duree.setText(String.valueOf(formationAModifier.getDuree()));
            Description.setText(formationAModifier.getDescription());
            Formateur.setText(formationAModifier.getFormateur());
            Lieu.setText(formationAModifier.getLieu());
            Titre.setText(formationAModifier.getTitre());

            // Assurez-vous également que DD et DF ne sont pas null
            if (formationAModifier.getDd() != null) {
                DD.setValue(LocalDate.parse(formationAModifier.getDd().toString()));
            }

            if (formationAModifier.getDf() != null) {
                DF.setValue(LocalDate.parse(formationAModifier.getDf().toString()));
            }
        } else {
            // Gérer le cas où formationAModifier ou id_formation est null
            showAlert("La formation à modifier est nulle ou id_formation est null.");
        }
    }}


