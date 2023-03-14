package Database;

/**
 * @author Patrick Kell
 */

import Model.Country;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DBCountries Class which handles queries to the countries table of the client_schedule database
 */
public class DBCountries {

    /**
     * GetAllCountries method which manages the retrieval of all countries from the database
     *
     * @return A list of all countries
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // creates a result set, two-dimensional list

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Country newCountry = new Country(countryId, country);
                countries.add(newCountry);
            }
        } catch (SQLException e) {
            // System.out.println("DBCountries.getAllCountries Error: " + e.getMessage());
        }

        return countries;
    }

    /**
     * GetCountryByName method which manages the retrieval of a country given a country name
     *
     * @param countryName The name of the country to be returned
     * @return The country with the given name
     */
    public static Country getCountryByName(String countryName) {
        Country country = null;

        try {
            String sql = "SELECT Country_ID, Country FROM countries WHERE Country = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery(); // creates a result set, two-dimensional list

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String country_name = rs.getString("Country");

                country = new Country(countryId, country_name);
            }
        } catch (SQLException e) {
            // System.out.println("DBCountries.getCountryByName Error: " + e.getMessage());
        }

        return country;
    }
}