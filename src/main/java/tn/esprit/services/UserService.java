package tn.esprit.services;


import tn.esprit.models.Status;
import tn.esprit.models.User;
import tn.esprit.utils.MyDatabase;
import tn.esprit.utils.PasswordHasher;
import tn.esprit.utils.SessionManager;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService <User>{

    private Connection connection;
public static boolean blocked=false;
    public UserService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {

        String req="INSERT INTO user (tel_user,nom_user,email_user,mdp_user,role_user,adresse_user,status_user,photo_user) VALUES ("+user.getTel()+",'"+user.getNom()+"','"+user.getEmail()+"','"+user.getMdp()+"','"+user.getRole()+"','"+user.getAdresse()+"','"+user.getStatus()+"','"+user.getPhoto()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
        System.out.println("Ajoutée");
    }

    @Override
    public void modifier(User user) throws SQLException {
        String req = "UPDATE user SET nom_user = ?, mdp_user = ?, role_user = ?, adresse_user = ? , photo_user = ? , email_user = ? , tel_user = ?,status_user = ? WHERE id_user = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, user.getNom());
        ps.setString(2, user.getMdp());
        ps.setString(3, user.getRole());
        ps.setString(4, user.getAdresse());
        ps.setString(5, user.getPhoto());
        ps.setString(6, user.getEmail());
        ps.setInt(7, user.getTel());
        ps.setString(8, user.getStatus().name());
        ps.setInt(9, user.getId());
        ps.executeUpdate();
        System.out.println("Modifié");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM user WHERE id_user = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, id);
        ps.executeUpdate();
        System.out.println("Supprimé");
    }

    @Override
    public List<User> recuperer() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user";
        Statement st = connection.createStatement();
        ResultSet rs =  st.executeQuery(req);

        while (rs.next()){
            User user = new User();
            user.setId(rs.getInt("id_user"));
            user.setTel(rs.getInt("tel_user"));
            user.setNom(rs.getString("nom_user"));
            user.setEmail(rs.getString("email_user"));
            user.setMdp(rs.getString("mdp_user"));
            user.setRole(rs.getString("role_user"));
            user.setAdresse(rs.getString("adresse_user"));
            String statusString = rs.getString("status_user");
                Status status = Status.fromString(statusString);
                user.setStatus(status);
            user.setPhoto(rs.getString("photo_user"));
            users.add(user);
        }
        return users;
    }


//    public User authenticateUser(String email, String mdp) {
//
//
//
//            String query = "SELECT * FROM user WHERE email_user = ? AND mdp_user = ?";
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, email);
//                statement.setString(2, mdp);
//
//                try (ResultSet resultSet = statement.executeQuery()) {
//                    if (resultSet.next()) {
//                        String statusString = resultSet.getString(
//                                "status_user");
//                        // Convert the string to the Status enum
//                        Status status = Status.fromString(statusString);
//
//                        return new User(
//                                resultSet.getInt("id_user"),
//                                resultSet.getInt("tel_user"),
//                                resultSet.getString("nom_user"),
//                                resultSet.getString("email_user"),
//                                resultSet.getString("mdp_user"),
//                                resultSet.getString("role_user"),
//                                resultSet.getString("adresse_user"),
//                                status,
//// Now you have the status as a string, you can convert it to the Status enum as needed
//
//                        resultSet.getString("photo_user")
//
//                                // Add other user properties as needed
//                        );
//                    }
//                }
//            }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public String authenticateUser(String email, String password) {
        try {
            // Hash the password
            String hashedPassword = PasswordHasher.hashPassword(password);

            // Prepare the SQL query
            String query = "SELECT * FROM user WHERE email_user = ?";

            // Create a PreparedStatement
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);

                // Execute the query
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve the hashed password and user status from the database
                        String storedHashedPassword = resultSet.getString("mdp_user");
                        Status userStatus = Status.fromString(resultSet.getString("status_user"));

                        // Compare the hashed passwords and check user status
                        if (hashedPassword.equals(storedHashedPassword)) {
                            // Passwords match, check user status
                            if (userStatus == Status.ACTIVE) {
                                // User is active, create session
                                User user = new User(
                                        resultSet.getInt("id_user"),
                                        resultSet.getInt("tel_user"),
                                        resultSet.getString("nom_user"),
                                        resultSet.getString("email_user"),
                                        resultSet.getString("mdp_user"),
                                        resultSet.getString("role_user"),
                                        resultSet.getString("adresse_user"),
                                        Status.fromString(resultSet.getString("status_user")),
                                        resultSet.getString("photo_user")
                                );
                                return SessionManager.createSession(user);
                            } else {
                                blocked=true;
                            }
                        } else {
                            // Passwords don't match
                            System.out.println("Invalid email or password.");
                        }
                    } else {
                        // No user found with the given email
                        System.out.println("User not found.");
                    }
                }
            }
        } catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserFromSession(String sessionId) {
        System.out.println(sessionId);

        return SessionManager.getSession(sessionId);
    }








    public static boolean doesEmailExist(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {


            connection = MyDatabase.getInstance().getConnection();


            String sql = "SELECT COUNT(*) AS count FROM user WHERE email_user = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);


            resultSet = statement.executeQuery();


            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

