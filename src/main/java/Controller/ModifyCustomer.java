package Controller;

import Model.Customer;
import Utility.Alerts;
import Utility.ChangeView;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author Patrick Kell
 */
public class ModifyCustomer {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user

    public TextField custIdTF;
    public TextField nameTF;
    public TextField phoneTF;
    public TextField addressTF;
    public ComboBox countryCombo;
    public ComboBox divisionCombo;
    public TextField postalTF;

    public void onSaveCustomer(ActionEvent event) {
    }

    public void sendCustomer(Customer selectedItem) {
    }

    public void onCancel(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }
}
