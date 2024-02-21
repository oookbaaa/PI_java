package tn.esprit.services;


import tn.esprit.models.Status;
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


    public User authenticateUser(String email, String mdp) {



            String query = "SELECT * FROM user WHERE email_user = ? AND mdp_user = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, mdp);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String statusString = resultSet.getString(
                                "status_user");
                        // Convert the string to the Status enum
                        Status status = Status.fromString(statusString);

                        return new User(
                                resultSet.getInt("id_user"),
                                resultSet.getInt("tel_user"),
                                resultSet.getString("nom_user"),
                                resultSet.getString("email_user"),
                                resultSet.getString("mdp_user"),
                                resultSet.getString("role_user"),
                                resultSet.getString("adresse_user"),
                                status,
// Now you have the status as a string, you can convert it to the Status enum as needed

                        resultSet.getString("photo_user")

                                // Add other user properties as needed
                        );
                    }
                }
            }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User getCurrentUser() {

        return new User(1,55420690, "okba", "okba@okba.tn", "123456","ADMIN","el ghazela",Status.ACTIVE,null);

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

