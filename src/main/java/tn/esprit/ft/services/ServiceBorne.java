package tn.esprit.ft.services;

import tn.esprit.ft.models.Bornes;
import tn.esprit.ft.models.Interventions;
import tn.esprit.ft.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceBorne implements IntService<Bornes> {

    private Connection connection;

    public ServiceBorne(){
        connection= MyDatabase.getInstance().getConnection();
    }

    public static void Ajouter(Bornes bornes) {
    }

    @Override
    public void ajouter(Bornes bornes) throws SQLException {
        System.out.println("test inside function with error 1");

        String req = "INSERT INTO bornes (description, etat, adresse, emplacement,filePath,longitude,latitude) VALUES (?, ?, ?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(req);
        pstmt.setString(1, bornes.getDescription());
        pstmt.setString(2, bornes.getEtat());
        pstmt.setString(3, bornes.getAdresse());
        pstmt.setString(4, bornes.getEmplacement());
        pstmt.setBytes(5, bornes.getFilePath());
        pstmt.setFloat(6,bornes.getLongitude());
        pstmt.setFloat(7,bornes.getLatitude());



        pstmt.executeUpdate();
        pstmt.close(); // Close the PreparedStatement after use

        System.out.println("test inside function without error 2 ");
    }


    @Override
    public void modifier(Bornes bornes) throws SQLException
    {
        String req = "update bornes set description=? , etat=? , adresse=?, emplacement=? , filePath=?,longitude=?,latitude=? WHERE id = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1,bornes.getDescription());
        pre.setString(2,bornes.getEtat());
        pre.setString(3,bornes.getAdresse());
        pre.setString(4,bornes.getEmplacement());
        pre.setBytes(5,bornes.getFilePath());
        pre.setInt(8,bornes.getId());
        pre.setFloat(6,bornes.getLongitude());
        pre.setFloat(7,bornes.getLatitude());


        pre.executeUpdate();

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM bornes WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, id);
        pre.executeUpdate();
    }


    /*@Override
    public void supprimer(int id) throws SQLException {

        String req = " DELETE FROM bornes WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1,id);
        pre.executeUpdate();

    }*/

    @Override
    public List<Bornes> afficher() throws SQLException {


        String req = "select * from bornes";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<Bornes> list =new ArrayList<>();
        while (res.next()){
            Bornes b = new Bornes();
            b.setId(res.getInt(1));
            b.setDescription(res.getString("description"));
            b.setEtat(res.getString("etat"));
            b.setAdresse(res.getString("adresse"));
            b.setEmplacement(res.getString("emplacement"));
            b.setFilePath(res.getBytes("filePath"));
            b.setLatitude(res.getFloat("latitude"));
            b.setLongitude(res.getFloat("longitude"));


            list.add(b);
        }
        return list;
    }

    public Bornes findById(int idBorne) throws SQLException {
        String req = "SELECT * FROM bornes WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, idBorne); // Use setInt for idBorne
        ResultSet res = pre.executeQuery();

        Bornes b = null; // Initialize outside the loop

        if (res.next()) {
            b = new Bornes();
            b.setId(res.getInt("id"));
            b.setDescription(res.getString("description"));
            b.setEtat(res.getString("etat"));
            b.setAdresse(res.getString("adresse"));
            b.setEmplacement(res.getString("emplacement"));
            b.setFilePath(res.getBytes("filePath"));
            b.setLongitude(res.getFloat("longitude"));
            b.setLatitude(res.getFloat("latitude"));
        }

        // Close resources
        if (res != null) {
            res.close();
        }
        if (pre != null) {
            pre.close();
        }

        return b;
    }

    public Bornes getLastInsertedBorne() throws SQLException {
        String req = "SELECT * FROM bornes ORDER BY id DESC LIMIT 1";
        PreparedStatement pre = connection.prepareStatement(req);
        ResultSet res = pre.executeQuery();

        Bornes b = null;

        if (res.next()) {

            b.setId(res.getInt("id"));
            b.setDescription(res.getString("description"));
            b.setEtat(res.getString("etat"));
            b.setAdresse(res.getString("adresse"));
            b.setEmplacement(res.getString("emplacement"));
            b.setFilePath(res.getBytes("filePath"));
            b.setLongitude(res.getFloat("longitude"));
            b.setLatitude(res.getFloat("latitude"));
            // Set other attributes as needed
        }

        // Close resources
        if (res != null) {
            res.close();
        }
        if (pre != null) {
            pre.close();
        }

        return b;
    }
    /*public void setEmplacement(Bornes.Emplacement emplacement) {
    }*/

}
