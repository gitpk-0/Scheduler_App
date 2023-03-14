package Database;

/**
 * @author Patrick Kell
 */

import Model.FLDivision;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DBFLDivisions Class which handles queries to the first_level_divisions table of the client_schedule database
 */
public class DBFLDivisions {

    /**
     * GetAllDivisions method which manages the retrieval of all first level divisions from the database
     *
     * @return A list of first level divisions
     */
    public static ObservableList<FLDivision> getAllDivisions() {
        ObservableList<FLDivision> divisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // creates a result set, two-dimensional list

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                FLDivision newDivision = new FLDivision(divisionId, division, countryId);
                divisions.add(newDivision);
            }
        } catch (SQLException e) {
            // System.out.println("DBFLDivisions.getAllDivisions Error: " + e.getMessage());
        }

        return divisions;
    }

    /**
     * GetDivisionByName method which manages the retrieval of first level divisions by their division name from the
     * database
     *
     * @param division The name of the division
     * @return A FLDivision object from the given name
     */
    public static FLDivision getDivisionByName(String division) {

        FLDivision flDivision = null;

        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Division = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, division);
            ResultSet rs = ps.executeQuery(); // creates a result set, two-dimensional list

            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                flDivision = new FLDivision(divisionId, divisionName, countryId);
            }
        } catch (SQLException e) {
            System.out.println("DBFLDivisions.getDivisionByName Error: " + e.getMessage());
        }

        return flDivision;
    }
}