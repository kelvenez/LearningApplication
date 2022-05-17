package com.example.studytoworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudyRoom {
    private HashMap<Integer,table> tables;
    private StringBuffer subject;
    private HashMap<table,Boolean> tableID_status;
    private int currentUserCounter = 0 ;
    StudyRoom(String subject){
        tables = new HashMap<Integer,table>(16); // new table -> create 10 -> get.10status from db.
        for(int i =0  ; i < 16 ; i++) {
            tables.put(i, new table(i));
        }
       // this.subject.append(subject);
    }

    public HashMap<Integer, table> getTables() {
        return tables;
    }

    public HashMap<table, Boolean> getTableID_status() {
        return tableID_status;
    }

    public void userGetInside(){
        currentUserCounter++;
    }

    public void userLeave(){
        currentUserCounter--;
    }

    public StringBuffer getSubject() {
        return subject;
    }

    public int getCurrentUserCounter() {
        return currentUserCounter;
    }




}
