package Controller;

/**
 * @author Patrick Kell
 */

import Database.DBAppointments;
import Database.DBContacts;
import Database.DBCustomers;
import Database.DBUsers;
import Utility.Alerts;
import Utility.ChangeView;
import Utility.FormValidation;
import Utility.IDGenerator;
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
 * The AddAppointment controller class which enables the user to create new appointments
 */
public class AddAppointment implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user

    public TextField titleTF;
    public TextField descTF;
    public TextField locationTF;
    public DatePicker startDP;
    public ComboBox<String> startTimeCB;
    public TextField typeTF;
    public ComboBox<Integer> custIdCB;
    public ComboBox<Integer> userIdCB;
    public ComboBox<String> contactCB;
    public DatePicker endDP;
    public ComboBox<String> endTimeCB;


    /**
     * Initialize method which initializes the AddAppointment controller class.
     * <p>
     * Each of the combo boxes is initialized based on the data received from the client_schedule database
     * <p>
     * The time combo boxes are initialized for open hours between 8am and 10pm
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
        int currentHour = 8; // starting hour

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
     * The onSaveAppt method which manages the creation of a new appointment.
     * <p>
     * The user enters information about a new appointment, which is then compiled and added to the appointments
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
        ArrayList<String> errors = validator.nullValueCheck(titleTF, descTF, locationTF, startDP, startTimeCB,
                typeTF, custIdCB, userIdCB, contactCB, endDP, endTimeCB); // null value checks

        try {
            if (errors.isEmpty()) { // no null value errors
                // date and time checks
                errors = validator.dateTimeChecks(startDP, startTimeCB, endDP, endTimeCB);
                if (errors.isEmpty()) { // no date/time value errors
                    // date and time overlaps checks
                    if (validator.apptOverlapExists(null, custIdCB, startDP, startTimeCB, endDP, endTimeCB)) {
                        errors = validator.addOverlapError(); // an appointment overlap exists
                        throw new Exception(); // redirect to the catch block below
                    }
                } else { // date and/or time value errors occurred
                    throw new Exception(); // redirect to the catch block below
                }

                // all input data is valid
                int id = IDGenerator.appointmentIDGenerator(); // create a unique id
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

                // add the appointment to the client_schedule database
                DBAppointments.addAppointment(id, title, desc, loca, type, start, end, custId, uId, contId);
                viewController.changeViewToMain(event); // redirect the user back to the main menu
            } else { // errors list is not empty
                throw new Exception(); // redirect to the catch block below
            }
        } catch (Exception e) {
            // System.out.println("AddAppointment Error: " + e.getMessage());
            alerts.inputError(errors); // display the errors to the user
        }

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