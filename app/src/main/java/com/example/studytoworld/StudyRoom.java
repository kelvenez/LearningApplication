package com.example.studytoworld;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class StudyRoom implements Parcelable {
    private static final String TAG = "Testing" ;
    private List<table> tables;
    private String subject;
    private List<Boolean> tableID_status ;

    StudyRoom(String subject){
        tables = new ArrayList<table>(16); // new table -> create 10 -> get.10status from db.
        for(int i =0  ; i < 16 ; i++) {
            tables.add(i, new table(i));
        }
        this.subject = subject;
        tableID_status = new ArrayList<Boolean>();
    }

    protected StudyRoom(Parcel in) {
        tableID_status = new ArrayList<Boolean>();
        in.readList(tableID_status,Boolean.class.getClassLoader());
        subject = in.readString();
        tables = new ArrayList<table>();
        in.readList(tables,table.class.getClassLoader());
    }




    public static final Creator<StudyRoom> CREATOR = new Creator<StudyRoom>() {
        @Override
        public StudyRoom createFromParcel(Parcel in) {
            return new StudyRoom(in);
        }

        @Override
        public StudyRoom[] newArray(int size) {
            return new StudyRoom[size];
        }
    };

    public List<table> getTables() {
        return tables;
    }

    public List<Boolean> getTableID_status() {
        return tableID_status;
    }


    public String getSubject() {
        return subject;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(tableID_status);
        dest.writeString(subject);
        dest.writeList(tables);

    }




}
