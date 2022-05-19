package com.example.studytoworld.UserProfile;

import java.util.ArrayList;
import java.util.HashMap;

public class StudyTime {
    HashMap<String, Integer> subjectStudyTime;
    int totalStudyTime;

    public StudyTime() {
    }

    public StudyTime(int totalStudyTime) {
        this.subjectStudyTime = new HashMap<>();
        subjectStudyTime.put("Chinese",0);
        subjectStudyTime.put("English",0);
        this.totalStudyTime = totalStudyTime;
    }


    public int getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setTotalStudyTime(int totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }
}
