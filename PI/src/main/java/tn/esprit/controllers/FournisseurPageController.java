package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tn.esprit.models.fournisseur;
import tn.esprit.services.ServiceFournisseur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class FournisseurPageController implements Initializable {
    @FXML
    private VBox listContainer;
    @FXML
    private AnchorPane ajoutPageFournisseur;
    @FXML
    private TextField nomInput;

    @FXML
    private TextField numtelInput;

    @FXML
    private TextField prenomInput;

    @FXML
    private ComboBox<String> typeCB;
    @FXML
    private AnchorPane editPageFournisseur;
    @FXML
    private ComboBox<String> typeCBEdit;
    @FXML
    private TextField numtelInputEdit;
    @FXML
    private TextField prenomInputEdit;
    @FXML
    private TextField nomInputEdit;
    @FXML
    private Label errorNameFAjout;

    @FXML
    private Label errorNameFEdit;

    @FXML
    private Label errorNumFAjout;

    @FXML
    private Label errorNumFEdit;

    @FXML
    private Label errorPrenomFAjout;

    @FXML
    private Label errorPrenomFEdit;

    private List<fournisseur> fournisseurs;
    private fournisseur currentFournisseur;
    @FXML
    private Text itemselcted;




    @Override
    public void initialize(URL url, ResourceBundle rs){
        ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
        try{
            List<fournisseur> fournisseurList = serviceFournisseur.afficher();
            setListFournisseur(fournisseurList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        ajoutPageFournisseur.setVisible(false);
        editPageFournisseur.setVisible(false);
    }
    public void setListFournisseur(List<fournisseur> list){
        listContainer.getChildren().clear();
        for(fournisseur f : list){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/fournisseur/fournisseurCardView.fxml"));
                Parent root = fxmlLoader.load();
                FournisseurCardViewController controller = fxmlLoader.getController();
                controller.remplirCard(f,this);
                listContainer.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        this.fournisseurs = list;
    }
    @FXML
    public void goAjoutFPage(){
        ObservableList<String> items = FXCollections.observableArrayList(
                "Choice 1",
                "Choice 2",
                "Choice 3"
        );
        typeCB.setItems(items);
        typeCB.setValue("Choice 1");
        ajoutPageFournisseur.setVisible(true);
    }
    @FXML
    public void goBackFList(MouseEvent event){
        ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
        try{
            List<fournisseur> fournisseurList = serviceFournisseur.afficher();
            setListFournisseur(fournisseurList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        clearAddFForm();
        ajoutPageFournisseur.setVisible(false);
        editPageFournisseur.setVisible(false);
        clearErrorSaisie();
    }
    public void clearAddFForm(){
        nomInput.clear();
        prenomInput.clear();
        numtelInput.clear();
    }
    public void clearErrorSaisie(){
        errorNameFAjout.setVisible(false);
        errorPrenomFAjout.setVisible(false);
        errorNumFAjout.setVisible(false);
        errorNameFEdit.setVisible(false);
        errorPrenomFEdit.setVisible(false);
        errorNumFEdit.setVisible(false);
    }
    @FXML
    public void ajoutFBtnClicked(MouseEvent event){
        if(nomInput.getText().isEmpty()) errorNameFAjout.setVisible(true);
        else errorNameFAjout.setVisible(false);
        if(prenomInput.getText().isEmpty()) errorPrenomFAjout.setVisible(true);
        else errorPrenomFAjout.setVisible(false);
        if (numtelInput.getText().length() != 8) errorNumFAjout.setVisible(true);
        else errorNumFAjout.setVisible(false);
        if(!nomInput.getText().isEmpty() && !prenomInput.getText().isEmpty() && numtelInput.getText().length() == 8){
            String nom = nomInput.getText();
            String prenom = prenomInput.getText();
            int numtel = Integer.parseInt(numtelInput.getText());
            String typeP = typeCB.getValue();
            fournisseur f = new fournisseur(0,nom,prenom,numtel,typeP);
            ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
            try {
                serviceFournisseur.ajouter(f);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try{
                List<fournisseur> fournisseurList = serviceFournisseur.afficher();
                setListFournisseur(fournisseurList);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            goBackFList(null);
        }
    }
    public void setEditPage(fournisseur f){
        nomInputEdit.setText(f.getNom());
        prenomInputEdit.setText(f.getPrenom());
        numtelInputEdit.setText(String.valueOf(f.getTelephone()));
        ObservableList<String> items = FXCollections.observableArrayList(
                "Choice 1",
                "Choice 2",
                "Choice 3"
        );
        typeCBEdit.setItems(items);
        typeCBEdit.setValue("Choice 1");
        editPageFournisseur.setVisible(true);
        currentFournisseur = f;
    }
    @FXML
    public void modifFBtnClicked(MouseEvent event){
        if(nomInputEdit.getText().isEmpty()) errorNameFEdit.setVisible(true);
        else errorNameFEdit.setVisible(false);
        if(prenomInputEdit.getText().isEmpty()) errorPrenomFEdit.setVisible(true);
        else errorPrenomFEdit.setVisible(false);
        if (numtelInputEdit.getText().length() != 8) errorNumFEdit.setVisible(true);
        else errorNumFEdit.setVisible(false);
        if(!nomInputEdit.getText().isEmpty() && !prenomInputEdit.getText().isEmpty() && numtelInputEdit.getText().length() == 8){
            String nom = nomInputEdit.getText();
            String prenom = prenomInputEdit.getText();
            int numtel = Integer.parseInt(numtelInputEdit.getText());
            String typeP = typeCBEdit.getValue();
            fournisseur f = new fournisseur(currentFournisseur.getId(),nom,prenom,numtel,typeP);
            ServiceFournisseur serviceFournisseur = new ServiceFournisseur();
            try {
                serviceFournisseur.modifier(f);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try{
                List<fournisseur> fournisseurList = serviceFournisseur.afficher();
                setListFournisseur(fournisseurList);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            goBackFList(null);
        }
    }

}
