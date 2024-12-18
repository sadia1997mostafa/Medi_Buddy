

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

public class HospitalList extends RecyclerView.Adapter<HospitalList.HospitalViewHolder> {
    private Activity context;
    private List<Hospital> hospitalList;

    public HospitalList(Activity context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItem = inflater.inflate(R.layout.list_item_hospital, parent, false);
        return new HospitalViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);
        holder.textViewName.setText(hospital.getName());
        holder.textViewAddress.setText(hospital.getAddress());
        holder.textViewPhone.setText(hospital.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HospitalDetailActivity.class);
                intent.putExtra("name", hospital.getName());
                intent.putExtra("address", hospital.getAddress());
                intent.putExtra("phone", hospital.getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewAddress, textViewPhone;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewPhone = itemView.findViewById(R.id.text_view_phone);
        }
    }
}
/*package com.example.logggin;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HospitalList extends RecyclerView.Adapter<HospitalList.HospitalViewHolder> {
    private Activity context;
    private List<Hospital> hospitalList;

    public HospitalList(Activity context, List<Hospital> hospitalList) {
        this.context = context;
        this.hospitalList = hospitalList;
    }

    @NonNull
    @Override
    public HospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listItem = inflater.inflate(R.layout.list_item_hospital, parent, false);
        return new HospitalViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalViewHolder holder, int position) {
        Hospital hospital = hospitalList.get(position);
        holder.textViewName.setText(hospital.getName());
        holder.textViewAddress.setText(hospital.getAddress());
        holder.textViewPhone.setText(hospital.getPhone());

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HospitalDetailActivity.class);
                intent.putExtra("name", hospital.getName());
                intent.putExtra("address", hospital.getAddress());
                intent.putExtra("phone", hospital.getPhone());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    class HospitalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewAddress, textViewPhone;

        public HospitalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewAddress = itemView.findViewById(R.id.text_view_address);
            textViewPhone = itemView.findViewById(R.id.text_view_phone);
        }
    }
}*/