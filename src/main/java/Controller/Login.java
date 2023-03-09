package Controller;

import Database.DBUsers;
import Utility.ChangeView;
import Utility.LoginTracker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Patrick Kell
 */
public class Login implements Initializable {

    ChangeView viewHandler = new ChangeView();
    LoginTracker loginTracker = new LoginTracker();

    ZoneId zone = ZoneId.systemDefault();
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    public AnchorPane anchor;
    public TextField usernameTF;
    public PasswordField passwordPF;
    public Label logo;
    public Label credentials;
    public Button loginBtn;
    public Label loginError;
    public Label zoneId;

    /**
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void onLogin(ActionEvent event) throws IOException, SQLException {
        String username = usernameTF.getText();
        String password = passwordPF.getText();
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault());

        if (DBUsers.login(username, password) && !username.isEmpty() && !password.isEmpty()) {
            // valid username & password entered
            loginTracker.log(username, dateTime, true);
            viewHandler.changeViewToMain(event);
        } else { // invalid username or password entered
            loginTracker.log(username, dateTime, false);
            usernameTF.clear();
            passwordPF.clear();
            loginError.setVisible(true);
        }

    }

    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> anchor.requestFocus()); // prevents the TextFields from being in focus on start up
        logo.setText(rb.getString("login"));
        credentials.setText(rb.getString("credentials"));
        usernameTF.setPromptText(rb.getString("username"));
        passwordPF.setPromptText(rb.getString("password"));
        loginBtn.setText(rb.getString("login"));
        loginError.setText(rb.getString("error"));
        zoneId.setText(String.valueOf(zone));
    }
}
