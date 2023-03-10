package Controller;

import Database.DBContacts;
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
import java.time.LocalDateTime;
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
    public DatePicker startDatePick;
    public ComboBox<String> startTimeCombo;
    public TextField typeTF;
    public ComboBox<Integer> custIdCombo;
    public ComboBox<Integer> userIdCombo;
    public ComboBox<String> contactCombo;
    public DatePicker endDatePick;
    public ComboBox<String> endTimeCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> contacts = FXCollections.observableArrayList();
        DBContacts.getAllContacts().stream().forEach(contact -> contacts.add(contact.toString())); // lambda
        contactCombo.setItems(contacts);

    }

    public void onSaveAppt(ActionEvent event) {
        FormValidation validator = new FormValidation(); // creates a new FormValidation object each time Save is clicked
        ArrayList<String> errors = validator.mainCheck(titleTF, descTF, locationTF, startDatePick, startTimeCombo,
                typeTF, custIdCombo, userIdCombo, contactCombo, endDatePick, endTimeCombo);

        try {
            if (errors.isEmpty()) {
                int apptId = IDGenerator.
                String title =

            }

        } catch (Exception e) {

        }

        String contactId = contactCombo.getSelectionModel().getSelectedItem().substring(0, 0);
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
