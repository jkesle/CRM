package jkesle.crm.parser.appointment;

import jkesle.crm.dto.AppointmentDTO;

public interface AppointmentResponseParser<T> {

    public AppointmentDTO getAppointmentDTO(T responseType);

    public T getAppointmentsFromResponse(String response);

}
