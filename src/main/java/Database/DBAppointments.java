package Database;

/**
 * @author Patrick Kell
 */

import Model.Appointment;
import Model.Customer;
import Utility.JDBC;
import Utility.TimeConversion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The DBAppointments Class which handles queries to the appointments table of the client_schedule database
 * <p>
 * FUTURE ENHANCEMENT:  (1) Pass array or list objects to the methods to reduce parameter size. Parse through the
 * array/list inside the method. (2) Make the creation of appointment objects more efficient. Perhaps a warehouse class
 * would be useful.
 */
public class DBAppointments {

    /**
     * AddAppointment method which manages the creation of new appointment in the database
     *
     * @param apptId     The id of the appointment
     * @param title      The title of the appointment
     * @param desc       The description of the appointment
     * @param loca       The location of the appointment
     * @param type       The type of the appointment
     * @param start      The start datetime of the appointment
     * @param end        The end datetime of the appointment
     * @param customerId The customer id of the appointment
     * @param userId     The user id of the appointment
     * @param contactId  The contact id of the appointment
     * @throws SQLException Signals an SQLException has occurred
     */
    public static void addAppointment(int apptId, String title, String desc, String loca, String type,
                                      LocalDateTime start, LocalDateTime end, int customerId, int userId,
                                      int contactId) throws SQLException {
        String sql = "INSERT INTO appointments VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"; // insert statement
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // prepare the query
        // assigning values to the bind variables
        ps.setInt(1, apptId); // Appointment_ID
        ps.setString(2, title); // Title
        ps.setString(3, desc); // Description
        ps.setString(4, loca); // Location
        ps.setString(5, type); // Type
        ps.setTimestamp(6, TimeConversion.localToUTC(start)); // Start
        ps.setTimestamp(7, TimeConversion.localToUTC(end)); // End
        ps.setTimestamp(8, TimeConversion.localToUTC(LocalDateTime.now())); // Create_Date
        ps.setString(9, DBUsers.currentUserName); // Created_By
        ps.setTimestamp(10, TimeConversion.localToUTC(LocalDateTime.now())); // Last_Update
        ps.setString(11, DBUsers.currentUserName); // Last_Updated_By
        ps.setInt(12, customerId); // Customer_ID
        ps.setInt(13, userId); // User_ID
        ps.setInt(14, contactId); // Contact_ID
        ps.executeUpdate(); // execute the query
    }

    /**
     * Update appointment method which manages the modification of an existing appointment
     *
     * @param id     The id of the appointment
     * @param title  The title of the appointment
     * @param desc   The description of the appointment
     * @param loca   The location of the appointment
     * @param type   The type of the appointment
     * @param start  The start datetime of the appointment
     * @param end    The end datetime of the appointment
     * @param custId The customer id of the appointment
     * @param uId    The user id of the appointment
     * @param contId The contact id of the appointment
     * @throws SQLException Signals an SQLException has occurred
     */
    public static void updateAppointment(int id, String title, String desc, String loca, String type,
                                         LocalDateTime start, LocalDateTime end, int custId, int uId,
                                         int contId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, " + // update statement
                "Description = ?, " + // 2
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " + // 5
                "End = ?, " +
                "Last_Update = ?, " +
                "Last_Updated_By = ?, " +
                "Customer_ID = ?, " + // 9
                "User_ID = ?, " +
                "Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // prepare the query
        // assigning values to the bind variables
        ps.setString(1, title); // Title
        ps.setString(2, desc); // Description
        ps.setString(3, loca); // Location
        ps.setString(4, type); // Type
        ps.setTimestamp(5, TimeConversion.localToUTC(start)); // Start
        ps.setTimestamp(6, TimeConversion.localToUTC(end)); // End
        ps.setTimestamp(7, TimeConversion.localToUTC(LocalDateTime.now())); // Last_Update
        ps.setString(8, DBUsers.currentUserName); // Last_Updated_By
        ps.setInt(9, custId); // Customer_ID
        ps.setInt(10, uId); // User_ID
        ps.setInt(11, contId); // Contact_ID
        ps.setInt(12, id); // Appointment_ID
        ps.executeUpdate(); // execute the query
    }

    /**
     * GetAppointments method which manages the retrieval of appointments from the database
     * <p>
     * This method can return all appointments, appointments within a month, or appointments within a week
     *
     * @param filter The amount of appointments the user wants to see (all, month, week)
     * @return All appointments or filtered version of all appointments
     */
    public static ObservableList<Appointment> getAppointments(String filter) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID"; // select statement

        try {
            switch (filter) {
                case "month":
                    sql = sql + " WHERE appointments.Start <= NOW() + INTERVAL 1 Month AND " +
                            "appointments.Start > NOW()"; // appointments within the next month
                    break;
                case "week":
                    sql = sql + " WHERE appointments.Start <= NOW() + INTERVAL 1 Week AND " +
                            "appointments.Start > NOW()"; // appointments within the next week
                    break;
                default:
                    sql = sql; // all appointments
                    break;
            }
            PreparedStatement ps = JDBC.connection.prepareStatement(sql); // prepare the query
            ResultSet rs = ps.executeQuery(); // execute the query

            while (rs.next()) {
                // each "get" call refers to the column name in the database
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String loca = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = TimeConversion.utcToLocal(rs.getTimestamp("Start")); // convert UTC (database time) to user local time
                LocalDateTime end = TimeConversion.utcToLocal(rs.getTimestamp("End")); // convert UTC (database time) to user local time
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppt = new Appointment(apptId, title, desc, loca, type, start, end, customerId, userId,
                        contactId, contactName); // create a new appointment object for the appointments table view to show
                appointments.add(newAppt); // list of appointments
            }
        } catch (SQLException e) {
            // System.out.println("DBAppointments.getAppointments Error: " + e.getMessage());
        }
        return appointments;
    }

    /**
     * DeleteAppointment class which manages the deletion of appointments from the database
     *
     * @param appt Appointment to be deleted
     * @return A boolean value indicating the success of the deletion
     * @throws SQLException Signals an SQLException has occurred
     */
    public static boolean deleteAppointment(Appointment appt) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?"; // delete statement
        PreparedStatement ps = JDBC.connection.prepareStatement(sql); // prepare query
        ps.setInt(1, appt.getApptId()); // assign the appointment id to the bind variable
        int rowsAffected = ps.executeUpdate(); // execute the query
        return rowsAffected == 1; // returns 1 if the deletion was successful, all other numbers indicate an error
    }

    /**
     * GetApptCountByCustomer (Get Appointment Count by Customer) method which manages the retrieval of
     * a count of appointments a given customer is currently scheduled for from the database.
     *
     * @param customer Customer to count appointments for
     * @return A count of appointments for the given customer
     * @throws SQLException Signals an SQLException has occurred
     */
    public static int getApptCountByCustomer(Customer customer) throws SQLException {
        String sql = "SELECT COUNT(Appointment_ID) AS apptCount FROM appointments WHERE Customer_ID = ?"; // select statement
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        ResultSet rs = ps.executeQuery();

        int totalAppts = 0;
        while (rs.next()) {
            totalAppts = rs.getInt("apptCount");
        }

        return totalAppts;
    }

    /**
     * GetApptTimesByCustomer (Get Appointment Times by Customer) method which manages the retrieval of appointment
     * times for a given customer from the database.
     *
     * @param customer Customer to find appointment times for
     * @param apptId   Appointment ID to exclude from the search (or null)
     * @return A list of start and end LocalDateTimes
     * @throws SQLException Signals an SQLException has occurred
     */
    public static ArrayList<LocalDateTime> getApptTimesByCustomer(Customer customer, String apptId) throws SQLException {
        ArrayList<LocalDateTime> apptTimes = new ArrayList<>();
        String sql = (apptId == null) ? "SELECT * FROM appointments WHERE Customer_ID = ?" :
                "SELECT * FROM appointments WHERE Customer_ID = ? AND Appointment_ID != ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        if (apptId != null) { // exclude the following appointment id
            ps.setInt(2, Integer.parseInt(apptId));
        }
        ResultSet rs = ps.executeQuery();

        int index = 0;
        while (rs.next()) {
            LocalDateTime start = TimeConversion.utcToLocal(rs.getTimestamp("Start"));
            LocalDateTime end = TimeConversion.utcToLocal(rs.getTimestamp("End"));

            apptTimes.add(index, start);
            apptTimes.add(index + 1, end);
            index += 2;
        }

        return apptTimes;
    }

    /**
     * GetAppointmentByContact method which manages the retrieval of appointments for a given contact from the database
     *
     * @param contact Contact to find appointments for
     * @return List of appointments for the given contact
     */
    public static ObservableList<Appointment> getAppointmentsByContact(String contact) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        int contact_id = Integer.valueOf(contact.substring(0, 1));
        String sql = "SELECT * FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE appointments.Contact_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contact_id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String loca = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = TimeConversion.utcToLocal(rs.getTimestamp("Start"));
                LocalDateTime end = TimeConversion.utcToLocal(rs.getTimestamp("End"));
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("appointments.Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment appt = new Appointment(apptId, title, desc, loca, type, start, end, customerId, userId,
                        contactId, contactName);
                appointments.add(appt);
            }

        } catch (SQLException e) {
            // System.out.println("DBAppointments.getAppointmentsByContact Error: " + e.getMessage());
        }

        return appointments;
    }

    /**
     * GetAllAppointmentTypes method which manages the retrieval of all distinct appointment types from the database
     *
     * @return A list of distinct appointment types
     */
    public static ObservableList<String> getAllAppointmentTypes() {
        ObservableList<String> types = FXCollections.observableArrayList();
        String sql = "SELECT DISTINCT Type FROM appointments";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("Type");
                types.add(type);
            }
        } catch (SQLException e) {
            // System.out.println("DBAppointments.getAllAppointmentTypes Error: " + e.getMessage());
        }

        return types;
    }

    /**
     * GetCountApptsByTypeAndMonth (Get count of appointments by type and month) method which manages the retrieval of
     * a count of appointments by a given type and a given month from the database.
     *
     * @param type  Type of appointment to count
     * @param month Month of appointment to count
     * @return an integer (or zero) of how many appointments by month and type exist
     */
    public static int getCountApptsByTypeAndMonth(String type, String month) {
        String sql = "SELECT COUNT(Appointment_ID) as total FROM appointments WHERE Type = ? " +
                "AND MONTHNAME(Start) = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, type);
            ps.setString(2, month);
            ResultSet rs = ps.executeQuery();

            rs.next();
            return rs.getInt("total");

        } catch (SQLException e) {
            // System.out.println("DBAppointments.getCountApptsByTypeAndMonth Error: " + e.getMessage());
        }

        return 0;
    }

    /**
     * HasAppointmentSoon method which manages the retrieval of an appointment that starts within 15 minutes of the
     * user logging in (if one exists) from the database
     *
     * @param userId User id to search for associated appointments
     * @return An Appointment (or null)
     */
    public static Appointment hasAppointmentSoon(int userId) {
        Appointment appointment = null;
        String sql = "SELECT * FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE appointments.User_ID = ? ";

        ArrayList<Appointment> appointments = new ArrayList<>(); // for further filtering

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String loca = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = TimeConversion.utcToLocal(rs.getTimestamp("Start"));
                LocalDateTime end = TimeConversion.utcToLocal(rs.getTimestamp("End"));
                int customerId = rs.getInt("Customer_ID");
                int uId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment foundAppt = new Appointment(apptId, title, desc, loca, type, start, end, customerId, uId,
                        contactId, contactName);
                appointments.add(foundAppt);
            }
        } catch (SQLException e) {
            // System.out.println("DBAppointments.hasAppointmentSoon Error: " + e.getMessage());
        }

        for (Appointment appt : appointments) {
            // if user has an appointment within the next 15 minutes return the appointment
            if (appt.getUserId() == userId) {
                LocalDateTime apptStart = appt.getStart(); // appointment start time
                LocalDateTime now = LocalDateTime.now(); // current time
                LocalDateTime plus15 = now.plusMinutes(15); // current time plus 15 minutes

                if ((apptStart.isBefore(plus15) || apptStart.isEqual(plus15)) && apptStart.isAfter(now)) {
                    System.out.println("made it here");
                    appointment = appt;
                    break;
                }
            }
        }

        return appointment;
    }
}