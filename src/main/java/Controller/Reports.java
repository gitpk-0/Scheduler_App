package Controller;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize contact combo box options
        ObservableList<String> contacts = FXCollections.observableArrayList();
        DBContacts.getAllContacts().stream().forEach(contact -> contacts.add(contact.toString())); // lambda
        contactCB.setItems(contacts);

        // initialize country combo box with all countries
        ObservableList<String> countries = FXCollections.observableArrayList();
        DBCountries.getAllCountries().stream()
                .forEach(country -> countries.add(country.getCountryName())); // lambda
        countryCB.setItems(countries);

        // initialize type combo box with all appointment types
        ObservableList<String> types = FXCollections.observableArrayList();
        DBAppointments.getAllAppointmentTypes().stream()
                .forEach(type -> types.add(type)); // lambda
        typeCB.setItems(types);

        // initialize month combo box with all months
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October",
                "November", "December");
        monthCB.setItems(months);
    }

    public void toMainMenu(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }

    @FXML
    public void filterByContact(ActionEvent event) {
        String contact = contactCB.getSelectionModel().getSelectedItem();
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

    @FXML
    public void filterByCountry(ActionEvent event) {
        String country = countryCB.getSelectionModel().getSelectedItem();

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

    @FXML
    public void updateTotal(ActionEvent event) {
    }


}