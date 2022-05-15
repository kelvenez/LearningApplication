package com.example.studytoworld;

public class table {
    private int tableID;
    private boolean status;
    private StringBuilder subject;
    private String userName;
    private DAOtable db;

    public table(int id) {
        this.tableID = id;
        status = false;
        db = new DAOtable();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public StringBuilder getSubject() {
        return subject;
    }

    public void setSubject(StringBuilder subject) {
        this.subject = subject;
    }

    public int getTableID() {
        return tableID;
    }



}
