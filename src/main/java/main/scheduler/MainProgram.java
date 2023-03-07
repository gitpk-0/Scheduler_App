package main.scheduler;

import Utility.JDBC;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainProgram extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainProgram.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 386, 467);
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


        Locale france = new Locale("fr", "FR");
        Locale spain = new Locale("es", "ES");
        Locale german = Locale.GERMAN;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a language (es, fr, de): ");
        String langCode = scanner.nextLine();

        if (langCode.equals("fr")) {
            Locale.setDefault(france); // set language to french
            System.out.println("Locale = " + Locale.getDefault());
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        } else if (langCode.equals("es")) {
            Locale.setDefault(spain);
            System.out.println("Locale = " + Locale.getDefault());
            ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        } else {
            Locale.setDefault(Locale.ENGLISH);
        }




        launch();

        JDBC.closeConnection();

    }
}