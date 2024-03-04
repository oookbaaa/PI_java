package com.example.benayedjavafx.services;

import com.example.benayedjavafx.entities.formation;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface Iservice <T>{

    void ajouter(T t) throws SQLException;


    formation Modifier(T t) throws SQLException;

    boolean supprimer(int ID) throws SQLException;




    List<formation> recuperer() throws SQLException;

    public ObservableList<formation> chercherFormation(String chaine);


}

