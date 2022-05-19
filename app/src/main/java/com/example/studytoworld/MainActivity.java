package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;

import com.example.studytoworld.Achievement.AchievementActivity;
import com.example.studytoworld.HelpAndInformation.HelpAndInformation;
import com.example.studytoworld.Schedule.CreateLearningSchedule;
import com.example.studytoworld.Schedule.LearningSchedule;
import com.example.studytoworld.Schedule.Schedule;
import com.example.studytoworld.UserProfile.UserProfile;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "currentUserMain";
    private ImageButton newPopUp_cancel;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button schedule, bg_music, chatroom;
    private String uid,email,password,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        email=intent.getStringExtra("email");
        password=intent.getStringExtra("password");
        userName=intent.getStringExtra("userName");

        StudyRoom chinese = new StudyRoom("Chinese");
        StudyRoom english = new StudyRoom("English");
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        final TextView chineseCount = (TextView) findViewById(R.id.chineseCounter);
       // Log.d(TAG, "finalcurrentUserCounter: " + chinese.getCurrentUserCounter());
        chineseCount.setText("Chinese StudyRoom:");
        final TextView englishCount = (TextView) findViewById(R.id.englishCounter);
        englishCount.setText("English StudyRoom");
        findViewById(R.id.room1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,ChineseStudyRoom.class);
                myIntent.putExtra("ChineseRoom",chinese);
                myIntent.putExtra("uid",uid);
                myIntent.putExtra("email",email);
                myIntent.putExtra("password", password);
                myIntent.putExtra("userName",userName);
                startActivity(myIntent);
            }
        });
        findViewById(R.id.room2).setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,EnglishStudyRoom.class);
                myIntent.putExtra("EnglishRoom",english);
                myIntent.putExtra("uid",uid);
                myIntent.putExtra("email",email);
                myIntent.putExtra("password", password);
                myIntent.putExtra("userName",userName);
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
            myIntent.putExtra("uid",uid);
            myIntent.putExtra("email",email);
            myIntent.putExtra("password", password);
            myIntent.putExtra("userName",userName);
            this.startActivity(myIntent);
        }
        else if (id == R.id.achievement) {
            // Handle the achievement action
            Intent myIntent = new Intent(this,AchievementActivity.class);
            myIntent.putExtra("uid",uid);
            this.startActivity(myIntent);
        }
        else if (id == R.id.schedule)
        {
            // Handle the schedule action
            Intent myIntent = new Intent(this, LearningSchedule.class);
            myIntent.putExtra("uid",uid);
            this.startActivity(myIntent);
        } else if(id == R.id.profile){
           //Handle the profile action
           Intent myIntent = new Intent(this, UserProfile.class);
           myIntent.putExtra("uid",uid);
           myIntent.putExtra("email",email);
           myIntent.putExtra("password", password);
           myIntent.putExtra("userName",userName);
           this.startActivity(myIntent);
           }
        else if(id == R.id.help){
           //Handle the help and information action
           Intent myIntent = new Intent(this, HelpAndInformation.class);
            myIntent.putExtra("uid",uid);
           this.startActivity(myIntent);
        }
        else if(id == R.id.chatroom)
        {
            //Handle the chatroom action
            Intent myIntent = new Intent(this,ChatRoomTitleActivity.class);
            myIntent.putExtra("uid",uid);
            myIntent.putExtra("userName",userName);
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