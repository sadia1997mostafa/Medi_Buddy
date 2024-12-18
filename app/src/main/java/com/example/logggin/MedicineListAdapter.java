package com.example.logggin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MedicineViewHolder> {

    private List<Medicine> medicineList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Medicine medicine);
    }

    public MedicineListAdapter(List<Medicine> medicineList, OnItemClickListener listener) {
        this.medicineList = medicineList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.bind(medicine, listener);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewName;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewName = itemView.findViewById(R.id.text_view_name);
        }

        public void bind(final Medicine medicine, final OnItemClickListener listener) {
            textViewName.setText(medicine.getName());
            Glide.with(itemView.getContext()).load(medicine.getImagePath()).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(medicine);
                }
            });
        }
    }
}