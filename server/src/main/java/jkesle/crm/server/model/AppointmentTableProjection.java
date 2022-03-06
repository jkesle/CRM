package jkesle.crm.server.model;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "tableview", types = {Appointment.class})
public interface AppointmentTableProjection {

    public int getAppointmentId();

    public String getAppointmentTitle();

    public String getAppointmentLocation();

    public String getAppointmentType();

    public String getAppointmentDescription();

    public Timestamp getAppointmentStartDatetime();

    public Timestamp getAppointmentEndDatetime();

    @Value("#{target.getCustomer().getCustomerId()}")
    public int getCustomerId();

    @Value("#{target.getContact().getContactId()}")
    public int getContactId();

    @Value("#{target.getContact().getContactName()}")
    public String getContactName();

    @Value("{#target.getUser().getUserId()}")
    public int getUserId();
}
