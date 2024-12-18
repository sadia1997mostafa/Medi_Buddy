package com.example.logggin;

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

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList;
    private Context context;

    public DoctorAdapter(List<Doctor> doctorList, Context context) {
        this.doctorList = doctorList;
        this.context = context;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.nameTextView.setText(doctor.getName());

        // Load image using Glide
        Glide.with(context)
                .load(doctor.getImagePath())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DoctorDetailsActivity.class);
            intent.putExtra("doctor", doctor);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.doctor_name);
            imageView = itemView.findViewById(R.id.doctor_image); // Ensure this ID matches your layout
        }
    }
}