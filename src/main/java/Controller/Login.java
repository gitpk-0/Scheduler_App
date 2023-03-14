package Controller;

/**
 * @author Patrick Kell
 */

import Database.DBUsers;
import Utility.Alerts;
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
 * The Login controller class which manages the login functionality
 */
public class Login implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private LoginTracker loginTracker = new LoginTracker(); // manages the tracking of login data

    // ZoneId zone = ZoneId.of("Asia/Tokyo");
    ZoneId zone = ZoneId.systemDefault();
    ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());

    public static boolean login = true;

    public AnchorPane anchor;
    public TextField usernameTF;
    public PasswordField passwordPF;
    public Label logo;
    public Label credentials;
    public Button loginBtn;
    public Label loginError;
    public Label zoneId;

    /**
     * Initializes the login screen, sets the text to the appropriate language
     *
     * @param url            The FXML location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
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
        zoneId.setText(String.valueOf(zone)); // Zone of the user
    }

    /**
     * The onLogin method which manages the login functionality
     * <p>
     * Each login attempt is tracked and logged in the login_activity.txt file
     *
     * @param event Login button clicked
     * @throws IOException  Signals an Input/Output exception has occurred
     * @throws SQLException Signals an SQLException has occurred
     */
    public void onLogin(ActionEvent event) throws IOException, SQLException {
        String username = usernameTF.getText();
        String password = passwordPF.getText();
        ZonedDateTime dateTime = ZonedDateTime.now(zone); // ZoneDateTime of user's local zone

        if (DBUsers.login(username, password) && !username.isEmpty() && !password.isEmpty()) {
            // valid username & password entered
            loginTracker.log(username, dateTime, true); // log the successful login attempt
            viewController.changeViewToMain(event); // redirect to the main menu screen
            Alerts.onLogin(); // alert the user if an appointment is scheduled in 15 minutes or less
        } else { // invalid username or password entered
            loginTracker.log(username, dateTime, false); // log the failed login attempt
            usernameTF.clear(); // clear the username text field
            passwordPF.clear(); // clear the password text field
            loginError.setVisible(true); // set the login error message visibility to true
        }
    }
}
