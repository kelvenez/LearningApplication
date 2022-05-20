package com.example.studytoworld.chatRoom;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studytoworld.R;

import java.util.ArrayList;

public class ChatRoomTitleAdapter extends RecyclerView.Adapter<ChatRoomTitleAdapter.ViewHolder>{

    ArrayList<String> chatroomList;
    Context context;
    String userName;

    public ChatRoomTitleAdapter(ArrayList<String> chatroomList, String userName, Context context) {
        this.chatroomList = chatroomList;
        this.context = context;
        this.userName = userName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatroom_title_item,parent,false);
        return new ChatRoomTitleAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titleTextView.setText(chatroomList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //navigate to another acitivty
                Intent intent = new Intent(context,ChatRoomActivity.class);
                intent.putExtra("NAME", chatroomList.get(position));
                intent.putExtra("userName", userName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatroomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTextView;
        ImageView iconImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.chatroom_title_text);
            iconImageView = itemView.findViewById(R.id.chatroom_icon_view);
        }
    }
}
