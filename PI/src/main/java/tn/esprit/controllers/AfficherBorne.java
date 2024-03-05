package tn.esprit.controllers;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import tn.esprit.models.Bornes;
import tn.esprit.services.ServiceBorne;
import tn.esprit.test.MainUI;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;



public class AfficherBorne {
    @FXML
    private AnchorPane anchorpaneajoutinter;
    public javafx.scene.control.TextField type_interventions;
    @FXML
    private ResourceBundle resources;

    private String filePath;
    @FXML
    private URL location;
    @FXML
    private ListView<String> listviewborne;
    @FXML
    private javafx.scene.control.TextField id_employee;

    @FXML
    private javafx.scene.control.TextField identity_borne;

    @FXML
    private DatePicker date_intervention;
    @FXML
    private ImageView imagefifi;

    private ServiceBorne serviceBorne = new ServiceBorne();
    private Map<String, String> filePaths = new HashMap<>();
    Map<String, Integer> idMap = new HashMap<>();

    @FXML
    private GridPane gridPaneBorne;

    @FXML
    private void initialize() {
        try {
            List<Bornes> borneList = serviceBorne.afficher();

            int row = 0;
            Label typeHeader = new Label("Description");
            Label dateHeader = new Label("Etat");
            Label imageHeader = new Label("Adresse");
            Label PlacementHeader = new Label("Emplacement");
            Label ImageHeader = new Label("Image");
            Label actionsHeader = new Label("Actions");

            typeHeader.setAlignment(Pos.CENTER);
            dateHeader.setAlignment(Pos.CENTER);
            imageHeader.setAlignment(Pos.CENTER);
            actionsHeader.setAlignment(Pos.CENTER);
            PlacementHeader.setAlignment(Pos.CENTER);
            ImageHeader.setAlignment(Pos.CENTER);

            gridPaneBorne.add(typeHeader, 0, 0);
            gridPaneBorne.add(dateHeader, 1, 0);
            gridPaneBorne.add(imageHeader, 2, 0);
            gridPaneBorne.add(PlacementHeader, 3, 0);
            gridPaneBorne.add(ImageHeader,4,0);
            gridPaneBorne.add(actionsHeader,5,0);
            row++;
            for (Bornes borne : borneList) {
                Label descriptionLabel = new Label(borne.getDescription());
                Label etatLabel = new Label(borne.getEtat());
                Label adresseLabel = new Label(borne.getAdresse());
                Label emplacementLabel = new Label(borne.getEmplacement());

                ImageView imageView = new ImageView();
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                byte[] imageData = borne.getFilePath(); // Assuming this returns the byte[] of the image

                try {
                    Image image = createImageFromBytes(imageData);
                    imageView.setImage(image);
                    System.out.println("IMAGE SET");
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Button mapButton = new Button("Localiser");
                ImageView modifyIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/modifyicon.png")));
                ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/deleteicon.png")));
                deleteIcon.setOnMouseClicked(event -> supprimer(borne));
                modifyIcon.setOnMouseClicked(event -> {
                    try {
                        modifier(borne);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                ImageView mapIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/mapsicon.png")));
                mapButton.setGraphic(mapIcon);
                modifyIcon.setFitWidth(20);
                modifyIcon.setFitHeight(20);
                deleteIcon.setFitWidth(20);
                deleteIcon.setFitHeight(20);
                mapIcon.setFitWidth(20);
                mapIcon.setFitHeight(20);
                mapIcon.setOnMouseClicked(event -> {
                    try {
                        openMap(borne.getLatitude(), borne.getLongitude());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                gridPaneBorne.add(descriptionLabel, 0, row);
                gridPaneBorne.add(etatLabel, 1, row);
                gridPaneBorne.add(adresseLabel, 2, row);
                gridPaneBorne.add(emplacementLabel, 3, row);
                gridPaneBorne.add(imageView, 4, row);
                gridPaneBorne.add(modifyIcon, 5, row);
                gridPaneBorne.add(deleteIcon, 6, row);
                gridPaneBorne.add(mapIcon, 7, row);

                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Image createImageFromBytes(byte[] imageData) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
        return new Image(bis);
    }

    private void openMap(float latitude, float longitude) throws IOException {
        LocationView.lat=latitude;
        LocationView.lon=longitude;
        Stage stage=new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader((MainUI.class.getResource("/LocationView.fxml")));
        System.out.println(LocationView.lat);
        System.out.println(LocationView.lon);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("localiser la borne");
        stage.setScene(scene);
        stage.show();
    }

    private void modifier(Bornes borne) throws IOException {
        ModifierBorne.adresseTF_static=borne.getAdresse();
        ModifierBorne.descriptionTF_static=borne.getDescription();
        ModifierBorne.etaTF_static=borne.getEtat();
        ModifierBorne.emplacement_static=borne.getEmplacement();
        ModifierBorne.filePath_static=borne.getFilePath();
        ModifierBorne.latitude= borne.getLatitude();
        ModifierBorne.longitude=borne.getLongitude();
        ModifierBorne.id=borne.getId();

        Stage primaryStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("/ModifierBorne.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("HELLO");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
        gridPaneBorne.getChildren().clear();
        initialize();
    }

    public void ImportBtn(ActionEvent event) {
        // Méthode pour importer une image
    }

    @FXML
    void supprimer(Bornes b) {
        if (b == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune borne sélectionnée");
            alert.setContentText("Veuillez sélectionner une borne à supprimer.");
            alert.showAndWait();
            return;
        }

        int id = b.getId();

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmer la suppression");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette borne ?");
        initialize();

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                ServiceBorne serviceBorne = new ServiceBorne();
                serviceBorne.supprimer(id);
                int rowIndex = -1;
                gridPaneBorne.getChildren().remove(b);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Borne supprimée");
                alert.setContentText("La borne a été supprimée avec succès.");
                alert.showAndWait();
                gridPaneBorne.getChildren().clear();
                initialize();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setContentText("Une erreur s'est produite lors de la suppression de la borne : " + e.getMessage());
                alert.showAndWait();
            }
        }
    }


    private byte[] readImageBytes(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void goToServices(MouseEvent event) {
        System.out.println("test");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goToServices(ActionEvent event) {
        System.out.println("test");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Afficher_intervention(MouseEvent mouseEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("/AfficherIntervention.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("HELLO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null && isImageFile(selectedFile)) {
            filePath = selectedFile.getAbsolutePath();
            System.out.println("File path stored: " + filePath);
            Image image = new Image(selectedFile.toURI().toString());
            imagefifi.setImage(image);
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

    public void supprimer(ActionEvent actionEvent) {
    }

    public void postFacebook(MouseEvent mouseEvent) throws SQLException {
        ServiceBorne b=new ServiceBorne();
        Bornes borne=b.getLastInsertedBorne();
        String appId = "1814989225580439";
        String appSecret = "3f407f71087c954e68105cc41d9b395e";
        String accessTokenString = "EAAZAyuRwP15cBO77UxhugoBFrOLGm5CASFlWO9pckI8ZCo4FzihRAz2jHkYuqxCdvuZBZALNlYSoc9MTgnH6jdZA9aOu50OPWrlWmO5LxviNTGZCXdTYsiMnTZCDnHh7bCeCkRQW38ZCBhAfMI7G14xmMJhZBQZANxCxbNts6mxjdm3UWLZBALAKS8oEoXujPQv3f99IfaUthqMOdDbNuqktQywQwgj1QjkclNl0zd48waZB";

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        String msg = "Recent Borne "
                + "\n*** Emplacement: "
                + borne.getEmplacement()
                + "\n***Adresse: "
                + borne.getAdresse() ;

        try {
            facebook.postStatusMessage(msg);
            System.out.println("Post shared successfully.");
        } catch (FacebookException e) {
            throw new RuntimeException(e);
        }
    }
    public void newinv(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();

        // Charger le fichier FXML de la vue DisplayEvent
        FXMLLoader fxmlLoader = new FXMLLoader(MainUI.class.getResource("/AjouterBorne.fxml"));
        Parent root = fxmlLoader.load();

        // Créer une scène avec le contenu chargé depuis le fichier FXML
        Scene scene = new Scene(root);

        // Définir le titre de la fenêtre principale
        primaryStage.setTitle("interventions");

        // Définir la scène sur le stage
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.showAndWait();
        initialize();
    }

}
