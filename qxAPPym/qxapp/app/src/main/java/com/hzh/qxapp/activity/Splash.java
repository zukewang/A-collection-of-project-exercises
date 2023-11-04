package com.hzh.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hzh.qxapp.Bean.User;
import com.hzh.qxapp.MainActivity;
import com.hzh.qxapp.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class Splash extends AppCompatActivity {

    private TextView sp_time;

    private int time = 5;

    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);   //快捷键：alt + enter

        initView();

        //延时操作
         timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                User user = BmobUser.getCurrentUser(User.class);
                if (user!=null){
                    startActivity(new Intent(Splash.this,MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(Splash.this,Login.class));
                    finish();
                }
            }
        }, 3000);

        Bmob.initialize(this,"ab34b8bf6de6d97060af91d796f2b9e1");

        sp_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = BmobUser.getCurrentUser(User.class);
                if (user!=null){
                    startActivity(new Intent(Splash.this,MainActivity.class));
                }else {
                    timer.cancel();
                    startActivity(new Intent(Splash.this,Login.class));
                }
            }
        });


    }

    private void initView() {
        sp_time = findViewById(R.id.sp_time);
    }



}
