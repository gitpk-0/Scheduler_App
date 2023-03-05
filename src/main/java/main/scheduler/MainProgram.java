package main.scheduler;

import Database.FruitsQuery;
import Utility.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

public class MainProgram extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainProgram.class.getResource("/View/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1020, 840);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();

        int rowsAffected = FruitsQuery.insert("Cherries", 1);

        if (rowsAffected > 0) {
            System.out.println("Insert successful");
        } else {
            System.out.println("Insert failed");
        }

        JDBC.closeConnection();
        // Locale.setDefault(new Locale("fr")); // set language to french
        // launch();
    }
}