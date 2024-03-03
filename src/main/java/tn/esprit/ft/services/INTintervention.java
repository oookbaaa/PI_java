package tn.esprit.ft.services;

import tn.esprit.ft.models.Interventions;

import java.sql.SQLException;

public interface INTintervention <T> {


        void ajouter(T t) throws java.sql.SQLException;
        void modifier(T t) throws java.sql.SQLException;


        //void supprimer(int id) throws SQLException;

  // void supprimer(int id_int) throws SQLException;


    java.util.List<T> afficher() throws java.sql.SQLException;

    }

