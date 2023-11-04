package com.hzh.qxapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hzh.qxapp.Adapter.ChatAdapter;
import com.hzh.qxapp.Bean.Comunity;
import com.hzh.qxapp.R;
import com.hzh.qxapp.activity.PushComunity;
import com.hzh.qxapp.activity.PushContent;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentChat extends Fragment {

    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    private FloatingActionButton add,addcontent,addcomunity;
    private RelativeLayout rvlayout;

    List<Comunity> data;

    private ChatAdapter chatAdapter;

    private PopupWindow pop;

    private View view;
    //论坛界面
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentchat,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

         view = getLayoutInflater().inflate(R.layout.pop_item,null);

        initView();

        Bmob.initialize(getActivity(),"ab34b8bf6de6d97060af91d796f2b9e1");
        //初始刷新一次
        Refresh();

        srlayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

        //对flaotbutton监听
        add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                pop = new PopupWindow(view,250,700,true);
                pop.setOutsideTouchable(true);
                pop.setFocusable(true);
                pop.showAtLocation(rvlayout,Gravity.CENTER,530,350);
            }
        });

        addcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PushContent.class));
            }
        });

        addcomunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PushComunity.class));
            }
        });
    }

    private void Refresh() {
        BmobQuery<Comunity> com = new BmobQuery<>();
        com.setLimit(1000);
        com.order("-createdAt");
        com.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                srlayout.setRefreshing(false);
                if (e==null){
                    data = list;
                    chatAdapter = new ChatAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(chatAdapter);
                }else {
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        rv = getActivity().findViewById(R.id.rvview);
        srlayout = getActivity().findViewById(R.id.sw);
        add = getActivity().findViewById(R.id.add);
        rvlayout = getActivity().findViewById(R.id.rvlayout);
        addcontent =view.findViewById(R.id.addcontent);
        addcomunity =view.findViewById(R.id.addcomunity);
    }

    @Override
    public void onResume() {
        super.onResume();
        Refresh();
    }
}
