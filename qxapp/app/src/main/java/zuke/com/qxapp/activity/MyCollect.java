package zuke.com.qxapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.het.smarttab.SmartTabLayout;
import com.het.smarttab.v4.FragmentPagerItem;
import com.het.smarttab.v4.FragmentPagerItems;
import com.het.smarttab.v4.FragmentStatePagerItemAdapter;

import zuke.com.qxapp.Fragment.Fragment_Community;
import zuke.com.qxapp.Fragment.Fragment_Push;
import zuke.com.qxapp.R;

public class MyCollect extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    private ImageView back;

    private FragmentStatePagerItemAdapter fragadapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);

        initView();

        viewPager.setOffscreenPageLimit(3);//预加载

        initab();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initab() {
        String[] tabs = new String[]{"帖子", "论坛"};
        FragmentPagerItems pages =new FragmentPagerItems(MyCollect.this);
        //取出tabs中的两个名字
        for (int i = 0; i < tabs.length; i++){
            Bundle args = new Bundle();
            args.putString("name", tabs[i]);
        }

        pages.add(FragmentPagerItem.of(tabs[0], Fragment_Push.class));
        pages.add(FragmentPagerItem.of(tabs[1], Fragment_Community.class));

        fragadapter = new FragmentStatePagerItemAdapter(getSupportFragmentManager(), pages);
        viewPager.setAdapter(fragadapter);
        smartTabLayout.setViewPager(viewPager);
    }

    private void initView() {
        smartTabLayout = findViewById(R.id.mycol_tab);
        viewPager = findViewById(R.id.mycol_ViewPage);
        back = findViewById(R.id.mycol_back);

    }
}
