package com.example.studytoworld.UserProfile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studytoworld.Achievement.AchievementActivity;
import com.example.studytoworld.HelpAndInformation.HelpAndInformation;
import com.example.studytoworld.Information.NearbyLibrary;
import com.example.studytoworld.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class UserProfile extends AppCompatActivity {
    private TextView emailTextView;
    private TextView lastnameTextView;
    private TextView firstnameTextView;
    String email, password, lastName, firstName;
    DatabaseReference reference;

    ImageView profileView;
    StorageReference storageReference;
    //ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        emailTextView = findViewById(R.id.email);
        lastnameTextView = findViewById(R.id.last_name);
        firstnameTextView = findViewById(R.id.first_name);
        showAllUserData();

        storageReference= FirebaseStorage.getInstance().getReference("images/"+1);
        profileView = findViewById(R.id.profile_image);
        showProfileImage();

    }

    private void showProfileImage() {
        try {
            File localFile = File.createTempFile("tempFile",".jpg");
            storageReference.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            profileView.setImageBitmap(bitmap);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed to retrieve profile image", Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        email=intent.getStringExtra("email");
        password=intent.getStringExtra("password");
        lastName=intent.getStringExtra("lastName");
        firstName=intent.getStringExtra("firstName");

        emailTextView.setText(email);
        lastnameTextView.setText(lastName);
        firstnameTextView.setText(firstName);

    }

    public void Edit(View view){
        Intent intentToEdit = new Intent(getApplicationContext(), EditUserProfile.class);
        intentToEdit.putExtra("email",email);
        intentToEdit.putExtra("password", password);
        intentToEdit.putExtra("firstName",firstName);
        intentToEdit.putExtra("lastName",lastName);

        startActivity(intentToEdit);
    }

    public void MoveToAchievementPage(View view) {
        Intent intent = new Intent(getApplicationContext(), AchievementActivity.class);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    public void MoveToNearbyLivrariesPage(View view) {
        Intent intent = new Intent(getApplicationContext(), NearbyLibrary.class);
        startActivity(intent);
    }

    public void MoveToHelpPage(View view) {
        Intent intent = new Intent(getApplicationContext(), HelpAndInformation.class);
        startActivity(intent);
    }
}