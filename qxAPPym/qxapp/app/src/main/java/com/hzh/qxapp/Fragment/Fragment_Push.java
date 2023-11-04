package com.hzh.qxapp.Fragment;

import android.icu.lang.UScript;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hzh.qxapp.Adapter.MycollectpushAdapter;
import com.hzh.qxapp.Bean.Post;
import com.hzh.qxapp.Bean.User;
import com.hzh.qxapp.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Fragment_Push extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private TextView error_push;

    private List<Post> data;

    private MycollectpushAdapter mycollectpushAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_push,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //逻辑处理
        initView();

        Refresh();

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

    }

    private void Refresh() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("relation",user);
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                swipeRefreshLayout.setRefreshing(false);
                if (e==null){
                    if (list.size()>0){
                        swipeRefreshLayout.setVisibility(View.VISIBLE);
                        data =list;
                        mycollectpushAdapter = new MycollectpushAdapter(data,getActivity());
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(mycollectpushAdapter);
                    }else {
                        error_push.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setVisibility(View.GONE);
                    }
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void initView() {
        swipeRefreshLayout = getActivity().findViewById(R.id.swipe_push);
        recyclerView = getActivity().findViewById(R.id.rv_push);
        error_push = getActivity().findViewById(R.id.error_push);
    }
}
