package Model;

/**
 * @author Patrick Kell
 */

/**
 * The User class which defines and manages users
 */
public class User {

    private int userId;
    private String username;
    private String password;

    /**
     * Creates a new user object with the provided arguments
     *
     * @param userId   The id of the user
     * @param username The username of the user
     * @param password The password of the user
     */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * @return the user's id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId set the user's id
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
        return this.userId + " " + this.username;
    }
}