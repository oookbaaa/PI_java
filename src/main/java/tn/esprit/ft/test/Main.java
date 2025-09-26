package tn.esprit.ft.test;

import tn.esprit.ft.models.Bornes;
import tn.esprit.ft.models.Interventions;
import tn.esprit.ft.services.ServiceBorne;
import tn.esprit.ft.services.ServiceIntervention;
import tn.esprit.ft.utils.MyDatabase;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
//import tn.esprit.ft.models.Bornes.Emplacement;

 
public class Main {
    public static void main(String[] args) {
        MyDatabase db = MyDatabase.getInstance();
        /*MyDatabase db2 = MyDatabase.getInstance();*/

        System.out.println(db);
        /*System.out.println(db2);*/


        System.out.println("test before error ");

      /*  Bornes b1 = new Bornes(1, "La maintenance régulière des bornes électriques est essentielle pour assurer leur bon fonctionnement continu", "active", "tunis", "entreprise");
        Bornes b2 = new Bornes(2, "L'installation d'une borne électrique implique la mise en place physique de l'équipement sur site", "hors service", "kasserine","co-propriété");
        Bornes b3 = new Bornes(3, "Ce processus implique la déconnexion de l'équipement électrique, le démontage de la borne et de ses composants", "en maintenance", "nabeul","collectivité");
        Bornes b4 = new Bornes(4, "L'installation d'une borne électrique implique la mise en place physique de l'équipement sur site", "en maintenance", "bizerte","propriété privée");
        Bornes b4 = new Bornes(4, "borne num4", "active", "emplaçement 4");     */


        ServiceBorne services = new ServiceBorne();

        //Enumération
       /* ServiceBorne service1 = new ServiceBorne();
        service1.setEmplacement(Emplacement.ENTREPRISES);
        ServiceBorne service2 = new ServiceBorne();
        service2.setEmplacement(Emplacement.CO_PROPRIETE);*/

        // ajouter les bornes


       /* try {
            services.ajouter(b1);
            System.out.println("test after error ");

            services.ajouter(b2);
            services.ajouter(b3);
            services.ajouter(b4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }   */


        //Affichage

       /* try {
            System.out.println(services.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        // modification
       /* try {
            services.modifier(b4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //supprimer

       /* try {
            services.supprimer(2);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

    }
        // assuming you have a LocalDate object
        /*LocalDate dateIntervention = LocalDate.of(2024, 2, 10);
        // Convert LocalDate to Date
        Date date = Date.valueOf(dateIntervention);
        // Create java.sql.Date objects for each intervention date
        Date date1 = Date.valueOf("2024-12-01");
        Date date2 = Date.valueOf("2023-11-10");
        Date date3 = Date.valueOf("2022-12-25");
        Date date4 = Date.valueOf("2023-3-5");


        // Create Interventions objects with the specified dates

        Interventions i1 = new Interventions(1, "maintenance", date1);
        Interventions i2 = new Interventions(2, "désinstallation", date2);
        Interventions i3 = new Interventions(3, "désinstallation", date3);
        Interventions i4 = new Interventions(4, "maintenance", date4);

        ServiceIntervention services = new ServiceIntervention();*/

        // ajouter les interventions


        /*try {
            services.ajouter(i1);
            System.out.println("test after error ");

            services.ajouter(i2);
            services.ajouter(i3);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        //Affichage

        /*try {
            System.out.println(services.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/


        // modification
       /* try {
            services.modifier(i2);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

        //supprimer

        /*try {
            services.supprimer(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    }*/ }


