package Database;

import Model.Country;
import Model.FLDivision;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Patrick Kell
 */
public class DBFLDivisions {
    public static ObservableList<FLDivision> getAllDivisions() {
        ObservableList<FLDivision> divisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM first_level_divisions";
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
            System.out.println("DBFLDivisions.getAllDivisions Error: " + e.getMessage());
        }

        return divisions;
    }

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
