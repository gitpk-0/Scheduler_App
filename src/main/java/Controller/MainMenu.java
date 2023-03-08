package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author Patrick Kell
 */
public class MainMenu {

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

    public void onActionExit(ActionEvent actionEvent) {
    }

    public void toAddApptView(ActionEvent actionEvent) {
    }

    public void toModifyApptView(ActionEvent actionEvent) {
    }

    public void onDeleteAppt(ActionEvent actionEvent) {
    }

    public void toAddCustomerView(ActionEvent actionEvent) {
    }

    public void toModifyCustomerView(ActionEvent actionEvent) {
    }

    public void onDeleteCustomer(ActionEvent actionEvent) {
    }

    public void toReportsView(ActionEvent actionEvent) {
    }

    public void filterThisMonth(ActionEvent actionEvent) {
    }

    public void filterThisWeek(ActionEvent actionEvent) {
    }

    public void showAllAppts(ActionEvent actionEvent) {
    }
}
