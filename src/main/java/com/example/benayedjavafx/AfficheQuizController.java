package com.example.benayedjavafx;

import com.example.benayedjavafx.entities.Quiz;
import com.example.benayedjavafx.entities.formation;
import com.example.benayedjavafx.services.ServiceQuiz;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Modality;


import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class AfficheQuizController implements Initializable {


    @FXML
    private TableColumn<Quiz, Integer> DureeQuiz;

    @FXML
    private TableColumn<Quiz, Integer> ID_Formation;

    @FXML
    private TableColumn<Quiz, Integer> IDquiz;

    @FXML
    private Button Modifierbutton;

    @FXML
    private TableColumn<Quiz, Integer> NB_Questions;

    @FXML
    private Button Supprimerbutton;

    @FXML
    private TableView<Quiz> TableQuiz;

    @FXML
    private TableColumn<Quiz, String> TitreQuiz;

    @FXML
    private Button addbutton;

Quiz quiz;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actualiserTableau();
    }

    @FXML
    void ADD(ActionEvent event) {
        try {
            // Assuming 'Affiche' is the source of the event (e.g., a button)
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            // Load the AddQuizController
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addQuiz.fxml"));
            Parent root = loader.load();
            AddQuizController addQuizController = loader.getController();

            // Show the AddQuizController stage
            Stage addStage = new Stage();
            addStage.setScene(new Scene(root));
            addStage.show();

            // Close the current AfficheQuizController stage
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
        actualiserTableau();
    }

    @FXML
    void Accueil(ActionEvent event) {
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
    void Modifier(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage currentStage = (Stage) source.getScene().getWindow();

            // Charger le fichier FXML pour l'édition du quiz
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editQuiz.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur associé à la scène d'édition
            EditQuizController editController = loader.getController();

            Stage addStage = new Stage();
            addStage.setScene(new Scene(root));

            // Afficher la scène d'édition en attente de résultat
            addStage.initModality(Modality.WINDOW_MODAL);
            addStage.initOwner(currentStage);
            addStage.showAndWait();

            // Vérifier si la chose a été modifiée
            boolean isModified = editController.isModified(); // Ajoutez une méthode isModified() dans votre contrôleur

            // Afficher l'alerte en conséquence
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(currentStage);

            if (isModified) {
                alert.setTitle("Modification réussie");
                alert.setHeaderText("La chose a été modifiée avec succès.");
            } else {
                alert.setTitle("Aucune modification");
                alert.setHeaderText("La chose n'a pas été modifiée.");
            }

            alert.showAndWait();

            // Fermer la fenêtre actuelle (Affiche page) uniquement si la chose a été modifiée
            if (isModified) {
                currentStage.close();
            }

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
        Quiz quizSelectionne = TableQuiz.getSelectionModel().getSelectedItem();

        if (quizSelectionne != null) {
            // Récupérer l'ID de la formation sélectionnée
            int idQuiz = quizSelectionne.getId_quiz();

            // Appeler la fonction de suppression dans votre service
            ServiceQuiz service = new ServiceQuiz();
            service.supprimer(idQuiz);

            // Rafraîchir le tableau ou effectuer d'autres mises à jour après la suppression
            actualiserTableau();

            // Afficher une alerte pour informer que la suppression a réussi
            showAlert("La suppression a réussi.");
        }
    }

    // Méthode pour afficher une boîte de dialogue d'alerte
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void actualiserTableau() {
        try {

            com.example.benayedjavafx.services.ServiceQuiz es = new ServiceQuiz();
            List<Quiz> events = es.recuperer();
            ObservableList list = FXCollections.observableArrayList(events);
            TableQuiz.setItems(list);


            IDquiz.setCellValueFactory(new PropertyValueFactory<>("id_quiz"));
            DureeQuiz.setCellValueFactory(new PropertyValueFactory<>("Duree"));
            ID_Formation.setCellValueFactory(new PropertyValueFactory<>("id_formation"));
            NB_Questions.setCellValueFactory(new PropertyValueFactory<>("nb_questions"));
            TitreQuiz.setCellValueFactory(new PropertyValueFactory<>("Titre"));


        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }


    private final ServiceQuiz service = new ServiceQuiz();

    private boolean isValidEmailAddress(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }


    void email(formation f){
        // Set the SMTP host and port for sending the email
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "azizbenayed179@gmail.com";
        String password = "357894612";

        // Set the properties for the email session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true"); // Enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // Enable TLS encryption

        // Create a new email session using the specified properties
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a new email message
            Message msg = new MimeMessage(session);

            // Set the "From" address for the email
            // msg.setFrom(new InternetAddress("ahmed.benabid2503@gmail.com"));

            // Add the "To" address for the email (including the recipient's name)
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("mahjoubeyaaa0@gmail.com"));

            // Set the subject and body text for the email
            msg.setSubject("Demande partenariat");
            msg.setText("Bonjour MR/Mme ,vous pouvez voir un nouveau demande à partire de "+f.getTitre()+" "+f.getDuree()+" de type: "+f.getId_formation()+".");

            // Create an alert to notify the user that the email was sent successfully

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation d'envoie");
            alert.setHeaderText("Voulez-vous envoyez ce mail?");
            alert.setContentText("Cette action est requise.");

            // Show the confirmation dialog and wait for the user's response
            Optional<ButtonType> resultt = alert.showAndWait();

            // Send the email

            if (resultt.get() == ButtonType.OK) {
                System.out.println("En cours d'envoie...");
                Transport.send(msg);
                System.out.println("Envoyé avec succès !");

            } else {
                // Close the dialog and do nothing
                alert.close();
                System.out.println("Echec d'envoie!");
            }


            // Print a message to the console to indicate that the email was sent successfully





        } catch (AddressException e) {
            // Create an alert to notify the user that there was an error with the email address
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }

}
