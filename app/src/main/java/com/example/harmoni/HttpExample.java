package com.example.harmoni;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpExample {

    public static boolean authenticate(String username, String password) throws Exception {
        URL url = new URL("http://localhost:9000/authenticate"); // Replace with the actual URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        String jsonBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int statusCode = con.getResponseCode();
        System.out.println("Status code: " + statusCode);
        return false;
    }

    public static boolean register(String username, String password) throws Exception {
        URL url = new URL("http://localhost:9000/authenticate"); // Replace with the actual URL
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        String jsonBody = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int statusCode = con.getResponseCode();
        System.out.println("Status code: " + statusCode);
        return false;
    }
}

