package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.os.Bundle;

import com.example.studytoworld.Schedule.CreateLearningSchedule;
import com.example.studytoworld.Schedule.LearningSchedule;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ImageButton newPopUp_cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button schedule;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        StudyRoom chinese = new StudyRoom("Chinese");
        StudyRoom english = new StudyRoom("English");
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.room1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,ChineseStudyRoom.class);
                myIntent.putExtra("ChineseRoom",chinese);
                startActivity(myIntent);
            }
        });
        findViewById(R.id.room2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,EnglishStudyRoom.class);
                myIntent.putExtra("EnglishRoom",english);
                startActivity(myIntent);
            }
        });
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);
        if (id == R.id.home) {
            // Handle the home action
            Intent myIntent = new Intent(this, MainActivity.class);
            this.startActivity(myIntent);
        }
        else if (id == R.id.achievement) {
            // Handle the achievement action
            Intent myIntent = new Intent(this,Register.class);
            this.startActivity(myIntent);
        }
        else if (id == R.id.schedule)
        {
            // Handle the schedule action
            Intent myIntent = new Intent(this,ChineseStudyRoom.class);
            this.startActivity(myIntent);
        }/*
       else if(id == R.id.profile)
       {
       //Handle the profile action
       }
       else if(id == R.id.help)
       {
       //Handle the help and information action
       }*/
        else if(id == R.id.chatroom)
        {
            //Handle the chatroom action
            Intent myIntent = new Intent(this,ChatRoomTitleActivity.class);
            //pass user name if ness
            this.startActivity(myIntent);
        } else if(id == R.id.music){
            //Handle the chatroom action
            Intent myIntent = new Intent(this,MusicPlay.class);
            this.startActivity(myIntent);
        }
        return true;
    }
}