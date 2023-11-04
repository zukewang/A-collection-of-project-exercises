package zuke.com.qxapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.Bean.Post;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

public class ReceiveCommunity extends AppCompatActivity {

    private TextView rec_commmunityname, rec_commmunityInfo, rec_commmunityUser;
    private ImageView rec_communitycollect, rec_communityback;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recivecommunity);

        initView();

        getInfo();

        getisrelated();

        rec_communityback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rec_communitycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                String Id = in.getStringExtra("id");
                BmobQuery<Community> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(Id, new QueryListener<Community>() {
                    @Override
                    public void done(Community community, BmobException e) {
                        if (community.getIsrelated().equals("0")){
                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Community com = new Community();
                            com.setObjectId(Id);
                            com.setIsrelated("1");
                            BmobRelation relation = new BmobRelation();
                            relation.add(user);
                            com.setRelation(relation);
                            com.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        rec_communitycollect.setImageResource(R.drawable.shoucang_black);
                                        Toast.makeText(ReceiveCommunity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(ReceiveCommunity.this, "收藏失败"+e, Toast.LENGTH_SHORT).show();
                                        Log.d("ReceiveCommunity收藏：", e.toString());
                                    }
                                }
                            });
                        }else {
                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Community com = new Community();
                            com.setObjectId(Id);
                            com.setIsrelated("0");
                            BmobRelation relation = new BmobRelation();
                            relation.remove(user);
                            com.setRelation(relation);
                            com.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        rec_communitycollect.setImageResource(R.drawable.sc_normal);
                                        Toast.makeText(ReceiveCommunity.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(ReceiveCommunity.this, "取消收藏失败"+e, Toast.LENGTH_SHORT).show();
                                        Log.d("ReceiveCommunity收藏:",e.toString());
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
        BmobQuery<Community> bmobQuery = new BmobQuery<>();
            bmobQuery.getObject(Id, new QueryListener<Community>() {
            @Override
            public void done(Community community, BmobException e) {
                if (community.getIsrelated().equals("1")){
                    //已被收藏
                    rec_communitycollect.setImageResource(R.drawable.shoucang_black);
                }else {
                    //无收藏
                }
            }
        });
    }

    private void getInfo() {
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String info = intent.getStringExtra("info");
        String owner = intent.getStringExtra("owner");
        rec_commmunityname.setText(name);
        rec_commmunityInfo.setText(info);
        rec_commmunityUser.setText(owner);
    }

    private void initView() {
        rec_commmunityname = findViewById(R.id.rec_commmunityname);
        rec_commmunityInfo = findViewById(R.id.rec_commmunityinfo);
        rec_commmunityUser = findViewById(R.id.rec_commmunityuser);
        rec_communitycollect = findViewById(R.id.rec_communitycollect);
        rec_communityback = findViewById(R.id.rec_communityback);
    }
}
