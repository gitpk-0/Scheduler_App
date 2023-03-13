package Controller;

/**
 * @author Patrick Kell
 */

import Database.DBCountries;
import Database.DBCustomers;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The AddCustomer controller class which enables the user to create new customers
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

    /**
     * Initialize the Countries combo box to all distinct Countries listed in the database
     * <p>
     * This method uses a lambda expression to add each country to the countries list. The lambda requires less
     * code to be written than potential alternatives, such as a for loop.
     *
     * @param url            The FXML location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> countries = FXCollections.observableArrayList();
        DBCountries.getAllCountries().stream()
                .forEach(country -> countries.add(country.getCountryName())); // lambda
        countryCB.setItems(countries);
    }

    /**
     * The onSaveCustomer method which manages the creation of a new customer.
     * <p>
     * The user enters information about a new customer, which is then compiled and added to the customers
     * table of the client_schedule database.
     * <p>
     * This method calls the form validation class which validates the data and alerts there are problems with the
     * input data.
     *
     * @param event Save Customer button clicked
     */
    public void onSaveCustomer(ActionEvent event) {
        FormValidation validator = new FormValidation(); // creates a new FormValidation object each time Save is clicked
        // null value checks
        ArrayList<String> errors = validator.nullValueCheckCustomer(nameTF, phoneTF, addressTF, postalTF, countryCB, divisionCB);

        try {
            if (errors.isEmpty()) { // no null value error, all input data is valid
                int id = IDGenerator.customerIDGenerator();
                String name = nameTF.getText();
                String phone = phoneTF.getText();
                String address = addressTF.getText();
                String postal = postalTF.getText();
                String country = countryCB.getSelectionModel().getSelectedItem();
                String division = divisionCB.getSelectionModel().getSelectedItem();
                int divisionId = DBFLDivisions.getDivisionByName(division).getDivisionId();

                // add the customer to the client_schedule database
                DBCustomers.addCustomer(id, name, phone, address, postal, country, divisionId);
                viewController.changeViewToMain(event); // redirect the user back to the main menu
            } else { // errors list is not empty
                throw new Exception(); // redirect to the catch block below
            }
        } catch (Exception e) {
            // System.out.println("AddCustomer Error: " + e.getMessage());
            alerts.inputError(errors); // display the errors to the user
        }
    }

    /**
     * The user selected a Country from the Countries combo box
     * <p>
     * Enable the Division combo box to be selected and filter the items based on divisions within the
     * selected Country
     * <p>
     * This method utilizes two lambda expressions to assist in retrieving the necessary divisions to display to
     * the user. With each lambda expression utilizing only one line of code, their functionality can be
     * easily understood.
     *
     * @param event User selected a Country
     */
    public void countrySelected(ActionEvent event) {
        String selectedCountryName = countryCB.getSelectionModel().getSelectedItem();
        Country selectedCountry = DBCountries.getCountryByName(selectedCountryName);

        ObservableList<String> divisions = FXCollections.observableArrayList();
        DBFLDivisions.getAllDivisions().stream()
                .filter(country -> country.getCountryId() == selectedCountry.getCountryID()) // lambda
                .forEach(division -> divisions.add(division.getDivisionName())); // lambda
        divisionCB.setItems(divisions);
        divisionCB.setDisable(false);
        divisionCB.setPromptText("Select a Division");
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