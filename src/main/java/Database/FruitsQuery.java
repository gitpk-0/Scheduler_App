package Database;

import Utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Patrick Kell
 */
public abstract class FruitsQuery {

    public static int insert(String fruitName, int colodId) throws SQLException {
        String sql = "INSERT INTO FRUITS (Fruit_Name, Color_ID) VALUES (?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName); // index of bind variable, value to assign to bind variable
        ps.setInt(2, colodId);
        int rowsAffected = ps.executeUpdate(); // returns number of rows affected after sql statement executed
        return rowsAffected;
    }
}
