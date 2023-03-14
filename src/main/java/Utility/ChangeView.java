package Utility;

/**
 * @author Patrick Kell
 */

import Controller.Login;
import Controller.ModifyAppointment;
import Controller.ModifyCustomer;
import Model.Appointment;
import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * ChangeView utility class which manages the changing of views between screens
 */
public class ChangeView {

    Stage stage;
    Parent scene;

    /**
     * changeViewToMain method used to redirect the user back to the Main Menu
     *
     * @param event Save or Cancel button clicked
     * @throws IOException Signals an Input/Output exception has occurred
     */
    public void changeViewToMain(ActionEvent event) throws IOException {
        // casting the event source to a Button type, then to a Stage type, and assigning it to the stage object
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        if (Login.login) stage.close(); // closes the login window to allow the login alert to show up on main menu screen
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage.setScene(new Scene(scene, 1234, 841));
        stage.setTitle("Appointment Scheduling System");
        stage.centerOnScreen(); // center the stage to the users screen
        stage.show();
    }

    /**
     * changeViewToAdd method to change view to specified screen
     *
     * @param event Add [Appointment or Customer] Button clicked
     * @param view  View screen to be redirected to
     * @throws IOException Signals an Input/Output exception has occurred
     */
    public void changeViewToAdd(ActionEvent event, String view) throws IOException {
        // casting the event source to a Button type, then to a Stage type, and assigning it to the stage object
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // remove all whitespace from view string
        scene = FXMLLoader.load(getClass().getResource("/View/" + view.replaceAll("\\s", "") + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle(view);
        stage.centerOnScreen(); // center the stage to the users screen
        stage.show();
    }

    /**
     * changeViewToModify method to change view to specified screen
     * facilitates passing of appointment/customer data to populate form fields
     *
     * @param event Modify [Appointment or Customer] Button clicked
     * @param view  View screen to be redirected to
     * @param table TableView Object used to find the selected appointment/customer to be modified
     * @throws IOException Signals an Input/Output exception has occurred  Signals that an Input/Output exception has occurred
     * @throws SQLException Signals an SQLException has occurred Signals that an SQLException exception has occurred
     */
    public void changeViewToModify(ActionEvent event, String view, TableView table) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/" + view.replaceAll("\\s", "") + ".fxml"));
        loader.load();

        if (view.equals("Modify Appointment ")) {
            ModifyAppointment mpc = loader.getController();
            // casting the selected object from the table to a Appointment object
            mpc.sendAppt((Appointment) table.getSelectionModel().getSelectedItem());
        } else if (view.equals("Modify Customer ")) {
            ModifyCustomer mpc = loader.getController();
            // casting the selected object from the table to a Customer object
            mpc.sendCustomer((Customer) table.getSelectionModel().getSelectedItem(), event);
        }

        // casting the event source to a Button type, then to a Stage type, and assigning it to the stage object
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle(view);
        stage.centerOnScreen(); // center the stage to the users screen
        stage.show();
    }

    /**
     * changeViewToReports method used to redirect the user to the Reports screen
     *
     * @param event View Reports button clicked
     * @throws IOException Signals an Input/Output exception has occurred
     */
    public void changeViewToReports(ActionEvent event) throws IOException {
        // casting the event source to a Button type, then to a Stage type, and assigning it to the stage object
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Reports.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Reports");
        stage.centerOnScreen(); // center the stage to the users screen
        stage.show();
    }
}