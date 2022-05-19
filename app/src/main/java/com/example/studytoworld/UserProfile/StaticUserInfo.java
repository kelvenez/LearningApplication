package com.example.studytoworld.UserProfile;

public class StaticUserInfo {
    static String userName;
    static String password;
    static String email;

    public StaticUserInfo() {
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        StaticUserInfo.userName = userName;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        StaticUserInfo.password = password;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        StaticUserInfo.email = email;
    }
}
