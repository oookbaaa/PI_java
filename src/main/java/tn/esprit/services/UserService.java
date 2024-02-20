package tn.esprit.services;


import tn.esprit.models.User;
import tn.esprit.utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService <User>{

    private Connection connection;

    public UserService(){
        connection = MyDatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {

        String req="INSERT INTO user (tel_user,nom_user,email_user,mdp_user,role_user,adresse_user,photo_user) VALUES ("+user.getTel()+",'"+user.getNom()+"','"+user.getEmail()+"','"+user.getMdp()+"','"+user.getRole()+"','"+user.getAdresse()+"','"+user.getPhoto()+"')";
        Statement st = connection.createStatement();
        st.executeUpdate(req);
        System.out.println("Ajoutée");
    }

    @Override
    public void modifier(User user) throws SQLException {
        String req = "UPDATE user SET nom_user = ?, mdp_user = ?, role_user = ?, adresse_user = ? , photo_user = ? , email_user = ? , tel_user = ? WHERE id_user = ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setString(1, user.getNom());
        ps.setString(2, user.getMdp());
        ps.setString(3, user.getRole());
        ps.setString(4, user.getAdresse());
        ps.setString(5, user.getPhoto());
        ps.setString(6, user.getEmail());
        ps.setInt(7, user.getTel());
        ps.setInt(8, user.getId());
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
            user.setPhoto(rs.getString("photo_user"));
            users.add(user);
        }
        return users;
    }

    // Method to authenticate a user based on username/email and password
    public User authenticateUser(String email, String mdp) {


            // Prepare SQL statement to retrieve user information based on username/email and password
            String query = "SELECT * FROM user WHERE email_user = ? AND mdp_user = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, mdp);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // User found, return a User object with user information
                        return new User(
                                resultSet.getInt("id_user"),
                                resultSet.getInt("tel_user"),
                                resultSet.getString("nom_user"),
                                resultSet.getString("email_user"),
                                resultSet.getString("mdp_user"),
                                resultSet.getString("role_user"),
                                resultSet.getString("adresse_user"),
                                resultSet.getString("photo_user")

                                // Add other user properties as needed
                        );
                    }
                }
            }
        catch (SQLException e) {
            e.printStackTrace(); // Handle database errors appropriately
        }
        // User not found or invalid credentials, return null
        return null;
    }

    public static User getCurrentUser() {

        // Logic to retrieve the currently logged-in user
        // This could involve retrieving the user from a session, database, or any other authentication mechanism
        // For demonstration, let's return a hardcoded user
        return new User(1,55420690, "okba", "okba@okba.tn", "123456","ADMIN","el ghazela",null);

    }






    public static boolean doesEmailExist(String email) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Get the database connection

            connection = MyDatabase.getInstance().getConnection();

            // Prepare the SQL statement
            String sql = "SELECT COUNT(*) AS count FROM user WHERE email_user = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            // Execute the query
            resultSet = statement.executeQuery();

            // Check if the email exists (count > 0)
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database resources
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

