package jkesle.crm.api.customer;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import jkesle.crm.dto.CustomerDTO;
import jkesle.crm.api.Client;

public class CustomerApi {

    public static class CustomerGET {

        public static CompletableFuture<HttpResponse<String>> doGETCustomers() throws Exception {  // loose representation of the JavaScript "Promise"
            List<CustomerDTO> all = new ArrayList<>();
            HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/customers?projection=tableview"))
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .GET()
                                    .build();
            return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    public static class CustomerDELETE {

        public static CompletableFuture<HttpResponse<String>> doCustomerDELETE(int customerId) throws Exception {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("http://localhost:8080/customers/%s", customerId)))
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .DELETE()
                                    .build();
            return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
        }
    }







}
