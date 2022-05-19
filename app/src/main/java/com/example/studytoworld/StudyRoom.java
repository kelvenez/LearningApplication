package com.example.studytoworld;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudyRoom implements Parcelable {
    private List<table> tables;
    private String subject;
    private List<Boolean> tableID_status ;
    private int currentUserCounter;
    private DAOtable datalist;
    StudyRoom(String subject){
        datalist = new DAOtable(subject);
        currentUserCounter = 0 ;
        tables = new ArrayList<table>(16); // new table -> create 10 -> get.10status from db.
        for(int i =0  ; i < 16 ; i++) {
            tables.add(i, new table(i));
        }
        tableID_status = datalist.getResult();
        for(Boolean value : tableID_status)
            if(value)
                currentUserCounter++;
        this.subject = subject;
    }

    protected StudyRoom(Parcel in) {
        currentUserCounter = in.readInt();
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

    public DAOtable getDatalist() {
        return datalist;
    }

    public List<table> getTables() {
        return tables;
    }

    public List<Boolean> getTableID_status() {
        return tableID_status;
    }

    public void userGetInside(){
        currentUserCounter++;
    }

    public void userLeave(){
        currentUserCounter--;

    }

    public int getCurrentUserCounter() {
        return currentUserCounter;

    }

    public StringBuffer getSubject() {
        return subject;
    }

    public int getCurrentUserCounter() {
        return currentUserCounter;
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
        dest.writeInt(currentUserCounter);
        dest.writeList(tableID_status);
        dest.writeString(subject);
        dest.writeList(tables);

    }
}
