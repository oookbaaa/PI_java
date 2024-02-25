package com.example.benayedjavafx;

import com.example.benayedjavafx.entities.formation;
import com.example.benayedjavafx.services.service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


import java.sql.SQLException;
import java.sql.SQLException;

public class AddFormationController {
    @FXML
    private Pane AddFormationPage;

    @FXML
    private VBox AddUserCard;

    @FXML
    private Button EditFormation;

    @FXML
    private VBox FormationAffichage;

    @FXML
    private Pane ListContainer;

    @FXML
    private Pane UpperSection;

    @FXML
    private Pane UpperSection1;

    @FXML
    private Pane UpperSection2;

    @FXML
    private VBox UserInterfaces;

    @FXML
    private TableColumn<?, ?> actionsColumn;

    @FXML
    private Button addFormation;

    @FXML
    private Button backbtn;

    @FXML
    private TableColumn<?, ?> ddColumn;

    @FXML
    private DatePicker ddField;

    @FXML
    private TableColumn<?, ?> decriptionColumn;

    @FXML
    private TextField descField;

    @FXML
    private TableColumn<?, ?> dfColumn;

    @FXML
    private DatePicker dfField;

    @FXML
    private TableColumn<?, ?> dureeColumn;

    @FXML
    private TextField dureeField;

    @FXML
    private TableColumn<?, ?> formateurColumn;

    @FXML
    private TextField formateurField;

    @FXML
    private TableView<?> formationtable;

    @FXML
    private Button gotoaddbtn;

    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> lieuColumn;

    @FXML
    private TextField lieuField;

    @FXML
    private Label titletextfield;

    @FXML
    private TableColumn<?, ?> titreColumn;

    @FXML
    private TextField titreField;

    @FXML
    void GoToUserAffichagePage(ActionEvent event) {

    }


    @FXML
    void addFormation(ActionEvent event) {
        service formationService = new service();
        formation f = new formation(1122, Integer.parseInt(dureeField.getText() ),titreField.getText(),descField.getText(),lieuField.getText(),"fdjnv",
                new java.sql.Date(1220,12,12),
                new  java.sql.Date(1220,12,12));

        new service().ajouter(f);

    }

    @FXML
    void handleEditUser(ActionEvent event) {

    }
}
