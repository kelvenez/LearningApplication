package com.example.studytoworld.Schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studytoworld.R;

import java.util.ArrayList;
import java.util.Locale;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    Context context;
    ArrayList<Schedule> list;

    public ScheduleAdapter(Context context, ArrayList<Schedule> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.schedule_item,parent,false);
        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {

        Schedule schedule = list.get(position);
        holder.subject.setText(schedule.getSubject());
        holder.date.setText(schedule.getDay()+"/"+ (schedule.getMonth()+1)+"/"+schedule.getYear());
        holder.time.setText(String.format(Locale.getDefault(),"%02d:%02d", schedule.getHour(),schedule.getMinute()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ScheduleViewHolder extends RecyclerView.ViewHolder{

        TextView subject,date,time;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subject);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
