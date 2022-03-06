package jkesle.crm.api.customer;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import jkesle.crm.dto.CustomerDTO;
import jkesle.crm.api.Client;

public class CustomerApi {

    public List<CustomerDTO> doGETCustomers() throws Exception {
        List<CustomerDTO> all = new ArrayList<>();
        HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/customers?projection=tableview"))
                                  .header("Accept", "application/json")
                                  .header("Content-Type", "application/json")
                                  .GET()
                                  .build();
        Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenAccept(req -> populateList(req, all)).join();

                                return all;
    }

    private List<CustomerDTO> populateList(HttpResponse response, List<CustomerDTO> list) {
        getCustomersFromResponse(response.body().toString()).forEach(customer -> {
            CustomerDTO c = getCustomerDTOFromJson(customer);
            list.add(c);
        });

        return list;
    }

    private CustomerDTO getCustomerDTOFromJson(JsonElement customer) {
        CustomerDTO c = new CustomerDTO();
        c.setCustomerName(getCustomerAttribute("customerName", customer).getAsString());
        c.setCustomerId(getCustomerAttribute("customerId", customer).getAsInt());
        c.setCustomerAddress(getCustomerAttribute("customerAddress", customer).getAsString());
        c.setCustomerPhone(getCustomerAttribute("customerPhone", customer).getAsString());
        c.setDivision(getCustomerAttribute("divisionName", customer).getAsString());
        return c;
    }

    private JsonElement getCustomerAttribute(String attr, JsonElement customer) {
        return customer.getAsJsonObject().get(attr);
    }

    private JsonArray getCustomersFromResponse(String json) {
        JsonElement ele = JsonParser.parseString(json);
        JsonObject root = ele.getAsJsonObject();
        JsonObject embedded = root.getAsJsonObject("_embedded");
        JsonArray arr = embedded.getAsJsonArray("customers");
        return arr;
    }
}
