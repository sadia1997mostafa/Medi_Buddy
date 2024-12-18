package com.example.logggin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        holder.textViewTitle.setText(notification.getTitle());
        holder.textViewMessage.setText(notification.getMessage());
        holder.textViewTimestamp.setText(String.valueOf(notification.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void updateNotifications(List<Notification> notifications) {
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewMessage, textViewTimestamp;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewMessage = itemView.findViewById(R.id.text_view_message);
            textViewTimestamp = itemView.findViewById(R.id.text_view_timestamp);
        }
    }
}