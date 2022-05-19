package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.studytoworld.UserProfile.StaticUserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {
    private EditText emailEditView, passwordEditView;
    private Button Btn;
    private TextView  create_Account;
    //private ProgressBar progressbar; #later design

    private FirebaseAuth mAuth;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

    private String userNameFromDB, emailFromDB, passwordFromDB, idFromDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
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

                                    getUserInfo(mAuth.getCurrentUser().getUid());
                                    //Intent intent1 = new Intent(Login.this, UserProfile.class);
                                    //intent1.putExtra("email",emailFromDB);
                                    //intent1.putExtra("password", passwordFromDB);
                                    //intent1.putExtra("firstName",firstNameFromDB);
                                    //intent1.putExtra("lastName",lastNameFromDB);

                                    // hide the progress bar
                                    // progressBar.setVisibility(View.GONE); #later design

                                    // if sign-in is successful
                                    // intent to home activity

                                    //Intent intent
                                    //         = new Intent(Login.this,
                                    //        MainActivity.class);
                                    //startActivity(intent1);
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

    private void getUserInfo(String userID){
        Query checkUser = reference.orderByChild("id").equalTo(userID);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userNameFromDB = snapshot.child(userID).child("userName").getValue(String.class);
                    emailFromDB = snapshot.child(userID).child("email").getValue(String.class);
                    passwordFromDB = snapshot.child(userID).child("password").getValue(String.class);
                    idFromDB = snapshot.child(userID).child("id").getValue(String.class);
                    StaticUserInfo.setUserName(userNameFromDB);
                    StaticUserInfo.setPassword(passwordFromDB);
                    StaticUserInfo.setEmail(emailFromDB);
                    Intent intent1 = new Intent(Login.this, MainActivity.class);
                    intent1.putExtra("email",emailFromDB);
                    intent1.putExtra("password", passwordFromDB);
                    intent1.putExtra("userName",userNameFromDB);
                    intent1.putExtra("uid",idFromDB);

                    // hide the progress bar
                    // progressBar.setVisibility(View.GONE); #later design

                    // if sign-in is successful
                    // intent to home activity

                    //Intent intent
                    //         = new Intent(Login.this,
                    //        MainActivity.class);
                    startActivity(intent1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}