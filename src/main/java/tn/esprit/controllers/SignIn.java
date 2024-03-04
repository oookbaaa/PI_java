package tn.esprit.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.utils.SessionManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;



public class SignIn implements Initializable {

    String password;
    private String code;
    @FXML
    private ImageView robot;
    @FXML
    private TextField codecap;
    private final UserService ps = new UserService();
    private double xOffset=0;
    private double yOffset=0;

    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private AnchorPane parent;
    @FXML
    private Pane parentpane;
    @FXML
    private ImageView reduceIcon;
    @FXML
    private ImageView lblopen;
    @FXML
    private ImageView lblclose;
    @FXML
    private Label LoginMessagelabel;
    @FXML
    private Label CodeMessagelabel;
    @FXML
    private JFXTextField txtShowPassword;





public void HidePasswordOnAction(KeyEvent event) {
    password=passwordField.getText();
    txtShowPassword.setText(password);
}
    public void ShowPasswordOnAction(KeyEvent event) {
    password=txtShowPassword.getText();
    passwordField.setText(password);
}

    public void Close_Eye_OnAction(MouseEvent event) {
      txtShowPassword.setVisible(true);
      lblopen.setVisible(true);
        lblclose.setVisible(false);
        passwordField.setVisible(false);

}
    public void Open_Eye_OnAction(MouseEvent event) {
        txtShowPassword.setVisible(false);
        lblopen.setVisible(false);
        lblclose.setVisible(true);
        passwordField.setVisible(true);
}
    @FXML
    void signIn(ActionEvent event) throws IOException {
        String codecaptcha = codecap.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            LoginMessagelabel.setText("Please enter email and password.");
            return;
        }
        else if (!usernameField.getText().matches(emailPattern)) {
            LoginMessagelabel.setText("Please enter a valid email address.");
            return;
        }

        if (!code.equals(codecaptcha)) {
           // CodeMessagelabel.setText("Please enter a valid code.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer le code correctement.");
            alert.showAndWait();
            return;
        }
        String sessionId = ps.authenticateUser(username, password);
        User user = ps.getUserFromSession(sessionId);
            if (UserService.blocked==true) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inactive Account");
                alert.setHeaderText(null);
                alert.setContentText("Your account is inactive. Please contact your administrator !");
                alert.showAndWait();
                UserService.blocked=false;
                return;
            }
            else{
                LoginMessagelabel.setText("Invalid email or password.");
            }
        User loggedInUser = SessionManager.getSession(SessionManager.getLastSessionId());

            openWelcomeWindow(loggedInUser.getNom());
    }
    private void openWelcomeWindow(String username) {
        try {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Welcome.fxml"));
            Parent root = loader.load();


            Welcome controller = loader.getController();
            controller.setUserName(username);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
            Stage signInStage = (Stage) usernameField.getScene().getWindow();
            signInStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void makeStageDrageable(){
        parent.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }

        });
        parent.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                parent.getScene().getWindow().setX(event.getScreenX() - xOffset);
                parent.getScene().getWindow().setY(event.getScreenY() - yOffset);
            }

        });
    }
    @FXML
    private void open_registration(MouseEvent event ) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/RegistrationUI.fxml"));
        parentpane.getChildren().removeAll();
        parentpane.getChildren().setAll(fxml);
    }
    @FXML
    private void open_forget(MouseEvent event ) throws IOException {

        Parent fxml = FXMLLoader.load(getClass().getResource("/ForgetPassword.fxml"));
        parentpane.getChildren().removeAll();
        parentpane.getChildren().setAll(fxml);
    }
    @FXML
    private void reduceWindow(MouseEvent event) {
        Stage stage = (Stage) reduceIcon.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private void close_app(MouseEvent event){
        System.exit(0);

    }
    private String generateCode() {
        // Generate a 6-digit random code
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return Integer.toString(code);
    }
    @FXML
    private void captcha(ActionEvent event) {
        this.code = generateCode();

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            String Information = "code : : " + code;
            int width = 300;
            int height = 300;

            BufferedImage bufferedImage = null;
            BitMatrix byteMatrix = qrCodeWriter.encode(Information, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }

            robot.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

        } catch (WriterException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codecap.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                codecap.setText(newValue.replaceAll("[^\\d]", ""));
            }

            if (codecap.getText().length() > 6) {
                String limitedText = codecap.getText().substring(0, 6);
                codecap.setText(limitedText);
            }
        });
        captcha(new ActionEvent());
        makeStageDrageable();
        txtShowPassword.setVisible(false);
        lblopen.setVisible(false);
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    signIn(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
