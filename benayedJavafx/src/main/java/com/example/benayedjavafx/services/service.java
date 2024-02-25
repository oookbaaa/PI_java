package com.example.benayedjavafx.services;



import com.example.benayedjavafx.entities.formation;
import com.example.benayedjavafx.util.MyDataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class service implements Iservice<formation> {

    private Connection connection;

    public service() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(formation formation) {

        try {

            String req = "INSERT INTO formation(duree, titre, description, lieu, formateur, Dd, Df) " +
                    "VALUES ('" + formation.getDuree() + "','" + formation.getTitre() + "','" + formation.getDescription() + "'," +
                    "'" + formation.getLieu() + "','" + formation.getFormateur() + "','" + formation.getDd() + "','" + formation.getDf() + "')";

            System.out.println(req);
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("formation created");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void Modifier(formation formation) throws SQLException {

    }

    @Override
    public void supprimer(int ID) throws SQLException {

    }

    @Override
    public List<formation> recuperer() throws SQLException {
        return null;
    }
}

