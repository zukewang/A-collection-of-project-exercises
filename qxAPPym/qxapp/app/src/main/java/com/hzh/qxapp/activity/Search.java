package com.hzh.qxapp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hzh.qxapp.Adapter.SearchAdapter;
import com.hzh.qxapp.Bean.Post;
import com.hzh.qxapp.R;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Search extends AppCompatActivity {

    private SwipeRefreshLayout search_swipe;
    private RecyclerView search_rv;
    private EditText searchcontent;
    private ImageView search,back;
    private LinearLayout search_isnone;
    private ProgressBar search_progress;

    private List<Post> data;

    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();

        search_swipe.setColorSchemeResources(R.color.orange,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        search_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新时
                getwant();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getwant();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getwant() {
        search_progress.setVisibility(View.VISIBLE);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String want = searchcontent.getText().toString().trim();
                BmobQuery<Post> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("content",want);
                bmobQuery.findObjects(new FindListener<Post>() {
                    @Override
                    public void done(List<Post> list, BmobException e) {
                        search_swipe.setRefreshing(false);
                        if (e==null){
                            //查询成功
                            if (list.size()>0){

                                search_progress.setVisibility(View.GONE);
                                search_isnone.setVisibility(View.GONE);
                                search_swipe.setVisibility(View.VISIBLE);
                                //数据适配

                                data = list;
                                searchAdapter = new SearchAdapter(Search.this,data);
                                search_rv.setLayoutManager(new LinearLayoutManager(Search.this));
                                search_rv.setAdapter(searchAdapter);

                            }else {
                                search_progress.setVisibility(View.GONE);
                                search_isnone.setVisibility(View.VISIBLE);
                                search_swipe.setVisibility(View.GONE);
                            }
                        }else {
                            Toast.makeText(Search.this, "查询失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        },250);

    }

    private void initView() {
        search_rv = findViewById(R.id.search_rv);
        search_swipe = findViewById(R.id.search_swipe);
        searchcontent = findViewById(R.id.searchcontent);
        search = findViewById(R.id.search);
        back = findViewById(R.id.back);
        search_isnone = findViewById(R.id.search_isnone);
        search_progress = findViewById(R.id.search_progress);
    }
}
