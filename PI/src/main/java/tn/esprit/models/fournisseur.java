package tn.esprit.models;

public class fournisseur {
    private int id ;
    private String nom;
    private String prenom;
    private int telephone ;
    private String typeP;

    public fournisseur() {

    }


    public fournisseur(int id, String nom, String prenom, int telephone , String typeP) {
        this.id = id;
        this.nom = nom;
        this.telephone=telephone;
        this.prenom=prenom;
        this.typeP=typeP;


    }

    public fournisseur(String nom, String prenom, int telephone, String typeP) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.typeP = typeP;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getTypeP() {
        return typeP;
    }

    public void setTypeP(String typeP) {
        this.typeP = typeP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "fournisseur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone=" + telephone +
                ", typeP='" + typeP + '\'' +
                '}';
    }
}
