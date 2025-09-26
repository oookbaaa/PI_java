
package com.example.benayedjavafx.entities;


import java.util.Date;

public class formation {
    private int id,duree;
    private String titre,description,lieu,formateur;
    private Date dd;
    private Date df;



    public formation(int id, int duree, String titre, String description, String lieu, String formateur, Date dd, Date df) {
        this.id = id;
        this.duree = duree;
        this.titre = titre;
        this.description = description;
        this.lieu = lieu;
        this.formateur = formateur;
        this.dd = dd;
        this.df = df;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getFormateur() {
        return formateur;
    }

    public void setFormateur(String formateur) {
        this.formateur = formateur;
    }

    public Date getDd() {
        return dd;
    }

    public void setDd(Date dd) {
        this.dd = dd;
    }

    public Date getDf() {
        return df;
    }

    public void setDf(Date df) {
        this.df = df;
    }


    @Override
    public String toString() {
        return "formation{" +
                "id=" + id +
                ", duree=" + duree +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", formateur='" + formateur + '\'' +
                ", dd=" + dd +
                ", df=" + df +
                '}';
    }




}


