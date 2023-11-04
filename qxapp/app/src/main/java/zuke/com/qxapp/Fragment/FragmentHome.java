package zuke.com.qxapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import zuke.com.qxapp.Adapter.HomeAdapter;
import zuke.com.qxapp.Bean.Post;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;
import zuke.com.qxapp.activity.Search;

public class FragmentHome extends Fragment {

    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    private TextView helloHome, welcome;

    private ImageView search;

    List<Post> data;

    HomeAdapter homeAdapter;

    private TextView username,title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenthome, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //逻辑处理
        initView();

        Bmob.initialize(getActivity(), "2b32d5db51bbd66773b4d95c72353b38");

        //初始刷新
        Refresh();

        srlayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light, android.R.color.holo_blue_light);
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                Refresh();
            }
        });

        //user加载  xxx欢迎你
        BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
        String userid = bmobUser.getObjectId();
        BmobQuery<User> user = new BmobQuery<>();
        user.getObject(userid, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    username.setText(user.getUsername());
                }else{
                    username.setText(" ");
                    welcome.setText(" ");
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Search.class));
            }
        });

    }

    private void Refresh() {
        BmobQuery<Post> post = new BmobQuery<Post>();
        post.order("-createdAt");
        post.setLimit(500);
        post.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                srlayout.setRefreshing(false);

                if (e == null){
                    data = list;
                    homeAdapter = new HomeAdapter(getActivity(), data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(homeAdapter);
                    for (Post post : data) {
                        Log.d("Post", "Nikename: " + post.getNickname());
                    }
                }else{
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "获取数据失败"+e, Toast.LENGTH_SHORT).show();
                    Log.d("home错误:", e.toString());
                }
            }
        });
    }

    private void initView() {
        rv = getActivity().findViewById(R.id.homeRyView);
        srlayout = getActivity().findViewById(R.id.homeSwipeRefresh);
        helloHome = getActivity().findViewById(R.id.helloHome);
        username = getActivity().findViewById(R.id.user);
        welcome = getActivity().findViewById(R.id.welcome);
        search = getActivity().findViewById(R.id.search);
    }
}