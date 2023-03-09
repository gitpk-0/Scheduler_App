package Controller;

import Database.DBAppointments;
import Model.Appointment;
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
    public TableColumn<Appointment, String> contId_tc;
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
    public TableView customerTableView;
    public TableColumn customerId_tc;
    public TableColumn name_tc;
    public TableColumn address_tc;
    public TableColumn postal_tc;
    public TableColumn phone_tc;
    public TableColumn country_tc;
    public TableColumn state_tc;


    /**
     * Initialize method which initializes the MainMenu controller class once its root element has been
     * completely processed.
     *
     * @param url            The FXML location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // assigning the Inventory.allAppointments Observable list to work with the apptsTableView Table
        apptTableView.setItems(DBAppointments.getAllAppointments());
        // assigning the values to populate each column with
        // apptId_tc.setCellValueFactory(new PropertyValueFactory<>("apptId")); // calls the getApptId Appointment method
        title_tc.setCellValueFactory(new PropertyValueFactory<>("title")); // calls the getTitle Appointment method
        desc_tc.setCellValueFactory(new PropertyValueFactory<>("description")); // calls the getDescription Appointment method
        loc_tc.setCellValueFactory(new PropertyValueFactory<>("location")); // calls the getLocation Appointment method

        // // assigning the Inventory.allProducts Observable list to work with the productsTableView Table
        // customerTableView.setItems(null/* DATABASE QUERY */);
        // // assigning the values to populate each column with
        // prodIdTCol.setCellValueFactory(new PropertyValueFactory<>("id")); // calls the getId Product method
        // prodNameTCol.setCellValueFactory(new PropertyValueFactory<>("name")); // calls the getName Product method
        // prodInvTCol.setCellValueFactory(new PropertyValueFactory<>("stock")); // calls the getStock Product method
        // prodPriceTCol.setCellValueFactory(new PropertyValueFactory<>("price")); // calls the getPrice Product method
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

    public void toModifyApptView(ActionEvent event) {
    }

    public void onDeleteAppt(ActionEvent event) {
    }

    public void toAddCustomerView(ActionEvent event) {
    }

    public void toModifyCustomerView(ActionEvent event) {
    }

    public void onDeleteCustomer(ActionEvent event) {
    }

    public void toReportsView(ActionEvent event) {
    }

    public void filterThisMonth(ActionEvent event) {
    }

    public void filterThisWeek(ActionEvent event) {
    }

    public void showAllAppts(ActionEvent event) {
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
