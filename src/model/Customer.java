package model;


import helper.JDBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Customer class encapsulates the state information and behavior
 * to properly communicate with the mySQL database
 *
 * @author Joshua Kesler
 */
public class Customer {

    private int customerId;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private int countryId;
    private int divisionId;
    private String divisionName;

    /**
     * constructs a new Customer object
     * @param id integer id of this Customer. (Set by database)
     * @param name string name of this Customer
     * @param address string street address of this Customer
     * @param postal string postal code of this Customer
     * @param phone string phone number of this Customer
     * @param divisionId integer id of the division associated with this Customer
     */
    public Customer(int id, String name, String address, String postal, String phone, int divisionId) {
        this.customerId = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * {@return customer ID} of this Customer
     */
    public int getCustomerId() { return customerId; }

    /**
     * {@return name} of this Customer
     */
    public String getName() {
        return name;
    }

    /**
     * {@return address} of this Customer
     */
    public String getAddress() {
        return address;
    }

    /**
     * {@return postal} of this Customer
     */
    public String getPostal() {
        return postal;
    }

    /**
     * {@return phone} of this Customer
     */
    public String getPhone() {
        return phone;
    }

    /**
     * {@return country ID} associated with this Customer
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * {@return division ID} associate with this Customer
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * {@return division name} associated with this Customer
     */
    public String getDivisionName(){
        return divisionName;
    }

    /**
     * sets the division name from provided division ID
     *
     * @param divId integer id to be used in database query
     * @throws SQLException if error occurs during database connection or query execution
     */
    public void setDivisionName(int divId) throws SQLException {
        Statement statement = JDBC.getConnection().createStatement();
        String query = "Select Division from first_level_divisions where Division_ID = '" + divId + "';";
        ResultSet division = statement.executeQuery(query);
        division.next();
        this.divisionName = division.getString("Division");
    }

    /**
     * sets the name of this Customer
     *
     * @param newName string name to be set
     */
    public void setName(String newName){
        this.name = newName;
    }

    /**
     * sets the country ID from provided division ID
     *
     * @param divId integer id to be used in database query
     * @throws SQLException if error occurs during database connection or query execution
     */
    public void setCountryId(int divId) throws SQLException {
        Statement statement = JDBC.getConnection().createStatement();
        String query = "select Country_ID from first_level_divisions where Division_ID = '" + divId + "';";
        ResultSet rs = statement.executeQuery(query);
        rs.next();
        this.countryId = rs.getInt("Country_ID");
    }

    /**
     * {@return <code>true</code>} if customer has existing appointment.
     * {@return <code>false</code>} if customer has no existing appointments
     *
     * @throws SQLException if error occurs during database connection or query execution
     */
    public boolean checkAppointments() throws SQLException {
        Statement statement = JDBC.getConnection().createStatement();
        String query = "select Customer_ID from appointments where Customer_ID = '" + this.getCustomerId() + "';";
        ResultSet rs = statement.executeQuery(query);
        return rs.next();
    }
}
