package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.text.TextUtils;
import android.content.Intent;
import com.google.firebase.auth.FirebaseAuth;
import android.view.View;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;




public class Register extends AppCompatActivity {
    private EditText emailTextView, passwordTextView ,lastnameTextView , firstnameTextView;
    private Button Btn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        lastnameTextView = findViewById(R.id.LastName);
        firstnameTextView = findViewById(R.id.firstName);
        Btn = findViewById(R.id.btnregister);
        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        // progressbar.setVisibility(View.VISIBLE); #later add this effect
        String email, password, first_name, last_name;
        // Take the value edit texts in Strings
        first_name = firstnameTextView.getText().toString();
        last_name = lastnameTextView.getText().toString();
        email = emailTextView.getText().toString();
        password = passwordTextView.getText().toString();
        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            // hide the progress bar
                            //progressBar.setVisibility(View.GONE);
                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(Register.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // Registration failed
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Registration failed!!"
                                            + " Please try again later",
                                    Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress bar
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}