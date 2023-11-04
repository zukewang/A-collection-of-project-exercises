package com.hzh.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hzh.qxapp.R;

import cn.bmob.v3.BmobUser;

public class Setting extends AppCompatActivity {

        private Button loginout;

        private ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

                loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(Setting.this,Login.class));
                //
                finish();
            }
        });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    private void initView() {
        back = findViewById(R.id.back);
        loginout = findViewById(R.id.loginout);
    }
}
