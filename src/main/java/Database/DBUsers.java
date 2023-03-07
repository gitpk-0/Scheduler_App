package Database;

import Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Patrick Kell
 */
public class DBUsers {

    public static boolean login(String username, String password) throws SQLException {
        String sql = "SELECT User_Name, Password FROM users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();



        return true;
    }

}
