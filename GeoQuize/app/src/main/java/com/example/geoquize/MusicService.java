package com.example.geoquize;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    MediaPlayer mPlayer = null;
    public MusicService(){}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mPlayer == null){
            mPlayer = MediaPlayer.create(this, R.raw.lemon);
            mPlayer.setLooping(false);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            int op = intent.getIntExtra("op", 0);
            switch(op){
                case 1:
                    if(mPlayer != null && !mPlayer.isPlaying())
                        mPlayer.start();
                    break;
                case 2:
                    if(mPlayer != null && mPlayer.isPlaying()){
                        mPlayer.stop();
                        try {
                            mPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }
}