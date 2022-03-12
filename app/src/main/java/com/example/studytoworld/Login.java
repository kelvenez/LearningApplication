package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class Login extends AppCompatActivity {
    private EditText emailEditView, passwordEditView;
    private Button Btn;
    private TextView  create_Account;
    //private ProgressBar progressbar; #later design

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        // initialising all views through id defined above
        emailEditView = findViewById(R.id.editEmail);
        passwordEditView = findViewById(R.id.Password);
        Btn = findViewById(R.id.buttonLogin);
        create_Account = findViewById(R.id.createAC);
        //progressbar = findViewById(R.id.progressBar);

        // Set on Click Listener on Sign-in button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });

        create_Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent
                        = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void loginUserAccount(){
            // Take the value of two edit texts in Strings
            String email, password;
            email = emailEditView.getText().toString();
            password = passwordEditView.getText().toString();

            // validations for input email and password
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
        // signin existing user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    // progressBar.setVisibility(View.GONE); #later design

                                    // if sign-in is successful
                                    // intent to home activity
                                    Intent intent
                                            = new Intent(Login.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                }

                                else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    // progressbar.setVisibility(View.GONE);
                                }
                            }
                        });
    }
}