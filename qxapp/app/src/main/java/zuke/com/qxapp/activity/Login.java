package zuke.com.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.MainActivity;
import zuke.com.qxapp.R;
import zuke.com.qxapp.UserManager;

public class Login extends AppCompatActivity {

    private EditText userName, passWord;
    private Button loginBtn, registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(userName.getText().toString().trim());
                user.setPassword(passWord.getText().toString().trim());
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User o, BmobException e) {
                        if (e == null){
//                            if(user.getIsmanager == "n"){
                            Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
//                            }
//                            else if(user.getIsmanager == "y"){
//                                Toast.makeText(Login.this, "欢迎管理员", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(Login.this, UserManager.class));
//                                finish();
//                            }
                        }else {
                            Log.d("wyz", String.valueOf(e));
                            Toast.makeText(Login.this, "登陆失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}
