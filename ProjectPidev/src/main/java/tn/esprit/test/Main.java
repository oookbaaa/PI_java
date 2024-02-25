package tn.esprit.test;

import tn.esprit.service.Servicecommande;
import java.sql.SQLException;
import java.util.List;
import java.util.Date;
import java.math.BigDecimal;
import tn.esprit.entities.commande;

/*public class Main {
    public static void main(String[] args) {
        serviceStock ss = new serviceStock();

        // Ajout d'un stock

       try {
            ss.ajouter(new stock(8, "ammmmmmmmm", 40, 900000, "akrich lekbir", PortDeCharge));
            System.out.println("Ajout effectué !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }


        // Mise à jour d'un stock

       try {
            stock stockToUpdate = new stock(4, "pppp8888ppp", 40, 5.2F, "akrich sghir", PortDeCharge);
            ss.Modifier(stockToUpdate);
            System.out.println("Mise à jour effectuée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }


        // Suppression d'un stock

        try {

            int stockIdToDelete =102; // Remplacez par l'identifiant du stock que vous souhaitez supprimer
            ss.supprimer(stockIdToDelete);
            System.out.println("Stock supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
            e.printStackTrace();
        }


        // Recuperer un stock
       try {
            List<stock> stocks = ss.recuperer();
            System.out.println("Liste des stocks récupérée !");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des stocks : " + e.getMessage());
            e.printStackTrace();
        }

    }

}
/*package org.example.test;

import org.example.utile.MyDataBase;
public class Main {
    public static void main(String[] args) {
        System.out.println(MyDataBase.getInstance());
        System.out.println(MyDataBase.getInstance());
    }
}*/
/*package org.example.test;
import org.example.entities.commande;
import org.example.service.Servicecommande;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.example.entities.commande.StatutCommande.*;
*/
public class Main {
    public static void main(String[] args) {
        Servicecommande serviceCommande = new Servicecommande();

        // Ajout d'une commande
        try {
            serviceCommande.ajouter(new commande(1, new Date(), 1, BigDecimal.valueOf(5.50), commande.StatutCommande.EnAttente, "ProduitA"));
            System.out.println("Ajout effectué !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }


        // Mise à jour d'une commande
        try {
            commande commandeToUpdate = new commande(1, new Date(), 5, BigDecimal.valueOf(700.75), commande.StatutCommande.Expediee, "ProduitB");
            serviceCommande.Modifier(commandeToUpdate);
            System.out.println("Mise à jour effectuée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
        }
/*

        // Suppression d'une commande
        try {
            int commandeIdToDelete = 1; // Remplacez par l'identifiant de la commande que vous souhaitez supprimer
            serviceCommande.supprimer(commandeIdToDelete);
            System.out.println("Commande supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
            e.printStackTrace();
        }
*/
       // Récupérer une liste de commandes
        try {
            List<commande> commandes = serviceCommande.recuperer();
            System.out.println("Liste des commandes récupérée !");
            for (commande cmd : commandes) {
                System.out.println(cmd);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des commandes : " + e.getMessage());
            e.printStackTrace();
        }
        //afficher la liste des commande
       /* int idCommande = 3;

        // Appelez la fonction afficherParID
        try {
            serviceCommande.afficher(idCommande);
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
        }
        */
   }
}


