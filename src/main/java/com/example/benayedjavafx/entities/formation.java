
package com.example.benayedjavafx.entities;


import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDate;
public class formation {
    private int id_formation,duree;
    private String titre,description,lieu,formateur;
    private Date dd;
    private Date df;
    private Quiz quiz;
    private LocalDate startDate;


    // Getter et Setter pour la propriété quiz
    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }



    public formation(int id_formation, int duree, String titre, String description, String lieu, String formateur, Date dd, Date df) {
        this.id_formation = id_formation;
        this.duree = duree;
        this.titre = titre;
        this.description = description;
        this.lieu = lieu;
        this.formateur = formateur;
        this.dd = dd;
        this.df = df;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public int getId_formation() {
        return id_formation;
    }

    public void setId(int id_formation) {
        this.id_formation = id_formation;
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
                "id_formation=" + id_formation +
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


