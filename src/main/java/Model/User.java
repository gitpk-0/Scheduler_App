package Model;

/**
 * @author Patrick Kell
 */

/**
 * The User class which defines and manages users
 */
public class User {

    private int id;
    private String username;
    private String password;

    /**
     * Creates a new user object with the provided arguments
     *
     * @param id       The id of the user
     * @param username The username of the user
     * @param password The password of the user
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * @return the user's id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id set the user's id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username set the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return a string of the user id and username
     */
    @Override
    public String toString() {
        return this.id + " " + this.username;
    }
}
