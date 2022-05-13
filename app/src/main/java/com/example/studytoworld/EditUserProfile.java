package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditUserProfile extends AppCompatActivity {
    private EditText emailTextView;
    private EditText passwordTextView;
    private EditText lastnameTextView;
    private EditText firstnameTextView;
    String email, password, lastName, firstName;

    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        lastnameTextView = findViewById(R.id.LastName);
        firstnameTextView = findViewById(R.id.firstName);

        //showAllData
        showAllUserData();
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        email=intent.getStringExtra("email");
        password=intent.getStringExtra("password");
        lastName=intent.getStringExtra("lastName");
        firstName=intent.getStringExtra("firstName");

        emailTextView.setText(email);
        passwordTextView.setText(password);
        lastnameTextView.setText(lastName);
        firstnameTextView.setText(firstName);

    }

    public void update(View view){

        if(isFirstNameChanged()|| isPasswordChanged()||isEmailChanged()||isLastNameChanged()){
            Toast.makeText(this,"Data has been updated.", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this,"Data is same and cannot be updated.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isFirstNameChanged() {
        if(!firstName.equals(firstnameTextView.getEditableText().toString())){
            reference.child(password).child("firstName").setValue(firstnameTextView.getEditableText().toString());
            firstName=firstnameTextView.getEditableText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isPasswordChanged() {
        if(!password.equals(passwordTextView.getEditableText().toString())){
            reference.child(password).child("passwd").setValue(passwordTextView.getEditableText().toString());
            password=passwordTextView.getEditableText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isLastNameChanged() {
        if(!lastName.equals(lastnameTextView.getEditableText().toString())){
            reference.child(password).child("LastName").setValue(lastnameTextView.getEditableText().toString());
            lastName=lastnameTextView.getEditableText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    private boolean isEmailChanged() {
        if(!email.equals(emailTextView.getEditableText().toString())){
            reference.child(password).child("email").setValue(emailTextView.getEditableText().toString());
            email=emailTextView.getEditableText().toString();
            return true;
        }
        else{
            return false;
        }
    }
}