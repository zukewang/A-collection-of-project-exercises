package zuke.com.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.MainActivity;
import zuke.com.qxapp.R;
import zuke.com.qxapp.UserManager;

public class Splash extends AppCompatActivity {

    private TextView sp_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

        //延时操作
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000);

        Bmob.initialize(this,"2b32d5db51bbd66773b4d95c72353b38");

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

    TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
//                startActivity(new Intent(Splash.this, MainActivity.class));
                BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                User user = new User();
                if (bmobUser != null){
//                    if (user.getIsmanager == "n"){
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();
//                } else if (user.getIsmanager == "y"){
//                        startActivity(new Intent(Splash.this, UserManager.class));
//                        finish();
//                    }
                }else{
                    //没有登陆
                    startActivity(new Intent(Splash.this, Login.class));
                    finish();
                }

            }
        };
}
