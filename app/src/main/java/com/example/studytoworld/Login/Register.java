package com.example.studytoworld.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.content.Intent;

import com.example.studytoworld.R;
import com.example.studytoworld.UserProfile.AchievementList;
import com.example.studytoworld.UserProfile.ScheduleList;
import com.example.studytoworld.UserProfile.StudyTime;
import com.example.studytoworld.UserProfile.UserInfo;
import com.google.firebase.auth.FirebaseAuth;
import android.view.View;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
    private EditText emailTextView, passwordTextView , userNameTextView ;
    private Button Btn;
    private FirebaseAuth mAuth;
    private TextView createdAccountTextView;
    String email, password, user_name;

    private FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        createdAccountTextView = findViewById(R.id.already);
        // initialising all views through id defined above
        emailTextView = findViewById(R.id.email);
        passwordTextView = findViewById(R.id.passwd);
        userNameTextView = findViewById(R.id.userName);
        createdAccountTextView = findViewById(R.id.already);
        Btn = findViewById(R.id.btnregister);
        // Set on Click Listener on Registration button
        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        createdAccountTextView.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        }));



    }


    private void registerNewUser() {

        // show the visibility of progress bar to show loading
        // progressbar.setVisibility(View.VISIBLE); #later add this effect
        // Take the value edit texts in Strings
        user_name = userNameTextView.getText().toString();
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

        database= FirebaseDatabase.getInstance();
        myRef = database.getReference("users");
        // create new user or register new user
        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            ScheduleList scheduleArrayList = new ScheduleList("Schedules");
                            AchievementList achievementArrayList = new AchievementList(1,"testing");
                            StudyTime studyTime = new StudyTime(0);
                            UserInfo helperClass = new UserInfo(email, password, user_name ,mAuth.getCurrentUser().getUid(),scheduleArrayList,achievementArrayList,studyTime);
                            myRef.child(mAuth.getCurrentUser().getUid()).setValue(helperClass);
                            //myRef.child(mAuth.getCurrentUser().getUid()).child("Schedules").setValue(new ArrayList<Schedule>());
                            //myRef.child(mAuth.getCurrentUser().getUid()).child("Achievement").setValue(new ArrayList<Achievement>());
                            myRef.child(mAuth.getCurrentUser().getUid()).child("studyTime").child("Subject").child("Chinese").setValue(0);
                            myRef.child(mAuth.getCurrentUser().getUid()).child("studyTime").child("Subject").child("English").setValue(0);
                            Toast.makeText(getApplicationContext(),
                                    "Registration successful!",
                                    Toast.LENGTH_LONG)
                                    .show();
                            // hide the progress bar
                            //progressBar.setVisibility(View.GONE);
                            // if the user created intent to login activity
                            Intent intent
                                    = new Intent(Register.this,Login.class);
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