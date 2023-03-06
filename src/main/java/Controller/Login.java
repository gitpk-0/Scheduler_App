package Controller;

import Database.DBUsers;
import Utility.ChangeView;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Patrick Kell
 */
public class Login {

    ChangeView viewController = new ChangeView();

    public TextField username;
    public TextField password;
    public Label loginError;

    public void onLogin(ActionEvent actionEvent) throws IOException, SQLException {

        String name = username.getText();
        String pass = password.getText();

        if (DBUsers.login(name, pass)) {
            viewController.changeViewToMain(actionEvent);
        }

        loginError.setText("Your username/password combination was incorrect.");
    }
}
