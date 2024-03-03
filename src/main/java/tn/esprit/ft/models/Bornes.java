package tn.esprit.ft.models;

import java.sql.Blob;

public class Bornes {

    //private Emplacement emplacement;

    private int id;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    private String description,etat,adresse,emplacement;
    private byte[] filePath;
    private float latitude,longitude;
    public Bornes(int id, String description, String etat, String adresse, String emplacement, byte[] filePath,float latitude,float longitude) {
        this.id = id;
        this.description = description;
        this.etat = etat;
        this.adresse = adresse;
        this.emplacement = emplacement;
        this.filePath = filePath;
        this.longitude=longitude;
        this.latitude=latitude;
    }
    public Bornes(int id, String description, String etat, String adresse, String emplacement, byte[] filePath) {
        this.id = id;
        this.description = description;
        this.etat = etat;
        this.adresse = adresse;
        this.emplacement = emplacement;
        this.filePath = filePath;
    }


    public Bornes(String description, String etat, String adresse, String emplacement, byte[] filePath) {
        this.description = description;
        this.etat = etat;
        this.adresse = adresse;
        this.emplacement = emplacement;
        this.filePath = filePath;
    }

    /*public enum Emplacement {
        ENTREPRISES,
        CO_PROPRIETE,
        COLLECTIVITE,
        PROPRIETE_PRIVEE
    }*/



    public Bornes() {
        this.description = description;
        this.etat = etat;
        this.adresse = adresse;
        this.emplacement=emplacement;
        this.filePath = filePath;
    }
    public Bornes( String description, String etat, String adresse, String emplacement, byte[] filePath,float latitude,float longitude) {
        this.description = description;
        this.etat = etat;
        this.adresse = adresse;
        this.emplacement = emplacement;
        this.filePath = filePath;
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmplacement() {return emplacement;}

    public void setEmplacement(String emplacement) {this.emplacement = emplacement;}

    public void setFilePath(byte[] filePath) {this.filePath = filePath;}
    public byte[] getFilePath() {return filePath;}
/* public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }*/

    @Override
    public String toString() {
        return description+","+adresse;
    }


}
