package tn.esprit.service;

import tn.esprit.entities.commande;
import tn.esprit.utile.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicecommande implements IcommandeService<commande> {

    private Connection connection;

    public Servicecommande() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(commande commande) throws SQLException {
        String sql = "INSERT INTO commande ( DateCommande, IDclient, MontantTotale, StatutCommande, ProduitCommande) VALUES ( ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new java.sql.Date(commande.getDateCommande().getTime()));
            preparedStatement.setInt(2, commande.getIDclient());
            preparedStatement.setBigDecimal(3, commande.getMontantTotale());
            preparedStatement.setString(4, commande.getStatutCommande().nomAffiche());
            preparedStatement.setString(5, commande.getProduitCommande());
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void Modifier(commande commande) throws SQLException {
        String sql = "UPDATE commande SET DateCommande=?, IDclient=?, MontantTotale=?, StatutCommande=?, ProduitCommande=? WHERE IDcommande=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDate(1, new java.sql.Date(commande.getDateCommande().getTime()));
            preparedStatement.setInt(2, commande.getIDclient());
            preparedStatement.setBigDecimal(3, commande.getMontantTotale());
            preparedStatement.setString(4, commande.getStatutCommande().name());
            preparedStatement.setString(5, commande.getProduitCommande());
            preparedStatement.setInt(6, commande.getIDcommande());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int ID) throws SQLException {
        String sql = "DELETE FROM commande WHERE IDcommande=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void afficher(commande C) throws SQLException {

    }

    /* @Override
     public  void afficher(int idCommande) throws SQLException {
        // commande commande = obtenircommandeParID(idCommande);

         if (idCommande != 0) {
             System.out.println("Détails de la commande (ID " + idCommande + ") :");
             System.out.println("Date de commande : " + commande.getDateCommande());
             System.out.println("ID du client : " + commande.getIDclient());
             System.out.println("Montant total : " + commande.getMontantTotale());
             System.out.println("Statut de commande : " + commande.getStatutCommande());
             System.out.println("Produit de commande : " + commande.getProduitCommande());
         } else {
             System.out.println("La commande avec l'ID " + idCommande + " n'existe pas.");
         }
     }*/
    @Override
    public List<commande> recuperer() throws SQLException {
        List<commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                commande commande = new commande();
                commande.setIDcommande(resultSet.getInt("IDcommande"));
                commande.setDateCommande(resultSet.getDate("DateCommande"));
                commande.setIDclient(resultSet.getInt("IDclient"));
                commande.setMontantTotale(resultSet.getBigDecimal("MontantTotale"));
               // commande.setStatutCommande(commande.StatutCommande.valueOf(resultSet.getString("StatutCommande")));
                commande.setProduitCommande(resultSet.getString("ProduitCommande"));
                commandes.add(commande);
            }
        }
        return commandes;
    }
}
