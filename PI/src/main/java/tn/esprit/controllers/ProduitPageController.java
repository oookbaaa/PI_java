package tn.esprit.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tn.esprit.models.produit;
import tn.esprit.services.serviceProduit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitPageController implements Initializable {
    @FXML
    private AnchorPane ajoutPageProduit;

    @FXML
    private Button ajouterProduitBtn;

    @FXML
    private AnchorPane editPageProduit;

    @FXML
    private VBox listContainer;

    @FXML
    private Button modifierProfuitBtn;

    @FXML
    private TextField nomInput;

    @FXML
    private TextField nomInputEdit;

    @FXML
    private TextField prixInput;

    @FXML
    private TextField prixInputEdit;

    @FXML
    private TextField quantiteInput;

    @FXML
    private TextField quantiteInputEdit;
    @FXML
    private Label errorNamePAjout;

    @FXML
    private Label errorNomPEdit;

    @FXML
    private Label errorPrixPAjout;

    @FXML
    private Label errorPrixPEdit;

    @FXML
    private Label errorQuantitePAjout;

    @FXML
    private Label errorQuantitePEdit;
    private List<produit> produits;
    private produit currentProduit;

    public String generateQRCodeAndSave(String text, String fileName) throws WriterException {
        // Generate the QR code

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 250, 250);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Convert the BufferedImage to a JavaFX Image
        Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);

        // Save the image to the specified directory
        String directoryPath = "C:\\Users\\EYA\\Downloads\\PI\\PI\\src\\main\\java\\tn\\Qr";
        Path directory = Paths.get(directoryPath);
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String filePath = directoryPath + "/" + fileName + ".png";
        File file = new File(filePath);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(fxImage, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @Override
    public void initialize(URL url, ResourceBundle rs){
        serviceProduit serviceP = new serviceProduit();
        try{
            List<produit> produitList = serviceP.afficher();
            setListProduit(produitList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        ajoutPageProduit.setVisible(false);
        editPageProduit.setVisible(false);
    }
    public void setListProduit(List<produit> list){
        listContainer.getChildren().clear();
        for(produit p : list){
            try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/gui/produit/produitCardView.fxml"));
                Parent root = fxmlLoader.load();
                ProduitCardViewController controller = fxmlLoader.getController();
                controller.remplirCard(p,this);
                listContainer.getChildren().add(root);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
        this.produits = list;
    }
    @FXML
    void ajoutPBtnClicked(MouseEvent event) {
        if(nomInput.getText().isEmpty()) errorNamePAjout.setVisible(true);
        else errorNamePAjout.setVisible(false);
        if(prixInput.getText().isEmpty()) errorPrixPAjout.setVisible(true);
        else errorPrixPAjout.setVisible(false);
        if (quantiteInput.getText().isEmpty()) errorQuantitePAjout.setVisible(true);
        else errorQuantitePAjout.setVisible(false);
        if(!quantiteInput.getText().isEmpty() && !nomInput.getText().isEmpty() && !prixInput.getText().isEmpty()){
            String nom = nomInput.getText();
            int price = Integer.parseInt(prixInput.getText());
            int quantite = Integer.parseInt(quantiteInput.getText());
            produit p = new produit(0,nom,price,quantite);
            serviceProduit serviceProduit = new serviceProduit();
            try {
                serviceProduit.ajouter(p);
                String sql = "nom"+nom+"price:"+price;
                this.generateQRCodeAndSave(sql,"QR_" +nom);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (WriterException e) {
                throw new RuntimeException(e);
            }
            try{
                List<produit> produitList = serviceProduit.afficher();
                setListProduit(produitList);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            goBackPList(null);
        }
    }

    @FXML
    void goAjoutPPage(MouseEvent event) {
        ajoutPageProduit.setVisible(true);
    }

    public void clearForm(){
        nomInput.clear();
        quantiteInput.clear();
        prixInput.clear();
    }
    public void clearWarninrError(){
        errorQuantitePEdit.setVisible(false);
        errorQuantitePAjout.setVisible(false);
        errorPrixPEdit.setVisible(false);
        errorPrixPAjout.setVisible(false);
        errorNomPEdit.setVisible(false);
        errorNamePAjout.setVisible(false);
    }
    @FXML
    void goBackPList(MouseEvent event) {
        serviceProduit serviceP = new serviceProduit();
        try{
            List<produit> produitList = serviceP.afficher();
            setListProduit(produitList);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        ajoutPageProduit.setVisible(false);
        editPageProduit.setVisible(false);
        clearForm();
        clearWarninrError();
    }

    @FXML
    void modifPBtnClicked(MouseEvent event) {
        if(nomInputEdit.getText().isEmpty()) errorNomPEdit.setVisible(true);
        else errorNomPEdit.setVisible(false);
        if(prixInputEdit.getText().isEmpty()) errorPrixPEdit.setVisible(true);
        else errorPrixPEdit.setVisible(false);
        if (quantiteInputEdit.getText().isEmpty()) errorQuantitePEdit.setVisible(true);
        else errorQuantitePEdit.setVisible(false);
        if(!quantiteInputEdit.getText().isEmpty() && !nomInputEdit.getText().isEmpty() && !prixInputEdit.getText().isEmpty()){
            String nom = nomInputEdit.getText();
            int price = Integer.parseInt(prixInputEdit.getText());
            int quantite = Integer.parseInt(quantiteInputEdit.getText());
            produit p = new produit(currentProduit.getId(),nom,price,quantite);
            serviceProduit serviceP = new serviceProduit();
            try {
                serviceP.modifier(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try{
                List<produit> produitList = serviceP.afficher();
                setListProduit(produitList);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
            goBackPList(null);
        }
    }
    public void setEditPage(produit p){
        nomInputEdit.setText(p.getNom());
        prixInputEdit.setText(String.valueOf(p.getPrix()));
        quantiteInputEdit.setText(String.valueOf(p.getQt()));
        editPageProduit.setVisible(true);
        currentProduit = p;
    }

}
