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

import de.hdodenhof.circleimageview.CircleImageView;

import zuke.com.qxapp.Bean.Post;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Receive extends AppCompatActivity {

    private TextView username, title, content, time;

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

//        touser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(Recive.this,MyInfo.class);
//                it.putExtra("user_onlyid",user_onlyid);
//                startActivity(it);
//            }
//        });
//
//        username.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it = new Intent(Recive.this,MyInfo.class);
//                it.putExtra("user_onlyid",user_onlyid);
//                startActivity(it);
//            }
//        });

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
                            Toast.makeText(Receive.this, "关注成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Receive.this, "关注失败"+e, Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(Receive.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Receive.this, "收藏失败"+e, Toast.LENGTH_SHORT).show();
                                        Log.d("Receive收藏：", e.toString());
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
                                        Toast.makeText(Receive.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Receive.this, "取消收藏失败"+e, Toast.LENGTH_SHORT).show();
                                        Log.d("Receive收藏:",e.toString());
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
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        BmobQuery<Post> query = new BmobQuery<>();
        query.getObject(id, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                if (e == null){
                    username.setText(post.getName());
                    content.setText(post.getContent());
                    time.setText(post.getCreatedAt());
                    title.setText(post.getTitle());
                }else{
                    Toast.makeText(Receive.this, "获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        username = findViewById(R.id.recusername);
        time = findViewById(R.id.rectime);
        content = findViewById(R.id.reccontent);
        title = findViewById(R.id.rectitle);
        back = findViewById(R.id.back);
        rec_collect = findViewById(R.id.rec_collect);
        focus_or_not = findViewById(R.id.focus_or_not);
        touser = findViewById(R.id.touser);


    }
}
