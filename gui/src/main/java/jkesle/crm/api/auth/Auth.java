package jkesle.crm.api.auth;

import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.Gson;

import jkesle.crm.dto.AuthDTO;
import jkesle.crm.api.Client;

public class Auth {

    private static Gson gson = new Gson();

    public static int doPOST(String username, String password) throws Exception {
        AuthDTO auth = new AuthDTO.AuthDTOBuilder().withUsername(username).withPassword(password).build();
        var bodyJson = gson.toJson(auth);
        var request = HttpRequest.newBuilder(new URI("http://localhost:8080/auth"))
                                  .header("Accept", "application/json")
                                  .header("Content-Type", "application/json")
                                  .POST(BodyPublishers.ofString(bodyJson))
                                  .build();
        return Client.getClient().sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().statusCode();
    }

}
