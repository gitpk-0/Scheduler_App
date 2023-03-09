package Controller;

import Database.DBAppointments;
import Database.DBCustomers;
import Model.Appointment;
import Model.Customer;
import Utility.Alerts;
import Utility.ChangeView;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Patrick Kell
 */
public class MainMenu implements Initializable {

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
    public RadioButton thisMonthRb;
    public RadioButton thisWeekRb;
    public RadioButton allApptsRb;
    public TableView<Customer> customerTableView;
    public TableColumn<Customer, Integer> customerId_tc;
    public TableColumn<Customer, String> name_tc;
    public TableColumn<Customer, String> address_tc;
    public TableColumn<Customer, String> postal_tc;
    public TableColumn<Customer, String> phone_tc;
    public TableColumn<Customer, String> country_tc;
    public TableColumn<Customer, String> state_tc;


    /**
     * Initialize method which initializes the MainMenu controller class once its root element has been
     * completely processed.
     *
     * @param url            The FXML location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // assigning the allAppointments Observable list to work with the apptsTableView Table
        apptTableView.setItems(DBAppointments.getAppointments("all"));

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

        // assigning the AllCustomers Observable list to work with the customerTableView Table
        customerTableView.setItems(DBCustomers.getAllCustomers());
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
     * Redirect the user to the AddAppointment screen
     *
     * @param event Add (Appointment) button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void toAddApptView(ActionEvent event) throws IOException {
        viewController.changeViewToAdd(event, "Add Appointment ");
    }

    public void toModifyApptView(ActionEvent event) throws IOException {
        Appointment appt = apptTableView.getSelectionModel().getSelectedItem();
        if (appt == null) { // no appointment selected
            alerts.nullSelection("Appointment", "modify"); // alert the user
            return;
        }

        // pass the event, view, and apptTableView to the changeViewModify method
        viewController.changeViewToModify(event, "Modify Appointment ", apptTableView);
    }

    public void onDeleteAppt(ActionEvent event) throws SQLException {
        Appointment appt = apptTableView.getSelectionModel().getSelectedItem();
        if (appt == null) { // no appointment selected
            alerts.nullSelection("Appointment", "modify"); // alert the user
            return;
        }

        if (alerts.confirmDelete("Appointment")) {
            if (DBAppointments.deleteAppointment(appt)) {
                apptTableView.setItems(DBAppointments.getAppointments("all"));
            }
        }
    }

    public void toAddCustomerView(ActionEvent event) throws IOException {
        viewController.changeViewToAdd(event, "Add Customer ");
    }

    public void toModifyCustomerView(ActionEvent event) throws IOException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if (customer == null) { // no customer selected
            alerts.nullSelection("Customer", "modify"); // alert the user
            return;
        }

        // pas the event, view, and customerTableView to the changeViewModify method
        viewController.changeViewToModify(event, "Modify Customer ", customerTableView);
    }

    public void onDeleteCustomer(ActionEvent event) throws SQLException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if (customer == null) { // no customer selected
            alerts.nullSelection("Customer", "modify"); // alert the user
            return;
        }

        if (alerts.confirmDelete("Customer")) {
            if (DBCustomers.deleteCustomer(customer)) {
                customerTableView.setItems(DBCustomers.getAllCustomers());
            }
        }

    }

    public void toReportsView(ActionEvent event) throws IOException {
        viewController.changeViewToReports(event);
    }

    public void filterThisMonth(ActionEvent event) {
        apptTableView.setItems(DBAppointments.getAppointments("month"));
    }

    public void filterThisWeek(ActionEvent event) {
        apptTableView.setItems(DBAppointments.getAppointments("week"));
    }

    public void showAllAppts(ActionEvent event) {
        apptTableView.setItems(DBAppointments.getAppointments("all"));
    }

    /**
     * Exit the program
     *
     * @param event Exit Button clicked
     */
    public void onActionExit(ActionEvent event) {
        System.exit(0); // close the application
    }

}
