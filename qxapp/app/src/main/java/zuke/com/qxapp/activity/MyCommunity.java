package zuke.com.qxapp.activity;

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
import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;

public class MyCommunity extends AppCompatActivity {

    private SwipeRecyclerView mycomrv;
    private TextView mycom_error;
    private SwipeRefreshLayout mycom_swipe;

    private ImageView mycom_back;

    List<Community> data;
    MyCommunityAdapter myCommunityAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycommunity);

        Bmob.initialize(MyCommunity.this,"2b32d5db51bbd66773b4d95c72353b38");

        initView();

//        初始刷新
        Refresh();

        mycom_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mycom_swipe.setColorSchemeResources(R.color.orange,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        mycom_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });

    }

    private void Refresh() {
        //获取我发布的帖子  一对多
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Community> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Community>() {
            @Override
            public void done(List<Community> list, BmobException e) {
                mycom_swipe.setRefreshing(false);
                if (e==null){
                    data = list;
                    if (data.size()>0){
                        mycom_swipe.setVisibility(View.VISIBLE);

                        mycomrv.addItemDecoration(new DefaultItemDecoration(Color.GRAY));
                        mycomrv.setOnItemMenuClickListener(swipeItemClick);
                        mycomrv.setSwipeMenuCreator(swipeMenuCreator);

                        myCommunityAdapter = new MyCommunityAdapter(MyCommunity.this,data);
                        mycomrv.setLayoutManager(new LinearLayoutManager(MyCommunity.this));
                        mycomrv.setAdapter(myCommunityAdapter);

                    }else {
                        mycom_error.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(MyCommunity.this, "加载失败"+e, Toast.LENGTH_SHORT).show();
                    Log.d("MyComunity",e.toString());
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
            SwipeMenuItem deleteItem = new SwipeMenuItem(MyCommunity.this)
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

                Community community = new Community();
                community.setObjectId(data.get(position).getObjectId());
                community.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            data.remove(position);
                            myCommunityAdapter.notifyDataSetChanged();
                        }else {
                            Toast.makeText(MyCommunity.this, "删除失败", Toast.LENGTH_SHORT).show();
                            Log.d("MyComunity_delete",e.toString());
                        }
                    }
                });
                data.remove(position);//删除item
                myCommunityAdapter.notifyDataSetChanged();
            }

        }
    };

    private void initView() {
        mycomrv = findViewById(R.id.mycomrv);
        mycom_error = findViewById(R.id.mycom_error);
        mycom_swipe = findViewById(R.id.mycom_swipe);
        mycom_back = findViewById(R.id.mycom_back);
    }
}
