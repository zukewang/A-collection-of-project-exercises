package zuke.com.qxapp.activity;

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
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

public class NicknameSetting extends AppCompatActivity {

    private ImageView nicknamesetback;
    private TextView originnickname, changednickname;
    private Button nicknamesetBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nicknameset);

        Bmob.initialize(NicknameSetting.this,"2b32d5db51bbd66773b4d95c72353b38");

        initView();

        nicknamesetback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nicknamesetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (originnickname.getText().toString().trim().isEmpty()){
                    Toast.makeText(NicknameSetting.this, "请输入原昵称", Toast.LENGTH_SHORT).show();
                }else if (changednickname.getText().toString().trim().isEmpty()){
                    Toast.makeText(NicknameSetting.this, "请输入想要的昵称", Toast.LENGTH_SHORT).show();
                }else{
                    User user = BmobUser.getCurrentUser(User.class);
                    user.setNickname(changednickname.getText().toString());
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Toast.makeText(NicknameSetting.this, "更新成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(NicknameSetting.this, "更新成功", Toast.LENGTH_SHORT).show();
                                Log.d("Nicknamesetting", e.toString());
                            }
                        }
                    });
                }
            }
        });

    }

    private void initView() {
        nicknamesetback = findViewById(R.id.nicknamesetback);
        originnickname = findViewById(R.id.originnickname);
        changednickname = findViewById(R.id.changednickname);
        nicknamesetBtn = findViewById(R.id.nicknamesetBtn);

    }
}
