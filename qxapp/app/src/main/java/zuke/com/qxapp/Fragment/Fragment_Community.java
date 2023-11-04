package zuke.com.qxapp.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import zuke.com.qxapp.Adapter.MyCollectionCommunityAdapter;
import zuke.com.qxapp.Adapter.MyCollectionPushAdapter;
import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

public class Fragment_Community extends Fragment {

    private SwipeRefreshLayout swipeCommunity;
    private RecyclerView recyclerCommunity;

    private TextView error_community;

    private List<Community> data;

    private MyCollectionCommunityAdapter myCollectionCommunityAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_community, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        Refresh();

        swipeCommunity.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light, android.R.color.holo_blue_light);
        swipeCommunity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

    }

    private void Refresh() {

        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Community> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("relation", user);
        bmobQuery.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                swipeCommunity.setRefreshing(false);
                if (e == null){
                    if (list.size()>0) {
                        swipeCommunity.setVisibility(View.VISIBLE);
                        data = list;
                        myCollectionCommunityAdapter = new MyCollectionCommunityAdapter(data, getActivity());
                        recyclerCommunity.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerCommunity.setAdapter(myCollectionCommunityAdapter);
                    }else{
                        error_community.setVisibility(View.VISIBLE);
                        swipeCommunity.setVisibility(View.GONE);
                    }
                }else{
                    swipeCommunity.setRefreshing(false);
                    Toast.makeText(getActivity(), "查询失败"+e, Toast.LENGTH_SHORT).show();
                    Log.d("Fragment_Push:" , e.toString());
                }
            }
        });
    }

    private void initView() {

        swipeCommunity = getActivity().findViewById(R.id.col_swipe_community);
        recyclerCommunity = getActivity().findViewById(R.id.col_rv_community);
        error_community = getActivity().findViewById(R.id.error_community);

    }
}
