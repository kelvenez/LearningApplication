package com.example.studytoworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.os.Bundle;

import com.example.studytoworld.Schedule.CreateLearningSchedule;
import com.example.studytoworld.Schedule.LearningSchedule;
import com.example.studytoworld.MusicPlay;

public class MainActivity extends AppCompatActivity {
    private ImageButton table1 ,newPopUp_cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button schedule, bg_music;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        table1 = findViewById(R.id.table1);
        schedule = findViewById(R.id.schedule);
        bg_music = findViewById(R.id.music_button);
        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewTableDiaglog();
            }
        });
        schedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){moveToSchedulePage();}
        });
        bg_music.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){moveToMusicPage();}
        });
    }

    private void moveToSchedulePage() {
        Intent intent = getIntent();
        password = intent.getStringExtra("password");

        Intent intent1 = new Intent(MainActivity.this, LearningSchedule.class);
        intent1.putExtra("password", password);
        startActivity(intent1);
    }

    private void moveToMusicPage() {
        Intent intent = getIntent();
        password = intent.getStringExtra("password");

        Intent intent2 = new Intent(MainActivity.this, MusicPlay.class);
        startActivity(intent2);
    }

    private void createNewTableDiaglog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View tablePopUpView = getLayoutInflater().inflate(R.layout.popup, null);
        newPopUp_cancel = (ImageButton) tablePopUpView.findViewById(R.id.cancelButton);
        dialogBuilder.setView(tablePopUpView);
        dialog = dialogBuilder.create();
        dialog.show();

        newPopUp_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define the cancel function
                dialog.dismiss();
            }
        });

    }
}

