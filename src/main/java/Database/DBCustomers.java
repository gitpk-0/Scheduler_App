package Database;

/**
 * @author Patrick Kell
 */

import Model.Customer;
import Utility.JDBC;
import Utility.TimeConversion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * The DBCustomers Class which handles queries to the customers table of the client_schedule database
 */
public class DBCustomers {

    /**
     * GetAllCustomers class which manages the retrieval of all customers from the database
     *
     * @return A list of all customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers " +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String country = rs.getString("Country");
                String division = rs.getString("Division");

                Customer newCustomer = new Customer(customerId, name, address, postal, phone, divisionId, country, division);
                customers.add(newCustomer);
            }
        } catch (SQLException e) {
            // System.out.println("DBCustomers.getAllCustomers Error: " + e.getMessage());
        }
        return customers;
    }

    /**
     * DeleteCustomer class which manages the deletion of a given customer from the database
     *
     * @param customer Customer to be deleted
     * @return A boolean value indicating the success of the deletion
     * @throws SQLException Signals an SQLException has occurred
     */
    public static boolean deleteCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected == 1;
    }

    /**
     * AddCustomer method which manages the creation of a new customer in the database
     *
     * @param id         The id of the customer
     * @param name       The name of the customer
     * @param phone      The phone number of the customer
     * @param address    The address of the customer
     * @param postal     The postal code of the customer
     * @param country    The country of the customer
     * @param divisionId The division id  of the customer
     * @throws SQLException Signals an SQLException has occurred
     */
    public static void addCustomer(int id, String name, String phone, String address,
                                   String postal, String country, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id); // Customer_ID
        ps.setString(2, name); // Customer_Name
        ps.setString(3, address); // Address
        ps.setString(4, postal); // Postal Code
        ps.setString(5, phone); // Phone
        ps.setTimestamp(6, TimeConversion.localToUTC(LocalDateTime.now())); // Create_Date
        ps.setString(7, DBUsers.currentUserName); // Created_By
        ps.setTimestamp(8, TimeConversion.localToUTC(LocalDateTime.now())); // Last_Update
        ps.setString(9, DBUsers.currentUserName); // Last_Updated_By
        ps.setInt(10, divisionId); // Division_ID
        ps.executeUpdate();
    }

    /**
     * UpdateCustomer method which manages the modification of an existing customer in the database
     *
     * @param id         The id of the customer
     * @param name       The name of the customer
     * @param phone      The phone number of the customer
     * @param address    The address of the customer
     * @param postal     The postal code of the customer
     * @param divisionId The division id of the customer
     * @throws SQLException Signals an SQLException has occurred
     */
    public static void updateCustomer(int id, String name, String phone, String address, String postal,
                                      int divisionId) throws SQLException {

        String sql = "UPDATE customers SET " +
                "Customer_Name = ?, " + // 1
                "Address = ?, " +
                "Postal_Code = ?, " + // 3
                "Phone = ?, " +
                "Last_Update = ?, " + // 5
                "Last_Updated_By = ?, " +
                "Division_ID = ? " + // 7
                "WHERE Customer_ID = ?";

        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, address);
        ps.setString(3, postal);
        ps.setString(4, phone);
        ps.setTimestamp(5, TimeConversion.localToUTC(LocalDateTime.now()));
        ps.setString(6, DBUsers.currentUserName);
        ps.setInt(7, divisionId);
        ps.setInt(8, id);
        ps.executeUpdate();
    }

    /**
     * GetCustomerByCountry method which manages the retrieval of customers given a country name from the database
     *
     * @param country_name Country to filter the customers by
     * @return A list of customer from the given country name
     */
    public static ObservableList<Customer> getCustomersByCountry(String country_name) {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM customers " +
                    "INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID " +
                    "INNER JOIN countries ON countries.Country_ID = first_level_divisions.Country_ID " +
                    "WHERE countries.Country = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, country_name);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                String country = rs.getString("Country");
                String division = rs.getString("Division");

                Customer customer = new Customer(customerId, name, address, postal, phone, divisionId, country, division);
                customers.add(customer);
            }
        } catch (SQLException e) {
            // System.out.println("DBCustomers.getCustomersByCountry Error: " + e.getMessage());
        }
        return customers;
    }
}