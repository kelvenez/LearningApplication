package com.example.studytoworld;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DAOtable {
    private DatabaseReference databaseReference;

    public DAOtable() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference();
    }

    public void pushMessage(String testing) {

        databaseReference.child("Testing").setValue(testing);
    }
    
    /*public void getMessage(){

    }*/
 


}
