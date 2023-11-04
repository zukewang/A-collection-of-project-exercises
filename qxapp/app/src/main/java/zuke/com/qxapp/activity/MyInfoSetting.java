package zuke.com.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import zuke.com.qxapp.R;

public class MyInfoSetting extends AppCompatActivity {

    private ImageView settingback;

    private LinearLayout nicknameset, passwordset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfosetting);

        initView();

        settingback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nicknameset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyInfoSetting.this,NicknameSetting.class));
            }
        });

        passwordset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyInfoSetting.this,PasswordSetting.class));
            }
        });

    }

    private void initView() {
        settingback = findViewById(R.id.settingback);
        nicknameset = findViewById(R.id.nicknameset);
        passwordset = findViewById(R.id.passwordset);
    }
}
