package jkesle.crm.parser.customer.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jkesle.crm.dto.CustomerDTO;
import jkesle.crm.parser.customer.CustomerResponseParser;

public class CustomerJsonParser implements CustomerResponseParser<JsonElement> {

    public CustomerDTO getCustomerDTO(JsonElement customer) {
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

    public JsonElement getCustomersFromResponse(String json) {
        JsonElement ele = JsonParser.parseString(json);
        JsonObject root = ele.getAsJsonObject();
        JsonObject embedded = root.getAsJsonObject("_embedded");
        JsonArray arr = embedded.getAsJsonArray("customers");
        return arr;
    }

}
