package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * @author Patrick Kell
 */
public class ModifyAppointment {

    public TextField apptIdTF;
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

    public void onCancel(ActionEvent actionEvent) {
    }

    public void onSaveAppt(ActionEvent actionEvent) {
    }
}
