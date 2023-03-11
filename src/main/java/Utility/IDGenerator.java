package Utility;

/**
 * @author Patrick Kell
 */

import Database.DBAppointments;

import java.util.List;
import java.util.Random;

/**
 * IdGenerator class which manages the creation of unique IDs for Appointments
 */
public class IDGenerator {

    /**
     * IdGenerator class for Appointments
     *
     * @return A unique ID for the Appointment
     */
    public static int appointmentIDGenerator() {
        Random random = new Random(); // random number generator object
        List<Integer> existingApptIDs = DBAppointments.getAppointments("all")
                .stream() // stream through each part in the inventory
                .map(appointment -> appointment.getApptId()) // map out each appointment's ID  **lambda
                .toList();

        int uniqueID = 1; // initialize the id field to 1
        while (existingApptIDs.contains(uniqueID)) { // if the previous id is already taken
            uniqueID = random.nextInt(9999); // change it to a new random number
        }
        return uniqueID; // return the uniqueID of the part
    }
}

