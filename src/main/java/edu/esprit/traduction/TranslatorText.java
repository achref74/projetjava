package edu.esprit.traduction;

import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.UnknownHostException;

public class TranslatorText {
    private static final String key = "592e62314d1a46f1a94ab25e39d11ef3";
    private static String location = "westeurope";

    // Cette fonction effectue une requête POST.
    public static Response post() throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "[{\"Text\": \"I would really like to drive your car around the block a few times!\"}]");
        Request request = new Request.Builder()
                .url("https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&from=en&to=fr")
                .post(body)
                .addHeader("Ocp-Apim-Subscription-Key", key)
                .addHeader("Ocp-Apim-Subscription-Region", location)
                .addHeader("Content-type", "application/json")
                .build();
        return client.newCall(request).execute();
    }

    public static String prettify(String jsonText) {
        JsonParser parser = new JsonParser();
        JsonElement json = parser.parse(jsonText);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public static void main(String[] args) {
        try {
            System.out.println("Envoi de la requête à l'API de traduction...");
            Response response = post();

            System.out.println("Réponse brute de l'API :");
            System.out.println(response.body().string());

            // Additional logging for debugging
            int statusCode = response.code();
            System.out.println("\nCode de statut HTTP : " + statusCode);

            if (statusCode == 401) {
                System.out.println("Unauthorized: Check your API key and permissions.");
            } else {
                System.out.println("\nRéponse formatée (JSON indenté) :");
                System.out.println(prettify(response.body().string()));
            }
        } catch (IOException e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
            if (e instanceof ProtocolException) {
                System.out.println("Protocol exception. Check the API endpoint and key.");
            } else if (e instanceof UnknownHostException) {
                System.out.println("Unknown host. Check your internet connection or API endpoint.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
