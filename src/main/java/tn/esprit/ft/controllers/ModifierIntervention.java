package tn.esprit.ft.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.ft.models.Bornes;
import tn.esprit.ft.models.Interventions;
import tn.esprit.ft.services.ServiceBorne;
import tn.esprit.ft.services.ServiceIntervention;

import java.io.*;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ModifierIntervention {
    @FXML
    private ComboBox<Bornes> bornes;
    public static int id;
    @FXML
    private TextField type;

    @FXML
    private ComboBox<String> emp;

    @FXML
    private DatePicker date;
    private ServiceBorne serviceBorne = new ServiceBorne();
    @FXML
    public static Bornes borne_static;
    public static String type_static;
    public static String date_static;
    public static byte[] filepath_static;
    public void initialize() throws SQLException, FileNotFoundException {

        List<Bornes> borneList = serviceBorne.afficher();
        for (Bornes borne : borneList) {
            bornes.getItems().add(borne);
        }
        String str="FOREIGN KEY NEEDED HERE /// TO DO ";
        emp.getItems().add(str);
        bornes.setValue(borne_static);
        type.setText(type_static);
        // Create a DateTimeFormatter with the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println(date_static);
        // Parse the string to LocalDate
        LocalDate localDate = LocalDate.parse(date_static, formatter);

        date.setValue(localDate);
        filePath=filepath_static;byte[] byteArray = new byte[filepath_static.length];
        for (int i = 0; i < filepath_static.length; i++) {
            byteArray[i] = filepath_static[i];
        }

// Create an InputStream from the byte[]
        InputStream inputStream = new ByteArrayInputStream(byteArray);

// Create the Image from the InputStream
        Image image = new Image(inputStream);

// Set the image to the ImageView
        ImageView.setImage(image);

    }

    public void ajouterIntervention() {
        Bornes selectedBorne = bornes.getValue();
        String interventionType = type.getText();
        String selectedEmployee = emp.getValue();
        LocalDate interventionDate = date.getValue();
        if (interventionDate != null && interventionDate.isBefore(LocalDate.now())) {
            showAlert("date ne peut pas étre dans le passé: "+ interventionDate);
            return;
        }
        // Check if any field is empty
        if (selectedBorne == null || interventionType.isEmpty() || interventionDate == null) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }
        if (filePath == null)
        {
            showAlert("Veuillez ajouter une photo");
            return;
        }

        // Create a new Intervention object with the provided data
        Interventions newIntervention = new Interventions(id,selectedBorne.getId(),1, interventionType, Date.valueOf(interventionDate.toString()),filePath);

        // Call the InterventionService to add the new Intervention
        try {
            ServiceIntervention i=new ServiceIntervention();
            i.modifier(newIntervention);
            showAlertAndClose("Intervention modifiée avec succées");

        } catch (Exception e) {
            showAlert("Erreur lors de l'ajout de l'intervention : " + e.getMessage());
        }
    }
    private Byte[] convertByteArrayToWrapperArray(byte[] input) {
        if (input == null) {
            return null;
        }
        Byte[] result = new Byte[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i];
        }
        return result;
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
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file is selected and it's an image
        if (selectedFile != null) {
            try {
                // Read the image file as bytes
                byte[] fileBytes = readBytesFromFile(selectedFile);

                // Store the byte array
                filePath = fileBytes;
                System.out.println("File path stored: " + selectedFile.getAbsolutePath());

                // Set the image in the ImageView
                Image image = new Image(new ByteArrayInputStream(fileBytes));
                ImageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please select a valid image file.");
        }
    }

    private byte[] readBytesFromFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();
        return bytes;
    }

    private byte[] filePath;

    @FXML
    private javafx.scene.image.ImageView ImageView;
    private void showAlertAndClose(String message) {
        showAlert(message);

        // Get the Stage from any node in the scene (in this case, ImageView)
        Stage stage = (Stage) ImageView.getScene().getWindow();
        stage.close();
    }
}
