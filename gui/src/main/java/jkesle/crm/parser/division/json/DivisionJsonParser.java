package jkesle.crm.parser.division.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jkesle.crm.dto.DivisionDTO;
import jkesle.crm.parser.division.DivisionResponseParser;

public class DivisionJsonParser implements DivisionResponseParser<JsonElement> {

    public DivisionDTO getDivisionDTO(JsonElement division) {
        DivisionDTO d = new DivisionDTO();
        d.setDivisionName(getDivisionAttribute("divisionName", division).getAsString());
        d.setDivisionId(getDivisionAttribute("divisionId", division).getAsInt());
        d.setCountryId(getDivisionAttribute("countryId", division).getAsInt());
        return d;
    }

    private JsonElement getDivisionAttribute(String attr, JsonElement division) {
        return division.getAsJsonObject().get(attr);
    }

    public JsonElement getDivisionsFromResponse(String json) {
        System.out.println(json);
        JsonElement ele = JsonParser.parseString(json);
        JsonObject root = ele.getAsJsonObject();
        JsonObject embedded = root.getAsJsonObject("_embedded");
        JsonArray arr = embedded.getAsJsonArray("divisions");
        return arr;
    }

}
