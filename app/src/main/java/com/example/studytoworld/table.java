package com.example.studytoworld;

import android.os.Parcel;
import android.os.Parcelable;

public class table implements Parcelable {
    private int tableID;
    private boolean status;
    private String userName;

    public table(int id) {
        this.tableID = id;
        status = false;
    }

    protected table(Parcel in) {
        tableID = in.readInt();
        status = in.readByte() != 0;
        userName = in.readString();
    }

    public static final Creator<table> CREATOR = new Creator<table>() {
        @Override
        public table createFromParcel(Parcel in) {
            return new table(in);
        }

        @Override
        public table[] newArray(int size) {
            return new table[size];
        }
    };

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

    public int getTableID() {
        return tableID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(tableID);
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(userName);
    }
}
