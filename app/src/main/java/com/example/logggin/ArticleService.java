package com.example.logggin;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class ArticleService {
    private static final String API_URL = "https://exercisedb.p.rapidapi.com/exercises";
    private static final String API_KEY = "cb88042013msha38cef8068155d1p1197f5jsn21f4ad3168f2";
    private OkHttpClient client;

    public ArticleService() {
        client = new OkHttpClient();
    }

    public List<Article> fetchArticles() throws IOException {
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", "exercisedb.p.rapidapi.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Log.d("ArticleService", "Response: " + responseBody); // Log the response
                return parseArticles(responseBody);
            } else {
                throw new IOException("Unexpected code " + response);
            }
        }
    }

    private List<Article> parseArticles(String json) {
        List<Article> articles = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Article article = new Article();
                article.setName(jsonObject.getString("name"));
                article.setBodyPart(jsonObject.getString("bodyPart"));
                article.setEquipment(jsonObject.getString("equipment"));
                article.setTarget(jsonObject.getString("target"));
                article.setSecondaryMuscles(jsonArrayToList(jsonObject.getJSONArray("secondaryMuscles")));
                article.setInstructions(jsonArrayToList(jsonObject.getJSONArray("instructions")));
                articles.add(article);
            }
        } catch (JSONException e) {
            Log.e("ArticleService", "Error parsing articles", e);
        }
        return articles;
    }

    private List<String> jsonArrayToList(JSONArray jsonArray) {
        List<String> list = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
        } catch (JSONException e) {
            Log.e("ArticleService", "Error converting JSONArray to List", e);
        }
        return list;
    }
}