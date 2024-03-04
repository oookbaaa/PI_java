package com.example.benayedjavafx.entities;


import javafx.application.Application;
import javafx.stage.Stage;

public class Quiz extends Application {
    private int id_quiz,Duree,id_formation,nb_questions;
    private String Titre;
    private formation formation;

    // Getter et Setter pour la propriété formation
    public formation getFormation() {
        return formation;
    }

    public void setFormation(formation formation) {
        this.formation = formation;
    }

    public Quiz(int id_quiz, int duree, int id_formation, int nb_questions, String titre) {
        this.id_quiz = id_quiz;
        Duree = duree;
        this.id_formation = id_formation;
        this.nb_questions = nb_questions;
        Titre = titre;
    }

    public int getId_quiz() {
        return id_quiz;
    }

    public int getDuree() {
        return Duree;
    }

    public int getId_formation() {
        return id_formation;
    }

    public int getNb_questions() {
        return nb_questions;
    }

    public String getTitre() {
        return Titre;
    }

    public void setId_quiz(int id_quiz) {
        this.id_quiz = id_quiz;
    }

    public void setDuree(int duree) {
        Duree = duree;
    }

    public void setId_formation(int id_formation) {
        this.id_formation = id_formation;
    }

    public void setNb_questions(int nb_questions) {
        this.nb_questions = nb_questions;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id_quiz=" + id_quiz +
                ", Duree=" + Duree +
                ", id_formation=" + id_formation +
                ", nb_questions=" + nb_questions +
                ", Titre='" + Titre + '\'' +
                '}';
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}

