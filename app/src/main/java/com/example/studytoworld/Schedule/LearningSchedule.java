package com.example.studytoworld.Schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.studytoworld.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;

public class LearningSchedule extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ScheduleAdapter scheduleAdapter;
    ArrayList<Schedule> list;
    String password;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_schedule);

        recyclerView = findViewById(R.id.schedule_list);
        button = findViewById(R.id.create_schedule);
        Intent intent = getIntent();
        password=intent.getStringExtra("password");
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child("abc12345678").child("Schedules");
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
    }

    private void moveToCreateSchedulePage() {
        Intent intent =new Intent(LearningSchedule.this,CreateLearningSchedule.class);
        intent.putExtra("password",password);
        startActivity(intent);
    }
}