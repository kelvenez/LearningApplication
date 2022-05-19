package com.example.studytoworld.UserProfile;

import com.example.studytoworld.Achievement.Achievement;
import com.example.studytoworld.Schedule.Schedule;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInfo {
    String email, password, userName;
    ScheduleList schedules;
    AchievementList achievements;
    StudyTime studyTime;
    String id;

    public UserInfo() {

    }

    public UserInfo(String email, String password, String userName, String id,ScheduleList scheduleList, AchievementList achievementList, StudyTime studyTime ) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.id=id;
        this.schedules=scheduleList;
        this.achievements=achievementList;
        this.studyTime = studyTime;
    }

    public StudyTime getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(StudyTime studyTime) {
        this.studyTime = studyTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
