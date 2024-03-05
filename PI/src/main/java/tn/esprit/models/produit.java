package tn.esprit.models;

public class produit {
    private int id ;
    private String nom ;
    private  int prix ;
    private int qt ;

    private int like=0;
    public produit() {

    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public produit(int id, String nom, int prix, int qt, int like) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.qt = qt;
        this.like = like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }
    public produit(int id, String nom, int prix , int qt) {
        this.id = id;
        this.nom = nom;
        this.prix=prix;
        this.qt=qt;


    }

    @Override
    public String toString() {
        return "produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                ", qt=" + qt +
                '}';
    }
}
