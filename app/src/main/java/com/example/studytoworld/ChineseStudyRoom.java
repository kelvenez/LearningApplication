package com.example.studytoworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChineseStudyRoom extends AppCompatActivity {
    private static final String TAG =  "Testing:";
    private StudyRoom studyroom;
    private List<ImageButton> table;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ImageButton newPopUp_cancel;
    private DatabaseReference databaseReference;
    List<Boolean> testing = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("table").child("id");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                testing.clear();
                for(DataSnapshot key : snapshot.getChildren()){
                    testing.add(key.getValue(Boolean.class));
                }
                updateChange(testing);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        DAOtable test = new DAOtable(this);
//        test.setMessage();
//        testing = test.getMessage();
        setContentView(R.layout.activity_chinese_study_room);
        getSupportActionBar().hide();
        studyroom = new StudyRoom("Chinese");
        table = new ArrayList<ImageButton>();
        studyroom.userGetInside();
        Log.d(TAG, "createNewTableDialog: " + testing);

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

    public void updateChange(List<Boolean> keys){
        Log.d(TAG, "createNewTableDialog: " + testing);
        this.testing = keys;
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

}