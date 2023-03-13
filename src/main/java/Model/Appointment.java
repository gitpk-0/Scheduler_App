package Model;

/**
 * @author Patrick Kell
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Appointment class which defines and manages appointments
 */
public class Appointment {

    private int apptId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;
    private String contactName;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private DateTimeFormatter date = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");
    private DateTimeFormatter timeAlt = DateTimeFormatter.ofPattern("H:mm");

    // /**
    //  * Creates a new appointment object with the provided arguments
    //  *
    //  * @param apptId      The id of the appointment
    //  * @param title       The title of the appointment
    //  * @param description The description of the appointment
    //  * @param location    The location of the appointment
    //  * @param type        The type of the appointment
    //  * @param start       The start time of the appointment
    //  * @param end         The end time of the appointment
    //  * @param customerId  The customer id of the appointment
    //  * @param userId      The user id of the appointment
    //  * @param contactId   The contact id of the appointment
    //  */
    // public Appointment(int apptId, String title, String description, String location, String type, LocalDateTime start,
    //                    LocalDateTime end, int customerId, int userId, int contactId) {
    //     this.apptId = apptId;
    //     this.title = title;
    //     this.description = description;
    //     this.location = location;
    //     this.type = type;
    //     this.start = start;
    //     this.end = end;
    //     this.customerId = customerId;
    //     this.userId = userId;
    //     this.contactId = contactId;
    // }

    /**
     * Creates a new appointment object with the provided arguments
     * <p>
     * Overloaded Constructor
     *
     * @param apptId      The id of the appointment
     * @param title       The title of the appointment
     * @param description The description of the appointment
     * @param location    The location of the appointment
     * @param type        The type of the appointment
     * @param start       The start time of the appointment
     * @param end         The end time of the appointment
     * @param customerId  The customer id of the appointment
     * @param userId      The user id of the appointment
     * @param contactName The contact name of the appointment
     */
    public Appointment(int apptId, String title, String description, String location, String type, LocalDateTime start,
                       LocalDateTime end, int customerId, int userId, int contactId, String contactName) {
        this.apptId = apptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
        this.contactName = contactName;
    }


    /**
     * @return the appointment's id
     */
    public int getApptId() {
        return apptId;
    }

    /**
     * @param apptId set the appointment's id
     */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /**
     * @return the appointment's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title set the appointment's title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the appointment's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description set the appointment's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the appointment's location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location set the appointment's location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the appointment's type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type set the appointment's type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the appointment's start time
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * @param start set the appointment's start time
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * @return the appointment's end time
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * @param end set the appointment's end time
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     * @return the appointment's customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId set the appointment's customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the appointment's user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId set the appointment's user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the appointment's contact id
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @param contactId set the appointment's  contact id
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * @return the appointment's start time
     */
    public String getStartTime() {
        return getStart().format(time);
    }

    /**
     * @return the appointment's start date
     */
    public String getStartDate() {
        return getStart().format(date);
    }

    /**
     * @return the appointment's end time
     */
    public String getEndTime() {
        return getEnd().format(time);
    }

    /**
     * @return the appointment's end date
     */
    public String getEndDate() {
        return getEnd().format(date);
    }

    /**
     * @return the appointment's contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName set the appointments contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the number of minutes until the appointments start time
     */
    public int getMinutesToStart() {
        int startMinute = start.getMinute();
        int currentMinute = LocalDateTime.now().getMinute();

        if (startMinute == 0) {
            startMinute = 60;
        }

        return startMinute - currentMinute;
    }
}