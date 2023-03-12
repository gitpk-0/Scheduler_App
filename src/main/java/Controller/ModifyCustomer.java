package Controller;

import Database.DBAppointments;
import Database.DBCountries;
import Database.DBCustomers;
import Database.DBFLDivisions;
import Model.Country;
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
public class ModifyCustomer implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user

    public TextField custIdTF;
    public TextField nameTF;
    public TextField phoneTF;
    public TextField addressTF;
    public TextField postalTF;
    public ComboBox<String> countryCB;
    public ComboBox<String> divisionCB;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize country combo box with all countries
        ObservableList<String> countries = FXCollections.observableArrayList();
        DBCountries.getAllCountries().stream()
                .forEach(country -> countries.add(country.getCountryName())); // lambda
        countryCB.setItems(countries);

        ObservableList<String> divisions = FXCollections.observableArrayList();
        DBFLDivisions.getAllDivisions().stream()
                .forEach(division -> divisions.add(division.getDivisionName())); // lambda
        divisionCB.setItems(divisions);
    }

    public void onSaveCustomer(ActionEvent event) {
        System.out.println(divisionCB.getSelectionModel().getSelectedItem());
        System.out.println(divisionCB.getSelectionModel().isEmpty());

        FormValidation validator = new FormValidation(); // creates a new FormValidation object each time Save is clicked
        // null value checks
        ArrayList<String> errors = validator.nullValueCheckCustomer(nameTF, phoneTF, addressTF, postalTF, countryCB, divisionCB);

        try {
            if (errors.isEmpty()) { // no null value errors
                // all input data is valid
                int id = Integer.parseInt(custIdTF.getText());
                String name = nameTF.getText();
                String phone = phoneTF.getText();
                String address = addressTF.getText();
                String postal = postalTF.getText();
                String division = divisionCB.getSelectionModel().getSelectedItem();
                int divisionId = DBFLDivisions.getDivisionByName(division).getDivisionId();

                // // update the customer in the client_schedule database
                DBCustomers.updateCustomer(id, name, phone, address, postal, divisionId);
                viewController.changeViewToMain(event); // redirect the user back to the main menu
            } else { // errors list is not empty
                throw new Exception(); // redirect to the catch block below
            }
        } catch (Exception e) {
            System.out.println("ModifyCustomer Error: " + e.getMessage());
            alerts.inputError(errors); // display the errors to the user
        }

    }

    public void sendCustomer(Customer customer, ActionEvent event) {
        custIdTF.setText(String.valueOf(customer.getCustomerId()));
        nameTF.setText(customer.getName());
        phoneTF.setText(customer.getPhone());
        addressTF.setText(customer.getAddress());
        postalTF.setText(customer.getPostal());
        countryCB.setValue(customer.getCountry());
        divisionCB.setValue(customer.getDivision());
        countrySelected(event); // enables the proper filtering of divisions, all divisions shown if this is removed
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
    }


}
