package tn.esprit.services;

import tn.esprit.models.Interventions;
import tn.esprit.models.Interventions;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class ServiceIntervention implements INTintervention<Interventions> {

    private Connection connection;

    public ServiceIntervention(){
        connection= MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Interventions interventions) throws SQLException {

        String req = "INSERT INTO interventions (id,id_emp, type, date,imagePath) VALUES (?,?, ?, ?,?)";
        PreparedStatement pstmt = connection.prepareStatement(req);
        pstmt.setInt(1,interventions.getId_borne());
        pstmt.setInt(2,interventions.getId_emp());
        pstmt.setString( 3,interventions.getType());
        pstmt.setDate( 4,new Date(interventions.getDate().getTime()));
        pstmt.setBytes( 5,interventions.getImagePath());



        pstmt.executeUpdate();
        pstmt.close(); // Close the PreparedStatement after use

    }
    @Override
    public void modifier(Interventions interventions) throws SQLException
    {

        String req = "UPDATE interventions SET   id_emp=? ,type=? , date=?, imagePath=? WHERE id_int=? ";
        PreparedStatement pre = connection.prepareStatement(req);
       // pre.setInt(1,interventions.getId_borne());
        pre.setInt(1,interventions.getId_emp());
        pre.setString(2,interventions.getType());
        pre.setObject(3,interventions.getDate());
        pre.setBytes(4,interventions.getImagePath());
        pre.setInt(5,interventions.getId_int());



        pre.executeUpdate();


    }
    //@Override
    public void supprimer(int id_int) throws SQLException {

        String req = " DELETE FROM `interventions` WHERE `id_int` = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, id_int);
        pre.executeUpdate();

    }
    @Override
    public List<Interventions> afficher() throws SQLException {

        String req = "SELECT * FROM interventions";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<Interventions> list =new ArrayList<>();
        while (res.next()){
            Interventions i = new Interventions();
            i.setId_int(res.getInt(1));
            i.setId_emp(res.getInt(2));
            i.setId_borne(res.getInt(3));
            i.setType(res.getString("type"));
            i.setDate(res.getDate("date"));

            list.add(i);
        }
        res.close();
        ste.close();
        return list;
    }

    public List<Interventions> getInterventions() {
        String req1 = "SELECT * FROM interventions";
        List<Interventions> events = new ArrayList<>();
        try {
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(req1);

            while (rs.next()) {
                Interventions e1 = new Interventions();
                e1.setId_int(rs.getInt(1));
                e1.setId_emp(rs.getInt(2));
                e1.setId_borne(rs.getInt(3));
                e1.setType(rs.getString("type"));
                e1.setDate(rs.getDate("date"));
                e1.setImagePath(rs.getBytes("imagePath"));


                events.add(e1);

                System.out.println("the event is: " + e1.toString());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return events;
    }

    public Interventions getLastInsertedIntervention() throws SQLException {
        String req = "SELECT * FROM interventions ORDER BY id DESC LIMIT 1";
        PreparedStatement pre = connection.prepareStatement(req);
        ResultSet res = pre.executeQuery();

        Interventions intervention = null;

        if (res.next()) {
            intervention = new Interventions();
            intervention.setId_int(res.getInt("id"));
            intervention.setType(res.getString("type"));
            intervention.setImagePath(res.getBytes("imagePath"));
            // Set other attributes as needed
        }

        // Close resources
        if (res != null) {
            res.close();
        }
        if (pre != null) {
            pre.close();
        }

        return intervention;
    }

}
