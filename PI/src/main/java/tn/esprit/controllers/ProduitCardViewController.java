package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import tn.esprit.models.produit;
import tn.esprit.services.serviceProduit;

import java.sql.SQLException;
import java.util.List;

public class ProduitCardViewController {
    @FXML
    private Text idProduit;

    @FXML
    private Button btnlikestle;

    @FXML
    private Text nomProduit;

    @FXML
    private Text price;

    @FXML
    private Text quantite;
    private produit produit;
    private ProduitPageController controller;

    int idp;
    int statuslike;
    public void remplirCard(produit p, ProduitPageController c){
        idp = p.getId();
        statuslike = p.getLike(); // Update statuslike with the value from the produit object


        idProduit.setText("#"+p.getId());
        nomProduit.setText(p.getNom());
        quantite.setText(String.valueOf(p.getQt()));
        price.setText(String.valueOf(p.getPrix()));
        this.controller = c;
        this.produit = p;
    }
    @FXML
    void deleteBtnClicked(MouseEvent event) {
        serviceProduit serviceP = new serviceProduit();
        try {
            serviceP.supprimer(produit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            List<produit> produitList = serviceP.afficher();
            controller.setListProduit(produitList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void editBtnClicked(MouseEvent event) {
        controller.setEditPage(produit);
    }

    @FXML
    public void likedislike(ActionEvent event) {

        serviceProduit sp = new serviceProduit();

        if (statuslike == 0) {
            btnlikestle.setStyle("-fx-background-color: grey;");
            btnlikestle.setTextFill(Color.BLACK);
            btnlikestle.setText("Like");

            sp.updateFavourite(idp, 1);

        } else {
            btnlikestle.setStyle("-fx-background-color: red;");
            btnlikestle.setTextFill(Color.WHITE);
            btnlikestle.setText("dislike");

            sp.updateFavourite(idp, 0);

        }

    }
}
