package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import tn.esprit.utils.MyDatabase;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Stat implements Initializable {
    public PieChart piechart2;
    public PieChart piechart;


    private Statement st;
    private ResultSet rs;
    private Connection cnx;


    ObservableList<PieChart.Data> data= FXCollections.observableArrayList();
    ObservableList<PieChart.Data> data2= FXCollections.observableArrayList();



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cnx= MyDatabase.getInstance().getConnection();
        stat();
    }
    private void stat()
    {


        try {

            String query = "SELECT COUNT(*), role_user FROM user WHERE role_user != 'ADMIN' GROUP BY role_user" ;

            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();


            while (rs.next()){
                data.add(new PieChart.Data(rs.getString("role_user"),rs.getInt("COUNT(*)")));
            }
        } catch (SQLException ex) {
        }

        piechart.setTitle("*Statistiques des Roles *");

        piechart.setLegendSide(Side.LEFT);
        piechart.setData(data);

        try {

            String query = "SELECT COUNT(*), role_user FROM user WHERE role_user != 'ADMIN' GROUP BY role_user" ;

            PreparedStatement PreparedStatement = cnx.prepareStatement(query);
            rs = PreparedStatement.executeQuery();


            while (rs.next()){
                data2.add(new PieChart.Data(rs.getString("role_user"),rs.getInt("COUNT(*)")));
            }
        } catch (SQLException ex) {
        }

        piechart2.setTitle("*Statistiques des Roles *");
        piechart2.setLegendSide(Side.LEFT);
        piechart2.setData(data2);

    }


}