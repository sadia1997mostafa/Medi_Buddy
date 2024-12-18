package com.example.logggin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private List<Medicine> medicineList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Medicine medicine);
    }

    public MedicineAdapter(List<Medicine> medicineList, Context context, OnItemClickListener listener) {
        this.medicineList = medicineList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine2, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.medicineName.setText(medicine.getName());
        holder.medicinePrice.setText("tk. " + medicine.getPrice());
        Glide.with(context).load(medicine.getImagePath()).into(holder.medicineImage);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(medicine));
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public void updateList(List<Medicine> newList) {
        medicineList = newList;
        notifyDataSetChanged();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        ImageView medicineImage;
        TextView medicineName;
        TextView medicinePrice;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineImage = itemView.findViewById(R.id.image_medicine);
            medicineName = itemView.findViewById(R.id.text_medicine_name);
            medicinePrice = itemView.findViewById(R.id.text_medicine_price);
        }
    }
}


/*package com.example.logggin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    private List<Medicine> medicineList;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Medicine medicine);
    }

    public MedicineAdapter(List<Medicine> medicineList, Context context, OnItemClickListener listener) {
        this.medicineList = medicineList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_medicine2, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);
        holder.medicineName.setText(medicine.getName());
        holder.medicinePrice.setText("tk. " + medicine.getPrice());
        Glide.with(context).load(medicine.getImagePath()).into(holder.medicineImage);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(medicine));
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        ImageView medicineImage;
        TextView medicineName;
        TextView medicinePrice;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medicineImage = itemView.findViewById(R.id.image_medicine);
            medicineName = itemView.findViewById(R.id.text_medicine_name);
            medicinePrice = itemView.findViewById(R.id.text_medicine_price);
        }
    }
}*/