package com.example.logggin;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.List;

public class ArticleActivity extends AppCompatActivity {
    private RecyclerView recyclerViewArticles;
    private ArticleService articleService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        recyclerViewArticles = findViewById(R.id.recycler_view_articles);
        recyclerViewArticles.setLayoutManager(new LinearLayoutManager(this));
        articleService = new ArticleService();

        fetchAndDisplayArticles();
    }

    private void fetchAndDisplayArticles() {
        new Thread(() -> {
            try {
                List<Article> articles = articleService.fetchArticles();
                runOnUiThread(() -> {
                    ArticleAdapter adapter = new ArticleAdapter(ArticleActivity.this, articles);
                    recyclerViewArticles.setAdapter(adapter);
                });
            } catch (IOException e) {
                Log.e("ArticleActivity", "Error fetching articles", e);
            }
        }).start();
    }
}
