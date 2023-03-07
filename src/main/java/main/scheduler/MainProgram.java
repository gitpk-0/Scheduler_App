package main.scheduler;

/**
 * @author Patrick Kell
 */

import Utility.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class MainProgram extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainProgram.class.getResource("/View/Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 380, 400);
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();

        /* DATABASE METHODS */
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


        /* LOCALIZATION */
        // Locale france = new Locale("fr", "FR");
        // Locale spain = new Locale("es", "ES");
        // Locale german = Locale.GERMAN;
        //
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter a language (es, fr, de): ");
        // String langCode = scanner.nextLine();
        //
        // if (langCode.equals("fr")) {
        //     Locale.setDefault(france); // set language to french
        //     System.out.println("Locale = " + Locale.getDefault());
        //     ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        //     System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        // } else if (langCode.equals("es")) {
        //     Locale.setDefault(spain);
        //     System.out.println("Locale = " + Locale.getDefault());
        //     ResourceBundle rb = ResourceBundle.getBundle("Nat", Locale.getDefault());
        //     System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        // } else {
        //     Locale.setDefault(Locale.ENGLISH);
        // }

        /* TIME ZONES */
        // System.out.println(ZoneId.systemDefault());
        // ZoneId.getAvailableZoneIds().stream()
        //         .filter(timeZone -> timeZone.contains("America"))
        //         .sorted().forEach(System.out::println);

        // LocalDate date = LocalDate.of(2023, 03, 07); // yyyy:mm:dd
        // LocalTime time = LocalTime.of(11, 04); // HH:mm
        // LocalDateTime dateTime = LocalDateTime.of(date, time);
        // ZoneId zone = ZoneId.systemDefault();
        // ZonedDateTime zdt = ZonedDateTime.of(dateTime, zone);
        // System.out.println(zdt);

        // System.out.println(zdt.toLocalDate()); // date object
        // System.out.println(zdt.toLocalTime()); // time object
        // System.out.println(zdt.toLocalDate().toString() + " " + zdt.toLocalTime());

        // // convert between time zones
        // System.out.println("User time: " + zdt);
        //
        // ZoneId utcZone = ZoneId.of("UTC");
        // ZonedDateTime utcZdt = ZonedDateTime.ofInstant(zdt.toInstant(), utcZone); // reset to utc
        // System.out.println("User time to UTC: " + utcZdt);
        //
        // zdt = ZonedDateTime.ofInstant(utcZdt.toInstant(), zone);
        // System.out.println("UTC to User time: " + zdt);


        /* TIME OVERLAPS */
        // // create dates and times
        // LocalDateTime start = LocalDateTime.of(2023, 03, 07, 11, 0);
        // LocalDateTime end = LocalDateTime.of(2023, 03, 07, 11, 30);
        // LocalDateTime comp = LocalDateTime.of(2023, 03, 07, 11, 30);
        //
        // // check time overlap
        // if (comp.isAfter(start) && comp.isBefore(end)) {
        //     System.out.println(comp + " is between " + start + " and " + end);
        // } else if (comp.equals(start)) {
        //     System.out.println("Start matches comp time");
        // } else if (comp.equals(end)) {
        //     System.out.println("End matches comp time");
        // } else {
        //     System.out.println(comp + " is not between " + start + " and " + end);
        // }



        /* TIME ALERTS - CHRONOUNITS */
        LocalTime start = LocalTime.of(11, 05);
        LocalTime now = LocalTime.now(); // current time
        long timeDif = ChronoUnit.MINUTES.between(start, now);
        System.out.println(timeDif);

        if (timeDif > -15 && timeDif < 0) { // event in next 15 minutes
            System.out.println("Event in " + Math.abs(timeDif) + " minute(s)");
        } else if (timeDif > 0) { // event started x minutes ago
            System.out.println("Event started " + timeDif + " minute(s) ago");
        }

        // launch();

        JDBC.closeConnection();

    }
}