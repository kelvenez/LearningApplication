package com.example.studytoworld;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DAOtable {
    private DatabaseReference databaseReference;
    private List<String> result = new ArrayList<String>();
    public interface  DataStatus{
        void DataIsLoad(List<Boolean> tables, List<Integer> Key);
    }


    public DAOtable() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("table").child("id");
    }

    public void pushMessage(String testing) {
      //  databaseReference.child("table").setValue(testing);
    }
    
    public List<Boolean> getMessage(){
        List<Boolean> keys = new ArrayList<Boolean>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "Testing the DAO";

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot key : snapshot.getChildren()){
                    keys.add(key.getValue(Boolean.class));
                    Log.d(TAG, ": " + key.getValue(Boolean.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return keys;
    }
 }


