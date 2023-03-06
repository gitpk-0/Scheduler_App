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
        FXMLLoader fxmlLoader = new FXMLLoader(MainProgram.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1020, 840);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();

        // int rowsAffected = FruitsQuery.insert("Cherries", 1);
        // int rowsAffected = FruitsQuery.update(7, "Red Peppers");
        // int rowsAffected = FruitsQuery.delete(8);

        // if (rowsAffected > 0) {
        //     System.out.println("Query successful");
        // } else {
        //     System.out.println("Query failed");
        // }

        // FruitsQuery.select();
        // FruitsQuery.select(2);
        launch();
        JDBC.closeConnection();
        // Locale.setDefault(new Locale("fr")); // set language to french

    }
}