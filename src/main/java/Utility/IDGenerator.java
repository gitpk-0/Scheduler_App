package Utility;

/**
 * @author Patrick Kell
 */

import Database.DBAppointments;
import Database.DBCustomers;

import java.util.List;

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
        List<Integer> existingApptIDs = DBAppointments.getAppointments("all")
                .stream() // stream through each appointment
                .map(appointment -> appointment.getApptId()) // map out each appointment's ID  **lambda
                .toList();

        return uniqueIdGenerator(existingApptIDs);
    }

    /**
     * IdGenerator class for Customers
     *
     * @return A unique ID for the Customer
     */
    public static int customerIDGenerator() {
        List<Integer> existingCustomerIds = DBCustomers.getAllCustomers()
                .stream() // stream through each customer
                .map(customer -> customer.getCustomerId()) // map out each customer's ID  **lambda
                .toList();

        return uniqueIdGenerator(existingCustomerIds);
    }

    /**
     * uniqueIdGenerator class which contains the logic of creating a unique id
     *
     * @param existingIds A list of existing ids
     * @return A unique ID
     */
    private static int uniqueIdGenerator(List<Integer> existingIds) {
        int uniqueID = existingIds.size(); // initialize the id field to the total number of existing ids
        while (existingIds.contains(uniqueID)) { // if the uniqueID id is already taken
            uniqueID++; // increment unique ID by one until there are no matching existing ids
        }
        return uniqueID; // return the uniqueID of the customer
    }
}

