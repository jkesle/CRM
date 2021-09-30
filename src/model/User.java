package model;

/**
 * User class holds the state of the current user using
 * the application
 *
 * @author Joshua Kesler
 */
public class User {

    private static String username;
    private static int userId;

    /**
     * sets the userID of this current user
     *
     * @param id id of current User
     */
    public static void setUserId(int id) {
        userId = id;
    }

    /**
     * {@return user ID} of this current User
     */
    public static int getUserId() {
        return userId;
    }

    /**
     * {@return username} of this current User
     */
    public static String getUsername() {
        return username;
    }

    /**
     * sets the username for this current User
     *
     * @param user string username to be set
     */
    public static void setUsername(String user) {
        username = user;
    }
}
