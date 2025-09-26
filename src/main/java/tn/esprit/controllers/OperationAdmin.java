package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OperationAdmin implements Initializable {


    @FXML
    private Text emailLabelDetail;

    @FXML
    private Text firstNameLabelDetail;

    @FXML
    private Text isActiveLabelDetail;


    @FXML
    private Text numberLabelDetail;

    @FXML
    private ImageView userImgContainer;

    @FXML
    private Text Adminlabel;

    private String addCourseImgPath;
    private Image detailImg;

    @Override
    public void initialize(URL url, ResourceBundle rb){


    }

    public void showUserDetails(User user) {
        addCourseImgPath = user.getPhoto();
        detailImg = new Image(addCourseImgPath);
        userImgContainer.setImage(detailImg);
        firstNameLabelDetail.setText(user.getNom());
        emailLabelDetail.setText(user.getEmail());
        isActiveLabelDetail.setText(String.valueOf(user.getStatus()));
        Adminlabel.setText(String.valueOf(user.getRole()));
        numberLabelDetail.setText(String.valueOf(user.getTel()));

    }



    @FXML
    private void goBack(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Dash.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1320, 827);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}