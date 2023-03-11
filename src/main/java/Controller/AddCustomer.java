package Controller;

import Database.DBAppointments;
import Database.DBCountries;
import Database.DBFLDivisions;
import Model.Country;
import Utility.Alerts;
import Utility.ChangeView;
import Utility.FormValidation;
import Utility.IDGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Patrick Kell
 */
public class AddCustomer implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user

    public TextField nameTF;
    public TextField phoneTF;
    public TextField addressTF;
    public TextField postalTF;
    public ComboBox<String> countryCB;
    public ComboBox<String> divisionCB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList();
        DBCountries.getAllCountries().stream()
                .forEach(country -> countries.add(country.getCountryName())); // lambda
        countryCB.setItems(countries);
    }


    public void onSaveCustomer(ActionEvent event) {
        FormValidation validator = new FormValidation(); // creates a new FormValidation object each time Save is clicked
        // null value checks
        ArrayList<String> errors = validator.nullValueCheckCustomer(nameTF, phoneTF, addressTF, postalTF, countryCB, divisionCB);

        try {
            if (errors.isEmpty()) { // no null value errors
                // all input data is valid

                // int id = IDGenerator.appointmentIDGenerator();
                // String title = titleTF.getText();
                // String desc = descTF.getText();
                // String loca = locationTF.getText();
                // String type = typeTF.getText();
                // int custId = custIdCB.getValue();
                // int uId = userIdCB.getValue();
                // String[] contact = contactCB.getSelectionModel().getSelectedItem().split(": ");
                // int contId = Integer.valueOf(contact[0]);
                // String contName = contact[1];
                //
                // String[] startT = startTimeCB.getSelectionModel().getSelectedItem().split(":"); // split time between hour and minutes
                // String[] endT = endTimeCB.getSelectionModel().getSelectedItem().split(":"); // split time between hour and minutes
                // LocalTime startTime = LocalTime.of(Integer.valueOf(startT[0]), Integer.valueOf(startT[1]));
                // LocalTime endTime = LocalTime.of(Integer.valueOf(endT[0]), Integer.valueOf(endT[1]));
                // LocalDate startDate = startDP.getValue();
                // LocalDate endDate = endDP.getValue();

                // // offset the start times by 1 sec to allow appts to start when another appt ends
                // LocalDateTime start = LocalDateTime.of(startDate, startTime).withSecond(1);
                // LocalDateTime end = LocalDateTime.of(endDate, endTime);
                //
                // // add the appointment to the client_schedule database
                // DBAppointments.addAppointment(id, title, desc, loca, type, start, end, custId, uId, contId, contName);
                // viewController.changeViewToMain(event); // redirect the user back to the main menu
            } else { // errors list is not empty
                throw new Exception(); // redirect to the catch block below
            }
        } catch (Exception e) {
            System.out.println("AddCustomer Error: " + e.getMessage());
            alerts.inputError(errors); // display the errors to the user
        }
    }

    public void onCancel(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }

    public void countrySelected(ActionEvent event) {
        String selectedCountryName = countryCB.getSelectionModel().getSelectedItem();
        Country selectedCountry = DBCountries.getCountryByName(selectedCountryName);

        ObservableList<String> divisions = FXCollections.observableArrayList();
        DBFLDivisions.getAllDivisions().stream()
                .filter(country -> country.getCountryId() == selectedCountry.getCountryID()) //lambda
                .forEach(division -> divisions.add(division.getDivisionName()));
        divisionCB.setItems(divisions);
        divisionCB.setDisable(false);
        divisionCB.setPromptText("Select a Division");
    }


}
