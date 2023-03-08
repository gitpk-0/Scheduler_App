package Utility;

/**
 * @author Patrick Kell
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Utility class which manages the changing of views between screens
 */
public class ChangeView {

    Stage stage;
    Parent scene;

    /**
     * changeViewToMain method used to redirect the user back to the Main Menu
     *
     * @param event Save or Cancel button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void changeViewToMain(ActionEvent event) throws IOException {
        // casting the event source to a Button type, then to a Stage type, and assigning it to the stage object
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Inventory Management System");
        stage.centerOnScreen(); // center the stage to the users screen
        stage.show();
    }

    /**
     * changeViewToAdd method to change view to specified screen
     *
     * @param event Add [Part or Product] Button clicked
     * @param view  View screen to be redirected to
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void changeViewToAdd(ActionEvent event, String view) throws IOException {
        // casting the event source to a Button type, then to a Stage type, and assigning it to the stage object
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        // remove all whitespace from view string
        scene = FXMLLoader.load(getClass().getResource("/View/" + view.replaceAll("\\s", "") + ".fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle(view + "Menu");
        stage.show();
    }

    /**
     * changeViewToModify method to change view to specified screen
     * facilitates passing of part/product data to text fields
     *
     * @param event Modify [Part or Product] Button clicked
     * @param view  View screen to be redirected to
     * @param table TableView Object used to find the selected part/product to be modified
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void changeViewToModify(ActionEvent event, String view, TableView table) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/" + view.replaceAll("\\s", "") + ".fxml"));
        loader.load();

        // if (view.equals("Modify Part ")) {
        //     ModifyPart mpc = loader.getController();
        //     // casting the selected object from the table to a Part object
        //     mpc.sendPart((Part) table.getSelectionModel().getSelectedItem(), event);
        // } else if (view.equals("Modify Product ")) {
        //     ModifyProduct mpc = loader.getController();
        //     // casting the selected object from the table to a Product object
        //     mpc.sendProduct((Product) table.getSelectionModel().getSelectedItem());
        // }

        // casting the event source to a Button type, then to a Stage type, and assigning it to the stage object
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.setTitle(view + "Menu");
        stage.show();
    }
}
