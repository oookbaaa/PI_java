package tn.esprit.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.models.Status;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.utils.PasswordHasher;
import tn.esprit.utils.SessionManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OperationUser implements Initializable {
    @FXML
    private ImageView closeWarningPassword;

    @FXML
    private Button confirmDeleteCourseBtn;

    @FXML
    private AnchorPane coursesDetailsContainer;

    @FXML
    private ImageView deleteCourseBtnImg;

    @FXML
    private Button deleteuser;

    @FXML
    private Label done;

    @FXML
    private AnchorPane doneEditCourse;

    @FXML
    private ImageView editCourseBtnImg;

    @FXML
    private Button editUser;

    @FXML
    private AnchorPane editanchorpane;

    @FXML
    private Button editbutton;

    @FXML
    private TextField editemailuser;

    @FXML
    private TextField editfirstnameUser;

    @FXML
    private TextField editgenderuser;

    @FXML
    private Button editimgbutton;

    @FXML
    private ImageView editimguser;

    @FXML
    private TextField editnumberuser;

    @FXML
    private TextField editadresseuser;

    @FXML
    private Text emailLabelDetail;

    @FXML
    private Text firstNameLabelDetail;


    @FXML
    private Button goBackCourseBtnEdit;

    @FXML
    private Button goBackCourseDetBtn;


    @FXML
    private Text isActiveLabelDetail;


    @FXML
    private Text numberLabelDetail;


    @FXML
    private ImageView userImgContainer;

    @FXML
    private AnchorPane warningDeleteCourseContainer;


    private String addCourseImgPath;
    private Image detailImg;
    @FXML
    private Text Adressdetail;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
                coursesDetailsContainer.setVisible(true);
        warningDeleteCourseContainer.setVisible(false);
      editanchorpane.setVisible(false);

    }




    public void showUserDetails(User user) {
        addCourseImgPath = user.getPhoto();
        detailImg = new Image(addCourseImgPath);
        userImgContainer.setImage(detailImg);
        firstNameLabelDetail.setText(user.getNom());
        emailLabelDetail.setText(user.getEmail());
        Adressdetail.setText(user.getAdresse());
        isActiveLabelDetail.setText(String.valueOf(user.getStatus()));
        numberLabelDetail.setText(String.valueOf(user.getTel()));

    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
        coursesDetailsContainer.setVisible(true);
        editanchorpane.setVisible(false);


    }

    @FXML
    public void confirmDeleteUser(ActionEvent event) throws IOException, SQLException, NoSuchAlgorithmException {
        UserService us = new UserService();

            try {
                User loggedInUser = SessionManager.getSession(SessionManager.getLastSessionId());
                int id = loggedInUser.getId();

                System.out.println("User ID: " + id);
                us.supprimer(id);
                doneEditCourse.setVisible(true);
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), e -> doneEditCourse.setVisible(false)));
                timeline.setCycleCount(1);
                timeline.play();
                FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("/MainUI.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 751);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (NumberFormatException e) {
                System.err.println("Error parsing user ID: " + e.getMessage());
            }
        }



    @FXML
    public void editUserBtnClicked(ActionEvent event) throws IOException {
        editanchorpane.setVisible(true);
        coursesDetailsContainer.setVisible(false);
        editfirstnameUser.setText(firstNameLabelDetail.getText());
        editadresseuser.setText(Adressdetail.getText());
        editemailuser.setText(emailLabelDetail.getText());
        editnumberuser.setText(String.valueOf(numberLabelDetail.getText()));
        editimguser.setImage(detailImg);


    }
    private String photo;
    @FXML
    public void editUserButton(ActionEvent event) throws SQLException, NoSuchAlgorithmException {
        User loggedInUser = SessionManager.getSession(SessionManager.getLastSessionId());
        int id = loggedInUser.getId();
        String nom = editfirstnameUser.getText();
        String email = editemailuser.getText();
        String adresse = editadresseuser.getText();
        int tel = Integer.parseInt(editnumberuser.getText());

      String photo = editimguser.getImage().getUrl();

        UserService serv = new UserService();

        User editeduser = new User(id, tel, nom, email, adresse, photo);
        System.out.println( "photo : " + photo);
        serv.modifier(editeduser);
        showUserDetails(editeduser);
//        refreshUserDetails();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("User updated successfully");
        alert.showAndWait();

    }




    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(editimguser.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            editimguser.setImage(image);
        }
    }




}
