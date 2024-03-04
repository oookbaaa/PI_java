package tn.esprit.ft.controllers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Media;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.ArrayUtils;
import tn.esprit.ft.services.ServiceBorne;
import tn.esprit.ft.services.ServiceIntervention;

import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.time.ZoneId;

import tn.esprit.ft.models.Bornes;
import tn.esprit.ft.models.Interventions;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import tn.esprit.ft.models.Interventions;
import tn.esprit.ft.services.ServiceIntervention;

import  javafx.scene.image.ImageView;
import  javafx.scene.image.Image;
import tn.esprit.ft.test.HelloApplication;

import java.awt.*;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import com.itextpdf.awt.geom.Point;
public class AfficherInterventionREMAKE {

    private List<Interventions> interventions;  ;

    private String filePath;
    @FXML
    private TextField id_b;
    @FXML
    private DatePicker datemodifier;
    @FXML
    private TextField idmupdate;
    @FXML
    private TextField internpath;
    @FXML
    private ImageView imageTF;
    @FXML
    private URL location;
    @FXML
    private TextField modifiertype;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField typemodifier;




    @FXML
    private void initialize() {
        try {
            ServiceIntervention serviceIntervention = new ServiceIntervention();
            List<Interventions> interventionList = serviceIntervention.getInterventions();

            int row = 0;

            Label typeHeader = new Label("Type");
            Label dateHeader = new Label("Date");
            Label imageHeader = new Label("Image");
            Label actionsHeader = new Label("Actions");

            typeHeader.setAlignment(Pos.CENTER);
            dateHeader.setAlignment(Pos.CENTER);
            imageHeader.setAlignment(Pos.CENTER);
            actionsHeader.setAlignment(Pos.CENTER);

            gridPane.add(typeHeader, 0, 0);
            gridPane.add(dateHeader, 1, 0);
            gridPane.add(imageHeader, 2, 0);
            gridPane.add(actionsHeader, 3, 0);

            row++;

            for (Interventions intervention : interventionList) {
                Label typeLabel = new Label(intervention.getType());
                Label dateLabel = new Label(intervention.getDate().toString());

                ImageView imageView = new ImageView();
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                if (intervention.getImagePath() != null && intervention.getImagePath().length > 0) {
                    byte[] imageData = intervention.getImagePath();

                    ByteArrayInputStream bis = new ByteArrayInputStream(imageData);

                    // Create the Image from the InputStream
                    Image image = new Image(bis);
                    imageView.setImage(image);
                }

               // Button supprimerButton = new Button("Supprimer");
             //   Button modifierButton = new Button("Modifier");

                // Set event handlers for the buttons
                /*supprimerButton.setOnAction(event -> {
                    handleDeleteButton(intervention);
                    refreshGrid(); // Call refreshGrid after deletion

                });*/

                /*modifierButton.setOnAction(event -> {
                    try {
                        handleUpdateButton(intervention);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
*/
                // Align labels to center
                typeLabel.setAlignment(Pos.CENTER);
                dateLabel.setAlignment(Pos.CENTER);

                // Align ImageView and buttons to center
  //              supprimerButton.setAlignment(Pos.BASELINE_LEFT);
    //            modifierButton.setAlignment(Pos.CENTER_LEFT);
                ImageView modifyIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/modifyicon.png")));
                ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/deleteicon.png")));
                deleteIcon.setOnMouseClicked(event -> {
                    handleDeleteButton(intervention);
                    refreshGrid(); // Call refreshGrid after deletion
                });
                modifyIcon.setFitWidth(20);
                modifyIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
                deleteIcon.setFitHeight(20);
                modifyIcon.setOnMouseClicked(event -> {
                    try {
                        handleUpdateButton(intervention);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                // Add the labels, image, and buttons to the gridPane at specific columns and row
                gridPane.add(typeLabel, 0, row);
                gridPane.add(dateLabel, 1, row);
                gridPane.add(imageView, 2, row);
                gridPane.add(modifyIcon, 3, row);
                gridPane.add(deleteIcon, 4, row);

                row++; // Move to the next row for the next Intervention
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        gridPane.getColumnConstraints().forEach(column -> {
            column.setHalignment(HPos.CENTER);
        });
        gridPane.setHgap(20);
    }

    private void handleUpdateButton(Interventions intervention) throws SQLException, IOException {
        ServiceBorne sb=new ServiceBorne();
     ModifierIntervention.borne_static=sb.findById(intervention.getId_borne());
     ModifierIntervention.date_static= intervention.getDate().toString();
     ModifierIntervention.filepath_static=intervention.getImagePath();
     ModifierIntervention.type_static=intervention.getType();
     ModifierIntervention.id=intervention.getId_int();
        Stage primaryStage = new Stage();

        // Charger le fichier FXML de la vue DisplayEvent
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/ModifierIntervention.fxml"));
        Parent root = fxmlLoader.load();

        // Créer une scène avec le contenu chargé depuis le fichier FXML
        Scene scene = new Scene(root);

        // Définir le titre de la fenêtre principale
        primaryStage.setTitle("HELLO");

        // Définir la scène sur le stage
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.showAndWait(); // Use showAndWait() to wait for the window to close
        refreshGrid();
     /*   ServiceIntervention es = new ServiceIntervention();
        int id = intervention.getId_int();
        int id_bo = intervention.getId_borne();
        String pathimage = intervention.getImagePath();
        Image image = new Image("file:" + pathimage);
        imageTF.setImage(image);
        String type =  intervention.getType();
        Date date = intervention.getDate();

        id_b.setText(String.valueOf(id_bo));
        modifiertype.setText(type);
        idmupdate.setText(String.valueOf(id));;
        internpath.setText(pathimage);
*/
    }
    @FXML
    void choix(ActionEvent event) {
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");

        // Set file extension filter to only allow image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file is selected and it's an image
        if (selectedFile != null && isImageFile(selectedFile)) {
            // Store the file path
            filePath = selectedFile.getAbsolutePath();
            System.out.println("File path stored: " + filePath);
            internpath.setText(filePath);

            // Set the image in the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imageTF.setImage(image);
        } else {
            System.out.println("Please select a valid image file.");
        }
    }

    private boolean isImageFile(File file) {
        try {
            Image image = new Image(file.toURI().toString());
            return !image.isError();
        } catch (Exception e) {
            return false;
        }
    }

    private void handleDeleteButton(Interventions intervention) {
        // Afficher une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation de la suppression");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

        // Obtenir la réponse de l'utilisateur
        Optional<ButtonType> result = alert.showAndWait();

        // Si l'utilisateur clique sur le bouton OK, supprimer l'événement
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ServiceIntervention si = new ServiceIntervention();
            try {
                si.supprimer(intervention.getId_int());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            refreshGrid();
        }
    }
    private void refreshGrid() {
        gridPane.getChildren().clear();
        initialize();
    }
    @FXML

    void confirmer(ActionEvent event) throws SQLException {
        int id_int = Integer.parseInt(idmupdate.getText());
        String type = modifiertype.getText();
        int id_emp = Integer.parseInt(id_b.getText());
        LocalDate date = datemodifier.getValue();

        // Check if a file path is selected
        if (filePath == null || filePath.isEmpty()) {
            showAlert("Veuillez sélectionner une image.");
            return;
        }

        // Convert the file path String to a byte[] array
        byte[] imagePathBytes = filePath.getBytes();

        Interventions ev = new Interventions(id_int, id_emp, type, Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()), imagePathBytes);
        ev.setType(type);
        ev.setId_emp(id_emp);
        ev.setId_int(id_int);
        ev.setDate(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        ev.setImagePath(imagePathBytes);

        ServiceIntervention si = new ServiceIntervention();
        si.modifier(ev);
        refreshGrid();
    }

    private void showAlert(String s) {
        System.out.println("CHECK UR CODE");
    }


    public void newinv(ActionEvent actionEvent) throws IOException {
        System.out.println("test");
        Stage primaryStage = new Stage();

        // Charger le fichier FXML de la vue DisplayEvent
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/AjouterIntervention.fxml"));
        Parent root = fxmlLoader.load();

        // Créer une scène avec le contenu chargé depuis le fichier FXML
        Scene scene = new Scene(root);

        // Définir le titre de la fenêtre principale
        primaryStage.setTitle("interventions");

        // Définir la scène sur le stage
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.showAndWait();
        refreshGrid();
    }

    public void goToServices(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void postFacebook(javafx.scene.input.MouseEvent mouseEvent) throws SQLException {
        ServiceIntervention i=new ServiceIntervention();
        Interventions borne=i.getLastInsertedIntervention();
        String appId = "1814989225580439";
        String appSecret = "3f407f71087c954e68105cc41d9b395e";
        String accessTokenString = "EAAMAF3v2JTEBOzEJlQVoCiBlQDjiO92YxO0ZCCGnM2YsiQdICeMomi250q3ZBFP4s3uy48XsXRvscv0MXnW4ApVXO65gOzxUtenLwZAZBd61m5rSe4UeZBlSueU1IuXQLHyLT8K5qhWePszL6F5XYaWNBFdNQScwY8FDxbjP3mbuJbQKoNIXJcot4FWf8kTKla0iwYJ9kuP4YHKOyQ2sxvsKQDdHSfmUZCJFYZAbNe5mr9d";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        String msg = "Intervention le plus récent"
                + "\n-Type: "
                + borne.getType()
                + "\n-Date: "
                + borne.getDate() ;

        try {
            facebook.postStatusMessage(msg);
            System.out.println("Post shared successfully.");
        } catch (FacebookException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void pdf(javafx.scene.input.MouseEvent event) throws SQLException {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des produits
            ServiceIntervention interventionService = new ServiceIntervention();
            List<Interventions> interventionsList = interventionService.afficher();

            try {
                // Créer le document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Créer une instance de l'image
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(System.getProperty("user.dir") + "/src/logo.png");

                // Positionner l'image en haut à gauche
                image.setAbsolutePosition(5, document.getPageSize().getHeight() - 120);

                // Modifier la taille de l'image
                image.scaleAbsolute(150, 150);

                // Ajouter l'image au document
                document.add(image);

                // Créer une police personnalisée pour la date
                com.itextpdf.text.Font fontDate = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18, com.itextpdf.text.Font.BOLD);
                BaseColor color = new BaseColor(50, 187, 111); // Rouge: 50, Vert: 187, Bleu: 111
                fontDate.setColor(color); // Définir la couleur de la police

                // Créer un paragraphe avec le lieu
                Paragraph tunis = new Paragraph("Tunis", fontDate);
                tunis.setIndentationLeft(455); // Définir la position horizontale
                tunis.setSpacingBefore(-30); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(tunis);

                // Obtenir la date d'aujourd'hui
                LocalDate today = LocalDate.now();

                // Créer un paragraphe avec la date
                Paragraph date = new Paragraph(today.toString(), fontDate);

                date.setIndentationLeft(437); // Définir la position horizontale
                date.setSpacingBefore(1); // Définir la position verticale
                // Ajouter le paragraphe au document
                document.add(date);

                // Créer une police personnalisée
                com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 32, com.itextpdf.text.Font.BOLD);
                BaseColor titleColor = new BaseColor(67, 136, 43); //
                font.setColor(titleColor);

                // Ajouter le contenu au document
                Paragraph title = new Paragraph("Liste des interventions", font);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingBefore(50); // Ajouter une marge avant le titre pour l'éloigner de l'image
                title.setSpacingAfter(20);
                document.add(title);

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.setSpacingBefore(30f);
                table.setSpacingAfter(30f);

                // Ajouter les en-têtes de colonnes
                com.itextpdf.text.Font hrFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16, com.itextpdf.text.Font.BOLD);
                BaseColor hrColor = new BaseColor(50, 89, 74); //
                hrFont.setColor(hrColor);

                PdfPCell cell1 = new PdfPCell(new Paragraph("ID", hrFont));
                BaseColor bgColor = new BaseColor(222, 254, 230);
                cell1.setBackgroundColor(bgColor);
                cell1.setBorderColor(titleColor);
                cell1.setPaddingTop(20);
                cell1.setPaddingBottom(20);
                cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell2 = new PdfPCell(new Paragraph("Type", hrFont));
                cell2.setBackgroundColor(bgColor);
                cell2.setBorderColor(titleColor);
                cell2.setPaddingTop(20);
                cell2.setPaddingBottom(20);
                cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell3 = new PdfPCell(new Paragraph("ID Borne", hrFont));
                cell3.setBackgroundColor(bgColor);
                cell3.setBorderColor(titleColor);
                cell3.setPaddingTop(20);
                cell3.setPaddingBottom(20);
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell cell4 = new PdfPCell(new Paragraph("Date", hrFont));
                cell4.setBackgroundColor(bgColor);
                cell4.setBorderColor(titleColor);
                cell4.setPaddingTop(20);
                cell4.setPaddingBottom(20);
                cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell1);
                table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                com.itextpdf.text.Font hdFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
                BaseColor hdColor = new BaseColor(50, 89, 74); //
                hrFont.setColor(hdColor);
                System.out.println(interventionsList);
                // Ajouter les données des produits
                for (Interventions intervention : interventionsList) {
                    PdfPCell cellR1 = new PdfPCell(new Paragraph(String.valueOf(intervention.getId_int()), hdFont));
                    cellR1.setBorderColor(titleColor);
                    cellR1.setPaddingTop(10);
                    cellR1.setPaddingBottom(10);
                    cellR1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR1);

                    PdfPCell cellR2 = new PdfPCell(new Paragraph(intervention.getType(), hdFont));
                    cellR2.setBorderColor(titleColor);
                    cellR2.setPaddingTop(10);
                    cellR2.setPaddingBottom(10);
                    cellR2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR2);

                    PdfPCell cellR3 = new PdfPCell(new Paragraph(String.valueOf(intervention.getId_borne()), hdFont));
                    cellR3.setBorderColor(titleColor);
                    cellR3.setPaddingTop(10);
                    cellR3.setPaddingBottom(10);
                    cellR3.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR3);


                    PdfPCell cellR5 = new PdfPCell(
                            new Paragraph(String.valueOf(intervention.getDate()), hdFont));
                    cellR5.setBorderColor(titleColor);
                    cellR5.setPaddingTop(10);
                    cellR5.setPaddingBottom(10);
                    cellR5.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cellR5);
                }
                table.setSpacingBefore(20);
                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void goToServices(ActionEvent event) {
        System.out.println("test");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startchercher(ActionEvent event) {
        System.out.println("Working");
    }

    public void stats(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/statsinterventions.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage stage = new Stage();
        stage.setTitle("Statistics"); // Set the title of the new stage

        // Set the scene to the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Show the new stage
        stage.show();

    }
}
