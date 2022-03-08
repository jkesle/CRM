package jkesle.crm.parser.country.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jkesle.crm.dto.CountryDTO;
import jkesle.crm.parser.country.CountryResponseParser;

public class CountryJsonParser implements CountryResponseParser<JsonElement>{

    public CountryDTO getCountryDTO(JsonElement country) {
        CountryDTO c = new CountryDTO();
        c.setCountryName(getCountryAttribute("countryName", country).getAsString());
        c.setCountryId(getCountryAttribute("countryId", country).getAsInt());
        return c;
    }

    private JsonElement getCountryAttribute(String attr, JsonElement country) {
        return country.getAsJsonObject().get(attr);
    }

    public JsonElement getCountriesFromResponse(String json) {
        System.out.println(json);
        JsonElement ele = JsonParser.parseString(json);
        JsonObject root = ele.getAsJsonObject();
        JsonObject embedded = root.getAsJsonObject("_embedded");
        JsonArray arr = embedded.getAsJsonArray("countries");
        return arr;
    }

}
