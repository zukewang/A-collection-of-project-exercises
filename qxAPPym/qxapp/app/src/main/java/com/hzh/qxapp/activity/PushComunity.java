package com.hzh.qxapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hzh.qxapp.Bean.Comunity;
import com.hzh.qxapp.Bean.User;
import com.hzh.qxapp.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class PushComunity extends AppCompatActivity {

    private ImageView back;
    private EditText com_name,com_info;
    private Button pushcom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushcomunity);

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pushcom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = BmobUser.getCurrentUser(User.class);
                Comunity comunity = new Comunity();
                comunity.setName(com_name.getText().toString());
                comunity.setInfo(com_info.getText().toString());
                comunity.setIsrelated("0");
                comunity.setUserOnlyId(user.getObjectId());
                comunity.setUser(user);
                comunity.setOnwer(user.getUsername());
                comunity.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null){
                            Toast.makeText(PushComunity.this, "创建成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(PushComunity.this, "创建失败"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        com_name = findViewById(R.id.com_name);
        com_info = findViewById(R.id.com_info);
        pushcom = findViewById(R.id.pushcom);
     }
}
