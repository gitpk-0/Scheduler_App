package Controller;

/**
 * @author Patrick Kell
 */

import Database.DBAppointments;
import Database.DBContacts;
import Database.DBCustomers;
import Database.DBUsers;
import Model.Appointment;
import Utility.Alerts;
import Utility.ChangeView;
import Utility.FormValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The ModifyAppointment controller class which enables the user to modify an existing appointment
 */
public class ModifyAppointment implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user

    public TextField apptIdTF;
    public TextField titleTF;
    public TextField descTF;
    public TextField locationTF;
    public DatePicker startDP;
    public DatePicker endDP;
    public ComboBox<Integer> userIdCB;
    public ComboBox<Integer> custIdCB;
    public ComboBox<String> contactCB;
    public ComboBox<String> startTimeCB;
    public ComboBox<String> endTimeCB;
    public TextField typeTF;

    /**
     * Initializes the Modify Appointment screen with the data from the appointment the user selected to modify
     * <p>
     * This method utilizes three lambda expressions which promote better code readability and reduce the amount of
     * code required. With each lambda expression utilizing only one line of code, their functionality can be
     * easily understood.
     *
     * @param url            The FXML location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize contact combo box options
        ObservableList<String> contacts = FXCollections.observableArrayList();
        DBContacts.getAllContacts().stream().forEach(contact -> contacts.add(contact.toString())); // lambda
        contactCB.setItems(contacts);

        // Initialize customer id combo box options
        ObservableList<Integer> customers = FXCollections.observableArrayList();
        DBCustomers.getAllCustomers().stream().forEach(customer -> customers.add(customer.getCustomerId())); // lambda
        custIdCB.setItems(customers);

        // Initialize user id combo box options
        ObservableList<Integer> users = FXCollections.observableArrayList();
        DBUsers.getAllUserIDs().stream().forEach(userID -> users.add(userID)); // lambda
        userIdCB.setItems(users);

        // Initialize start and end time combo box options
        ObservableList<String> times = FXCollections.observableArrayList();
        int currentHour = 8;

        while (currentHour < 22) {
            times.add(currentHour + ":00");
            times.add(currentHour + ":15");
            times.add(currentHour + ":30");
            times.add(currentHour + ":45");
            currentHour++;
        }
        times.add("22:00");
        startTimeCB.setItems(times);
        endTimeCB.setItems(times);
    }

    /**
     * The onSaveAppt method which manages the modification of an existing appointment.
     * <p>
     * The user edits information about an existing appointment, which is then compiled and added to the appointments
     * table of the client_schedule database.
     * <p>
     * This method calls the form validation class which validates the data and alerts there are problems with the
     * input data.
     *
     * @param event Save Appointment button clicked
     * @throws SQLException Signals an SQLException has occurred
     */
    public void onSaveAppt(ActionEvent event) throws SQLException {
        FormValidation validator = new FormValidation(); // creates a new FormValidation object each time Save is clicked
        ArrayList<String> errors = validator.nullValueCheckAppt(titleTF, descTF, locationTF, startDP, startTimeCB,
                typeTF, custIdCB, userIdCB, contactCB, endDP, endTimeCB); // null value checks

        try {
            if (errors.isEmpty()) { // no null value errors
                // date and time checks
                errors = validator.dateTimeChecks(startDP, startTimeCB, endDP, endTimeCB);
                if (errors.isEmpty()) { // no date/time value errors
                    // date and time overlaps checks
                    if (validator.apptOverlapExists(apptIdTF, custIdCB, startDP, startTimeCB, endDP, endTimeCB)) {
                        errors = validator.addOverlapError(); // an appointment overlap exists
                        throw new Exception(); // redirect to the catch block below
                    }
                } else { // date and/or time value errors occurred
                    throw new Exception(); // redirect to the catch block below
                }

                // all input data is valid
                int id = Integer.parseInt(apptIdTF.getText());
                String title = titleTF.getText();
                String desc = descTF.getText();
                String loca = locationTF.getText();
                String type = typeTF.getText();
                int custId = custIdCB.getValue();
                int uId = userIdCB.getValue();
                int contId = Integer.valueOf(contactCB.getSelectionModel().getSelectedItem().substring(0, 1));

                String[] startT = startTimeCB.getSelectionModel().getSelectedItem().split(":"); // split time between hour and minutes
                String[] endT = endTimeCB.getSelectionModel().getSelectedItem().split(":"); // split time between hour and minutes
                LocalTime startTime = LocalTime.of(Integer.valueOf(startT[0]), Integer.valueOf(startT[1]));
                LocalTime endTime = LocalTime.of(Integer.valueOf(endT[0]), Integer.valueOf(endT[1]));
                LocalDate startDate = startDP.getValue();
                LocalDate endDate = endDP.getValue();

                // offset the start times by 1 sec to allow appts to start when another appt ends
                LocalDateTime start = LocalDateTime.of(startDate, startTime).withSecond(1);
                LocalDateTime end = LocalDateTime.of(endDate, endTime);

                // update the existing appointment in the client_schedule database
                DBAppointments.updateAppointment(id, title, desc, loca, type, start, end, custId, uId, contId);
                viewController.changeViewToMain(event); // redirect the user back to the main menu
            } else { // errors list is not empty
                throw new Exception(); // redirect to the catch block below
            }
        } catch (Exception e) {
            alerts.inputError(errors); // display the errors to the user
        }

    }

    /**
     * User clicked Modify Appointment Button in the Appointment table
     * <p>
     * Sends the information about the Appointment selected to populate the ModifyAppointment text fields
     *
     * @param appointment Appointment to be modified
     */
    public void sendAppt(Appointment appointment) {
        String startTime = appointment.getStartTime();
        String endTime = appointment.getEndTime();
        if (startTime.indexOf("0") == 0) { // if leading zero is present
            startTime = startTime.substring(1); // ignore the leading zero
        }
        if (endTime.indexOf("0") == 0) { // if leading zero is present
            endTime = endTime.substring(1); // ignore the leading zero
        }

        apptIdTF.setText(String.valueOf(appointment.getApptId()));
        titleTF.setText(appointment.getTitle());
        descTF.setText(appointment.getDescription());
        locationTF.setText(appointment.getLocation());
        startDP.setValue(appointment.getStart().toLocalDate());
        endDP.setValue(appointment.getStart().toLocalDate());
        userIdCB.setValue(appointment.getUserId());
        custIdCB.setValue(appointment.getCustomerId());
        contactCB.setValue(appointment.getContactId() + ": " + appointment.getContactName());
        startTimeCB.setValue(startTime);
        endTimeCB.setValue(endTime);
        typeTF.setText(appointment.getType());
    }

    /**
     * User clicked cancel button, view is changed back to Main Menu
     *
     * @param event Cancel button clicked
     * @throws IOException Signals an Input/Output exception has occurred
     */
    public void onCancel(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }
}
