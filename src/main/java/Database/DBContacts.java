package Database;

/**
 * @author Patrick Kell
 */

import Model.Contact;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The DBContacts Class which handles queries to the contacts table of the client_schedule database
 */
public class DBContacts {

    /**
     * GetAllContacts method which manages the retrieval of all contacts from the database
     *
     * @return A list of all contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Contact_ID, Contact_Name, Email FROM contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // creates a result set, two-dimensional list

            while (rs.next()) {
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact newCustomer = new Contact(contactID, contactName, email);
                contacts.add(newCustomer);
            }
        } catch (SQLException e) {
            // System.out.println("DBContacts.getAllContacts Error: " + e.getMessage());
        }

        return contacts;
    }
}