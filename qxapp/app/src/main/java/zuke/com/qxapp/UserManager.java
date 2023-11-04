package zuke.com.qxapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import zuke.com.qxapp.Adapter.MyCommunityAdapter;
import zuke.com.qxapp.Adapter.UserAdapter;
import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;
import zuke.com.qxapp.activity.Login;
import zuke.com.qxapp.activity.MyCommunity;
import zuke.com.qxapp.activity.Setting;

public class UserManager extends AppCompatActivity {

    private SwipeRecyclerView usermanagerrv;

    private SwipeRefreshLayout usermanager_swipe;

    private ImageView usermanager_set, usermanagerback;

    List<User> data;
    UserAdapter userAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermanager);

        Bmob.initialize(UserManager.this,"2b32d5db51bbd66773b4d95c72353b38");

        initView();

//        初始刷新
        Refresh();

        usermanagerback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserManager.this, Setting.class));
            }
        });

        usermanager_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(UserManager.this, Login.class));
                finish();
            }
        });

        usermanager_swipe.setColorSchemeResources(R.color.orange,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        usermanager_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });


    }

    private void Refresh() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                usermanager_swipe.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        usermanager_swipe.setVisibility(View.VISIBLE);

                        usermanagerrv.addItemDecoration(new DefaultItemDecoration(Color.GRAY));
                        usermanagerrv.setOnItemMenuClickListener(swipeItemClick);
                        usermanagerrv.setSwipeMenuCreator(swipeMenuCreator);

                        userAdapter = new UserAdapter(UserManager.this,data);
                        usermanagerrv.setLayoutManager(new LinearLayoutManager(UserManager.this));
                        usermanagerrv.setAdapter(userAdapter);

                    }
                }else {
                    Toast.makeText(UserManager.this, "加载失败"+e, Toast.LENGTH_SHORT).show();
                    Log.d("UserManager",e.toString());
                }
            }
        });

    }

    // 设置菜单监听器。
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        // 创建菜单：
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(UserManager.this)
                    .setTextColor(Color.WHITE)
                    .setBackgroundColor(Color.RED)
                    .setText("删除")
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };



    // 菜单点击监听。
    OnItemMenuClickListener swipeItemClick = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int Position) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();

            // 左侧还是右侧菜单：
            int direction = menuBridge.getDirection();
            // 菜单在Item中的Position：
            int menuPosition = menuBridge.getPosition();
            int position = menuBridge.getPosition();

            if(direction == SwipeRecyclerView.RIGHT_DIRECTION){

                User user = new User();
                user.setObjectId(data.get(position).getObjectId());
                user.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            data.remove(position);
                            userAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(UserManager.this, "删除失败", Toast.LENGTH_SHORT).show();
                            Log.d("usermanager_delete",e.toString());
                        }
                    }
                });
                data.remove(position);//删除item
                userAdapter.notifyDataSetChanged();
            }

        }
    };


    private void initView() {
        usermanagerrv = findViewById(R.id.usermanagerrv);
        usermanager_swipe = findViewById(R.id.usermanager_swipe);
        usermanager_set = findViewById(R.id.usermanager_set);
        usermanagerback = findViewById(R.id.usermanagerback);
    }
}
