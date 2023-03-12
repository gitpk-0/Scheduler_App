package Controller;

/**
 * @author Patrick Kell
 */

import Database.DBAppointments;
import Database.DBCustomers;
import Database.DBUsers;
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
 * The MainMenu controller class which acts as the main page for the application. Allows users to view, Add, Modify,
 * and Delete Appointments and Customers, redirects to the Reports screen and Exit the application.
 */
public class MainMenu implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user
    private boolean login = true;

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

        if (Login.login) loginAlert();
    }

    public void loginAlert() {
        int user = DBUsers.currentUserId;
        Appointment apptNear = DBAppointments.hasAppointmentSoon(user);

        if (apptNear != null) {
            Login.login = false;
            alerts.appointmentSoon(apptNear.getApptId(), apptNear.getStartDate(), apptNear.getStartTime());
        } else {
            Login.login = false;
            alerts.noAppointmentSoon();
        }
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

    /**
     * Redirect the user to the ModifyAppointment screen
     *
     * @param event Modify Appointment button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     * @throws IOException Signals that an SQLException exception has occurred
     */
    public void toModifyApptView(ActionEvent event) throws IOException, SQLException {
        Appointment appt = apptTableView.getSelectionModel().getSelectedItem(); // get the selected appointment
        if (appt == null) { // no appointment selected
            alerts.nullSelection("Appointment", "modify"); // alert the user
            return;
        }

        // pass the event, view, and apptTableView to the changeViewModify method
        viewController.changeViewToModify(event, "Modify Appointment ", apptTableView);
    }

    /**
     * Delete the selected Appointment or alert user no Appointment to be deleted is selected
     *
     * @param event Delete Appointment button clicked
     * @throws SQLException Signals that a SQLException exception has occurred
     */
    public void onDeleteAppt(ActionEvent event) throws SQLException {
        Appointment appt = apptTableView.getSelectionModel().getSelectedItem(); // get the selected appointment
        if (appt == null) { // no appointment selected
            alerts.nullSelection("Appointment", "modify"); // alert the user
            return;
        }

        if (alerts.confirmDelete("Appointment")) { // if Ok button clicked
            if (DBAppointments.deleteAppointment(appt)) { // if appointment was deleted
                apptTableView.setItems(DBAppointments.getAppointments("all")); // reset the table view
            }
        }
    }

    /**
     * Redirect the user to the Add Customer screen
     *
     * @param event Add Customer button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void toAddCustomerView(ActionEvent event) throws IOException {
        viewController.changeViewToAdd(event, "Add Customer ");
    }

    /**
     * Redirect the user to the Modify Customer screen
     *
     * @param event Modify Customer button clicked
     * @throws IOException  Signals that an Input/Output exception has occurred
     * @throws SQLException Signals that an SQLException exception has occurred
     */
    public void toModifyCustomerView(ActionEvent event) throws IOException, SQLException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem(); // get the selected customer
        if (customer == null) { // no customer selected
            alerts.nullSelection("Customer", "modify"); // alert the user
            return;
        }

        // pas the event, view, and customerTableView to the changeViewModify method
        viewController.changeViewToModify(event, "Modify Customer ", customerTableView);
    }

    /**
     * Delete the selected Customer or alert user no Customer to be deleted is selected
     *
     * @param event Delete Customer button clicked
     * @throws SQLException Signals that a SQLException exception has occurred
     */
    public void onDeleteCustomer(ActionEvent event) throws SQLException {
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if (customer == null) { // no customer selected
            alerts.nullSelection("Customer", "modify"); // alert the user
            return;
        }

        if (DBAppointments.getApptCountByCustomer(customer) > 0) { // Customer has appointments
            alerts.associatedApptsError(); // alert the user
            return;
        }

        if (alerts.confirmDelete("Customer")) { // if Ok button clicked
            if (DBCustomers.deleteCustomer(customer)) { // if Customer was deleted
                customerTableView.setItems(DBCustomers.getAllCustomers()); // reset the table view
            }
        }

    }

    /**
     * Redirect the user to the Reports screen
     *
     * @param event View Reports button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void toReportsView(ActionEvent event) throws IOException {
        viewController.changeViewToReports(event);
    }

    /**
     * Filter the Appointments Table View by Appointments this month
     *
     * @param event This Month radio button clicked
     */
    public void filterThisMonth(ActionEvent event) {
        apptTableView.setItems(DBAppointments.getAppointments("month"));
    }

    /**
     * Filter the Appointments Table View by Appointments this week
     *
     * @param event This Week radio button clicked
     */
    public void filterThisWeek(ActionEvent event) {
        apptTableView.setItems(DBAppointments.getAppointments("week"));
    }


    /**
     * Show all Appointments in the Appointments Table View
     *
     * @param event All Appointments radio button clicked
     */
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
