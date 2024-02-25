package tn.esprit.service;

import tn.esprit.entities.commande;

import java.sql.SQLException;
import java.util.List;

public interface IcommandeService <C>{

    void ajouter(C c) throws SQLException;


    void Modifier(commande commande) throws SQLException;

    void supprimer(int ID) throws SQLException;

    void afficher(C C) throws SQLException;


    List<commande> recuperer() throws SQLException;
}
