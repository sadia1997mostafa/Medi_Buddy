package com.example.logggin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ManageMedicineActivity extends AppCompatActivity {

    private CardView cardAddMedicine, cardUpdateMedicine, cardAddPersonalCareProduct, cardUpdatePersonalCareProduct, cardDailyRevenue, cardMonthlyRevenue, cardPendingOrders, cardDeliveryMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_medicine);

        cardAddMedicine = findViewById(R.id.card_add_medicine);
        cardUpdateMedicine = findViewById(R.id.card_update_medicine);


        cardAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMedicineActivity.this, AddMedicineActivity.class));
            }
        });

        cardUpdateMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMedicineActivity.this, UpdateMedicineActivity.class));
            }
        });

       /* cardAddPersonalCareProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMedicineActivity.this, AddPersonalCareProductActivity.class));
            }
        });

        cardUpdatePersonalCareProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMedicineActivity.this, UpdatePersonalCareProductActivity.class));
            }
        });

        cardDailyRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMedicineActivity.this, DailyRevenueActivity.class));
            }
        });

        cardMonthlyRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMedicineActivity.this, MonthlyRevenueActivity.class));
            }
        });*/



       /* cardDeliveryMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManageMedicineActivity.this, DeliveryManActivity.class));
            }
        });*/
    }
}