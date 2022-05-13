package com.example.studytoworld.Schedule;

import android.util.Log;

public class Schedule {
    public int id;
    public String subject;
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;

    public Schedule(){

    }

    public Schedule(int id, String subject, int year, int month, int day, int hour, int minute) {
        this.id=id;
        this.subject = subject;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }


}
