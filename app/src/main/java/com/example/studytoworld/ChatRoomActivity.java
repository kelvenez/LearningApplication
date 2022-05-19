package com.example.studytoworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoomActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText messageBox;
    ImageView sendButton;
    TextView titleTextView;
    TextView noMessageTextView;
    String chatroomName;
    ChatRoomMessageAdapter messageAdapter;
    ArrayList<Message> messageList = new ArrayList<>();
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_chatroom);
        recyclerView = findViewById(R.id.recycler_chat_view);
        messageBox = findViewById(R.id.messageBox);
        sendButton = findViewById(R.id.sendButton);
        titleTextView = findViewById(R.id.chatroom_title_text);
        noMessageTextView = findViewById(R.id.no_chat_text);
        Intent intent = getIntent();
        chatroomName=intent.getStringExtra("NAME");
        titleTextView.setText(chatroomName);
        dbRef = FirebaseDatabase.getInstance().getReference("chatrooms").child(chatroomName);
        messageAdapter = new ChatRoomMessageAdapter(messageList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
        dbRef.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Message message = dataSnapshot.getValue(Message.class);
                    messageList.add(message);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String message = messageBox.getText().toString();
                if(!message.isEmpty()) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    Message messageObj = new Message("None", message, currentDateandTime);
                    dbRef.push().setValue(messageObj);
                    messageBox.setText("");
                }
            }
        });
    }
}
