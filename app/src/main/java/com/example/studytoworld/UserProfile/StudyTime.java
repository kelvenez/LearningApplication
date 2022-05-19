package com.example.studytoworld.UserProfile;

import java.util.ArrayList;
import java.util.HashMap;

public class StudyTime {
    SubjectTime subjectStudyTime;
    int totalStudyTime;

    public StudyTime() {
    }

    public StudyTime(int totalStudyTime) {
        this.subjectStudyTime = new SubjectTime();
        this.totalStudyTime = totalStudyTime;
    }


    public int getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setTotalStudyTime(int totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }

    private class SubjectTime {
        String title;
        HashMap<String, Integer> hashMap;

        public SubjectTime() {
            title = "Subject";
            hashMap= new HashMap<>();
            hashMap.put("Chinese",0);
            hashMap.put("English",0);
        }
    }
}
