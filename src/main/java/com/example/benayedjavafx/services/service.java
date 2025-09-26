package com.example.benayedjavafx.services;


import com.example.benayedjavafx.entities.formation;
import com.example.benayedjavafx.util.MyDataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Properties;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class service implements Iservice<formation> {

    private Connection connection;

    public service() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(formation formation) {

        try {

            String req = "INSERT INTO formation(duree, titre, description, lieu, formateur, Dd, Df) " +
                    "VALUES ('" + formation.getDuree() + "','" + formation.getTitre() + "','" + formation.getDescription() + "'," +
                    "'" + formation.getLieu() + "','" + formation.getFormateur() + "','" + formation.getDd() + "','" + formation.getDf() + "')";

            System.out.println(req);
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("formation created");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public formation Modifier(formation formation) throws SQLException {
        try {
            String req = "UPDATE `formation` SET "
                    + "`lieu`=?, `duree`=?, `dd`=?, `df`=?, `description`=?, `titre`=?, `formateur`=? WHERE id_formation =" + formation.getId_formation();
            PreparedStatement pst = connection.prepareStatement(req);
            int i = 0;
            pst.setString(++i, formation.getLieu());
            pst.setInt(++i, formation.getDuree());
            pst.setDate(++i, new java.sql.Date(formation.getDd().getTime()));
            pst.setDate(++i, new java.sql.Date(formation.getDf().getTime()));
            pst.setString(++i, formation.getDescription());
            pst.setString(++i, formation.getTitre());
            pst.setString(++i, formation.getFormateur());

            // Execute the update
            int rowsAffected = pst.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                // Return the modified formation object
                return formation;
            } else {
                // Handle the case when the update didn't modify any rows (e.g., formation with the given ID not found)
                System.err.println("No rows were updated for formation with ID: " + formation.getId_formation());
                return null; // Or throw an exception, depending on your requirements
            }

        } catch (SQLException ee) {
            System.err.println(ee.getMessage());
            throw ee; // Rethrow the exception to propagate it to the caller
        }
    }


    @Override
    public boolean supprimer(int id_formation) throws SQLException {
        // Logique pour supprimer la formation avec l'ID spécifié
        try {
            String req = "DELETE FROM `formation` "
                    + "WHERE id_formation=" + id_formation
                    + ";";
            System.out.println(req);
            Statement s = connection.createStatement();

            return s.executeUpdate(req) > 0;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return false;
    }


    @Override
    public List<formation> recuperer() throws SQLException {
        ArrayList<formation> list = new ArrayList<>();


        String requete = "SELECT * FROM formation";
        PreparedStatement pst = connection.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            list.add(new formation(rs.getInt("id_formation"),
                    rs.getInt("duree"),
                    rs.getString("titre")
                    , rs.getString("description")
                    , rs.getString("lieu")
                    , rs.getString("formateur"),
                    rs.getDate("dd"),
                    rs.getDate("df")
            ));
        }


        System.out.println(list);
        return list;
    }


    public ObservableList<formation> chercherFormation(String chaine) {
        String sql;
        ObservableList<formation> myList = FXCollections.observableArrayList();



             try {
                System.out.println("Recherche par titre: " + chaine);
                sql = "SELECT * FROM formation WHERE titre LIKE ? ORDER BY id_formation";
                PreparedStatement stee = connection.prepareStatement(sql);
                String ch = "%" + chaine + "%";
                stee.setString(1, ch);

                ResultSet rs = stee.executeQuery();

                while (rs.next()) {
                    formation f = new formation(
                            rs.getInt("id_formation"),
                            rs.getInt("duree"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getString("lieu"),
                            rs.getString("formateur"),
                            rs.getDate("dd"),
                            rs.getDate("df")
                    );

                    myList.add(f);
                    System.out.println("Formation trouvée par titre ! ");
                }

        } catch (SQLException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
        }

        return myList;
    }
    public void genererPDF(formation formation, String cheminFichier) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(new File(cheminFichier)));

            document.open();
            document.add(new Paragraph("ID Formation: " + formation.getId_formation()));
            document.add(new Paragraph("Titre: " + formation.getTitre()));
            document.add(new Paragraph("Description: " + formation.getDescription()));
            document.add(new Paragraph("Lieu: " + formation.getLieu()));
            document.add(new Paragraph("Formateur: " + formation.getFormateur()));
            document.add(new Paragraph("Date Début: " + formation.getDd()));
            document.add(new Paragraph("Date Fin: " + formation.getDf()));
            document.add(new Paragraph("Durée: " + formation.getDuree() + " heures"));
            document.close();

            System.out.println("PDF généré avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String userName = "azizbenayed179@gmail.com";
        //Your gmail password
        String password = "357894612";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }

        });

        //Prepare email message
        Message message = prepareMessage(session, userName, recepient);

        //Send mail
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Réclamation [Update]");
            String htmlCode = "<h1> Reclamation </h1> <br/> <h2><b>User </b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
        }
        return null;
    }

}






