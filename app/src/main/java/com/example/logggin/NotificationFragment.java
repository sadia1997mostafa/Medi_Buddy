package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    private NotificationAdapter notificationAdapter;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationViewModel = new ViewModelProvider(requireActivity()).get(NotificationViewModel.class);
        notificationAdapter = new NotificationAdapter(new ArrayList<>());
        recyclerView.setAdapter(notificationAdapter);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail().replace("@", "_").replace(".", "_");
            databaseReference = FirebaseDatabase.getInstance().getReference("notifications").child(userEmail);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Notification> notifications = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Notification notification = dataSnapshot.getValue(Notification.class);
                        if (notification != null) {
                            notifications.add(notification);
                        }
                    }
                    notificationViewModel.setNotifications(notifications);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle possible errors.
                }
            });
        }

        notificationViewModel.getNotifications().observe(getViewLifecycleOwner(), notifications -> {
            notificationAdapter.updateNotifications(notifications);
        });

        // Handle back press
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = new Intent(getActivity(), MedicineListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        return view;
    }
}