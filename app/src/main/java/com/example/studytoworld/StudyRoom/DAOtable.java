package com.example.studytoworld.StudyRoom;
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
    private static final String TAG = "Testing" ;
    private DatabaseReference databaseReference;
    private List<Boolean> result = new ArrayList<Boolean>();
    public interface  firebaseCallback{
        void onCallback(List<Boolean> tablesStatus);
    }
    public DAOtable(String subject) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(subject).child("id");
     /*   readData(tablesStatus -> {
                 for(Boolean k : tablesStatus)
                     result.add(k);
                 Log.d(TAG,"DAO TableStatus:"+tablesStatus);
                 Log.d(TAG,"DAO Result:"+result);
        }); */
        db.getReference(subject).child("id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    for(DataSnapshot snap : task.getResult().getChildren())
                        Log.d("firebase", "test" + snap);
                      //  result.add(snap.getValue(Boolean.class));
                }
            }
        });
    }

    public void readData(firebaseCallback firebasecallback){
        List<Boolean> keys = new ArrayList<Boolean>();
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot key : snapshot.getChildren()){
                    keys.add(key.getValue(Boolean.class));
                }
                firebasecallback.onCallback(keys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public List<Boolean> getResult() {
        return result;
    }
}

