package tn.esprit.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class commande {
    private int idCommande;
    private Date dateCommande;
    private int idClient;
    private BigDecimal montantTotal;
    private StatutCommande statutCommande;
    private String produitCommande;

    public commande() {
    }

    public commande(int idCommande, Date dateCommande, int idClient,
                    BigDecimal montantTotal, StatutCommande statutCommande,
                    String produitCommande) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.idClient = idClient;
        this.montantTotal = montantTotal;
        this.statutCommande = statutCommande;
        this.produitCommande = produitCommande;
    }

    @Override
    public String toString() {
        return "commande{" +
                "idCommande=" + idCommande +
                ", dateCommande=" + dateCommande +
                ", idClient=" + idClient +
                ", montantTotal=" + montantTotal +
                ", statutCommande=" + statutCommande +
                ", produitCommande='" + produitCommande + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        commande commande = (commande) o;
        return idCommande == commande.idCommande &&
                Objects.equals(dateCommande, commande.dateCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, dateCommande);
    }

    // Getters and setters for the attributes
    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public BigDecimal getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatutCommande getStatutCommande() {
        return statutCommande;
    }

    public void setStatutCommande(StatutCommande statutCommande) {
        this.statutCommande = statutCommande;
    }

    public String getProduitCommande() {
        return produitCommande;
    }

    public void setProduitCommande(String produitCommande) {
        this.produitCommande = produitCommande;
    }

    public int getIDclient() {
        return idClient;
    }

    public BigDecimal getMontantTotale() {
    return montantTotal;}

    public int getIDcommande() {
        return idCommande;
    }

    public void setIDcommande(int iDcommande) {
        this.idCommande=idCommande;
    }

    public void setMontantTotale(BigDecimal montantTotale) {
        this.montantTotal=montantTotale;
    }

    public void setIDclient(int iDclient) {
        this.idClient=iDclient;
    }

    // Enumeration for the statut of the command
    public enum StatutCommande {
        EnAttente,
        Expediee,
        Livree;


        public String nomAffiche() { switch (this) {
            case EnAttente:
                return "EnAttente";
            case Expediee:
                return "Expediee";
            case Livree:
                return "Livree";
            default:
                return "Statut inconnue";
        }
        }
    }

}

