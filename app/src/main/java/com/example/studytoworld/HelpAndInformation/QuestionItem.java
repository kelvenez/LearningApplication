package com.example.studytoworld.HelpAndInformation;

public class QuestionItem {
    String uid;
    String content;

    public QuestionItem() {
    }

    public QuestionItem(String uid, String content) {
        this.uid = uid;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
