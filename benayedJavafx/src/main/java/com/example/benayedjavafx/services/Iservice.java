package com.example.benayedjavafx.services;

import com.example.benayedjavafx.entities.formation;

import java.sql.SQLException;
import java.util.List;

public interface Iservice <T>{

    void ajouter(T t) throws SQLException;


    void Modifier(T t) throws SQLException;

    void supprimer(int ID) throws SQLException;




    List<formation> recuperer() throws SQLException;
}

