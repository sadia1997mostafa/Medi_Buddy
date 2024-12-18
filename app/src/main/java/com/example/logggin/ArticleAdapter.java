package com.example.logggin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<Article> articles;
    private Context context;

    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.textViewName.setText(article.getName());
        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExerciseDetailActivity.class);
            intent.putExtra("name", article.getName());
            intent.putExtra("bodyPart", article.getBodyPart());
            intent.putExtra("equipment", article.getEquipment());
            intent.putExtra("target", article.getTarget());
            intent.putExtra("secondaryMuscles", String.join(", ", article.getSecondaryMuscles()));
            intent.putExtra("instructions", String.join(", ", article.getInstructions()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        CardView cardView;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}