package com.example.studytoworld.music;

import android.media.MediaPlayer;

public class MyMediaPlayer {
    static MediaPlayer instance;
    static MediaPlayer curInstanace;

    public static MediaPlayer getInstance(){
        if(instance == null){
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static MediaPlayer getCurInstance(){
        if(curInstanace == null){
            curInstanace = new MediaPlayer();
        }
        return curInstanace;
    }

    public static int currentIndex = -1;
}
