package tn.esprit.services;

import tn.esprit.models.Interventions;

import java.sql.SQLException;

public interface INTintervention <T> {


        void ajouter(T t) throws SQLException;
        void modifier(T t) throws SQLException;


        //void supprimer(int id) throws SQLException;

  // void supprimer(int id_int) throws SQLException;


    java.util.List<T> afficher() throws SQLException;

    }

