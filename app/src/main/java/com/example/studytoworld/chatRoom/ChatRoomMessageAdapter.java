package com.example.studytoworld.chatRoom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.studytoworld.R;

import java.util.ArrayList;

public class ChatRoomMessageAdapter extends RecyclerView.Adapter<ChatRoomMessageAdapter.ViewHolder>{
    ArrayList<Message> messageList;
    Context context;

    public ChatRoomMessageAdapter(ArrayList<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message,parent,false);
        return new ChatRoomMessageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatRoomMessageAdapter.ViewHolder holder, int position) {
        Message messageData = messageList.get(position);
        holder.senderTextView.setText(messageData.getSenderName());
        holder.messageTextView.setText(messageData.getMessageTxt());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView senderTextView;
        TextView messageTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            senderTextView = itemView.findViewById(R.id.sender_name_text);
            messageTextView = itemView.findViewById(R.id.message_text);
        }
    }
}

