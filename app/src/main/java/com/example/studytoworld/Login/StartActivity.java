package com.example.studytoworld.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studytoworld.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_start);
        findViewById(R.id.loginStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartActivity.this, Login.class);
                StartActivity.this.startActivity(myIntent);
            }
        });
        findViewById(R.id.regStart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartActivity.this, Register.class);
                StartActivity.this.startActivity(myIntent);
            }
        });
        findViewById(R.id.loginStartbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartActivity.this, Login.class);
                StartActivity.this.startActivity(myIntent);
            }
        });
        findViewById(R.id.regStartbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(StartActivity.this, Register.class);
                StartActivity.this.startActivity(myIntent);
            }
        });
    }
}