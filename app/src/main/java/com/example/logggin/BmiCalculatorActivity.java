package com.example.logggin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.IOException;

public class BmiCalculatorActivity extends AppCompatActivity {

    private EditText editTextWeight, editTextHeight, editTextSex, editTextAge, editTextWaist, editTextHip;
    private Button buttonCalculate;
    private TextView textViewResult;
    private BmiService bmiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calculator);

        editTextWeight = findViewById(R.id.edit_text_weight);
        editTextHeight = findViewById(R.id.edit_text_height);
        editTextSex = findViewById(R.id.edit_text_sex);
        editTextAge = findViewById(R.id.edit_text_age);
        editTextWaist = findViewById(R.id.edit_text_waist);
        editTextHip = findViewById(R.id.edit_text_hip);
        buttonCalculate = findViewById(R.id.button_calculate);
        textViewResult = findViewById(R.id.text_view_result);

        bmiService = new BmiService();

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = editTextWeight.getText().toString();
                String height = editTextHeight.getText().toString();
                String sex = editTextSex.getText().toString();
                String age = editTextAge.getText().toString();
                String waist = editTextWaist.getText().toString();
                String hip = editTextHip.getText().toString();

                if (!weight.isEmpty() && !height.isEmpty() && !sex.isEmpty() && !age.isEmpty() && !waist.isEmpty() && !hip.isEmpty()) {
                    String jsonData = String.format("{\"weight\":{\"value\":\"%s\",\"unit\":\"kg\"},\"height\":{\"value\":\"%s\",\"unit\":\"cm\"},\"sex\":\"%s\",\"age\":\"%s\",\"waist\":\"%s\",\"hip\":\"%s\"}", weight, height, sex, age, waist, hip);
                    calculateBmi(jsonData);
                } else {
                    Toast.makeText(BmiCalculatorActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void calculateBmi(String jsonData) {
        new Thread(() -> {
            try {
                JSONObject response = bmiService.calculateBmi(jsonData);
                if (response != null) {
                    runOnUiThread(() -> {
                        String result = "BMI: " + response.optJSONObject("bmi").optString("value") + "\n" +
                                "Status: " + response.optJSONObject("bmi").optString("status") + "\n" +
                                "Risk: " + response.optJSONObject("bmi").optString("risk") + "\n" +
                                "Ideal Weight: " + response.optString("ideal_weight") + "\n" +
                                "Surface Area: " + response.optString("surface_area") + "\n" +
                                "Ponderal Index: " + response.optString("ponderal_index") + "\n" +
                                "BMR: " + response.optJSONObject("bmr").optString("value") + "\n" +
                                "WHR: " + response.optJSONObject("whr").optString("value") + " (" + response.optJSONObject("whr").optString("status") + ")\n" +
                                "WHtR: " + response.optJSONObject("whtr").optString("value") + " (" + response.optJSONObject("whtr").optString("status") + ")";
                        textViewResult.setText(result);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(BmiCalculatorActivity.this, "Error calculating BMI", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(BmiCalculatorActivity.this, "Error fetching data: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}