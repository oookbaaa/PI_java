package com.example.benayedjavafx.services;
import com.example.benayedjavafx.entities.Quiz;


import java.sql.SQLException;
import java.util.List;
public interface IserviceQuiz<T> {
    void ajouter(T t) throws SQLException;


    Quiz Modifier(T t) throws SQLException;

    boolean supprimer(int ID) throws SQLException;




    List<Quiz> recuperer() throws SQLException;

}
