package Utility;

/**
 * @author Patrick Kell
 */

import Database.DBAppointments;
import Database.DBCustomers;
import Model.Customer;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Form Validation class to validate data input by the user
 */
public class FormValidation {

    private ArrayList<String> inputErrors;
    private ArrayList<String> outputErrorMessages;

    /**
     * FormValidation Constructor
     * <p>
     * Creates two ArrayLists responsible for the possible input errors and the error messages that the
     * user will see if an input error has occurred
     */
    public FormValidation() {
        this.outputErrorMessages = new ArrayList<>(); // for displaying the errors the user has incurred
        this.inputErrors = new ArrayList<>(); // for the possible input errors
        inputErrors.add("No data in Title field"); // 0 - Title Error
        inputErrors.add("No data in Description field"); // 1 - Description Error
        inputErrors.add("No data in Location field"); // 2 - Location Error
        inputErrors.add("No data in Type field"); // 3 - Type Error
        inputErrors.add("No Customer ID selected"); // 4 - Customer ID Error
        inputErrors.add("No User ID selected"); // 5 - User ID Error
        inputErrors.add("No Contact selected"); // 6 - Contact Error
        inputErrors.add("No Start Time selected"); // 7 - Start Time Error
        inputErrors.add("No End Time selected"); // 8 - End Time Error
        inputErrors.add("No Start Date selected"); // 9 - Start Date Error
        inputErrors.add("No End Date selected"); // 10 - End Date Error
        inputErrors.add("Improper Start Date format"); // 11 - Start Date Format Error
        inputErrors.add("Improper End Date format"); // 12 - End Date Format Error
        inputErrors.add("Start Date must be before End Date"); // 13 - Date Selection Error
        inputErrors.add("Start Time must be before End Time"); // 14 - Time Selection Error
        inputErrors.add("Scheduling conflict: this customer already has an appointment scheduled during the " +
                " selected times."); // 15 - Time Selection Error
    }


    public ArrayList<String> mainCheck(TextField titleTF, TextField descTF, TextField locationTF,
                                       DatePicker startDatePick, ComboBox<String> startTimeCombo, TextField typeTF,
                                       ComboBox<Integer> custIdCombo, ComboBox<Integer> userIdCombo,
                                       ComboBox<String> contactCombo, DatePicker endDatePick,
                                       ComboBox<String> endTimeCombo) {

        // validate Title field
        if (titleTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(0));
        }

        // validate Description field
        if (descTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(1));
        }

        // validate Location field
        if (locationTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(2));
        }

        // validate Type field
        if (typeTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(3));
        }

        // validate Customer ID combo
        if (custIdCombo.getSelectionModel().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(4));
        }

        // validate User ID combo
        if (userIdCombo.getSelectionModel().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(5));
        }

        // validate Contact combo
        if (contactCombo.getSelectionModel().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(6));
        }

        // validate Start Time combo
        if (startTimeCombo.getSelectionModel().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(7));
        }

        // validate End Time combo
        if (endTimeCombo.getSelectionModel().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(8));
        }

        // validate Start Date picker
        if (startDatePick.getValue() == null) {
            outputErrorMessages.add(inputErrors.get(9));
        }

        // validate End Date picker
        if (endDatePick.getValue() == null) {
            outputErrorMessages.add(inputErrors.get(10));
        }


        return outputErrorMessages;

    }


    public ArrayList<String> dateChecks(DatePicker startDatePick, ComboBox<String> startTimeCombo,
                                        DatePicker endDatePick, ComboBox<String> endTimeCombo) {

        // validate Start Date picker format
        try {
            LocalDate startDate = startDatePick.getValue();
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(11));
        }

        // validate End Date picker format
        try {
            LocalDate startDate = endDatePick.getValue();
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(12));
        }

        // validate Start Date before End Date
        if (endDatePick.getChronology().compareTo(startDatePick.getChronology()) < 0) {
            outputErrorMessages.add(inputErrors.get(13));
        }

        // validate Start Time before End Time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startTime = LocalTime.parse(startTimeCombo.toString(), formatter);
        LocalTime endTime = LocalTime.parse(endTimeCombo.toString(), formatter);
        if (startTime.isAfter(endTime)) {
            outputErrorMessages.add(inputErrors.get(14));
        }

        return outputErrorMessages;
    }

    public boolean appointmentOverlapExists(ComboBox<Integer> custIdCombo, DatePicker startDatePick,
                                            ComboBox<String> startTimeCombo,
                                            DatePicker endDatePick, ComboBox<String> endTimeCombo) throws SQLException {

        int customerId = custIdCombo.getSelectionModel().getSelectedItem(); // get the selected customer id
        Customer customerToCheck = null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startSelection = LocalDateTime.parse(startDatePick.toString() + startTimeCombo.toString(), formatter);
        LocalDateTime endSelection = LocalDateTime.parse(endDatePick.toString() + endTimeCombo.toString(), formatter);

        for (Customer customer : DBCustomers.getAllCustomers()) { // find the Customer object which id matches
            if (customer.getCustomerId() == customerId) {
                customerToCheck = customer; // assign it to customerToCheck
            }
        }

        if (customerToCheck != null) {
            // get all appointments times for the customer from database
            ArrayList<LocalDateTime> apptTimes = DBAppointments.getApptTimesByCustomer(customerToCheck);

            for (int i = 0; i < apptTimes.size(); i += 2) { // loop through the appointment times
                LocalDateTime start = apptTimes.get(i); // existing appointment start time
                LocalDateTime end = apptTimes.get(i + 1); // existing appointment end time

                // An OVERLAP exists if start of the selected is before the end of the existing appointment
                // AND the start of the existing appointment is before the end of the selected
                boolean overlapExists = startSelection.isBefore(end) && start.isBefore(endSelection);

                if (overlapExists) {
                    return true; // an overlap exists (alert the user)
                }
            }
            return false;
        }
        return false;
    }


}
