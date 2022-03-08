package jkesle.crm.dto;

import java.sql.Timestamp;

public class AppointmentDTO {
    private int appointmentId;
    private String appointmentType;
    private String appointmentTitle;
    private String appointmentDescription;
    private String appointmentLocation;
    private String appointmentStartDatetime;
    private String appointmentEndDatetime;
    private int contactId;
    private String contactName;
    private int customerId;
    private int userId;

    public AppointmentDTO(){};

    public int getAppointmentId() {
        return appointmentId;
    }
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }
    public String getAppointmentType() {
        return appointmentType;
    }
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }
    public String getAppointmentTitle() {
        return appointmentTitle;
    }
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }
    public String getAppointmentDescription() {
        return appointmentDescription;
    }
    public void setAppointmentDescription(String appointmentDescription) {
        this.appointmentDescription = appointmentDescription;
    }
    public String getAppointmentLocation() {
        return appointmentLocation;
    }
    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }
    public String getAppointmentStartDatetime() {
        return appointmentStartDatetime;
    }
    public void setAppointmentStartDatetime(String appointmentStartDatetime) {
        this.appointmentStartDatetime = appointmentStartDatetime;
    }
    public String getAppointmentEndDatetime() {
        return appointmentEndDatetime;
    }
    public void setAppointmentEndDatetime(String appointmentEndDatetime) {
        this.appointmentEndDatetime = appointmentEndDatetime;
    }
    public String getContactName() {
        return contactName;
    }
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    public int getContactId() {
        return contactId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }


}
