// MedicineCategoryAdapter.java
package com.example.logggin;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MedicineCategoryAdapter extends RecyclerView.Adapter<MedicineCategoryAdapter.CategoryViewHolder> {
    private Activity context;
    private List<String> categoryList;

    public MedicineCategoryAdapter(Activity context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItem = inflater.inflate(R.layout.list_item_category, parent, false);
        return new CategoryViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String category = categoryList.get(position);
        holder.textViewCategory.setText(category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MedicineListActivity.class);
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.text_view_category);
        }
    }
}