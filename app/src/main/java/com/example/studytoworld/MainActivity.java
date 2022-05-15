package com.example.studytoworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.os.Bundle;

import com.example.studytoworld.Schedule.CreateLearningSchedule;
import com.example.studytoworld.Schedule.LearningSchedule;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ImageButton table1 ,newPopUp_cancel;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private Button schedule;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        table1 = findViewById(R.id.table1);
        schedule = findViewById(R.id.schedule);
     //   DAOtable test = new DAOtable();
     //   test.add("hello");
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
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
    }

    private void moveToSchedulePage() {
        Intent intent = getIntent();
        password = intent.getStringExtra("password");

        Intent intent1 = new Intent(MainActivity.this, LearningSchedule.class);
        intent1.putExtra("password", password);
        startActivity(intent1);
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

