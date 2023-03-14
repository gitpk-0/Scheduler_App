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
 * The DBUsers Class which handles queries to the users table of the client_schedule database
 */
public class DBUsers {

    public static String currentUserName; // publicly visible username of currently logged-in user
    public static int currentUserId; // public visible userID of the currently logged-in user

    /**
     * Login method for validating user login information
     *
     * @param username The value the user entered in the username TextField
     * @param password The value the user entered in the password TextField
     * @return A boolean value indicating the input password matches the username's password in the database
     * @throws SQLException Signals an SQLException has occurred
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

    /**
     * GetAllUserIDs method which manages the retrieval of all user ids from the database
     *
     * @return A list of integers corresponding to each user id
     */
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
            // System.out.println("DBUsers.getAllUserIDs Error: " + e.getMessage());
        }

        return users;
    }
}