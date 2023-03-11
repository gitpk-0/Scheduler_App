package Database;

import Model.Appointment;
import Model.Customer;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Patrick Kell
 */
public class DBCustomers {

    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

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
            System.out.println(e.getMessage());
        }

        return customers;
    }

    public static boolean deleteCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected == 1;
    }

    public static void addCustomer(int id, String name, String phone, String address,
                                   String postal, String country, int divisionId) throws SQLException {
        String sql = "INSERT INTO customers VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, id); // Customer_ID
        ps.setString(2, name); // Customer_Name
        ps.setString(3, address); // Address
        ps.setString(4, postal); // Postal Code
        ps.setString(5, phone); // Phone
        ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now())); // Create_Date
        ps.setString(7, DBUsers.currentUser); // Created_By
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); // Last_Update
        ps.setString(9, DBUsers.currentUser); // Last_Updated_By
        ps.setInt(10, divisionId); // Division_ID
        ps.executeUpdate();
    }
}
