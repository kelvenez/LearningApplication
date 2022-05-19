package com.example.studytoworld.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ApkChecksum;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.studytoworld.Achievement.Achievement;
import com.example.studytoworld.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LearningSchedule extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    DatabaseReference timerReference;
    Button timeStop;
    int existedTime;
    int currentTime;
    Timer timer;
    TimerTask timerTask;
    ArrayList<Achievement> systemAchievementList;

    ScheduleAdapter scheduleAdapter;
    ArrayList<Schedule> list;
    String uid;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_schedule);

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
}