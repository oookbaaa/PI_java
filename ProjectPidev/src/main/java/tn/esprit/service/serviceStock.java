package tn.esprit.service;

import tn.esprit.entities.stock;
import tn.esprit.utile.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class serviceStock implements Iservice<stock> {

    private Connection connection;

    public serviceStock() {
        connection = MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter(stock stock) throws SQLException {
        String sql = "INSERT INTO stock (NomProduit, Quantite, PrixVente, Fournisseur, Categorie) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, stock.getNomProduit());
            preparedStatement.setInt(2, stock.getQuantite());
            preparedStatement.setDouble(3, stock.getPrixVente());
            preparedStatement.setString(4, stock.getFournisseur());
            preparedStatement.setString(5, stock.getCategorie().nomAffiche());
            preparedStatement.executeUpdate();
        }
    }

     @Override
    public void Modifier(stock stock) throws SQLException {

       String sql = "update stock set NomProduit= ?,Quantite=?,PrixVente=?,Fournisseur=?,Categorie=? where IDproduit=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, stock.getNomProduit());
        preparedStatement.setInt(2, stock.getQuantite());
        preparedStatement.setDouble(3, stock.getPrixVente());
        preparedStatement.setString(4, stock.getFournisseur());
        preparedStatement.setString(5, stock.getCategorie().nomAffiche());
        preparedStatement.setInt(6, stock.getIDproduit());
        preparedStatement.executeUpdate();
  }
@Override
    public void supprimer (int id ) throws SQLException {
        String sql ="DELETE FROM `stock` WHERE IDproduit=?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1,id);
    preparedStatement.executeUpdate();
}

    @Override
    public void afficher(stock stock) throws SQLException {

    }

    @Override
    public List<stock> recuperer() throws SQLException{

        List<stock> stocks=new ArrayList<>();
        String sql ="Select * From stock";
        Statement statement = connection.createStatement();
    ResultSet rs = statement.executeQuery(sql);
    while(rs.next()) {
        stock s = new stock();
        s.setIDproduit(rs.getInt("IDproduit"));
        s.setNomProduit(rs.getString("NomProduit"));
        s.setQuantite(rs.getInt("Quantite"));
        s.setPrixVente(rs.getFloat("PrixVente"));
        s.setFournisseur(rs.getString("Fournisseur"));
        stocks.add(s);
    }
return stocks;

    }



}
