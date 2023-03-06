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
        ps.setString(1, fruitName); // index of the first bind variable "?", value to assign to bind variable
        ps.setInt(2, colodId); // index of the second bind variable "?", value to assign to bind variable
        int rowsAffected = ps.executeUpdate(); // returns number of rows affected after sql statement executed
        return rowsAffected;
    }

    public static int update(int fruitId, String fruitName) throws SQLException {
        String sql = "UPDATE FRUITS SET Fruit_Name = ? WHERE Fruit_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, fruitName);
        ps.setInt(2, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int fruitId) throws SQLException {
        String sql = "DELETE FROM FRUITS WHERE Fruit_Id = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, fruitId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
