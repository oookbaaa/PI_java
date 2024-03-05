package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.fournisseur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class homePageController implements Initializable {
    @FXML
    private Button fournissuerBtn;

    @FXML
    private AnchorPane mainPageContainer;

    @FXML
    private Button produitBtn;

    @FXML
    void generateExcel(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if(job != null){
            Window primaryStage = null;
            job.showPrintDialog(null);

            Node root=this.mainPageContainer
                    ;
            job.printPage(root);

            job.endJob();



        }

    }

    @FXML
    void likedislike(ActionEvent event) {

    }
    public List<fournisseur> trierEtConvertir(List<fournisseur> livraisons, Comparator<fournisseur> comparator) {
        List<fournisseur> resultatsTries = livraisons.stream()
                .sorted(comparator)
                .collect(Collectors.toList());

        return (List<fournisseur>) resultatsTries.stream()
                .map(fournisseur -> new fournisseur(fournisseur.getPrenom(), fournisseur.getTypeP(),fournisseur.getTelephone(),fournisseur.getNom()))
                .collect(Collectors.toList());
    }


    @Override
    public void initialize(URL url, ResourceBundle rs){

    }
    @FXML
    public void fournisseurBtnClicked(MouseEvent event){
        mainPageContainer.getChildren().clear();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gui/fournisseur/fournisseurPage.fxml"));
            Parent root = fxmlLoader.load();
            mainPageContainer.getChildren().add(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void produitBtnClicked(MouseEvent event){
        mainPageContainer.getChildren().clear();
        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/gui/produit/produitPage.fxml"));
            Parent root = fxmlLoader.load();
            mainPageContainer.getChildren().add(root);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void generateExcel(javafx.event.ActionEvent actionEvent) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            Window primaryStage = null;
            job.showPrintDialog(null);

            Node root = this.mainPageContainer;
            job.printPage(root);

            job.endJob();

        }
    }
}
