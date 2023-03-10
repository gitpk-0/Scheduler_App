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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        userIdCB.setItems(users);

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
                System.out.println("here");
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
                System.out.println(errors.size());
                if (!errors.isEmpty()) {
                    System.out.println("Made it here2");
                    throw new Exception(); // redirect to the catch block below
                } else {
                    System.out.println("Made it here2");
                    if (validator.apptOverlapExists(custIdCB, startDP, startTimeCB, endDP, endTimeCB)) {
                        System.out.println("Made it here3");
                        errors = validator.addOverlapError();
                        throw new Exception(); // redirect to the catch block below
                    }
                }
                System.out.println("Made it here4");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
                String[] startT = startTimeCB.getSelectionModel().getSelectedItem().split(":");
                String[] endT = endTimeCB.getSelectionModel().getSelectedItem().split(":");
                LocalTime startTime = LocalTime.of(Integer.valueOf(startT[0]), Integer.valueOf(startT[1]));
                LocalTime endTime = LocalTime.of(Integer.valueOf(endT[0]), Integer.valueOf(endT[1]));
                LocalDate startDate = startDP.getValue();
                LocalDate endDate = endDP.getValue();

                LocalDateTime start = LocalDateTime.of(startDate, startTime).withSecond(1);
                LocalDateTime end = LocalDateTime.of(endDate, endTime);
                /* TODO: may need to offset the start and end times by 1 sec to allow appts creation */
                // LocalDateTime start = LocalDateTime.parse(startDP.toString() + startTimeCB.toString(), formatter).withSecond(1);
                // LocalDateTime end = LocalDateTime.parse(endDP.toString() + endTimeCB.toString(), formatter);

                System.out.println("Made it here5");


                DBAppointments.addAppointment(id, title, desc, loca, type, start, end, custId, uId, contId, contName);
                viewController.changeViewToMain(event);
            } else { // errors list is not empty
                throw new Exception(); // redirect to the catch block below
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            int len = e.getStackTrace().length -1;
            e.printStackTrace();
            System.out.println(len + " **len");
            System.out.println(e.getStackTrace()[len - 1].getLineNumber());
            System.out.println(e.getCause());
            System.out.println(e.getClass());
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
