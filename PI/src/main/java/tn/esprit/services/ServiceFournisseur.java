package tn.esprit.services;


import tn.esprit.models.fournisseur;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceFournisseur implements IService<fournisseur> {
    private Connection connection;

    public ServiceFournisseur(){
        connection= MyDatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(fournisseur fournisseur) throws SQLException {
        String req ="insert into fournisseur (nom,prenom,telephone,typeP) values(?,?,?,?)";
        PreparedStatement ste= connection.prepareStatement(req);
        ste.setString(1,fournisseur.getNom());
        ste.setString(2,fournisseur.getPrenom());
        ste.setInt(3,fournisseur.getTelephone());
        ste.setString(4,fournisseur.getTypeP());
        ste.executeUpdate();
    }

    @Override
    public void modifier(fournisseur fournisseur) throws SQLException {
        String req = "update fournisseur set nom=? , prenom=? , telephone=? , typeP=? where id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1,fournisseur.getNom());
        pre.setString(2,fournisseur.getPrenom());
        pre.setInt(3,fournisseur.getTelephone());
        pre.setString(4,fournisseur.getTypeP());
        pre.setInt(5,fournisseur.getId());
        pre.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public List<fournisseur> recuperer() throws SQLException {
        return null;
    }

    public void supprimer(fournisseur fournisseur) throws SQLException {
        String req = " delete from fournisseur where id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1,fournisseur.getId());
        pre.executeUpdate();
    }

    public List<fournisseur> afficher() throws SQLException {

        String req = "select * from fournisseur";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<fournisseur> list =new ArrayList<>();
        while (res.next()){
            fournisseur f = new fournisseur();
            f.setId(res.getInt(1));
            f.setNom(res.getString("nom"));
            f.setPrenom(res.getString("prenom"));
            f.setTelephone(res.getInt("telephone"));
            f.setTypeP(res.getString("typeP"));

            list.add(f);
        }
        return list;
    }
}
