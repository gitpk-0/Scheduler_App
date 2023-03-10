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

public class MainProgram extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainProgram.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 380, 420);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, IOException {
        // Locale.setDefault(new Locale("fr", "FR"));
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
        LoginTracker.cleanUp();
    }
}