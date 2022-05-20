package com.example.studytoworld.chatRoom;

import java.io.Serializable;

public class Message implements Serializable {
    String senderName;
    String messageTxt;
    String sendTime;

    public Message(){

    }

    public Message(String senderName, String messageTxt, String sendTime) {
        this.senderName = senderName;
        this.messageTxt = messageTxt;
        this.sendTime = sendTime;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessageTxt() {
        return messageTxt;
    }

    public void setMessageTxt(String messageTxt) {
        this.messageTxt = messageTxt;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
