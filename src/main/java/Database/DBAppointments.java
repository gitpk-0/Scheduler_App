package Database;

/**
 * @author Patrick Kell
 */

import Model.Appointment;
import Model.Customer;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 */
public class DBAppointments {

    /**
     * @param filter
     * @return
     */
    public static ObservableList<Appointment> getAppointments(String filter) {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";

        try {
            switch (filter) {
                case "month":
                    sql = sql + " WHERE appointments.Start <= NOW() + INTERVAL 1 Month AND " +
                            "appointments.Start > NOW()";
                    break;
                case "week":
                    sql = sql + " WHERE appointments.Start <= NOW() + INTERVAL 1 Week AND " +
                            "appointments.Start > NOW()";
                    break;
                default:
                    sql = sql;
                    break;
            }
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String loca = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment newAppt = new Appointment(apptId, title, desc, loca, type, start, end, customerId, userId,
                        contactId, contactName);
                appointments.add(newAppt);
            }
        } catch (SQLException e) {
            System.out.println("DBAppointments.getAppointments Error: " + e.getMessage());
        }

        return appointments;
    }


    /**
     * @param appt
     * @return
     * @throws SQLException
     */
    public static boolean deleteAppointment(Appointment appt) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appt.getApptId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected == 1;
    }

    /**
     * @param customer
     * @return
     * @throws SQLException
     */
    public static int getApptCountByCustomer(Customer customer) throws SQLException {
        String sql = "SELECT COUNT(Appointment_ID) AS apptCount FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        ResultSet rs = ps.executeQuery();

        int totalAppts = 0;
        while (rs.next()) {
            totalAppts = rs.getInt("apptCount");
        }

        return totalAppts;
    }


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
            Timestamp startTimeStamp = rs.getTimestamp("Start");
            Timestamp endTimeStamp = rs.getTimestamp("End");

            LocalDateTime start = startTimeStamp.toLocalDateTime();
            LocalDateTime end = endTimeStamp.toLocalDateTime();

            apptTimes.add(index, start);
            apptTimes.add(index + 1, end);
            index += 2;
        }

        return apptTimes;
    }

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
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("appointments.Contact_ID");
                String contactName = rs.getString("Contact_Name");

                Appointment appt = new Appointment(apptId, title, desc, loca, type, start, end, customerId, userId,
                        contactId, contactName);
                appointments.add(appt);
            }

        } catch (SQLException e) {
            System.out.println("DBAppointments.getAppointmentsByContact Error: " + e.getMessage());
        }

        return appointments;
    }

    /**
     * @param apptId
     * @param title
     * @param desc
     * @param loca
     * @param type
     * @param start
     * @param end
     * @param customerId
     * @param userId
     * @param contactId
     * @param contactName
     * @throws SQLException
     */
    public static void addAppointment(int apptId, String title, String desc, String loca, String type,
                                      LocalDateTime start, LocalDateTime end, int customerId, int userId,
                                      int contactId, String contactName) throws SQLException {
        String sql = "INSERT INTO appointments VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, apptId); // Appointment_ID
        ps.setString(2, title); // Title
        ps.setString(3, desc); // Description
        ps.setString(4, loca); // Location
        ps.setString(5, type); // Type
        ps.setTimestamp(6, Timestamp.valueOf(start)); // Start
        ps.setTimestamp(7, Timestamp.valueOf(end)); // End
        ps.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now())); // Create_Date
        ps.setString(9, DBUsers.currentUserName); // Created_By
        ps.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now())); // Last_Update
        ps.setString(11, DBUsers.currentUserName); // Last_Updated_By
        ps.setInt(12, customerId); // Customer_ID
        ps.setInt(13, userId); // User_ID
        ps.setInt(14, contactId); // Contact_ID
        ps.executeUpdate();
    }

    public static void updateAppointment(int id, String title, String desc, String loca, String type,
                                         LocalDateTime start, LocalDateTime end, int custId, int uId,
                                         int contId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, " +
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
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, title); // Title
        ps.setString(2, desc); // Description
        ps.setString(3, loca); // Location
        ps.setString(4, type); // Type
        ps.setTimestamp(5, Timestamp.valueOf(start)); // Start
        ps.setTimestamp(6, Timestamp.valueOf(end)); // End
        ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now())); // Last_Update
        ps.setString(8, DBUsers.currentUserName); // Last_Updated_By
        ps.setInt(9, custId); // Customer_ID
        ps.setInt(10, uId); // User_ID
        ps.setInt(11, contId); // Contact_ID
        ps.setInt(12, id); // Appointment_ID
        ps.executeUpdate();
    }

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
            System.out.println("DBAppointments.getAllAppointmentTypes Error: " + e.getMessage());
        }

        return types;
    }

    public static int getCountApptsByTypeAndMonth(String type, String month) {
        String sql = "SELECT * FROM appointments WHERE Type = ? " +
                "AND MONTHNAME(Start) = ?";

        int count = 0;
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, type);
            ps.setString(2, month);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                count++;
            }

        } catch (SQLException e) {
            System.out.println("DBAppointments.getCountApptsByTypeAndMonth Error: " + e.getMessage());
        }

        return count;
    }

    public static Appointment hasAppointmentSoon(int user) {
        Appointment appointment = null;
        String sql = "SELECT * FROM appointments " +
                "INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID " +
                "WHERE appointments.Start <= NOW() + INTERVAL 15 Minute AND " +
                "appointments.Start > Now() AND " +
                "appointments.User_ID = ?";

        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, user);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int apptId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String desc = rs.getString("Description");
                String loca = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                appointment = new Appointment(apptId, title, desc, loca, type, start, end, customerId, userId,
                        contactId, contactName);
            }
        } catch (SQLException e) {
            System.out.println("DBAppointments.hasAppointmentSoon Error: " + e.getMessage());
        }

        return appointment;
    }

    // public static Appointment getAppointmentDetails(int appointmentId) throws SQLException {
    //     String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
    //     PreparedStatement ps = JDBC.connection.prepareStatement(sql);
    //     ps.setInt(1, appointmentId);
    //     ResultSet rs = ps.executeQuery();
    //
    //     Appointment apptDetails = null;
    //
    //     while (rs.next()) {
    //         int apptId = rs.getInt("Appointment_ID");
    //         String title = rs.getString("Title");
    //         String desc = rs.getString("Description");
    //         String loca = rs.getString("Location");
    //         String type = rs.getString("Type");
    //         LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
    //         LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
    //         int customerId = rs.getInt("Customer_ID");
    //         int userId = rs.getInt("User_ID");
    //         int contactId = rs.getInt("Contact_ID");
    //         String contactName = rs.getString("Contact_Name");
    //
    //         apptDetails = new Appointment(apptId, title, desc, loca, type, start, end, customerId, userId,
    //                 contactId, contactName);
    //     }
    //     return apptDetails;
    // }
}
