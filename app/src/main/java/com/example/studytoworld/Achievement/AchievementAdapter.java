package com.example.studytoworld.Achievement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studytoworld.R;
import com.example.studytoworld.Achievement.Achievement;
import com.example.studytoworld.Achievement.AchievementAdapter;

import java.util.ArrayList;
import java.util.Locale;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>{

    Context context;
    ArrayList<Achievement> list;

    public AchievementAdapter(Context context, ArrayList<Achievement> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.achievement_item,parent,false);
        return new AchievementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementAdapter.AchievementViewHolder holder, int position) {

        Achievement achievement = list.get(position);
        holder.content.setText(achievement.getContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class AchievementViewHolder extends RecyclerView.ViewHolder{

        TextView content;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }
}
