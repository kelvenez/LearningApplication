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
        timerReference = FirebaseDatabase.getInstance().getReference("users").child(uid).child("StudyTime");
        existedTime=0;
        currentTime=0;
        timeStop= findViewById(R.id.timer_stop);
        timer = new Timer();
        startTimer();
        systemAchievementList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        scheduleAdapter = new ScheduleAdapter(this,list);
        recyclerView.setAdapter(scheduleAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Schedule schedule = dataSnapshot.getValue(Schedule.class);
                    list.add(schedule);
                }
                scheduleAdapter.notifyDataSetChanged();
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

        timerReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null){
                    existedTime=snapshot.child("Total").getValue(int.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        timeStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopCounting();
            }
        });
    }

    private void stopCounting() {
        timer.cancel();
        currentTime=(int) Math.round(currentTime);
        checkAchievement();
        addUserTime();
    }

    private void addUserTime() {
        int time = existedTime;
        time = time + currentTime;
        timerReference.child("Total").setValue(time);
    }

    private void checkAchievement() {
        DatabaseReference achievementListRef = FirebaseDatabase.getInstance().getReference("achievement");
        //get system achievements and save them in an arrayList
        achievementListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Achievement achievement = dataSnapshot.getValue(Achievement.class);
                    systemAchievementList.add(achievement);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //get user achievements;
        DatabaseReference userAchievementRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Achievement");
        userAchievementRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int sumOfExistedTimeAndCurrentTime = existedTime+currentTime;
                for(int i=0; i<systemAchievementList.size();++i){
                    Achievement systemAchievementItem = systemAchievementList.get(i);
                    //fulfil condition
                    if(sumOfExistedTimeAndCurrentTime>=systemAchievementItem.getCondition()){
                        //check if the user already got the achievement
                        boolean haveAlready=false;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Achievement achievement = dataSnapshot.getValue(Achievement.class);
                            if(achievement.getCondition()==systemAchievementItem.getCondition()){
                                haveAlready=true;
                                break;
                            }
                        }
                        if(haveAlready==false){
                            //add to user database and show a congratulation notification
                            userAchievementRef.child(Integer.toString(systemAchievementItem.getCondition())).setValue(systemAchievementItem);
                            Toast.makeText(getApplicationContext(), "Congratulation,Your have accomplished an achievement", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void startTimer() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //runOnUiThread(new Runnable() {
                    //@Override
                   // public void run() {
                        currentTime++;
                    //}
                //});
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }

    private void moveToCreateSchedulePage() {
        Intent intent =new Intent(LearningSchedule.this,CreateLearningSchedule.class);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }
}