package zuke.com.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.BmobUser;
import zuke.com.qxapp.Fragment.FragmentMine;
import zuke.com.qxapp.R;
import zuke.com.qxapp.UserManager;

public class Setting extends AppCompatActivity {
    private Button loginout, infosetting, usermanager_set;

    private ImageView back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();

        infosetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this,MyInfoSetting.class));
            }
        });

        usermanager_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this, UserManager.class));
                finish();
            }
        });

        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(Setting.this,Login.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Setting.this, FragmentMine.class));
            }
        });
    }

    private void initView() {
        back = findViewById(R.id.back);
        loginout = findViewById(R.id.loginout);
        infosetting = findViewById(R.id.infosetting);
        usermanager_set = findViewById(R.id.usermanager_set);
    }
}
