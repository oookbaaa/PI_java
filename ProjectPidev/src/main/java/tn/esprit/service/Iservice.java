package tn.esprit.service;

import tn.esprit.entities.stock;

import java.sql.SQLException;
import java.util.List;

public interface Iservice <T>{

    void ajouter(T t) throws SQLException;


    void Modifier(stock stock) throws SQLException;

    void supprimer(int ID) throws SQLException;

void afficher(T t) throws SQLException;


    List<stock> recuperer() throws SQLException;
}

