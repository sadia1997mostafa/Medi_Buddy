package com.example.logggin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class OrderRequestViewModel extends ViewModel {
    private final MutableLiveData<List<OrderRequest>> orderRequests = new MutableLiveData<>();

    public LiveData<List<OrderRequest>> getOrderRequests() {
        return orderRequests;
    }

    public void setOrderRequests(List<OrderRequest> orderRequests) {
        this.orderRequests.setValue(orderRequests);
    }
}