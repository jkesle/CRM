package jkesle.crm.api.appointment;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonStreamParser;
import com.google.gson.stream.JsonReader;

import jkesle.crm.api.Client;
import jkesle.crm.dto.AppointmentDTO;

public class AppointmentApi {


    public static class AppointmentGET {


        public static CompletableFuture<HttpResponse<String>> doGETAppointments() throws Exception {
            List<AppointmentDTO> all = new ArrayList<>();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/appointments?projection=tableview"))
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .GET()
                                    .build();
           return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());

        }

        public static CompletableFuture<HttpResponse<String>> doGETAppointmentsByCustomer(int customerId) throws Exception {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("http://localhost:8080/appointments/search/custid?customerId=%s&projection=tableview", customerId)))
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .GET()
                                    .build();
           return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
        }
    }

        public static class AppointmentDELETE {

            public static CompletableFuture<HttpResponse<String>> doAppointmentDELETE(int appointmentId) throws Exception {
                HttpRequest request = HttpRequest.newBuilder(new URI(String.format("http://localhost:8080/appointments/%s", appointmentId)))
                                        .header("Accept", "application/json")
                                        .header("Content-Type", "application/json")
                                        .DELETE()
                                        .build();
                return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());

        }

    }
}
