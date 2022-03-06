package jkesle.crm.api;

import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;

public class Client {

    private static HttpClient client;

    public static HttpClient getClient() {

        if (client == null) {

        client =  HttpClient.newBuilder()
          .version(Version.HTTP_1_1)
          .followRedirects(Redirect.NEVER)
          .proxy(ProxySelector.getDefault())
          .build();
        }
        return client;

    }
}
