package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ExerciseDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        TextView textViewName = findViewById(R.id.text_view_name);
        TextView textViewBodyPart = findViewById(R.id.text_view_body_part);
        TextView textViewEquipment = findViewById(R.id.text_view_equipment);
        TextView textViewTarget = findViewById(R.id.text_view_target);
        TextView textViewSecondaryMuscles = findViewById(R.id.text_view_secondary_muscles);
        TextView textViewInstructions = findViewById(R.id.text_view_instructions);

        Intent intent = getIntent();
        if (intent != null) {
            textViewName.setText(intent.getStringExtra("name"));
            textViewBodyPart.setText("Body Part: " + intent.getStringExtra("bodyPart"));
            textViewEquipment.setText("Equipment: " + intent.getStringExtra("equipment"));
            textViewTarget.setText("Target: " + intent.getStringExtra("target"));
            textViewSecondaryMuscles.setText("Secondary Muscles: " + intent.getStringExtra("secondaryMuscles"));
            textViewInstructions.setText("Instructions: " + intent.getStringExtra("instructions"));
        }
    }
}