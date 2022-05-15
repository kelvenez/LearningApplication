package com.example.studytoworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudyRoom {
    private List<table> tables;
    private StringBuilder subject;
    private HashMap<Integer,Boolean> tableID_status;
    private int currentUserCounter = 0 ;
    StudyRoom(){
        tables = new ArrayList<table>(16); // new table -> create 10 -> get.10status from db.
        for(int i =0  ; i < 16 ; i++)
            tables.add(new table(i));
        subject.append("COMP4521");
    }

    public void userGetInside(){
        currentUserCounter++;
    }

    public void userLeave(){
        currentUserCounter--;
    }

    public StringBuilder getSubject() {
        return subject;
    }

    public int getCurrentUserCounter() {
        return currentUserCounter;
    }




}
