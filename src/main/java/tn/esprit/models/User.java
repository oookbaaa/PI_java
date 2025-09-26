package tn.esprit.models;

public class User {
    private int id,tel;
    private String nom;
    private String email;
    private String mdp;
    private String role;
    private String adresse;
    private Status status= Status.ACTIVE;
    private String photo;
    public static User Current_User;

    public User() {

    }
    public User(int id, int tel, String nom, String email, String mdp, String role, String adresse, Status status, String photo) {
        this.id = id;
        this.tel = tel;
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
        this.role = role;
        this.adresse = adresse;
        this.photo = photo;
        this.status = status;
    }

    public User(int tel, String nom, String email, String mdp, String role, String adresse,Status status, String photo) {
        this.tel = tel;
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
        this.role = role;
        this.adresse = adresse;
        this.photo = photo;
    }


    public User(int id, int tel, String nom, String email, String mdp, String adresse, String photo) {
        this.id = id;
        this.tel = tel;
        this.nom = nom;
        this.email = email;
        this.mdp = mdp;
        this.adresse = adresse;
        this.photo = photo;
    }

    public User(int id,int tel, String nom, String email, String adresse, String photo) {
        this.id = id;
        this.tel = tel;
        this.nom = nom;
        this.email = email;
        this.adresse = adresse;
        this.photo = photo;
    }

    public static User getCurrent_User() {
        return Current_User;
    }

    public static void setCurrent_User(User Current_User) {
        User.Current_User = Current_User;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
                ", status='" + status + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
