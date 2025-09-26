package com.example.benayedjavafx;
import com.example.benayedjavafx.entities.Quiz;
import com.example.benayedjavafx.services.ServiceQuiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditQuizController  {




        @FXML
        private TextField Duree;

        @FXML
        private TextField id_formation;

        @FXML
        private TextField id_quiz;

        @FXML
        private TextField nbquestions;

        @FXML
        private TextField titre;

        @FXML
        void Edit_quiz(ActionEvent event) {
                try {
                        Quiz q = new Quiz(Integer.parseInt(id_quiz.getText()), Integer.parseInt(Duree.getText()), Integer.parseInt(id_formation.getText()), Integer.parseInt(nbquestions.getText()), titre.getText());

                        // Récupérer les détails de la formation à modifier à partir du service
                        ServiceQuiz service = new ServiceQuiz();
                        Quiz updatedQuiz = service.Modifier(q);

                        if (updatedQuiz != null) {
                                System.out.println("Quiz updated successfully: " + updatedQuiz);
                                // Display a success message or take appropriate action
                        } else {
                                System.out.println("Quiz update failed or not found");
                                // Display an error message or take appropriate action
                        }

                        // Charger l'interface FXML d'édition avec les détails de la formation
                        Node source = (Node) event.getSource();
                        Stage currentStage = (Stage) source.getScene().getWindow();

                        // Load the new page
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("afficheQuiz.fxml"));
                        Parent root = loader.load();

                        // Show the new page
                        Stage newStage = new Stage();
                        newStage.setScene(new Scene(root));
                        newStage.show();

                        // Close the current page
                        currentStage.close();
                } catch (IOException | RuntimeException e) {
                        e.printStackTrace();
                        // Handle the exception appropriately
                }

        }



        public boolean isModified() {
        return true ;}
}




