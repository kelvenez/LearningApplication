package com.example.studytoworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StudyRoom {
    private List<table> tables;
    private String subject;
    private HashMap<Integer,Boolean> tableID_status;

    StudyRoom(){
        tables = new ArrayList<table>(); // new table -> create 10 -> get.10status from db.
        subject = "COMP4521";
    }


}
