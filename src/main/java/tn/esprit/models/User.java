package tn.esprit.models;

public class User {
    private int id,tel;
    private String nom;
    private String email;
    private String mdp;
    private String role;
    private String adresse;
    private String photo;

    public User() {

    }
    public User(int id, int tel, String nom, String email, String mdp, String role, String adresse, String photo) {
        this.id = id;
        this.tel = tel;
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
        this.role = role;
        this.adresse = adresse;
        this.photo = photo;
    }

    public User(int tel, String nom, String email, String mdp, String role, String adresse, String photo) {
        this.tel = tel;
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
        this.role = role;
        this.adresse = adresse;
        this.photo = photo;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", tel=" + tel +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", role='" + role + '\'' +
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
