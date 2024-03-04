package com.example.benayedjavafx.services;

import com.example.benayedjavafx.entities.Quiz;
import com.example.benayedjavafx.util.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceQuiz implements IserviceQuiz<Quiz> {

    private Connection connection;

    public ServiceQuiz() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Quiz quiz) {

        try {

            String req = "INSERT INTO Quiz(id_quiz, Duree, id_formation, nb_questions,Titre) " +
                    "VALUES ('" + quiz.getId_quiz() + "','" + quiz.getDuree() + "','" + quiz.getId_formation() + "'," +
                    "'" + quiz.getNb_questions() + "','" + quiz.getTitre() + "')";

            System.out.println(req);
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Quiz created");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @Override
    public Quiz Modifier(Quiz quiz) {
        try {

            String req = "UPDATE Quiz SET Duree='" + quiz.getDuree() + "', " +
                    "id_formation='" + quiz.getId_formation() + "', " +
                    "nb_questions='" + quiz.getNb_questions() + "', " +
                    "Titre='" + quiz.getTitre() + "' " +
                    "WHERE id_quiz='" + quiz.getId_quiz() + "'";

            System.out.println(req);
            Statement st = connection.createStatement();
            int rowCount = st.executeUpdate(req);

            if (rowCount > 0) {
                System.out.println("Quiz with id_quiz " + quiz.getId_quiz() + " updated successfully");
            } else {
                System.out.println("Quiz with id_quiz " + quiz.getId_quiz() + " not found or update failed");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return quiz;
    }


    @Override
    public boolean supprimer(int id_quiz) throws SQLException {
        // Logique pour supprimer la formation avec l'ID spécifié
        try {
            String req = "DELETE FROM `quiz` "
                    +"WHERE id_quiz="+id_quiz
                    + ";";
            System.out.println(req);
            Statement s = connection.createStatement();

            return s.executeUpdate(req) > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }

    @Override
    public List<Quiz> recuperer() throws SQLException {
        ArrayList<Quiz> list = new ArrayList<>();



        String requete = "SELECT * FROM quiz";
        PreparedStatement pst = connection.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            list.add(new Quiz(rs.getInt("id_quiz"),
                    rs.getInt("Duree")
                    , rs.getInt("id_formation")
                    , rs.getInt("nb_questions")
                    ,rs.getString("Titre")
            ));
        }
        return list;  }


}