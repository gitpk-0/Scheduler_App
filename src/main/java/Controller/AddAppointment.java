package Controller;

import Database.DBAppointments;
import Database.DBContacts;
import Database.DBCustomers;
import Database.DBUsers;
import Model.Customer;
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

/**
 * @author Patrick Kell
 */

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        custIdCB.setItems(customers);

        // Initialize start and end time combo box options
        ObservableList<String> times = FXCollections.observableArrayList();
        int currentHour = 8;

        while (currentHour < 22) {
            StringBuilder time = new StringBuilder();
            times.add(String.valueOf(currentHour) + ":00");
            times.add(String.valueOf(currentHour) + ":15");
            times.add(String.valueOf(currentHour) + ":30");
            times.add(String.valueOf(currentHour) + ":45");
            currentHour++;
        }
        times.add("22:00");
        startTimeCB.setItems(times);
        endTimeCB.setItems(times);
    }

    public void onSaveAppt(ActionEvent event) throws SQLException {
        FormValidation validator = new FormValidation(); // creates a new FormValidation object each time Save is clicked
        ArrayList<String> errors = validator.nullValueCheck(titleTF, descTF, locationTF, startDP, startTimeCB,
                typeTF, custIdCB, userIdCB, contactCB, endDP, endTimeCB);

        try {
            if (errors.isEmpty()) {
                int id = IDGenerator.appointmentIDGenerator();
                String title = titleTF.getText();
                String desc = descTF.getText();
                String loca = locationTF.getText();
                String type = typeTF.getText();
                int custId = custIdCB.getValue();
                int uId = userIdCB.getValue();
                String[] contact = contactCB.getSelectionModel().getSelectedItem().split(": ");
                int contId = Integer.valueOf(contact[0]);
                String contName = contact[1];

                // date selection, date format, date overlaps checks
                errors = validator.dateChecks(startDP, startTimeCB, endDP, endTimeCB);
                if (errors.isEmpty()) {
                    if (validator.apptOverlapExists(custIdCB, startDP, startTimeCB, endDP, endTimeCB)) {
                        errors = validator.addOverlapError();
                        throw new Exception();
                    }
                    throw new Exception();
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                /* TODO: may need to offset the start and end times by 1 sec to allow appts creation */
                LocalDateTime start = LocalDateTime.parse(startDP.toString() + startTimeCB.toString(), formatter).withSecond(1);
                LocalDateTime end = LocalDateTime.parse(endDP.toString() + endTimeCB.toString(), formatter);


                DBAppointments.addAppointment(id, title, desc, loca, type, start, end, custId, uId, contId, contName);
                viewController.changeViewToMain(event);
            }

        } catch (Exception e) {
            alerts.inputError(errors);
        }

    }

    /**
     * User clicked cancel button, view is changed back to Main Menu
     *
     * @param event Cancel button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void onCancel(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }


    public void selectUserID(ActionEvent event) {
    }

    public void selectCustomerID(ActionEvent event) {
    }

    public void selectContact(ActionEvent event) {

    }

    public void selectEndTime(ActionEvent event) {
    }

    public void selectStartTime(ActionEvent event) {
    }
}
