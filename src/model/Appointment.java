package model;

import controller.LoginController;
import helper.JDBC;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Appointment class encapsulates the state information needed to
 * properly communicate with the mySQL database
 *
 * @author Joshua Kesler
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private int contactId;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private int customerId;
    private int userId;
    private String contactName;
    private Timestamp startTimeAdjusted;
    private Timestamp endTimeAdjusted;

    /**
     * Constructs a new Appointment object
     *
     * @param id id to be appointmentId. set by database
     * @param title string title
     * @param description string description
     * @param location string location
     * @param contactId id of associated contact
     * @param type string type of appointment
     * @param startDateTime Timestamp of appointment start in UTC
     * @param endDateTime Timestamp of appointment end in UTC
     * @param customerId id of associated customer
     * @param userId id of associate user
     */
    public Appointment(int id, String title, String description, String location, int contactId, String type,
                       Timestamp startDateTime, Timestamp endDateTime, int customerId, int userId) {
        this.appointmentId = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.startTimeAdjusted = new Timestamp((this.getStartDateTime().getTime() + LoginController.getOffsetLocalTime()));
        this.endTimeAdjusted = new Timestamp((this.getEndDateTime().getTime() + LoginController.getOffsetLocalTime()));
    }

    /**
     * sets contact name based on selected contact ID
     *
     * @param contactId id used in database query
     * @throws SQLException if error occurs during database connection or query execution
     */
    public void setContactName(int contactId) throws SQLException {
        Statement statement = JDBC.getConnection().createStatement();
        String query = "Select Contact_Name from contacts WHERE Contact_ID = '" + contactId + "';";
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        this.contactName = rs.getString("Contact_Name");
        statement.close();
        rs.close();
    }

    /**
     * {@return appointmentId} of this appointment
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * {@return description} of this appointment
     */
    public String getDescription() {
        return description;
    }

    /**
     * {@return contact name} of this appointment
     */
    public String getContactName() { return contactName; }

    /**
     * {@return contact id} of this appointment
     */
    public int getContactId() { return contactId; }

    /**
     * {@return title} of this appointment
     */
    public String getTitle() { return title; }

    /**
     * sets title of this appointment
     *
     * @param title string used as title in this appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * {@return location} of this appointment
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets location of this appointment
     *
     * @param location string used as location in this appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * {@return type} of this appointment
     */
    public String getType() {
        return type;
    }

    /**
     * {@return startDateTime <code>Timestamp</code> in UTC} of this appointment
     */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * {@return endDateTime <code>Timestamp</code> in UTC} of this appointment
     */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * {@return customer ID} of this appointment
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * {@return user ID} of this appointment
     */
    public int getUserId() {
        return userId;
    }

    /**
     * {@return <code>Timestamp</code> start time} of this appointment in user's local timezone
     */
    public Timestamp getStartTimeAdjusted() {
        return startTimeAdjusted;
    }

    /**
     * {@return <code>Timestamp</code> end time} of this appointment in user's local timezone
     */
    public Timestamp getEndTimeAdjusted() {
        return endTimeAdjusted;
    }

}
