package zuke.com.qxapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zuke.com.qxapp.Adapter.ChatAdapter;
import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.R;
import zuke.com.qxapp.activity.PushCommunity;
import zuke.com.qxapp.activity.PushContent;

public class FragmentChat extends Fragment {

    //论坛界面

    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;

    List<Community> data;

    private ChatAdapter chatAdapter;

    private FloatingActionButton addBtn, addContent, addComunity;
    private RelativeLayout rlLayout;
    private PopupWindow popupWindow;
    private View pop_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentchat, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pop_view = getLayoutInflater().inflate(R.layout.pop_item, null);

        initView();

        Bmob.initialize(getActivity(), "2b32d5db51bbd66773b4d95c72353b38");

        //初始刷新
        Refresh();

        srlayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light, android.R.color.holo_blue_light);
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

        //对floatingActionButton进行监听
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = new PopupWindow(pop_view, addBtn.getWidth(), 300, true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(rlLayout, Gravity.RIGHT, 75, 400);
            }
        });

        addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity(), PushContent.class));
            }
        });

        addComunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PushCommunity.class));
            }
        });



    }

    private void Refresh() {
        BmobQuery<Community> Com = new BmobQuery<Community>();
        Com.order("-createdAT");
        Com.setLimit(1000);
        Com.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                srlayout.setRefreshing(false);
                if(e==null){
                    data = list;
                    chatAdapter = new ChatAdapter(getActivity(), data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(chatAdapter);
                }else{
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "获取数据失败"+e, Toast.LENGTH_SHORT).show();
                    Log.d("chat错误:", e.toString());
                }
            }
        });
    }

    private void initView() {
        rv = getActivity().findViewById(R.id.chatRv);
        srlayout = getActivity().findViewById(R.id.chatSwip);
        addBtn = getActivity().findViewById(R.id.addBtn);
        rlLayout = getActivity().findViewById(R.id.rlLayout);
        addContent = pop_view.findViewById(R.id.addContent);
        addComunity = pop_view.findViewById(R.id.addComunity);

    }

    @Override
    public void onResume() {
        super.onResume();
        Refresh();
    }
}
