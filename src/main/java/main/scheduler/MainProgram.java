package main.scheduler;

/**
 * @author Patrick Kell
 */

import Utility.JDBC;
import Utility.LoginTracker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

/**
 * MainProgram class of the application which inherits the Application class which sets the stage for the application.
 */
public class MainProgram extends Application {

    /**
     * The entry point method of the JavaFX application where all the graphics code of JavaFX is executed
     *
     * @param stage The window in which the application appears in
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainProgram.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 380, 420);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The Scheduler application's entry point
     *
     * @param args An array of command-line arguments for the application
     * @throws SQLException Signals an SQLException has occurred
     * @throws IOException  Signals that an Input/Output exception has occurred
     */
    public static void main(String[] args) throws SQLException, IOException {
        // Locale.setDefault(new Locale("fr", "FR"));
        JDBC.openConnection(); // open the connection to the database
        launch(); // launch the JavaFX application
        JDBC.closeConnection(); //  close the connection to the database
        LoginTracker.cleanUp(); // close the login_activity.txt file
    }
}