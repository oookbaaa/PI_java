package tn.esprit.services;


import tn.esprit.models.produit;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class serviceProduit implements IService<produit> {
    private Connection connection;

    public serviceProduit(){
        connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(produit produit) throws SQLException {
        String req ="insert into produit (nom,prix,qt) values(?,?,?)";
        PreparedStatement ste= connection.prepareStatement(req);
        ste.setString(1, produit.getNom());
        ste.setInt(2, produit.getPrix());
        ste.setInt(3, produit.getQt());
        ste.executeUpdate();
    }

    @Override
    public void modifier(produit produit) throws SQLException {
        String req = "update produit set nom=? , prix=? , qt=? where id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1,produit.getNom());
        pre.setInt(2,produit.getPrix());
        pre.setInt(3,produit.getQt());
        pre.setInt(4,produit.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<produit> recuperer() throws SQLException {
        return null;
    }


    public void supprimer(produit produit) throws SQLException {

        String req = " delete from produit where id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1,produit.getId());
        pre.executeUpdate();

    }

    public List<produit> afficher() throws SQLException {

        String req = "select * from produit";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<produit> list =new ArrayList<>();
        while (res.next()){
            produit p = new produit();
            p.setId(res.getInt(1));
            p.setNom(res.getString("nom"));
            p.setPrix(res.getInt(3));
            p.setQt(res.getInt(4));


            list.add(p);
        }
        return list;
    }

    public void updateFavourite(int id, int favourite) {
        String req = "UPDATE produit SET liked=? WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, favourite);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("like updated !");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
