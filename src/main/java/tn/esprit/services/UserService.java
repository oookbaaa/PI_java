package tn.esprit.services;



import tn.esprit.controllers.SmsSender;
import tn.esprit.models.Status;
import tn.esprit.models.User;
import tn.esprit.utils.MyDatabase;
import tn.esprit.utils.PasswordHasher;
import tn.esprit.utils.SessionManager;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        String req = "UPDATE user SET nom_user = ?,  adresse_user = ? , photo_user = ? , email_user = ? , tel_user = ?,status_user = ? WHERE id_user = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, user.getNom());
            ps.setString(2, user.getAdresse());
            ps.setString(3, user.getPhoto());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getTel());
            ps.setString(6, user.getStatus().name());
            ps.setInt(7, user.getId());

            System.out.println("Modifié");
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsAffected);
        } catch (SQLException e) {
            // Handle or log the exception
            System.err.println("Error modifying user: " + e.getMessage());
            throw e; // Rethrow the exception if necessary
        }
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
                                    String messageBody = "Welcome back, " + user.getNom() + ".";
                                //SmsSender.sendSms(String.valueOf(user.getTel()), messageBody);

                                return SessionManager.createSession(user);
                            } else {

                                String messageBody = "Your account has been blocked, please contact us for more information :" + "evh0hve@gmail.com .";
                                SmsSender.sendSms("55420690", messageBody);
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
                } catch (IOException e) {
                    throw new RuntimeException(e);
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

    public boolean checkEmailExists(String email) {
        connection = MyDatabase.getInstance().getConnection();
        boolean result = false;
        try {
            String req = "SELECT * FROM user WHERE email = ?";
            PreparedStatement st = connection.prepareStatement(req);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            result = rs.next();
            System.out.println("result : " + result);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return result;
    }

    public User getUserByEmail(String email) {
        connection = MyDatabase.getInstance().getConnection();
        String query = "SELECT * from user WHERE email_user = '" + email + "'";

        User t = null;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                t = new User(rs.getInt("id_user"), rs.getInt("tel_user"), rs.getString("nom_user"), rs.getString("email_user"),
                        rs.getString("mdp_user"), rs.getString("role_user"), rs.getString("adresse_user"), Status.valueOf(rs.getString("status_user").toUpperCase()),
                        rs.getString("photo_user"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return t;

    }
    public void changePassword(String mdp, String email) throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = PasswordHasher.hashPassword(mdp);
        String req5 = "UPDATE user SET mdp_user = ?  WHERE email_user = ?";
        PreparedStatement pst = connection.prepareStatement(req5);
        pst.setString(1, hashedPassword);
        pst.setString(2, email);
        int rowUpdated = pst.executeUpdate();
        if (rowUpdated > 0) {
            System.out.println("Mdp modifié");
        } else {
            System.out.println("ERR");
        }    }

    public User getUserById(int id){
        String sql = "SELECT * FROM users WHERE id_user = ?";
        try{
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,id);
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
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
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;}

    public List<User> searchUsers(String searchTerm) {
        List<User> searchResults = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE nom_user LIKE ? OR role_user LIKE ? AND photo_user IS NOT NULL";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            String likeTerm = "%" + searchTerm + "%";
            preparedStatement.setString(1, likeTerm);
            preparedStatement.setString(2, likeTerm);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
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

                searchResults.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }


    public boolean isPasswordConfirmed(int idUser,String password){
        String qry = "SELECT mdp_user FROM user WHERE  id_user = ? AND mdp_user = ?";
        try{
            PreparedStatement stm = connection.prepareStatement(qry);
            stm.setInt(1,idUser);
            stm.setString(2,password);
            ResultSet rs = stm.executeQuery();
            if(rs.next())
                return true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}

