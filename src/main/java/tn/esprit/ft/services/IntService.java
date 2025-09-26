package tn.esprit.ft.services;

import java.sql.SQLException;

public interface IntService <T> {

    void ajouter(T t) throws java.sql.SQLException;
    void modifier(T t) throws java.sql.SQLException;


    void supprimer(int id) throws SQLException;

    java.util.List<T> afficher() throws java.sql.SQLException;

}

