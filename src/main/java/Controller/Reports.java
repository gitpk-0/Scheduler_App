package Controller;

/**
 * @author Patrick Kell
 */

import Database.DBAppointments;
import Database.DBContacts;
import Database.DBCountries;
import Database.DBCustomers;
import Model.Appointment;
import Model.Customer;
import Utility.Alerts;
import Utility.ChangeView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Reports controller class which manages the reports screen functionality
 */
public class Reports implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user

    public TableView<Appointment> apptTableView;
    public TableColumn<Appointment, Integer> apptId_tc;
    public TableColumn<Appointment, String> title_tc;
    public TableColumn<Appointment, String> desc_tc;
    public TableColumn<Appointment, String> loc_tc;
    public TableColumn<Appointment, String> contactName_tc;
    public TableColumn<Appointment, String> type_tc;
    public TableColumn<Appointment, String> sDate_tc;
    public TableColumn<Appointment, String> sTime_tc;
    public TableColumn<Appointment, String> eTime_tc;
    public TableColumn<Appointment, String> eDate_tc;
    public TableColumn<Appointment, Integer> custId_tc;
    public TableColumn<Appointment, Integer> userId_tc;
    public ComboBox<String> contactCB;
    public TableView<Customer> customerTableView;
    public TableColumn<Customer, Integer> customerId_tc;
    public TableColumn<Customer, String> name_tc;
    public TableColumn<Customer, String> address_tc;
    public TableColumn<Customer, String> postal_tc;
    public TableColumn<Customer, String> phone_tc;
    public TableColumn<Customer, String> country_tc;
    public TableColumn<Customer, String> state_tc;
    public ComboBox<String> countryCB;
    public ComboBox<String> monthCB;
    public ComboBox<String> typeCB;
    public Label totalLbl;
    public Button calculateBtn;

    /**
     * Initialize method which initializes the Reports controller class.
     * <p>
     * Each of the combo boxes is initialized based on the data received from the client_schedule database
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

        // Initialize country combo box with all countries
        ObservableList<String> countries = FXCollections.observableArrayList();
        DBCountries.getAllCountries().stream()
                .forEach(country -> countries.add(country.getCountryName())); // lambda
        countryCB.setItems(countries);

        // Initialize type combo box with all appointment types
        ObservableList<String> types = FXCollections.observableArrayList();
        DBAppointments.getAllAppointmentTypes().stream()
                .forEach(type -> types.add(type)); // lambda
        typeCB.setItems(types);

        // Initialize month combo box with all months
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
                "November", "December");
        monthCB.setItems(months);
    }

    /**
     * User clicked the Return to Main Menu button
     *
     * @param event Return to Main Menu button clicked
     * @throws IOException Signals an Input/Output exception has occurred
     */
    public void toMainMenu(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }

    /**
     * Filter By Contact method which displays the appointments based on the contact the user selected
     *
     * @param event User selected a contact from the contact combo box
     */
    @FXML
    public void filterByContact(ActionEvent event) {
        String contact = contactCB.getSelectionModel().getSelectedItem(); // get the selected contact
        // assigning the getAppointmentsByContact Observable list to work with the apptsTableView Table
        apptTableView.setItems(DBAppointments.getAppointmentsByContact(contact));

        /* assigning the values to populate each column with, each new PropertyValueFactory object calls the
        getter method for the appropriate Appointment object's variable */
        apptId_tc.setCellValueFactory(new PropertyValueFactory<>("apptId"));
        title_tc.setCellValueFactory(new PropertyValueFactory<>("title"));
        desc_tc.setCellValueFactory(new PropertyValueFactory<>("description"));
        loc_tc.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactName_tc.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        type_tc.setCellValueFactory(new PropertyValueFactory<>("type"));
        sDate_tc.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        sTime_tc.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        eTime_tc.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        eDate_tc.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        custId_tc.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId_tc.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Filter By Country method which displays the customers based on the country the user selected
     *
     * @param event User selected a country from the country combo box
     */
    @FXML
    public void filterByCountry(ActionEvent event) {
        String country = countryCB.getSelectionModel().getSelectedItem(); // get the selected country

        // assigning the getCustomersByCountry Observable list to work with the customerTableView Table
        customerTableView.setItems(DBCustomers.getCustomersByCountry(country));

        /* assigning the values to populate each column with, each new PropertyValueFactory object calls the
        getter method for the appropriate Customer object's variable */
        customerId_tc.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        name_tc.setCellValueFactory(new PropertyValueFactory<>("name"));
        address_tc.setCellValueFactory(new PropertyValueFactory<>("address"));
        postal_tc.setCellValueFactory(new PropertyValueFactory<>("postal"));
        phone_tc.setCellValueFactory(new PropertyValueFactory<>("phone"));
        country_tc.setCellValueFactory(new PropertyValueFactory<>("country"));
        state_tc.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    /**
     * Calculate the total number of appointments by type and month
     *
     * @param event Calculate Total Appointments button clicked
     */
    public void calculate(ActionEvent event) {
        boolean typeSelected = typeCB.getSelectionModel().isEmpty();
        boolean monthSelected = monthCB.getSelectionModel().isEmpty();

        if (!typeSelected && !monthSelected) { // both combo boxes have selections
            String type = typeCB.getValue(); // appointment type
            String month = monthCB.getValue(); // month

            totalLbl.setText(String.valueOf(DBAppointments.getCountApptsByTypeAndMonth(type, month))); // display the total
        } else {
            alerts.nullSelectionReports(); // alert the user
        }
    }

    /**
     * Clears the total label when a different appointment type is selected
     *
     * @param event User selected a new appointment type
     */
    public void selectAppt(ActionEvent event) {
        totalLbl.setText("");
    }

    /**
     * Clears the total label when a different month is selected
     *
     * @param event User selected a new month
     */
    public void selectMonth(ActionEvent event) {
        totalLbl.setText("");
    }
}