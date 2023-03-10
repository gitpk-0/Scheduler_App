package Database;

import Model.Appointment;
import Model.Customer;
import Utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author Patrick Kell
 */
public class DBAppointments {

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

                Appointment newAppt = new Appointment(apptId, title, desc, loca, type, start, end, customerId, userId, contactId, contactName);
                appointments.add(newAppt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return appointments;
    }


    public static boolean deleteAppointment(Appointment appt) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appt.getApptId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected == 1;
    }

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

    public static ArrayList<LocalDateTime> getApptTimesByCustomer(Customer customer) throws SQLException {
        ArrayList<LocalDateTime> apptTimes = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
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

    public static void addAppointment(int apptId, String title, String desc, String loca, String type,
                                      LocalDateTime start, LocalDateTime end, int customerId, int userId,
                                      int contactId, String contactName) {
    }
}
