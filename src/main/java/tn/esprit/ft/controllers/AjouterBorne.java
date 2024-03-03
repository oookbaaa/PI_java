package tn.esprit.ft.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.ft.models.Bornes;
import tn.esprit.ft.services.ServiceBorne;
import javafx.event.ActionEvent;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.image.ImageView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.ft.test.HelloApplication;

public class AjouterBorne {

    @FXML
    private TextField adresseTF;

    @FXML
    private AnchorPane anchorpaneajout;


    @FXML
    private TextField descriptionTF;
@FXML
private static Button btnajt;
    @FXML
    private ComboBox<String> etaTF;
    @FXML
    private ComboBox<String> emplacement;
    private ServiceBorne serviceBorne = new ServiceBorne();
    private  String filePath;
    @FXML
    private ImageView ImageView;
    private Image img;
    static float latitude;
    static float longitude;

    @FXML
    void initialize() {
        emplacement.getItems().addAll("Entreprise", "co-propriété", "collectivité", "propriété privée");
        etaTF.getItems().addAll("En attente", "En cours", "Termine");


    }
    //import image

    public void ImportButton(ActionEvent actionEvent)
     {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Set file extension filter to only allow image files


        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file is selected and it's an image
         if (selectedFile != null && isImageFile(selectedFile)) {
             try {
                 byte[] imageData = Files.readAllBytes(selectedFile.toPath());

                 filePath = selectedFile.getAbsolutePath();
                 System.out.println("File path stored: " + filePath);

                 Image image = new Image(new ByteArrayInputStream(imageData));
                 ImageView.setImage(image);

                 filePathBytes  = imageData;
             } catch (IOException e) {
                 e.printStackTrace();
             }
         } else {
             System.out.println("Please select a valid image file.");
         }

    }
    byte[] filePathBytes;
    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return image.isError() ? false : true;
        } catch (Exception e) {
            return false;
        }
    }


    @FXML
    void AjouterBorne(ActionEvent event) {

        if(latitude==0||longitude==0)
        {
            showAlert("Erreur","Tu dois selectionner la localisation avec le carte.");
            return;
        }
        if (descriptionTF.getText().isEmpty() || etaTF.getValue().isEmpty() || adresseTF.getText().isEmpty() || emplacement.getValue().isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        } else {
            try {
                String adresse = adresseTF.getText().trim();
                if (adresse.isEmpty()) {
                    showAlert("Erreur", "Veuillez saisir une adresse.");
                    return;
                }

                // Vérifier si l'adresse respecte un format spécifique
                if (!adresse.matches("^\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)$")) {
                    showAlert("Erreur", "Le format de l'adresse est incorrect.");
                    return;
                }
                if(filePath==null)
                {
                    showAlert("Erreur","Veuillez selectionnez une photo stp");
                    return;
                }
                // Vérifier si l'adresse existe déjà
                /*if (adresseExisteDeja(adresse)) {
                    showAlert("Erreur", "Cette adresse existe déjà.");
                    return;
                }*/

                // Si toutes les validations sont réussies, poursuivre avec l'ajout de la borne
                Bornes bornes = new Bornes(descriptionTF.getText(), etaTF.getValue(), adresseTF.getText(), emplacement.getValue(), filePathBytes,latitude,longitude);
                serviceBorne.ajouter(bornes);

                showAlertAndClose("Nouvelle borne ajouté avec succées");


            } catch (Exception e) {
                e.printStackTrace();
                // Handle the exception appropriately, for example, by showing an alert
                showAlert("Erreur", "Erreur lors de l'ajout de la borne : " + e.getMessage());
            }
        }

// Méthode pour vérifier si l'adresse existe déjà
       /* private boolean adresseExisteDeja(String adresse) {
            // Ici vous pouvez implémenter la logique pour vérifier si l'adresse existe déjà
            // Par exemple, vérifier dans une base de données si cette adresse est déjà enregistrée
            return false; // Pour l'exemple, toujours retourner false
        }*/

    }
        @FXML
        void AfficherBorne (ActionEvent event)
        {
            try {
                Parent fxml = FXMLLoader.load(getClass().getResource("/AfficherBorne.fxml"));
                anchorpaneajout.getChildren().removeAll();
                anchorpaneajout.getChildren().setAll(fxml);


            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately, for example, by showing an alert
                showAlert("Erreur", "Erreur lors du chargement de l'interface  : " + e.getMessage());
            }

        }
        private void showAlert (String title, String content){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }


    public void openmap(ActionEvent actionEvent) throws IOException {
        tn.esprit.ft.controllers.MapController m=new MapController();
        m.showWindow();
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader((HelloApplication.class.getResource("/webview.fxml")));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("localiser le borne");
        stage.setScene(scene);
        stage.show();
    }

    public void savecoords(String latitude, String longitude) {

        AjouterBorne.latitude =Float.parseFloat(latitude);
        AjouterBorne.longitude =Float.parseFloat(longitude);
        System.out.println("LANG LAT FROM CONTROLLER BORNE"+latitude+"  "+latitude);
    }
    private void showAlertAndClose(String message) {
        showSucessAlert("success",message);

        // Get the Stage from any node in the scene (in this case, ImageView)
        Stage stage = (Stage) ImageView.getScene().getWindow();
        stage.close();
    }

    private void showSucessAlert(String success, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(success);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





