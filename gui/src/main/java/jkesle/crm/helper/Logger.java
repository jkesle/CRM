package jkesle.crm.helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 * class used to log login attempts to login_activity.txt
 *
 * @author Joshua Kesler
 */
public class Logger {

    /**
     * makes an entry to the login_activity.txt file
     *
     * @param username username associated with attempt
     * @param accepted <code>true</code> if login accepted, <code>false</code> if not accepted
     * @throws IOException if error occurs during input or output
     */
    public static void log (String username, boolean accepted) throws IOException {
        try (FileWriter fw = new FileWriter("login_activity.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {

            pw.println(ZonedDateTime.now() + " " + username + (accepted ? " Success" : " Failure"));

        } catch (IOException error) {

            System.out.println(error.getMessage());
        }
    }
}
