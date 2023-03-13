package Database;

/**
 * @author Patrick Kell
 */


import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Database utility class for managing the communication between the application and the
 * users table of the client_schedule database
 */
public class DBUsers {

    public static String currentUserName;
    public static int currentUserId;

    /**
     * Method for validating user login information
     *
     * @param username the value the user entered in the username TextField
     * @param password the value the user entered in the password TextField
     * @return if the input password matches the username's password in the database
     * @throws SQLException Signals an SQLException has occurred if an SQL error occurs
     */
    public static boolean login(String username, String password) throws SQLException {
        String sql = "SELECT User_ID, User_Name, Password FROM users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            currentUserId = rs.getInt("User_ID");
            currentUserName = rs.getString("User_Name");
            String storedPassword = rs.getString("Password");
            return password.equals(storedPassword);
        }

        return false;
    }

    public static ObservableList<Integer> getAllUserIDs() {
        ObservableList<Integer> users = FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID FROM users";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                users.add(userID);
            }
        } catch (SQLException e) {
            System.out.println("DBUsers.getAllUserIDs Error: " + e.getMessage());
        }

        return users;
    }

}
