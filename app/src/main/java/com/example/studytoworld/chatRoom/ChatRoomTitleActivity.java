package com.example.studytoworld.chatRoom;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studytoworld.R;

import java.util.ArrayList;

public class ChatRoomTitleActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noChatRoomTextView;
    ArrayList<String> chatRoomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_chatroom_title);

        recyclerView = findViewById(R.id.recycler_title_view);
        noChatRoomTextView = findViewById(R.id.no_room_text);

        chatRoomList.add("AI");
        chatRoomList.add("HCI");
        chatRoomList.add("MobileApp");
        chatRoomList.add("Multimedia");
        chatRoomList.add("VR");
        String userName = getIntent().getStringExtra("userName");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ChatRoomTitleAdapter(chatRoomList, userName, getApplicationContext()));


//        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
//        while(cursor.moveToNext()){
//            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
//            if(new File(songData.getPath()).exists())
//                songsList.add(songData);
//        }
//
//        if(songsList.size()==0){
//            noMusicTextView.setVisibility(View.VISIBLE);
//        }else{
//            //recyclerview
//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
//            recyclerView.setAdapter(new MusicListAdapter(songsList,getApplicationContext()));
//        }

    }
}
