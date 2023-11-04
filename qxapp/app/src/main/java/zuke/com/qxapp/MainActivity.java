 package zuke.com.qxapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import zuke.com.qxapp.Adapter.SectionsPagerAdapter;
import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.Fragment.FragmentChat;
import zuke.com.qxapp.Fragment.FragmentHome;
import zuke.com.qxapp.Fragment.FragmentMine;

 public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

     private ViewPager viewPager;
     private BottomNavigationBar bottomNavigationBar;
     private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        bottomNavigationBar = findViewById(R.id.bottom);

        initview();
    }

    private void initview(){
        initViewpager();
        initBottomNavigationBar();
    }

     private void initBottomNavigationBar() {

        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);   //自适应大小
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar.setBarBackgroundColor(R.color.white).setActiveColor(R.color.colorbase1).setInActiveColor(R.color.black);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.homepage_fill,"首页").setInactiveIconResource(R.drawable.homepage))
                .addItem(new BottomNavigationItem(R.drawable.mobilephone_fill,"论坛").setInactiveIconResource(R.drawable.mobilephone))
                .addItem(new BottomNavigationItem(R.drawable.mine_fill,"我的").setInactiveIconResource(R.drawable.mine))
                .setFirstSelectedPosition(0)
                .initialise();

     }

     private void initViewpager() {

        viewPager.setOffscreenPageLimit(3); //缓存

         fragments = new ArrayList<Fragment>();
         fragments.add(new FragmentHome());
         fragments.add(new FragmentChat());
         fragments.add(new FragmentMine());

         viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(), fragments));
         viewPager.addOnPageChangeListener(this);
         viewPager.setCurrentItem(0);

     }


     @Override
     public void onTabSelected(int position) {
        viewPager.setCurrentItem(position);
     }

     @Override
     public void onTabUnselected(int i) {

     }

     @Override
     public void onTabReselected(int i) {

     }

     @Override
     public void onPageScrolled(int i, float v, int i1) {

     }

     @Override
     public void onPageSelected(int i) {
         bottomNavigationBar.selectTab(i);
     }

     @Override
     public void onPageScrollStateChanged(int state) {

     }
 }




















//        BmobUser user = BmobUser.getCurrentUser(User.class);
//        String id = user.getObjectId();
//        BmobQuery<User> myuser = new BmobQuery<User>();
//
//        myuser.getObject(id, new QueryListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if(e == null){
//                    username.setText(user.getUsername());
//                    nickname.setText(user.getNickname());
//                }else{
//                    Toast.makeText(MainActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });