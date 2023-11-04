package com.hzh.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hzh.qxapp.Bean.Post;
import com.hzh.qxapp.Bean.User;
import com.hzh.qxapp.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class Recive extends AppCompatActivity {

    private TextView username,content,time;
    private ImageView back;

    private ImageView rec_collect;

    private String user_onlyid;

    private CircleImageView touser;

    //关注按钮
    private Button focus_or_not;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive);

        initView();
        initData();

        getisrelated();

        final Intent in = getIntent();
        user_onlyid = in.getStringExtra("user_onlyid");

        //监听返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        touser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Recive.this,MyInfo.class);
                it.putExtra("user_onlyid",user_onlyid);
                startActivity(it);
            }
        });



        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Recive.this,MyInfo.class);
                it.putExtra("user_onlyid",user_onlyid);
                startActivity(it);
            }
        });

//        关注按钮的监听
        focus_or_not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关注监听
                //获取本app登录用户
                final User current_user = BmobUser.getCurrentUser(User.class);
                //新建一个User类，其实是现在对应页面的用户（点进来的）
                User this_user = new User();
                //设置objectid
                this_user.setObjectId(BmobUser.getCurrentUser(User.class).getObjectId());
                BmobRelation relation = new BmobRelation();

                focus_or_not.setText("已关注");
                focus_or_not.setTag("1");
                // 将这个用户列入关注列表里
                // 添加到多对多关联中
                relation.add(this_user);
                current_user.setFocuId(relation);
                current_user.increment("focusId_sum");
                current_user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            Toast.makeText(Recive.this, "关注成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Recive.this, "关注失败"+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



        //收藏监听
        rec_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = getIntent();
                String Id = in.getStringExtra("id");
                BmobQuery<Post> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(Id, new QueryListener<Post>() {
                    @Override
                    public void done(Post post, BmobException e) {
                        if (post.getIsrelated().equals("0")){

                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Post po = new Post();
                            po.setObjectId(Id);
                            po.setIsrelated("1");
                            BmobRelation relation = new BmobRelation();
                            relation.add(user);
                            po.setRelation(relation);
                            po.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        rec_collect.setImageResource(R.drawable.shoucang_black);
                                        Toast.makeText(Recive.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Recive.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Post po = new Post();
                            po.setObjectId(Id);
                            po.setIsrelated("0");
                            BmobRelation relation = new BmobRelation();
                            relation.remove(user);
                            po.setRelation(relation);
                            po.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        rec_collect.setImageResource(R.drawable.sc_normal);
                                        Toast.makeText(Recive.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Recive.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });

    }

    private void getisrelated() {
        Intent in = getIntent();
        String Id = in.getStringExtra("id");
        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (post.getIsrelated().equals("1")){
                    //已被收藏
                    rec_collect.setImageResource(R.drawable.shoucang_black);
                }else {
                    //无收藏

                }
            }
        });
    }


    private void initData() {

        //第二种
        Intent a = getIntent();
        Intent b = getIntent();
        Intent c = getIntent();
        String usernamea = a.getStringExtra("username");
        String contenta = a.getStringExtra("content");
        String timea = a.getStringExtra("time");

        username.setText(usernamea);
        content.setText(contenta);
        time.setText(timea);


    }

    private void initView() {
        username = findViewById(R.id.username);
        content = findViewById(R.id.content);
        time = findViewById(R.id.time);
        back = findViewById(R.id.back);
        rec_collect = findViewById(R.id.rec_collect);
        focus_or_not = findViewById(R.id.focus_or_not);
        touser = findViewById(R.id.touser);
    }
}
