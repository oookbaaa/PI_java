package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import tn.esprit.models.fournisseur;
import tn.esprit.services.ServiceFournisseur;

import java.sql.SQLException;
import java.util.List;

public class FournisseurCardViewController {
    @FXML
    private Text idFournisseur;
    @FXML
    private Text nomPrenom;
    @FXML
    private Text numTel;
    @FXML
    private Text typeProduit;
    private fournisseur fournisseur;
    private FournisseurPageController controller;
    public void remplirCard(fournisseur f,FournisseurPageController c){
        idFournisseur.setText("#"+f.getId());
        nomPrenom.setText(f.getNom()+" "+f.getPrenom());
        numTel.setText(String.valueOf(f.getTelephone()));
        typeProduit.setText(f.getTypeP());
        this.controller = c;
        this.fournisseur = f;
    }
    @FXML
    public void deleteBtnClicked(MouseEvent event){
        ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
        try {
            serviceFournisseur.supprimer(fournisseur);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try{
            List<fournisseur> fournisseurList = serviceFournisseur.afficher();
            controller.setListFournisseur(fournisseurList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void editBtnClicked(MouseEvent event){
        controller.setEditPage(fournisseur);
    }
}
