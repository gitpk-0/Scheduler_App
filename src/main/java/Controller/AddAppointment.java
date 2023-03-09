package Controller;

import Utility.Alerts;
import Utility.ChangeView;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * @author Patrick Kell
 */
public class AddAppointment {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private Alerts alerts = new Alerts(); // manages the alerts to the user

    public TextField titleTF;
    public TextField descTF;
    public TextField locationTF;
    public DatePicker startDatePick;
    public DatePicker endDatePick;
    public ComboBox userIdCombo;
    public ComboBox custIdCombo;
    public ComboBox contactCombo;
    public ComboBox endTimeCombo;
    public ComboBox startTimeCombo;
    public TextField typeTF;


    public void onSaveAppt(ActionEvent event) {
    }

    public void onCancel(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }
}
