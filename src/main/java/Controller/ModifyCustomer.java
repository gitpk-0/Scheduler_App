package Controller;

import Model.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * @author Patrick Kell
 */
public class ModifyCustomer {

    public TextField custIdTF;
    public TextField nameTF;
    public TextField phoneTF;
    public TextField addressTF;
    public ComboBox countryCombo;
    public ComboBox divisionCombo;
    public TextField postalTF;

    public void onSaveCustomer(ActionEvent event) {
    }

    public void onCancel(ActionEvent event) {
    }

    public void sendCustomer(Customer selectedItem) {
    }
}
