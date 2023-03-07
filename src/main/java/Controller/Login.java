package Controller;

import Database.DBUsers;
import Utility.ChangeView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Patrick Kell
 */
public class Login implements Initializable {

    ChangeView viewController = new ChangeView();

    public TextField username;
    public TextField password;
    public Label loginError;
    public AnchorPane anchor;


    public void onLogin(ActionEvent actionEvent) throws IOException, SQLException {

        String name = username.getText();
        String pass = password.getText();

        if (DBUsers.login(name, pass)) {
            viewController.changeViewToMain(actionEvent);
        }

        loginError.setText("Your username/password combination was incorrect.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> anchor.requestFocus()); // prevents the TextFields from being in focus on start up
    }
}
