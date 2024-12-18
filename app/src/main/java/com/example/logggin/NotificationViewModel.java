package com.example.logggin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class NotificationViewModel extends ViewModel {
    private final MutableLiveData<List<Notification>> notifications = new MutableLiveData<>();

    public LiveData<List<Notification>> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications.setValue(notifications);
    }

    public void addNotification(Notification notification) {
        List<Notification> currentNotifications = notifications.getValue();
        if (currentNotifications != null) {
            currentNotifications.add(notification);
            notifications.setValue(currentNotifications);
        }
    }
}