package Utility;

/**
 * @author Patrick Kell
 */

import Controller.Login;
import Database.DBAppointments;
import Database.DBUsers;
import Model.Appointment;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Alert utility class which manages the alert messages to the user
 */
public class Alerts {

    /**
     * OnLogin method which manages the alert upon a successful user login
     */
    public static void onLogin() {
        int user = DBUsers.currentUserId; // get the current user's id
        Appointment apptNear = DBAppointments.hasAppointmentSoon(user); // appointment within next 15 minutes

        if (Login.login) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // creation of Alert Object
            alert.setTitle("Login Successful"); // set the title
            alert.setHeaderText("Welcome"); // set the header text

            if (apptNear != null) { // appointment is starting soon
                alert.setContentText("You have an appointment starting in " + apptNear.getMinutesToStart() +
                        " minute(s). Appointment " + apptNear.getApptId() +
                        " scheduled for " + apptNear.getStartTime() +
                        " " + apptNear.getStartDate());
                Login.login = false; // set the Login.login variable to false to only show alert once (on login)
            } else { // no appointment starting soon
                alert.setContentText("You do not have any appointments scheduled in the next 15 minutes");
                Login.login = false; // set the Login.login variable to false to only show alert once (on login)
            }
            alert.showAndWait(); // display the alert and wait for a response from the user
        }
    }

    /**
     * Input Error method which alerts the user of an input error(s)
     * <p>
     * This method features a lambda expression used to append error messages and new lines to the errorMessage
     * StringBuilder object. This greatly simplifies the code readability.
     *
     * @param errors A list of errors that have occurred
     */
    public void inputError(ArrayList<String> errors) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle("Input Error"); // set the title
        alert.setHeaderText("Form Input Error"); // set the header text

        // string builder to contain each of the input error messages
        StringBuilder errorMessage = new StringBuilder("Exception: \n");
        // streaming through each error message received and appending each on a new line to the error message to the user
        errors.stream().forEach(error -> errorMessage.append(error).append("\n"));
        // set the content text to the string format of the errorMessage StringBuilder object
        alert.setContentText(errorMessage.toString());
        alert.showAndWait(); // display the alert and wait for a response from the user
    }

    /**
     * Confirm delete method which asks the user to confirm the deletion
     *
     * @param type The object type to be deleted (Appointment or Customer)
     * @return A boolean value indicating that the OK button was pressed
     */
    public boolean confirmDelete(String type) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // creation of Alert Object
        alert.setTitle("Delete " + type); // set the title
        alert.setHeaderText("Delete Confirmation"); // set the header text
        // set the content text to the string format of the errorMessage StringBuilder object
        alert.setContentText("Are you sure you want to delete this " + type + "?");

        // create an optional-button type object which displays the alert and waits for a response from the user
        Optional<ButtonType> result = alert.showAndWait();

        // if the user clicked the OK button
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true; // delete the product
        }
        return false; // users clicked cancel, do not delete
    }

    /**
     * Associated Appointment(s) error method which alerts the user if a customer attempting to be deleted has
     * associated appointment(s)
     */
    public void associatedApptsError() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle("Associated Appointment(s) Error"); // set the title
        alert.setHeaderText("Associated Appointment(s) Error"); // set the header text
        alert.setContentText("This customer cannot be deleted because there are one or more appointments associated " +
                "with them. To delete this customer, remove all of their associated appointments."); // error message
        alert.showAndWait(); // display the alert and wait for a response from the user
    }

    /**
     * Null Selection method which alerts the user of a null selection error
     *
     * @param type   Type of object that needs to be selected (Appointment or Customer)
     * @param action Delete or Modify
     */
    public void nullSelection(String type, String action) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle("No " + type + " Selected"); // set the title
        alert.setHeaderText("Null Selection Error"); // set the header text
        alert.setContentText("No " + type + " is selected. " + "Please select a " + type + " to " + action + ".");
        alert.showAndWait(); // display the alert and wait for a response from the user
    }

    /**
     * Null Selection Reports method which alerts the user of a null selection error
     */
    public void nullSelectionReports() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle("Null Selection Error"); // set the title
        alert.setHeaderText("Please Select a Type and Month"); // set the header text
        alert.setContentText("A Type and a Month must be selected in order to calculate the total.");
        alert.showAndWait(); // display the alert and wait for a response from the user
    }

    /**
     * InformOfDeletion method which informs the user that a deletion has been made
     *
     * @param id     ID of the deleted object (Appointment or Customer)
     * @param object String value of the deleted object type
     * @param type   Type of the deleted appointment (null if Customer)
     */
    public void informOfDeletion(int id, String object, String type) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle(object + " Deleted"); // set the title
        alert.setHeaderText(object + " Deleted"); // set the header text
        if (object.equals("Customer")) {
            alert.setContentText(object + " with the ID: " + id + " has successfully been deleted.");
        } else if (object.equals("Appointment")) {
            alert.setContentText(object + " with the ID: " + id + " and type of: " + type +
                    " has successfully been deleted.");
        }
        alert.showAndWait(); // display the alert and wait for a response from the user
    }
}