package jkesle.crm.parser.appointment.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jkesle.crm.dto.AppointmentDTO;
import jkesle.crm.parser.appointment.AppointmentResponseParser;

public class AppointmentJsonParser implements AppointmentResponseParser<JsonElement> {

    public AppointmentDTO getAppointmentDTO(JsonElement appointment) {
        AppointmentDTO a = new AppointmentDTO();
        a.setAppointmentId(getAppointmentAttribute("appointmentId", appointment).getAsInt());
        a.setAppointmentDescription(getAppointmentAttribute("appointmentDescription", appointment).getAsString());
        a.setAppointmentTitle(getAppointmentAttribute("appointmentTitle", appointment).getAsString());
        a.setAppointmentType(getAppointmentAttribute("appointmentType", appointment).getAsString());
        a.setAppointmentLocation(getAppointmentAttribute("appointmentLocation", appointment).getAsString());
        a.setAppointmentStartDatetime(getAppointmentAttribute("appointmentStartDatetime", appointment).getAsString());
        a.setAppointmentEndDatetime(getAppointmentAttribute("appointmentEndDatetime", appointment).getAsString());
        a.setContactId(getAppointmentAttribute("contactId", appointment).getAsInt());
        a.setContactName(getAppointmentAttribute("contactName", appointment).getAsString());
        a.setCustomerId(getAppointmentAttribute("customerId", appointment).getAsInt());
        a.setUserId(getAppointmentAttribute("userId", appointment).getAsInt());
        return a;
    }

    private static JsonElement getAppointmentAttribute(String attr, JsonElement appointment) {
        return appointment.getAsJsonObject().get(attr);
    }

    public JsonElement getAppointmentsFromResponse(String json) {
        JsonElement ele = JsonParser.parseString(json);
        JsonObject root = ele.getAsJsonObject();
        JsonObject embedded = root.getAsJsonObject("_embedded");
        JsonArray arr = embedded.getAsJsonArray("appointments");
        return arr;
    }

}
