package com.example.logggin;

import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;
import android.util.Log;

public class BmiService {
    private static final String API_URL = "https://bmi-calculator1.p.rapidapi.com/bmi";
    private static final String API_KEY = "cb88042013msha38cef8068155d1p1197f5jsn21f4ad3168f2";

    public JSONObject calculateBmi(String jsonData) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("X-RapidAPI-Key", API_KEY);
        connection.setRequestProperty("X-RapidAPI-Host", "bmi-calculator1.p.rapidapi.com");
        connection.setDoOutput(true);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonData.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        Log.d("BmiService", "Response Code: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Log.d("BmiService", "Response: " + response.toString());
                return new JSONObject(response.toString());
            } catch (JSONException e) {
                Log.e("BmiService", "Error parsing response", e);
                throw new IOException("Error parsing response", e);
            }
        } else {
            Log.e("BmiService", "Unexpected response code: " + responseCode);
            throw new IOException("Unexpected code " + responseCode);
        }
    }
}