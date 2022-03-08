package jkesle.crm.api.division;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import jkesle.crm.api.Client;

public class DivisionApi {

    public static class DivisionGET {

        public static CompletableFuture<HttpResponse<String>> doGETDivisionsByCountry(int countryId) throws URISyntaxException {
            HttpRequest request = HttpRequest.newBuilder(new URI(String.format("http://localhost:8080/divisions/search/country?countryId=%s&projection=combobox", countryId)))
                                    .header("Accept", "application/json")
                                    .header("Content-Type", "application/json")
                                    .GET()
                                    .build();
            return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());

        }
    }

}
