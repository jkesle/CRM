package helper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * database connection helper class
 *
 * @author Joshua Kesler
 */
public class JDBC {

    private static final String jdbcUrl = "jdbc:mysql://localhost/client_schedule?connectionTimeZone = LOCAL";
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**
     * opens connection to the database
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * closes database connection
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * {@return connection to database}
     */
    public static Connection getConnection() {
        return connection;
    }
}
