package com.hzh.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.het.smarttab.SmartTabLayout;
import com.het.smarttab.v4.FragmentPagerItem;
import com.het.smarttab.v4.FragmentPagerItems;
import com.het.smarttab.v4.FragmentStatePagerItemAdapter;
import com.hzh.qxapp.Bean.Comunity;
import com.hzh.qxapp.Bean.Post;
import com.hzh.qxapp.Bean.User;
import com.hzh.qxapp.Bean.user_followers;
import com.hzh.qxapp.Fragment.Fragment_Comunity;
import com.hzh.qxapp.Fragment.Fragment_MyComunity;
import com.hzh.qxapp.Fragment.Fragment_MyPush;
import com.hzh.qxapp.Fragment.Fragment_Push;
import com.hzh.qxapp.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyInfo extends AppCompatActivity{

    private ImageView back;
    private TextView my_name,my_pushnum,my_comnum,my_nickname,usercreattime;

    private TextView info_title;

    private ImageView my_gender;

    private String user_onlyid
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private FragmentStatePagerItemAdapter fragadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        initView();

        //配置veiwpager
        viewPager.setOffscreenPageLimit(3);

        initTab();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        

       
    }

   

    private void initTab() {

        String[] tabs = new String[]{"动态","话题"};
        FragmentPagerItems pages = new FragmentPagerItems(MyInfo.this);
        for (int i = 0 ; i<tabs.length ; i++){
            Bundle args = new Bundle();
            args.putString("name",tabs[i]);
        }
        pages.add(FragmentPagerItem.of(tabs[0],Fragment_MyPush.class));
        pages.add(FragmentPagerItem.of(tabs[1],Fragment_MyComunity.class));
        viewPager.removeAllViews();
        fragadapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(),pages);
        viewPager.setAdapter(fragadapter);
        smartTabLayout.setViewPager(viewPager);

    }

    private void getothercomunitynum() {

        BmobQuery<Comunity> query = new BmobQuery<>();
        query.addWhereEqualTo("user_onlyid",user_onlyid);
        query.include("user");
        query.order("-createdAt");
        query.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                if (e == null){
                    String size = String.valueOf(list.size());
                    my_comnum.setText(size);
                }else {
                    Toast.makeText(MyInfo.this, "查询帖数失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getotherpushnum() {

        BmobQuery<Post> query = new BmobQuery<>();
        query.addWhereEqualTo("user_onlyid",user_onlyid);
        query.include("author");
        query.order("-createdAt");
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e == null){
                    String size = String.valueOf(list.size());
                    my_pushnum.setText(size);
                }else {
                    Toast.makeText(MyInfo.this, "查询帖数失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getotherInfo() {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.include("follower_id");
        bmobQuery.getObject(user_onlyid, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    my_name.setText(user.getUsername());
                    info_title.setText(user.getUsername());
                    my_nickname.setText(user.getNickname());
                    followeList_id = user.getFollower_id().getObjectId();
                    if (user.getGender().equals("man")){
                        my_gender.setImageResource(R.drawable.man);
                    }else {
                        my_gender.setImageResource(R.drawable.gril);
                    }
                }else {
                    //Toast.makeText(MyInfo.this, "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getmycomumitynum() {
        //获取我创建的论坛数
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Comunity> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                String size = String.valueOf(list.size());
                my_comnum.setText(size);
            }
        });
    }

    private void getmypushnum() {
        //获取我发布的帖子数
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("author",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                String size = String.valueOf(list.size());
                my_pushnum.setText(size);
            }
        });
    }

    private void getmyInfo() {
        //获取我的个人信息(名字和签名)
        User auser = BmobUser.getCurrentUser(User.class);
        String Id = auser.getObjectId();
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    my_name.setText(user.getUsername());
                    info_title.setText(user.getUsername());
                    my_nickname.setText(user.getNickname());
                    followeList_id = user.getFollower_id().getObjectId();
                    if (user.getGender().equals("man")){
                        my_gender.setImageResource(R.drawable.man);
                    }else {
                        my_gender.setImageResource(R.drawable.gril);
                    }
                } //Toast.makeText(MyInfo.this, "加载失败", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void initView() {
        back = findViewById(R.id.back);
        my_name = findViewById(R.id.my_name);
//        my_pushnum = findViewById(R.id.my_pushnum);
//        my_comnum = findViewById(R.id.my_comnum);
        my_nickname = findViewById(R.id.mynickname);
//        usercreattime = findViewById(R.id.usercreattime);
        my_gender = findViewById(R.id.my_gender);
        info_title = findViewById(R.id.info_title);
        focus = findViewById(R.id.focus);
        editmyinfo = findViewById(R.id.editmyinfo);
        smartTabLayout = findViewById(R.id.myinfotab);
        viewPager = findViewById(R.id.myinfovp);

        focus_or_not = findViewById(R.id.focus);
    }
}
