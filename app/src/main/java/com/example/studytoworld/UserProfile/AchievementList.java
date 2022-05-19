package com.example.studytoworld.UserProfile;

import com.example.studytoworld.Achievement.Achievement;

import java.util.ArrayList;

public class AchievementList {
    int title;
    //ArrayList<Achievement> list;
    String dummy;

    public AchievementList(int title,String dummy) {
        this.title=title;
        this.dummy = dummy;
        //list = new ArrayList<>();
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}
