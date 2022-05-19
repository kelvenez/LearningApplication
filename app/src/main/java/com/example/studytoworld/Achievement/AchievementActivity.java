package com.example.studytoworld.Achievement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.studytoworld.R;
import com.example.studytoworld.Schedule.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.sql.Array;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AchievementActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AchievementAdapter achievementAdapter;
    ArrayList<Achievement> list;
    DatabaseReference achievementRef;

    TextView totalLearningTime;
    TextView firstSubject, secondSubject, thirdSubject;
    DatabaseReference studyTimeRef;
    DatabaseReference totalStudyTimeRef;
    HashMap<String, Integer> subjectTimeMap;
    String[] newSubjectTimeMap;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("test", "check1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        totalLearningTime = findViewById(R.id.total_learning_time);
        totalStudyTimeRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("StudyTime").child("totalStudyTime");
        studyTimeRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("StudyTime").child("Subject");
        firstSubject = findViewById(R.id.first_subject);
        secondSubject = findViewById(R.id.second_subject);
        thirdSubject = findViewById(R.id.third_subject);
        subjectTimeMap = new HashMap<>();
        newSubjectTimeMap = new String[3];

        recyclerView = findViewById(R.id.achievement_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        achievementAdapter = new AchievementAdapter(this,list);
        recyclerView.setAdapter(achievementAdapter);

        achievementRef = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Achievement");
        achievementRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        Achievement achievement = dataSnapshot.getValue(Achievement.class);
                        list.add(achievement);
                    }
                    achievementAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        totalStudyTimeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int temp = snapshot.getValue(int.class); //This got problem
                totalLearningTime.setText(Integer.toString(temp));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        studyTimeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot!=null) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Integer time = dataSnapshot.getValue(Integer.class);
                        String subject = dataSnapshot.getKey();
                        subjectTimeMap.put(subject, time);
                    }
                    //Log.d("TestingAchievement", String.valueOf(subjectTimeMap));
                    sortSubjectTime();
                    showSubjectTime();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showSubjectTime() {
        for(int i=0;i<newSubjectTimeMap.length;++i){
            if(i==0){
                firstSubject.setText(newSubjectTimeMap[i]);
            }
            else if(i==1){
                secondSubject.setText(newSubjectTimeMap[i]);
            }
            else if(i==2){
                thirdSubject.setText(newSubjectTimeMap[i]);
            }
        }
    }

    private void sortSubjectTime() {
        HashMap<String,Integer> temp = new HashMap<>();
        HashMap<String,Integer> copy = subjectTimeMap;
        int index=0;
        for( Map.Entry<String,Integer> entry: copy.entrySet()){
            int max= entry.getValue();
            String max_key = entry.getKey();
            for(Map.Entry<String,Integer> anotherEntry: copy.entrySet()){
                int otherElement = anotherEntry.getValue();
                if(max<otherElement || temp.containsKey(max_key)){
                    if(!temp.containsKey(anotherEntry.getKey())) {
                        max = otherElement;
                        max_key = anotherEntry.getKey();
                    }
                }
            }
            //Log.d("TestingAchievement", String.valueOf(max_key));
            temp.put(max_key,max);
            newSubjectTimeMap[index] = max_key;
            ++index;
            //copy.remove(max_key);
        }
        //Log.d("TestingAchievement", String.valueOf(temp));
    }
}