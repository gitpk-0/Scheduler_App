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
 * Form Validation utility class to validate data input by the user
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
        inputErrors.add("Start and End Dates must be the same"); // 13 - Date Selection Error
        inputErrors.add("Start Time must be before End Time"); // 14 - Time Selection Error
        inputErrors.add("This customer already has an appointment scheduled during the " +
                "selected times."); // 15 - Time Selection Error
        inputErrors.add("No data in Name field"); // 16 - Name Error
        inputErrors.add("No data in Phone Number field"); // 17 - Phone Number Error
        inputErrors.add("No data in Address field"); // 18 - Address Error
        inputErrors.add("No data in Postal Code field"); // 19 - Postal Code Error
        inputErrors.add("No Country selected"); // 20 - Country Error
        inputErrors.add("No Division selected"); // 21 - Division Error
        inputErrors.add("Start time selection must be between the hours of 8AM and 10PM EST"); // 22 - Start Time Error
        inputErrors.add("End time selection must be between the hours of 8AM and 10PM EST"); // 23 - End Time Error
        // inputErrors.add(""); // 24 -
    }


    /**
     * NullValueCheckAppt method which checks for null values when an appointment is created or modified
     *
     * @param titleTF        The title text field from the add or modify appointment screen
     * @param descTF         The description text field from the add or modify appointment screen
     * @param locationTF     The location text field from the add or modify appointment screen
     * @param startDatePick  The start date picker from the add or modify appointment screen
     * @param startTimeCombo The start time combo box from the add or modify appointment screen
     * @param typeTF         The type text field from the add or modify appointment screen
     * @param custIdCombo    The customer id combo box from the add or modify appointment screen
     * @param userIdCombo    The user id combo box from the add or modify appointment screen
     * @param contactCombo   The contact combo box from the add or modify appointment screen
     * @param endDatePick    The end date picker from the add or modify appointment screen
     * @param endTimeCombo   The end time combo box from the add or modify appointment screen
     * @return A list of errors the user needs to rectify
     */
    public ArrayList<String> nullValueCheckAppt(TextField titleTF, TextField descTF, TextField locationTF,
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


    /**
     * DateTimeChecks method which checks the user selected valid dates and times
     *
     * @param startDatePick  The start date picker from the add or modify appointment screen
     * @param startTimeCombo The start time combo box from the add or modify appointment screen
     * @param endDatePick    The end date picker from the add or modify appointment screen
     * @param endTimeCombo   The end time combo box from the add or modify appointment screen
     * @return A list of errors the user needs to rectify
     */
    public ArrayList<String> dateTimeChecks(DatePicker startDatePick, ComboBox<String> startTimeCombo,
                                            DatePicker endDatePick, ComboBox<String> endTimeCombo) {

        // validate Start Date picker format
        try {
            LocalDate startDate = startDatePick.getValue();
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(11));
        }

        // validate End Date picker format
        try {
            LocalDate endDate = endDatePick.getValue();
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(12));
        }

        // validate Start Date and End Date selections
        try {
            LocalDate startDate = startDatePick.getValue();
            LocalDate endDate = endDatePick.getValue();

            if (!startDate.equals(endDate)) {
                outputErrorMessages.add(inputErrors.get(13));
            }
        } catch (Exception ignore) {
            // already handled above
        }

        String[] start = startTimeCombo.getSelectionModel().getSelectedItem().split(":");
        String[] end = endTimeCombo.getSelectionModel().getSelectedItem().split(":");

        LocalTime startTime = LocalTime.of(Integer.valueOf(start[0]), Integer.valueOf(start[1]));
        LocalTime endTime = LocalTime.of(Integer.valueOf(end[0]), Integer.valueOf(end[1]));
        LocalDate startDate = startDatePick.getValue();
        LocalDate endDate = endDatePick.getValue();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);

        // validate Start Date Time before End Date Time
        if (startDateTime.isAfter(endDateTime) || startDateTime.equals(endDateTime)) {
            outputErrorMessages.add(inputErrors.get(14));
        }

        // convert local times to EST
        int startHourEST = TimeConversion.localToEST(startDateTime).getHour();
        int endHourEST = TimeConversion.localToEST(endDateTime).getHour();
        int endMinEST = TimeConversion.localToEST(endDateTime).getMinute();

        // validate start time
        if (startHourEST < 8 || startHourEST > 22) {
            outputErrorMessages.add(inputErrors.get(22));
        }

        // validate end time
        if (endHourEST < 8 || endHourEST > 22 || (endHourEST == 22 && endMinEST > 0)) {
            outputErrorMessages.add(inputErrors.get(23));
        }

        return outputErrorMessages;
    }

    /**
     * ApptOverlapExists (Appointment Overlap Exists) method which checks for an appointment overlap based on the
     * customer the user selected
     *
     * @param apptIdTF       The appointment id text field from the add or modify appointment screen
     * @param custIdCombo    The customer id combo box from the add or modify appointment screen
     * @param startDatePick  The start date picker from the add or modify appointment screen
     * @param startTimeCombo The start time combo box from the add or modify appointment screen
     * @param endDatePick    The end date picker from the add or modify appointment screen
     * @param endTimeCombo   The end time combo box from the add or modify appointment screen
     * @return A boolean value indicating the existence of an appointment overlap
     * @throws SQLException Signals an SQLException has occurred
     */
    public boolean apptOverlapExists(TextField apptIdTF, ComboBox<Integer> custIdCombo, DatePicker startDatePick,
                                     ComboBox<String> startTimeCombo,
                                     DatePicker endDatePick, ComboBox<String> endTimeCombo) throws SQLException {

        int customerId = custIdCombo.getSelectionModel().getSelectedItem(); // get the selected customer id
        Customer customerToCheck = null;

        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
        String[] startT = startTimeCombo.getSelectionModel().getSelectedItem().split(":");
        String[] endT = endTimeCombo.getSelectionModel().getSelectedItem().split(":");

        LocalTime startTime = LocalTime.of(Integer.valueOf(startT[0]), Integer.valueOf(startT[1]));
        LocalTime endTime = LocalTime.of(Integer.valueOf(endT[0]), Integer.valueOf(endT[1]));
        LocalDate startDate = startDatePick.getValue();
        LocalDate endDate = endDatePick.getValue();

        LocalDateTime startSelection = LocalDateTime.of(startDate, startTime);
        LocalDateTime endSelection = LocalDateTime.of(endDate, endTime);

        for (Customer customer : DBCustomers.getAllCustomers()) { // find the Customer object which id matches
            if (customer.getCustomerId() == customerId) {
                customerToCheck = customer; // assign it to customerToCheck
            }
        }

        String apptId = null;
        if (apptIdTF != null) {
            apptId = apptIdTF.getText();
        }

        if (customerToCheck != null) {
            // get all appointments times for the customer from database
            ArrayList<LocalDateTime> apptTimes = DBAppointments.getApptTimesByCustomer(customerToCheck, apptId);

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


    /**
     * AddOverlapError method which adds the overlap error message to the outputErrorMessages (to the user)
     *
     * @return A list of errors the user needs to rectify
     */
    public ArrayList<String> addOverlapError() {
        outputErrorMessages.add(inputErrors.get(15));
        return outputErrorMessages;
    }

    /**
     * nullValueCheckCustomer method which checks for null values when a customer is created or modified
     *
     * @param nameTF     The name text field from the add or modify customer screen
     * @param phoneTF    The phone number text field from the add or modify customer screen
     * @param addressTF  The address text field from the add or modify customer screen
     * @param postalTF   The postal code text field from the add or modify customer screen
     * @param countryCB  The country combo box from the add or modify customer screen
     * @param divisionCB The division combo box from the add or modify customer screen
     * @return A list of errors the user needs to rectify
     */
    public ArrayList<String> nullValueCheckCustomer(TextField nameTF, TextField phoneTF, TextField addressTF,
                                                    TextField postalTF, ComboBox<String> countryCB,
                                                    ComboBox<String> divisionCB) {

        // validate Name field
        if (nameTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(16));
        }

        // validate Phone field
        if (phoneTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(17));
        }

        // validate Address field
        if (addressTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(18));
        }

        // validate Postal field
        if (postalTF.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(19));
        }

        // validate Country combo
        if (countryCB.getSelectionModel().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(20));
        }

        // validate Division combo
        if (divisionCB.getSelectionModel().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(21));
        }

        return outputErrorMessages;
    }
}