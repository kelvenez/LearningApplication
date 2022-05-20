package com.example.studytoworld.HelpAndInformation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studytoworld.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HelpAndInformation extends AppCompatActivity {

    EditText contentView;
    Button button;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_and_information);

        contentView = findViewById(R.id.content);
        button=findViewById(R.id.submit_button);
        databaseReference= FirebaseDatabase.getInstance().getReference("questions");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitQuestion();
            }
        });
    }


    public void submitQuestion() {
        databaseReference= FirebaseDatabase.getInstance().getReference("questions");
        String content = String.valueOf(contentView.getText());
        Intent intent = getIntent();
        String userId = intent.getStringExtra("uid");

        Log.d("TestingHelp", String.valueOf(new QuestionItem(userId,content)));
        databaseReference.child(userId).setValue(new QuestionItem(userId,content));
    }
}