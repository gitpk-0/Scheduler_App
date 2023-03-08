package Model;

/**
 * @author Patrick Kell
 */

/**
 * The Contact class which defines and manages contacts
 */
public class Contact {

    private int contactID;
    private String name;
    private String email;

    /**
     * Creates a new contact object with the provided arguments
     *
     * @param contactID The id of the contact
     * @param name      The name of the contact
     * @param email     The email address of the contact
     */
    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    /**
     * @return the contact's id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID set the contact's id
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * @return the contact's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name set the contact's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the contact's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email set the contact's email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return a string representation of the contact's id and name
     */
    @Override
    public String toString() {
        return this.contactID + " " + this.name;
    }
}
