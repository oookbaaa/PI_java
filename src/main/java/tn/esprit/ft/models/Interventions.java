package tn.esprit.ft.models;

import java.sql.Blob;
import java.time.LocalDate;
import java.util.Date;

public class Interventions {
    private int id_int;
    private int id_borne, id_emp;
    private String type;
    private Date date;
    private byte[] imagePath ;


    public int getId_int() {
        return id_int;
    }

    public void setId_int(int id_int) {
        this.id_int = id_int;
    }

    public int getId_borne() {
        return id_borne;
    }

    public void setId_borne(int id_borne) {
        this.id_borne = id_borne;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Interventions() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getImagePath() {
        return imagePath;
    }

    public void setImagePath(byte[] imagePath) {
        this.imagePath = imagePath;
    }

    public Interventions(int id_borne, int id_emp, String type, Date date, byte[] imagePath) {
        this.id_borne = id_borne;
        this.id_emp = id_emp;
        this.type = type;
        this.date = date;
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Interventions{" +
                "id_int=" + id_int +
                ", id_borne=" + id_borne +
                ", id_emp=" + id_emp +
                ", type='" + type + '\'' +
                ", date=" + date +
                '}';
    }

    public Interventions(int id_int, int id_borne, int id_emp, String type, Date date, byte[] imagePath) {
        this.id_int = id_int;
        this.id_borne = id_borne;
        this.id_emp = id_emp;
        this.type = type;
        this.date = date;
        this.imagePath = imagePath;
    }
}