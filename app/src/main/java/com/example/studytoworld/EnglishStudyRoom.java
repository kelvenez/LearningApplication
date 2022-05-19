package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class EnglishStudyRoom extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG =  "Testing:";
    private StudyRoom studyroom;
    private List<ImageButton> table;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ImageButton newPopUp_cancel;
    private DatabaseReference databaseReference;
    private List<Boolean> result = new ArrayList<Boolean>();
    Double time = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawerLayout drawerLayout = findViewById(R.id.englishDraw);
        table = new ArrayList<ImageButton>();
        studyroom =  getIntent().getExtras().getParcelable("EnglishRoom");
        studyroom.userGetInside();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        this.databaseReference = db.getReference(studyroom.getSubject()).child("id");
        setContentView(R.layout.activity_english_study_room); // activity_chinese_study_room
        getSupportActionBar().hide();
        Log.d(TAG,"StudyRoomData" + studyroom.getTableID_status());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studyroom.getTableID_status().clear();
                for(DataSnapshot key : snapshot.getChildren()){
                    studyroom.getTableID_status().add(key.getValue(Boolean.class));
                }
                updateChange(studyroom.getTableID_status());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        findViewById(R.id.imageMenuEnglish).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationChinese);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void updateChange(List<Boolean> result){
        this.result = result;
        Log.d(TAG, "updateChange: " + result);
        for(int i=0; i<16; i++) {
            int buttonId = this.getResources().getIdentifier("table"+i, "id", this.getPackageName());
            table.add((ImageButton)findViewById(buttonId));
            if(result.get(i) == true) {
                int finalI = i;
                table.get(i).setImageResource(R.drawable.table1);
                table.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pushMessage(Integer.toString(finalI),false);
                        createNewTableDialog(finalI);
                    }
                });
            }
            else
                table.get(i).setImageResource(R.drawable.tableonclick);
        }
    }


    public void pushMessage(String table_id , Boolean value) {
        databaseReference.child(table_id).setValue(value);
    }




    private void createNewTableDialog(int i) {
        dialogBuilder = new AlertDialog.Builder(this);
        final View tablePopUpView = getLayoutInflater().inflate(R.layout.popup, null);
        newPopUp_cancel = (ImageButton) tablePopUpView.findViewById(R.id.cancelButton);
        TextView timerText =  tablePopUpView.findViewById(R.id.timerTextpopup);
        dialogBuilder.setView(tablePopUpView);
        dialog = dialogBuilder.create();
        dialog.getWindow().setDimAmount(0);
        dialog.show();
        int table_ID = i;
        time = 0.0;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }

                    private String getTimerText() {
                        int rounded = (int) Math.round(time);
                        int seconds = ((rounded % 86400) % 3600) % 60;
                        int minutes = ((rounded % 86400) % 3600) / 60;
                        int hours = ((rounded % 86400) / 3600);
                        return formatTime(seconds, minutes, hours);
                    }

                    private String formatTime(int seconds, int minutes, int hours) {
                        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

        newPopUp_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define the cancel function
                dialog.dismiss();
                pushMessage(Integer.toString(table_ID),true);
                timerTask.cancel();
            }
        });

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
        return true;
    }
}

