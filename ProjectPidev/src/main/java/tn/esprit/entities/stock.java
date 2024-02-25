package tn.esprit.entities;

import java.util.Objects;

public class stock {
    private int IDproduit;
    private String NomProduit;
    private int Quantite;
    private float PrixVente;
    private String Fournisseur;
    private Categorie categorie;

    public stock() {
        this.Quantite = 0;
    }

    public stock(int IDproduit, String NomProduit, int Quantite,
                 float PrixVente, String Fournisseur,
                 Categorie categorie) {
        this.IDproduit = IDproduit;
        this.NomProduit = NomProduit;
        this.Quantite = Quantite;
        this.PrixVente = PrixVente;
        this.Fournisseur = Fournisseur;
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "stock{" +
                "IDproduit=" + IDproduit +
                ", NomProduit='" + NomProduit + '\'' +
                ", Quantite=" + Quantite +
                ", PrixVente=" + PrixVente +
                ", Fournisseur='" + Fournisseur + '\'' +
                ", categorie=" + categorie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        stock stock = (stock) o;
        return IDproduit == stock.IDproduit &&
                Objects.equals(NomProduit, stock.NomProduit);
    }

    // Méthode hashCode() pour générer un code de hachage
    @Override
    public int hashCode() {
        return Objects.hash(IDproduit, NomProduit);
    }

    // Getters et setters pour les attributs
    public int getIDproduit() {
        return IDproduit;
    }

    public void setIDproduit(int IDproduit) {
        this.IDproduit = IDproduit;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public void setNomProduit(String NomProduit) {
        this.NomProduit = NomProduit;
    }

    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int Quantite) {
        this.Quantite = Quantite;
    }

    public double getPrixVente() {
        return PrixVente;
    }

    public void setPrixVente(float PrixVente) {
        this.PrixVente = PrixVente;
    }

    public String getFournisseur() {
        return Fournisseur;
    }

    public void setFournisseur(String Fournisseur) {
        this.Fournisseur = Fournisseur;
    }
    public Categorie getCategorie() {
        return categorie;
    }


    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

}