package com.example.geoquize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class QuizePagerActivity extends AppCompatActivity {
    private static final String INDEX = "INDEX";
    private static final String START_INDEX = "START_INDEX";
    int mIndex=0;
    private String TAG="QuizePagerAcitivity";
    ViewPager mQuizePager;

    public static Intent newIntent(Context context, int position){
        Intent it = new Intent(context, QuizePagerActivity.class);
        it.putExtra(START_INDEX, position);
        return it;
    }

    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        outstate.putInt(INDEX,mIndex);
        Log.d(TAG,"onSaveInstanceState called, mIndex=" + mIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize_pager);

        Intent it = getIntent();
        mIndex = it.getIntExtra(START_INDEX,0);

        if (savedInstanceState != null){
            mIndex= savedInstanceState.getInt(INDEX,0);
        }

        mQuizePager = findViewById(R.id.quizeViewPager);
        FragmentManager fm = getSupportFragmentManager();
        mQuizePager.setAdapter(new FragmentPagerAdapter(fm) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return QuizeFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return QuestionBank.mQuestions.length;
            }
        });
        mQuizePager.setCurrentItem(mIndex);
    }
}