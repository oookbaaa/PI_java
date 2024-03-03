package tn.esprit.ft.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;
import tn.esprit.ft.utils.MyDatabase;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.input.MouseEvent;

public class statsInterventionsController implements Initializable {
    public PieChart piechart2;
    public PieChart piechart;


        private Statement st;
        private ResultSet rs;
        private Connection cnx;


        ObservableList<PieChart.Data> data= FXCollections.observableArrayList();
        ObservableList<PieChart.Data> data2= FXCollections.observableArrayList();


        /**
         * Initializes the controller class.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            cnx= MyDatabase.getInstance().getConnection();
            stat();
        }
        private void stat()
        {


            try {

                String query = "SELECT COUNT(*),type FROM interventions GROUP BY type" ;

                PreparedStatement PreparedStatement = cnx.prepareStatement(query);
                rs = PreparedStatement.executeQuery();


                while (rs.next()){
                    data.add(new PieChart.Data(rs.getString("type"),rs.getInt("COUNT(*)")));
                }
            } catch (SQLException ex) {
            }

            piechart.setTitle("*Statistiques des types des interventions *");
            piechart.setLegendSide(Side.LEFT);
            piechart.setData(data);

            try {

                String query = "SELECT COUNT(*),emplacement FROM bornes GROUP BY emplacement" ;

                PreparedStatement PreparedStatement = cnx.prepareStatement(query);
                rs = PreparedStatement.executeQuery();


                while (rs.next()){
                    data2.add(new PieChart.Data(rs.getString("emplacement"),rs.getInt("COUNT(*)")));
                }
            } catch (SQLException ex) {
            }

            piechart2.setTitle("*Statistiques des emplacements des interventions *");
            piechart2.setLegendSide(Side.LEFT);
            piechart2.setData(data2);

        }


    }
