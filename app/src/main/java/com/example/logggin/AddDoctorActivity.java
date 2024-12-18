package com.example.logggin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddDoctorActivity extends AppCompatActivity {

    private EditText doctorName, doctorAddress, doctorQualifications, doctorChamber, doctorHospital, doctorPhone;
    private Spinner doctorCategorySpinner;
    private Button addDoctorButton;
    private DatabaseReference databaseReference;

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri imageUri;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        doctorName = findViewById(R.id.doctor_name);
        doctorAddress = findViewById(R.id.doctor_address);
        doctorQualifications = findViewById(R.id.doctor_qualifications);
        doctorChamber = findViewById(R.id.doctor_chamber);
        doctorHospital = findViewById(R.id.doctor_hospital);
        doctorPhone = findViewById(R.id.doctor_phone);
        doctorCategorySpinner = findViewById(R.id.doctor_category_spinner);
        addDoctorButton = findViewById(R.id.add_doctor_button);

        databaseReference = FirebaseDatabase.getInstance().getReference("doctors");

        imageView = findViewById(R.id.image_view);
        Button buttonUploadImage = findViewById(R.id.button_upload_image);

        buttonUploadImage.setOnClickListener(v -> openFileChooser());
        addDoctorButton.setOnClickListener(v -> saveDoctorDetails());

        setupCategorySpinner();
    }

    private void setupCategorySpinner() {
        String[] categories = {
                "Medicine Specialist", "Child Specialist", "Neurologist", "Cardiologist",
                "Gynecologist", "Dermatologist", "Gastroenterologist", "Psychiatrist",
                "Orthopedic", "ENT Specialist"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorCategorySpinner.setAdapter(adapter);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            saveImageLocally(imageUri);
        }
    }

    private void saveImageLocally(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            // Correct the orientation
            InputStream inputStreamForExif = getContentResolver().openInputStream(uri);
            ExifInterface exif = new ExifInterface(inputStreamForExif);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            bitmap = rotateBitmap(bitmap, orientation);
            inputStreamForExif.close();

            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDir, System.currentTimeMillis() + ".jpg");
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            imageView.setImageBitmap(bitmap);
            imageUrl = imageFile.getAbsolutePath();
            Toast.makeText(this, "Image saved locally", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap;
        }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private void saveDoctorDetails() {
        String name = doctorName.getText().toString().trim();
        String address = doctorAddress.getText().toString().trim();
        String qualifications = doctorQualifications.getText().toString().trim();
        String chamber = doctorChamber.getText().toString().trim();
        String hospital = doctorHospital.getText().toString().trim();
        String phone = doctorPhone.getText().toString().trim();
        String category = doctorCategorySpinner.getSelectedItem().toString();

        if (name.isEmpty() || address.isEmpty() || qualifications.isEmpty() || chamber.isEmpty() || hospital.isEmpty() || phone.isEmpty() || category.isEmpty() || imageUrl == null) {
            Toast.makeText(this, "Please fill all fields and upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = databaseReference.push().getKey();
        Doctor doctor = new Doctor(id, name, address, qualifications, chamber, hospital, phone, category, imageUrl);
        databaseReference.child(id).setValue(doctor);

        Toast.makeText(this, "Doctor details saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}