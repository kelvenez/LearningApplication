package com.example.studytoworld;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class ChineseStudyRoom extends AppCompatActivity {
    private static final String TAG =  "Testing:";
    private StudyRoom studyroom;
    private List<ImageButton> table;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ImageButton newPopUp_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinese_study_room);
        getSupportActionBar().hide();
        studyroom = new StudyRoom("Chinese");
        table = new ArrayList<ImageButton>();
        studyroom.userGetInside();
        DAOtable test = new DAOtable();
        List<Boolean> testing = test.getMessage();
        Log.d(TAG, "createNewTableDialog: " + testing);

        for(int i=0; i<16; i++) {
            int buttonId = this.getResources().getIdentifier("table"+i, "id", this.getPackageName());
            table.add((ImageButton)findViewById(buttonId));
            table.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        createNewTableDialog();
                }
            });
       }
    }
    private void createNewTableDialog() {
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