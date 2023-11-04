package zuke.com.qxapp.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;


public class Register extends AppCompatActivity {

    private EditText userName, passWord, nickName;
    private Button registerBtn, choosegender;

    private LinearLayout getman,getgril;

    private ImageView lookpass;

    private ImageView back;
    private ImageView reg_menu;

    private String mychoosegender = "man";

    private LinearLayout reg_help,reg_fankui,reg_quit;

    private Boolean isPswVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reg_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMenu();
            }
        });

        //对eye监听
        lookpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPswVisible();
            }
        });

        //设置性别监听
        choosegender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Register.this,R.style.BottomDialog);
                LinearLayout root = (LinearLayout) LayoutInflater.from(Register.this).inflate(R.layout.reg_choose,null);
                getman = root.findViewById(R.id.reg_man);
                getgril = root.findViewById(R.id.reg_gril);
                reg_quit = root.findViewById(R.id.reg_quit);
                getman.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mychoosegender = "man";
                        if (mychoosegender.toString().equals("man"))
                            choosegender.setText("男");
                        dialog.dismiss();
                    }
                });
                getgril.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mychoosegender = "gril";
                        if (mychoosegender.toString().equals("gril"))
                            choosegender.setText("女");
                        dialog.dismiss();
                    }
                });
                reg_quit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setContentView(root);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.BOTTOM);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.x = 0;
                lp.y = 0;
                lp.width = getResources().getDisplayMetrics().widthPixels;
                root.measure(0,0);
                lp.height = root.getMeasuredHeight();
                lp.alpha = 9f;
                window.setAttributes(lp);
                dialog.show();

            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUsername(userName.getText().toString().trim());
                user.setPassword(passWord.getText().toString().trim());
                user.setNickname(nickName.getText().toString().trim());
                user.setGender(mychoosegender);
                if (userName.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "用户名没有输入", Toast.LENGTH_SHORT).show();
                }else if(passWord.getText().toString().isEmpty()){
                    Toast.makeText(Register.this, "密码没有输入", Toast.LENGTH_SHORT).show();
                }else if (nickName.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "昵称没有输入", Toast.LENGTH_SHORT).show();
                }else if (passWord.getText().toString().length()<5){
                    Toast.makeText(Register.this, "密码需超过5位", Toast.LENGTH_SHORT).show();
                }else{
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, BmobException e) {
                            if (e == null){
                                Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });

    }


    private void getMenu() {
        final Dialog dialog = new Dialog(Register.this,R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.reg_help,null);
        root.findViewById(R.id.reg_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册帮助
                Toast.makeText(Register.this, "成功", Toast.LENGTH_SHORT).show();
            }
        });
        root.findViewById(R.id.reg_fankui).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //反馈
            }
        });
        root.findViewById(R.id.reg_quit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(root);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        lp.y = 0;
        lp.width = getResources().getDisplayMetrics().widthPixels;
        root.measure(0,0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f;
        window.setAttributes(lp);
        dialog.show();

    }

    private void setPswVisible() {
        isPswVisible = !isPswVisible;
        if (isPswVisible){
            lookpass.setImageResource(R.drawable.eyeopen);
            HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();
            passWord.setTransformationMethod(method);
        }else {
            lookpass.setImageResource(R.drawable.eyeclose);
            PasswordTransformationMethod method = PasswordTransformationMethod.getInstance();
            passWord.setTransformationMethod(method);
        }
    }


    private void initView(){
        userName = findViewById(R.id.username);
        passWord = findViewById(R.id.password);
        nickName = findViewById(R.id.nickname);
        registerBtn = findViewById(R.id.register);
        choosegender = findViewById(R.id.choosegender);
        lookpass = findViewById(R.id.lookpass);
        back = findViewById(R.id.back);
        reg_menu = findViewById(R.id.reg_menu);
    };
}




