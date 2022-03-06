package jkesle.crm.api.appointment;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jkesle.crm.api.Client;
import jkesle.crm.dto.AppointmentDTO;

public class AppointmentApi {

    public List<AppointmentDTO> doGETAppointments() throws Exception {
        List<AppointmentDTO> all = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/appointments?projection=tableview"))
                                  .header("Accept", "application/json")
                                  .header("Content-Type", "application/json")
                                  .GET()
                                  .build();
        Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenAccept(req -> populateList(req, all)).join();

        return all;

    }














    private List<AppointmentDTO> populateList(HttpResponse response, List<AppointmentDTO> list) {
        getAppointmentsFromResponse(response.body().toString()).forEach(appointment -> {
            AppointmentDTO a = getAppointmentDTOFromJson(appointment);
            list.add(a);
        });

        return list;
    }

    private AppointmentDTO getAppointmentDTOFromJson(JsonElement appointment) {
        AppointmentDTO a = new AppointmentDTO();
        a.setAppointmentId(getAppointmentAttribute("appointmentId", appointment).getAsInt());
        a.setAppointmentDescription(getAppointmentAttribute("appointmentDescription", appointment).getAsString());
        a.setAppointmentTitle(getAppointmentAttribute("appointmentTitle", appointment).getAsString());
        a.setAppointmentType(getAppointmentAttribute("appointmentType", appointment).getAsString());
        a.setAppointmentLocation(getAppointmentAttribute("appointmentLocation", appointment).getAsString());
        a.setAppointmentStartDatetime(Timestamp.valueOf(getAppointmentAttribute("appointmentStartDatetime", appointment).getAsString()));
        a.setAppointmentEndDatetime(Timestamp.valueOf(getAppointmentAttribute("appointmentEndDatetime", appointment).getAsString()));
        a.setContactId(getAppointmentAttribute("contactId", appointment).getAsInt());
        a.setCustomerId(getAppointmentAttribute("customerId", appointment).getAsInt());
        a.setUserId(getAppointmentAttribute("userId", appointment).getAsInt());
        return a;
    }

    private JsonElement getAppointmentAttribute(String attr, JsonElement appointment) {
        return appointment.getAsJsonObject().get(attr);
    }

    private JsonArray getAppointmentsFromResponse(String json) {
        JsonElement ele = JsonParser.parseString(json);
        JsonObject root = ele.getAsJsonObject();
        JsonObject embedded = root.getAsJsonObject("_embedded");
        JsonArray arr = embedded.getAsJsonArray("apointments");
        return arr;
    }

}
