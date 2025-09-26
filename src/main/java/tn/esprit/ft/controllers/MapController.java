package tn.esprit.ft.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import tn.esprit.ft.controllers.AjouterBorne;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private WebView webView;

    public void showWindow() {
        System.out.println("OPENING MAPS");

        System.out.println("TEST SHOW WINDOW");

    }
    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    String latitude;
    String longitude;
    private void closeWindow() {
        // Get the Stage and close it
        Stage stage = (Stage) webView.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void confirm() throws IOException {
        System.out.println("confirming");

        WebEngine webEngine = webView.getEngine();

        String script = "document.getElementById('latitude').innerText + ',' + document.getElementById('longitude').innerText";

        String latLong = (String) webEngine.executeScript(script);

        if (latLong != null && !latLong.isEmpty()) {
            String[] parts = latLong.split(",");
            if (parts.length == 2) {
                String latitude = parts[0].trim();
                String longitude = parts[1].trim();
                AjouterBorne ajouterBorne=new AjouterBorne();
                ajouterBorne.savecoords(latitude,longitude);
                ModifierBorne modifierBorne=new ModifierBorne();
                modifierBorne.savecoords(latitude,longitude);
                // Now you can navigate back to commande_client.fxml
                Stage currentStage = (Stage) webView.getScene().getWindow();
                currentStage.close();                System.out.println("Latitude: " + latitude);
                System.out.println("Longitude: " + longitude);
            } else {
                System.err.println("Error parsing latitude and longitude");
            }
        } else {
            System.err.println("Latitude and longitude not found in HTML");
        }
    }


    public void setMapLocation(double latitude, double longitude) {
        String script = "initialize(" + latitude + ", " + longitude + ");";
        WebEngine webEngine = webView.getEngine();
        webEngine.executeScript(script);
    }
    public static float latitude_static;
    public static float longitude_static;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/googleMaps.html").toExternalForm());

    }
}
