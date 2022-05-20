package com.example.studytoworld.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.studytoworld.Achievement.AchievementActivity;
import com.example.studytoworld.chatRoom.ChatRoomTitleActivity;
import com.example.studytoworld.HelpAndInformation.HelpAndInformation;
import com.example.studytoworld.MainActivity;
import com.example.studytoworld.music.MusicPlay;
import com.example.studytoworld.R;
import com.example.studytoworld.UserProfile.StaticUserInfo;
import com.example.studytoworld.UserProfile.UserProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LearningSchedule extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView recyclerView;
    DatabaseReference databaseReference;


    ScheduleAdapter scheduleAdapter;
    ArrayList<Schedule> list;
    String uid;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_schedule);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.schedule_list);
        button = findViewById(R.id.create_schedule);
        Intent intent = getIntent();
        uid=intent.getStringExtra("uid");

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Schedules");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(this,list);
        recyclerView.setAdapter(scheduleAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Schedule schedule = dataSnapshot.getValue(Schedule.class);
                        list.add(schedule);
                    }
                    scheduleAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                moveToCreateSchedulePage();
            }
        });

    }

    private void moveToCreateSchedulePage() {
        Intent intent =new Intent(LearningSchedule.this,CreateLearningSchedule.class);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);
        if (id == R.id.home) {
            // Handle the home action
            Intent myIntent = new Intent(this, MainActivity.class);
            myIntent.putExtra("email",StaticUserInfo.getEmail());
            myIntent.putExtra("password", StaticUserInfo.getPassword());
            myIntent.putExtra("userName", StaticUserInfo.getUserName());
            this.startActivity(myIntent);
        }
        else if (id == R.id.achievement) {
            // Handle the achievement action
            Intent myIntent = new Intent(this, AchievementActivity.class);
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
            myIntent.putExtra("email",StaticUserInfo.getEmail());
            myIntent.putExtra("password", StaticUserInfo.getPassword());
            myIntent.putExtra("userName", StaticUserInfo.getUserName());
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
            Intent myIntent = new Intent(this, ChatRoomTitleActivity.class);
            myIntent.putExtra("uid",uid);
            myIntent.putExtra("userName", StaticUserInfo.getUserName());
            //pass user name if ness
            this.startActivity(myIntent);
        } else if(id == R.id.music){
            //Handle the chatroom action
            Intent myIntent = new Intent(this, MusicPlay.class);
            this.startActivity(myIntent);
        }
        return true;
    }
}