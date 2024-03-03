package tn.esprit.ft.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

import java.io.*;
import java.sql.Blob;
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

public class ModifierBorne {

    @FXML
    private TextField adresseTF;

    @FXML
    private AnchorPane anchorpaneajout;


    @FXML
    private TextField descriptionTF;

    @FXML
    private ComboBox<String> etaTF;
    @FXML
    private ComboBox<String> emplacement;
    private ServiceBorne serviceBorne = new ServiceBorne();
    private  byte[] filePath;
    @FXML
    private ImageView ImageView;
    private Image img;

    public static String adresseTF_static;
    public static String descriptionTF_static;
    public static String etaTF_static;
    public static String emplacement_static;
    public static byte[] filePath_static;
    public static float lang_static;
    public static float lati_static;
    public static int id;
    static float latitude;
    static float longitude;
    @FXML
    void initialize() throws FileNotFoundException {
        emplacement.getItems().addAll("Entreprise", "co-propriété", "collectivité", "propriété privée");
        etaTF.getItems().addAll("En attente", "En cours", "Termine");
        adresseTF.setText(adresseTF_static);
        descriptionTF.setText(descriptionTF_static);
        etaTF.setValue(etaTF_static);
        emplacement.setValue(emplacement_static);
        filePath=filePath_static;
        ImageView.setImage(new Image(new ByteArrayInputStream(filePath_static)));


    }
    private File selectedImageFile;

    public void ImportButton(ActionEvent actionEvent)
    {
         FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        selectedImageFile = fileChooser.showOpenDialog(stage);
        if (selectedImageFile != null) {
            try {
                Image image = new Image(new FileInputStream(selectedImageFile));
                ImageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return image.isError() ? false : true;
        } catch (Exception e) {
            return false;
        }
    }
    private byte[] loadImage(File file) throws FileNotFoundException {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] imageData = new byte[(int) file.length()];
            fis.read(imageData);
            return imageData;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("Error loading image file");
        }
    }

    @FXML
    void AjouterBorne(ActionEvent event) {

            if (selectedImageFile == null && filePath_static==null) {
                showAlert("erreur", "Selectionner une image stp.");
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

                // Vérifier si l'adresse existe déjà
                /*if (adresseExisteDeja(adresse)) {
                    showAlert("Erreur", "Cette adresse existe déjà.");
                    return;
                }*/
                if (selectedImageFile==null)
                    filePath=filePath_static;
                else
                    filePath=loadImage(selectedImageFile);
                // Si toutes les validations sont réussies, poursuivre avec l'ajout de la borne
                Bornes bornes = new Bornes(id,descriptionTF.getText(), etaTF.getValue(), adresseTF.getText(), emplacement.getValue(), filePath,latitude,longitude);
                serviceBorne.modifier(bornes);

                showAlertAndClose("Borne modifiée avec succées");

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
    public void newinv(ActionEvent event)
    {

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
    private void showSuccess (String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void savecoords(String latitude, String longitude) {

        ModifierBorne.latitude =Float.parseFloat(latitude);
        ModifierBorne.longitude =Float.parseFloat(longitude);
        System.out.println("LANG LAT FROM CONTROLLER BORNE"+latitude+"  "+latitude);
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
    private void showAlertAndClose(String message) {
        showSuccess("success",message);

        // Get the Stage from any node in the scene (in this case, ImageView)
        Stage stage = (Stage) ImageView.getScene().getWindow();
        stage.close();
    }
}





