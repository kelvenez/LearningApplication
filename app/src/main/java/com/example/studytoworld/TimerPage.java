package com.example.studytoworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class TimerPage extends AppCompatActivity {

    TextView timerText;
    Button startStopButton;

    Timer timer;
    TimerTask timerTask;
    Double time = 0.0;

    boolean timerStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = (TextView) findViewById(R.id.timerText);
        startStopButton = (Button) findViewById(R.id.startStopButton);

        timer = new Timer();
    }

    public void resetTapped(View view){
        AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
        resetAlert.setTitle("Reset Timer");
        resetAlert.setMessage("Are you sure you want ot reset the timer?");
        resetAlert.setPositiveButton("Reset", new DialogInterface. OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                if(timerTask != null){
                    timerTask.cancel();
                    startStopButton.setText("START");
                    startStopButton.setTextColor(Color.parseColor("#34c759"));
                    time = 0.0;
                    timerStarted = false;
                    timerText.setText(formatTime(0, 0, 0));
                }
            }
        });
        resetAlert.setNeutralButton("Cancel", new DialogInterface. OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){

            }
        });
        resetAlert.show();
    }

    public void startStopTapped(View view){
        if(timerStarted == false){
            timerStarted = true;
            startStopButton.setText("STOP");
            startStopButton.setTextColor(Color.parseColor("#ff3b30"));
            startTimer();
        }else{
            timerStarted  = false;
            startStopButton.setText("START");
            startStopButton.setTextColor(Color.parseColor("#34c759"));

            timerTask.cancel();
        }
    }

    private void startTimer(){
        timerTask = new TimerTask(){
            @Override
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private String getTimerText(){
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hours = ((rounded % 86400) / 3600);
        return formatTime(seconds, minutes, hours);
    }

    private String formatTime(int seconds, int minutes, int hours){
        return String.format("%02d", hours) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }
}