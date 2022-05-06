package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    private TextView emailTextView;
    private TextView lastnameTextView;
    private TextView firstnameTextView;
    String email, password, lastName, firstName;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        emailTextView = findViewById(R.id.email);
        lastnameTextView = findViewById(R.id.last_name);
        firstnameTextView = findViewById(R.id.first_name);
        //showAllData
        showAllUserData();
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        email=intent.getStringExtra("email");
        Log.d("UserProfile", email);
        password=intent.getStringExtra("password");
        Log.d("UserProfile", password);
        lastName=intent.getStringExtra("lastName");
        Log.d("UserProfile", lastName);
        firstName=intent.getStringExtra("firstName");
        Log.d("UserProfile", firstName);

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
}