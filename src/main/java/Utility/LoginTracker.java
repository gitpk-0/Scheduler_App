package Utility;

/**
 * @author Patrick Kell
 */

import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for managing the logging of all login attempts
 */
public class LoginTracker {

    private static FileWriter writer;
    private final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Constructs a new LoginTracker object, setting login_activity.txt as the file to write to
     * and sets the append boolean to true so that data will be written to the end of the file
     */
    public LoginTracker() {
        try {
            writer = new FileWriter("login_activity.txt", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method which logs the login attempt to the text file
     *
     * @param username the username of the user attempting to log in
     * @param dateTime the datetime of the login event
     * @param valid    the result of the login attempt
     * @throws IOException Signals an Input/Output exception has occurred if an Input/Output error occurs
     */
    public int log(String username, ZonedDateTime dateTime, boolean valid) throws IOException {
        String result = valid ? "SUCCESSFUL" : "FAILED"; // SUCCESSFUL if valid = true, FAILED if valid = false
        String date = dateTime.toLocalDate().toString(); // the date of the login attempt
        String minute = String.valueOf(dateTime.getMinute());
        if (Integer.parseInt(minute) < 10) {
            minute = "0" + dateTime.getMinute();
        }
        String time = String.format(dateTime.getHour() + ":" + minute, timeFormat); // the hours and minutes of the login attempt

        // write the following to the login_activity.txt file
        writer.write(result + " LOG IN on " + date + " at " + time + " by user: " + username + "\n");
        writer.flush(); // flushes the stream
        return 1;
    }

    /**
     * Closes the file that was being written to
     *
     * @throws IOException Signals an Input/Output exception has occurred if an Input/Output error occurs
     */
    public static void cleanUp() throws IOException {
        writer.close(); // close the file
    }
}
