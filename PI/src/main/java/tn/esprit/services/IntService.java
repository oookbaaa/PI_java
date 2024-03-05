package tn.esprit.services;

import java.sql.SQLException;

public interface IntService <T> {

    void ajouter(T t) throws SQLException;
    void modifier(T t) throws SQLException;


    void supprimer(int id) throws SQLException;

    java.util.List<T> afficher() throws SQLException;

}

