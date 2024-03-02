package tn.esprit.test;

import tn.esprit.models.Status;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.sql.SQLException;

import static tn.esprit.services.UserService.doesEmailExist;

public class Main {
    public static void main(String[] args) {
        UserService ps = new UserService();
        try {

            ps.modifier(new User(13,55420690, "okba", "okba@okba.tn", "123456789aA","EMPLOYE","el ghazela", Status.ACTIVE,"Users/BEDHIAFI/IdeaProjects/EVH/src/main/resources/img/voiture-electrique%20(1).png))"));
            //ps.supprimer(1);
             //System.out.println(ps.recuperer());
//          boolean p=  doesEmailExist("okba@okba.tn");
//            System.out.println(p);
            System.out.println("DONE !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}