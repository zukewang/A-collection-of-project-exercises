package zuke.com.qxapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import zuke.com.qxapp.Bean.Post;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

public class PushContent extends AppCompatActivity {

    private EditText pushContent, pushtitle;
    private ImageView back;
    private Button push;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pushcontent);

        initView();

        //发帖操作
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pushContent.getText().toString().isEmpty()){
                    Toast.makeText(PushContent.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }else{
                    User user = BmobUser.getCurrentUser(User.class);
                    Post post = new Post();
                    post.setName(user.getUsername());
                    post.setTitle(pushtitle.getText().toString());
                    post.setContent(pushContent.getText().toString());
                    post.setIsrelated("0");
                    post.setAuthor(user);
                    post.setUserOnlyId(user.getObjectId());
                    post.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null){
                                pushContent.setText("");
                                Toast.makeText(PushContent.this, "发布成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(PushContent.this, "发布失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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
        pushtitle = findViewById(R.id.pushtitle);
        pushContent = findViewById(R.id.pushcontent);
        push = findViewById(R.id.push);
        back = findViewById(R.id.back);
    }
}
