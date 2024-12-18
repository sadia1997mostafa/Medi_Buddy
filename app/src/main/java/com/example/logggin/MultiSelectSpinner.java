package com.example.logggin;

import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class MultiSelectSpinner extends Spinner implements DialogInterface.OnMultiChoiceClickListener {

    private List<String> items;
    private boolean[] selected;
    private ArrayAdapter<String> adapter;

    public MultiSelectSpinner(Context context) {
        super(context);
        init();
    }

    public MultiSelectSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        items = new ArrayList<>();
        selected = new boolean[0];
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        setAdapter(adapter);
    }

    public void setItems(List<String> items) {
        this.items = items;
        selected = new boolean[items.size()];
        adapter.clear();
        adapter.add("Select Categories");
        adapter.notifyDataSetChanged();
    }

    public List<String> getSelectedItems() {
        List<String> selectedItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (selected[i]) {
                selectedItems.add(items.get(i));
            }
        }
        return selectedItems;
    }

    @Override
    public boolean performClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMultiChoiceItems(items.toArray(new CharSequence[0]), selected, this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.clear();
                adapter.add(getSelectedItems().toString());
                adapter.notifyDataSetChanged();
            }
        });
        builder.show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
        if (which >= 0 && which < selected.length) {
            selected[which] = isChecked;
        }
    }
}