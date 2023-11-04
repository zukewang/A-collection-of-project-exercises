package zuke.com.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

public class PasswordSetting extends AppCompatActivity {

    private ImageView passworsetback;
    private TextView originpwd, changedpwd;
    private Button pwdsetBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordset);

        initView();

        passworsetback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pwdsetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (originpwd.getText().toString().trim().isEmpty()){
                    Toast.makeText(PasswordSetting.this, "请输入原密码", Toast.LENGTH_SHORT).show();
                }else if (changedpwd.getText().toString().trim().isEmpty()){
                    Toast.makeText(PasswordSetting.this, "请输入想要的密码", Toast.LENGTH_SHORT).show();
                }else{
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setPassword(changedpwd.getText().toString());
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Toast.makeText(PasswordSetting.this, "更新成功", Toast.LENGTH_SHORT).show();
                                BmobUser.logOut();
                                startActivity(new Intent(PasswordSetting.this,Login.class));
                                finish();
                            }else{
                                Toast.makeText(PasswordSetting.this, "更新成功", Toast.LENGTH_SHORT).show();
                                Log.d("Passwordsetting", e.toString());

                            }
                        }
                    });
                }
            }
        });

    }

    private void initView() {
        passworsetback = findViewById(R.id.passworsetback);
        originpwd = findViewById(R.id.originpwd);
        changedpwd = findViewById(R.id.changedpwd);
        pwdsetBtn = findViewById(R.id.pwdsetBtn);
    }

}
