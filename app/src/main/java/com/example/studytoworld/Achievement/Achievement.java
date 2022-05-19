package com.example.studytoworld.Achievement;

public class Achievement {
    int condition;
    String content;

    public Achievement() {
    }

    public Achievement(int condition, String content) {
        this.condition = condition;
        this.content = content;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
