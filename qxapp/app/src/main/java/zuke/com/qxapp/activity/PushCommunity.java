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

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

public class PushCommunity extends AppCompatActivity {

    private ImageView back;
    private TextView communityname, communityinfo;
    private Button pushCommunity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushcommunity);

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pushCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (communityname.getText().toString().isEmpty() || communityinfo.getText().toString().isEmpty()) {
                    Toast.makeText(PushCommunity.this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    User user = BmobUser.getCurrentUser(User.class);
                    Community community = new Community();
                    community.setName(communityname.getText().toString());
                    community.setInfo(communityinfo.getText().toString());
                    community.setIsrelated("0");
                    community.setUser(user);
                    community.setOwner(user.getUsername());
                    community.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(PushCommunity.this, "创建成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PushCommunity.this, "创建失败" + e, Toast.LENGTH_SHORT).show();
                                Log.d("community错误:", e.toString());
                            }
                        }
                    });
                }
            }
        });

    }


    private void initView() {
        try {
            back = findViewById(R.id.pushcom_back);
            communityname = findViewById(R.id.pushcom_name);
            communityinfo = findViewById(R.id.pushcom_info);
            pushCommunity = findViewById(R.id.pushcombtn);
        }catch(Exception e){
            System.out.println("异常："+e.getMessage());
        }
    }


}
