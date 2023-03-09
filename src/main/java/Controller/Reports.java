package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Reports {

    public TableView apptTableView;
    public TableColumn apptId_tc;
    public TableColumn title_tc;
    public TableColumn desc_tc;
    public TableColumn loc_tc;
    public TableColumn contId_tc;
    public TableColumn type_tc;
    public TableColumn sDate_tc;
    public TableColumn sTime_tc;
    public TableColumn eTime_tc;
    public TableColumn eDate_tc;
    public TableColumn custId_tc;
    public TableColumn userId_tc;
    public ComboBox contactCombo;
    public TableView customerTableView;
    public TableColumn customerId_tc;
    public TableColumn name_tc;
    public TableColumn address_tc;
    public TableColumn postal_tc;
    public TableColumn phone_tc;
    public TableColumn country_tc;
    public TableColumn state_tc;
    public ComboBox countryCombo;
    public ComboBox monthCombo;
    public ComboBox typeCombo;
    public Label totalLbl;

    public void toMainMenu(ActionEvent event) {
    }

    @FXML
    public void filterByContact(ActionEvent event) {
    }

    @FXML
    public void filterByCountry(ActionEvent event) {
    }

    @FXML
    public void updateTotal(ActionEvent event) {
    }
}