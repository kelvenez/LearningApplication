package com.example.studytoworld.UserProfile;

import java.util.ArrayList;

public class StudyTime {
    ArrayList<Integer> subjectStudyTime;
    int totalStudyTime;

    public StudyTime() {
    }

    public StudyTime(int totalStudyTime) {
        this.subjectStudyTime = new ArrayList<>();
        this.totalStudyTime = totalStudyTime;
    }

    public ArrayList<Integer> getSubjectStudyTime() {
        return subjectStudyTime;
    }

    public void setSubjectStudyTime(ArrayList<Integer> subjectStudyTime) {
        this.subjectStudyTime = subjectStudyTime;
    }

    public int getTotalStudyTime() {
        return totalStudyTime;
    }

    public void setTotalStudyTime(int totalStudyTime) {
        this.totalStudyTime = totalStudyTime;
    }
}
