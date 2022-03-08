package jkesle.crm.api.country;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import jkesle.crm.api.Client;

public class CountryApi {

    public static class CountryGET {

        public static CompletableFuture<HttpResponse<String>> doGETCountries() throws Exception {
        HttpRequest request = HttpRequest.newBuilder(new URI("http://localhost:8080/countries?projection=combobox"))
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/json")
                                .GET()
                                .build();
        return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    }

}
