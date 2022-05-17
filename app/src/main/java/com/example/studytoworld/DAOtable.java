package com.example.studytoworld;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DAOtable {
    ChineseStudyRoom chineseStudyRoom;
    private DatabaseReference databaseReference;
    private List<String> result = new ArrayList<String>();
    public interface  DataStatus{
        void DataIsLoad(List<Boolean> tables, List<Integer> Key);
    }


    public DAOtable() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("table").child("id");
    }

    public DAOtable(ChineseStudyRoom chineseStudyRoom) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("table").child("id");
        this.chineseStudyRoom = chineseStudyRoom;
    }

    public void pushMessage(String testing) {
      //  databaseReference.child("table").setValue(testing);
    }
    
    public void setMessage(){
        List<Boolean> keys = new ArrayList<Boolean>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot key : snapshot.getChildren()){
                    keys.add(key.getValue(Boolean.class));
                }
                chineseStudyRoom.updateChange(keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Boolean> getMessage(){
        List<Boolean> keys = new ArrayList<>();
        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for(DataSnapshot key : task.getResult().getChildren()){
                        keys.add(key.getValue(Boolean.class));
                    }
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });
        return keys;
    }
 }


