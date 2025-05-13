package com.example.shope;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class KtpUploadActivity extends AppCompatActivity {

    private EditText etKtpNumber;
    private ImageView ivKtpPhoto;
    private TextView tvClickToPhoto;
    private Button btnAmbilFoto;
    private Button btnSimpan;

    private Uri currentPhotoUri;
    private String currentPhotoPath;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Camera permission is required", Toast.LENGTH_SHORT).show();
                }
            });

    private ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    displayCapturedImage();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_ktp_uplod);

        // Setup the back button in the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Nomor KTP");
        }

        // Initialize views
        etKtpNumber = findViewById(R.id.et_ktp_number);
        ivKtpPhoto = findViewById(R.id.iv_ktp_photo);
        tvClickToPhoto = findViewById(R.id.tv_click_to_photo);
        btnAmbilFoto = findViewById(R.id.btn_ambil_foto);
        btnSimpan = findViewById(R.id.btn_simpan);

        // Pre-fill KTP number if needed
        etKtpNumber.setText("2010100010011");

        // Setup click listeners
        View.OnClickListener cameraClickListener = v -> checkCameraPermissionAndOpen();

        ivKtpPhoto.setOnClickListener(cameraClickListener);
        tvClickToPhoto.setOnClickListener(cameraClickListener);
        btnAmbilFoto.setOnClickListener(cameraClickListener);

        btnSimpan.setOnClickListener(v -> saveKtpData());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkCameraPermissionAndOpen() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Toast.makeText(this, "Error creating image file", Toast.LENGTH_SHORT).show();
            return;
        }

        // Continue only if the File was successfully created
        if (photoFile != null) {
            currentPhotoUri = FileProvider.getUriForFile(this,
                    "com.example.ktpupload.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri);
            takePictureLauncher.launch(takePictureIntent);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "KTP_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void displayCapturedImage() {
        // Hide the "Click to photo" text
        tvClickToPhoto.setVisibility(View.GONE);

        // Display the captured image
        ivKtpPhoto.setImageURI(currentPhotoUri);
    }

    private void saveKtpData() {
        String ktpNumber = etKtpNumber.getText().toString().trim();

        if (ktpNumber.isEmpty()) {
            Toast.makeText(this, "Please enter KTP number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentPhotoUri == null) {
            Toast.makeText(this, "Please take a KTP photo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Here you would typically save the data to database or send to server
        Toast.makeText(this, "KTP data saved successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}

