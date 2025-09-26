package tn.esprit.ft.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {

    public HelloApplication() throws IOException {
    }

    @Override
            public void start(Stage stage) throws IOException{
    FXMLLoader fxmlLoader = new FXMLLoader((HelloApplication.class.getResource("/HomePage.fxml")));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("hello");
    stage.setScene(scene);
    stage.setWidth(stage.getWidth()+50);

        stage.show();
   // stage.setFullScreen(true);
}

public static void main(String [] args){launch();}}