package Database;

import Model.Contact;
import Model.Country;
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
public class DBCountries {

    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // creates a result set, two-dimensional list

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Country newCountry = new Country(countryId, country);
                countries.add(newCountry);
            }
        } catch (SQLException e) {
            System.out.println("DBCountries.getAllCountries Error: " + e.getMessage());
        }

        return countries;
    }

    public static Country getCountryByName(String countryName) {
        Country country = null;

        try {
            String sql = "SELECT * FROM countries WHERE Country = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery(); // creates a result set, two-dimensional list

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country_name = rs.getString("Country");

                country = new Country(countryId, country_name);
            }
        } catch (SQLException e) {
            System.out.println("DBCountries.getCountryByName Error: " + e.getMessage());
        }

        return country;
    }
}
