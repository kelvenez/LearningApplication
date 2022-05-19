package com.example.studytoworld.UserProfile;

import com.example.studytoworld.Schedule.Schedule;

import java.util.ArrayList;

public class ScheduleList {

    String title;
    //ArrayList<Schedule> list;


    public ScheduleList(String title) {
        this.title=title;
        //list=new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //public ArrayList<Schedule> getList() {
        //return list;
    //}

    //public void setList(ArrayList<Schedule> list) {
    //    this.list = list;
    //}
}
