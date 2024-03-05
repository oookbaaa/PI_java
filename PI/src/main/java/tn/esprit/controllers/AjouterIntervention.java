package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Bornes;
import tn.esprit.models.Interventions;
import tn.esprit.models.User;
import tn.esprit.services.ServiceBorne;
import tn.esprit.services.ServiceIntervention;
import tn.esprit.utils.SessionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class AjouterIntervention {


    @FXML
    private ComboBox<Bornes> bornes;

    @FXML
    private TextField type;

    @FXML
    private ComboBox<String> emp;

    @FXML
    private DatePicker date;
    private ServiceBorne serviceBorne = new ServiceBorne();

    User loggedInUser = SessionManager.getSession(SessionManager.getLastSessionId());

    @FXML
    public void initialize() throws SQLException {
        List<Bornes> borneList = serviceBorne.afficher();
        for (Bornes borne : borneList) {
            bornes.getItems().add(borne);
        }
        String str = "FOREIGN KEY NEEDED HERE /// TO DO ";

        emp.getItems().add(str);
    }

    public void ajouterIntervention() throws SQLException, IOException {
        Bornes selectedBorne = bornes.getValue();
        String interventionType = type.getText();
        String selectedEmployee = emp.getValue();
        LocalDate interventionDate = date.getValue();
        LocalDate today = LocalDate.now();

        // Check if interventionDate is in the past
        if (interventionDate.isBefore(today)) {
            showAlert("Date dans le passé");
            return;
        }
        // Check if any field is empty
        if (selectedBorne == null || interventionType.isEmpty() || interventionDate == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }
        if (imageFile == null) {
            showAlert("Veuillez ajouter une photo");
            return;
        }

        // Convert the image file to byte[]
        byte[] imageData = convertImageToByteArray(imageFile);
        int id = loggedInUser.getId();
        // Create a new Intervention object with the provided data
        Interventions newIntervention = new Interventions(selectedBorne.getId(), id, interventionType, Date.valueOf(interventionDate.toString()), imageData);

        // Call the InterventionService to add the new Intervention
        try {
            ServiceIntervention i = new ServiceIntervention();
            i.ajouter(newIntervention);
            showAlertAndClose("Intervention ajoutée avec succès.");

        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout de l'intervention : " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void ImportButton(ActionEvent actionEvent) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Set file extension filter to only allow image files
        imageFile = fileChooser.showOpenDialog(new Stage());

        // Show open file dialog
        if (imageFile != null) {
            // Set the image in the ImageView
            Image image = new Image(imageFile.toURI().toString());
            ImageView.setImage(image);
        } else {
            System.out.println("Please select a valid image file.");
        }
    }

    private void showAlertAndClose(String message) {
        showAlert(message);

        // Get the Stage from any node in the scene (in this case, ImageView)
        Stage stage = (Stage) ImageView.getScene().getWindow();
        stage.close();
    }

    private Blob convertImageToBlob(File file) throws IOException, SQLException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return new javax.sql.rowset.serial.SerialBlob(inputStream.readAllBytes());
        }
    }

    private byte[] convertImageToByteArray(File file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        }
    }

    private File imageFile;
    @FXML
    private javafx.scene.image.ImageView ImageView;
}
