package com.example.benayedjavafx;

import com.example.benayedjavafx.entities.formation;
import com.example.benayedjavafx.services.service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Document;


import javax.mail.MessagingException;
import javax.mail.internet.ParseException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.application.Application.launch;

public class AfficheFormationController implements Initializable {

    @FXML
    private Button AfficheQuiz;

    @FXML
    private TableColumn<?, ?> DatedebutField;

    @FXML
    private TableColumn<?, ?> DatefinField;

    @FXML
    private TableColumn<?, ?> DescriptionField;

    @FXML
    private TableColumn<?, ?> DureeField;

    @FXML
    private TableColumn<?, ?> FormateurField;

    @FXML
    private TableView<formation> FormationTable;

    @FXML
    private TableColumn<?, ?> ID_formationField;

    @FXML
    private TableColumn<?, ?> LieuField;

    @FXML
    private Button ModifierField;
    @FXML
    private TextField rechercheTextField;

    @FXML
    private Button Notification;

    @FXML
    private TableColumn<?, ?> TitreField;

    @FXML
    private Button supprimerButton;

    @FXML
    private TextField textFieldTriPersonnalise;







    @FXML
    void Add(ActionEvent event) {
        try {
            // Assuming 'Affiche' is the source of the event (e.g., a button)
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            // Load the Edit page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addFormation.fxml"));
            Parent root = loader.load();


            // Show the Edit page
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
    void Edit(ActionEvent event) throws SQLException {
        formation formationSelectionnee = FormationTable.getSelectionModel().getSelectedItem();
        System.out.println(formationSelectionnee);
        if (formationSelectionnee != null) {
            // Récupérer l'ID de la formation sélectionnée
            // Récupérer les détails de la formation à modifier à partir du service
            service service = new service();
            formation formationAModifier = service.Modifier(formationSelectionnee);
            System.out.println(formationAModifier);


            try {
                // Assuming 'Affiche' is the source of the event (e.g., a button)
                Node source = (Node) event.getSource();
                Stage currentStage = (Stage) source.getScene().getWindow();

                // Load the Edit page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editFormation.fxml"));
                Parent root = loader.load();

                // Pass any necessary data to the controller
                EditFormationController modifierController = loader.getController();
                modifierController.initDonnees(formationAModifier);

                // Show the Edit page
                Stage editStage = new Stage();
                editStage.setScene(new Scene(root));
                editStage.show();

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


    }

    @FXML
    void AfficheQuiz(ActionEvent event) {
        try {
            // Assuming 'Affiche' is the source of the event (e.g., a button)
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            // Load the Edit page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficheQuiz.fxml"));
            Parent root = loader.load();


            // Show the Edit page
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
    void delete(ActionEvent event) throws SQLException {
        // Récupérer l'élément sélectionné dans le tableau
        formation formationSelectionnee = FormationTable.getSelectionModel().getSelectedItem();

        if (formationSelectionnee != null) {
            // Récupérer l'ID de la formation sélectionnée
            int idFormation = formationSelectionnee.getId_formation();

            // Appeler la fonction de suppression dans votre service
            service service = new service();
            service.supprimer(idFormation);

            // Rafraîchir le tableau ou effectuer d'autres mises à jour après la suppression
            actualiserTableau();
        }
    }


    private final service monService = new service();
    private ObservableList<formation> toutesFormations;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualiserTableau();

        rechercheTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleRecherche(newValue);
        });
    }

    private void actualiserTableau() {
        try {
            List<formation> events = monService.recuperer();
            toutesFormations = FXCollections.observableArrayList(events);

            FormationTable.setItems(toutesFormations);

            ID_formationField.setCellValueFactory(new PropertyValueFactory<>("id_formation"));
            LieuField.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            DescriptionField.setCellValueFactory(new PropertyValueFactory<>("description"));
            DatefinField.setCellValueFactory(new PropertyValueFactory<>("df"));
            TitreField.setCellValueFactory(new PropertyValueFactory<>("titre"));
            DatedebutField.setCellValueFactory(new PropertyValueFactory<>("dd"));
            DureeField.setCellValueFactory(new PropertyValueFactory<>("duree"));
            FormateurField.setCellValueFactory(new PropertyValueFactory<>("formateur"));


        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception de manière appropriée
        }
    }

    private void handleRecherche(String chaine) {
        ObservableList<formation> resultats;

        if (chaine.isEmpty()) {
            // Si le champ de recherche est vide, réaffichez toutes les formations
            resultats = toutesFormations;
        } else {
            // Sinon, effectuez la recherche normale
            resultats = monService.chercherFormation(chaine);
        }

        FormationTable.setItems(resultats);
        // Traitez les résultats comme nécessaire (par exemple, mettez à jour une liste dans votre interface graphique)
    }
    @FXML
    void imprimerPDF(ActionEvent event) {
        // Récupérer la formation sélectionnée
        formation formationSelectionnee = FormationTable.getSelectionModel().getSelectedItem();

        if (formationSelectionnee != null) {
            // Utilisez le service pour générer le PDF
            service monService = new service();
            String cheminFichier = formationSelectionnee.getTitre()+".pdf"; // Spécifiez le chemin de votre fichier PDF

            monService.genererPDF(formationSelectionnee, cheminFichier);

            // Affichez un message ou effectuez d'autres actions après la génération du PDF
            System.out.println("PDF généré avec succès pour la formation: " + formationSelectionnee.getTitre());
        } else {
            // Affichez un message d'erreur si aucune formation n'est sélectionnée
            System.err.println("Aucune formation sélectionnée.");
        }
    }
}






