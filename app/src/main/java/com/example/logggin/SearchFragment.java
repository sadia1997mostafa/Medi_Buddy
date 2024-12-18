package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.activity.OnBackPressedCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;
    private DatabaseReference medicinesRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize UI components
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Firebase reference
        medicinesRef = FirebaseDatabase.getInstance().getReference("medicines");

        // Initialize adapter and list
        medicineList = new ArrayList<>();
        adapter = new MedicineAdapter(medicineList, getContext(), medicine -> {
            // Open MedicineDetailActivity when a medicine is clicked
            Intent intent = new Intent(getContext(), MedicineDetailActivity.class);
            intent.putExtra("medicine", medicine); // Parcelable object
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Handle search queries
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMedicines(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMedicines(newText);
                return false;
            }
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

    private void searchMedicines(String query) {
        medicinesRef.orderByChild("name").startAt(query).endAt(query + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Medicine> filteredList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Medicine medicine = dataSnapshot.getValue(Medicine.class);
                            if (medicine != null) {
                                filteredList.add(medicine);
                            }
                        }
                        adapter.updateList(filteredList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

/*package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;
    private DatabaseReference medicinesRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize UI components
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Firebase reference
        medicinesRef = FirebaseDatabase.getInstance().getReference("medicines");

        // Initialize adapter and list
        medicineList = new ArrayList<>();
        adapter = new MedicineAdapter(medicineList, getContext(), medicine -> {
            // Open MedicineDetailActivity when a medicine is clicked
            Intent intent = new Intent(getContext(), MedicineDetailActivity.class);
            intent.putExtra("medicine", medicine); // Parcelable object
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Handle search queries
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMedicines(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchMedicines(newText);
                return false;
            }
        });

        return view;
    }

    private void searchMedicines(String query) {
        medicinesRef.orderByChild("name").startAt(query).endAt(query + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Medicine> filteredList = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Medicine medicine = dataSnapshot.getValue(Medicine.class);
                            if (medicine != null) {
                                filteredList.add(medicine);
                            }
                        }
                        adapter.updateList(filteredList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
*/