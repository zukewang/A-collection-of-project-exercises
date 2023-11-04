package com.example.geoquize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class QuizeActivity extends AppCompatActivity {
    private static final String INDEX = "INDEX";
    private static final String START_INDEX = "START_INDEX";
    Button mTrueBtn;
    Button mFalseBtn;
    Button mLastBtn;
    Button mNextBtn;
    TextView mTextView;



    int mIndex = 0;
    private String TAG="QuizeAcitivity";

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy called");
    }

    public static Intent newIntent(Context context,int position){
        Intent it = new Intent(context, QuizeActivity.class);
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
        setContentView(R.layout.activity_main);
        Log.d(TAG,"onCreate called");

        Intent it = getIntent();
        mIndex = it.getIntExtra(START_INDEX,0);

        if (savedInstanceState != null){
            mIndex= savedInstanceState.getInt(INDEX,0);
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragmentContainer);
        if(f==null){
            f = QuizeFragment.newInstance(mIndex);
            fm.beginTransaction().add(R.id.fragmentContainer, f).commit();
        }

//        Spinner mSpinner = findViewById(R.id.spinner);
//        mTextView = findViewById(R.id.question);
//        String[] mHuman = getResources().getStringArray(R.array.human);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mHuman);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpinner.setAdapter(adapter);
//        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String current = mHuman[position];
//                Toast.makeText(MainActivity.this, current, Toast.LENGTH_SHORT).show();
//                Log.d("wyz", current);
//                for (int i=0;i<mQuestions.length;i++){
//                    int resId = mQuestions[i].getResId();
//                    String ques = getString(resId);
//                    if (ques.contains(current)){
//                        mIndex = i;
//                        mTextView.setText(resId);
//                    }
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



//        mTrueBtn = findViewById(R.id.trueButton);
//        mTrueBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG,"onClick called"+mIndex);
//                checkAnswer(true);
//                Toast.makeText(MainActivity.this,R.string.successInfo, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        mFalseBtn = findViewById(R.id.falseButton);
//        mFalseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG,"onClick called"+mIndex);
//                checkAnswer(false);
//                Toast.makeText(MainActivity.this,R.string.failInfo,Toast.LENGTH_SHORT).show();
//            }
//        });

//        mNextBtn = findViewById(R.id.nextButton);
//        mNextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
//                startActivity(intent);
//            }
//        });

//        mTextView = findViewById(R.id.question);
//        int strId = mQuestions[mIndex].getResId();
//        mTextView.setText(strId);
//
//        mNextBtn = findViewById(R.id.nextButton);
//        mNextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mIndex = (mIndex + 1) % mQuestions.length;
//                int strId = mQuestions[mIndex].getResId();
//                mTextView.setText(strId);
//            }
//        });
//
//        mLastBtn = findViewById(R.id.lastButton);
//        mLastBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mIndex = (mIndex - 1) % mQuestions.length;
//                int strId = mQuestions[mIndex].getResId();
//                mTextView.setText(strId);
//            }
//        });


    }

//    private void checkAnswer(boolean b) {
//        boolean bAns = mQuestions[mIndex].isAnswerTrue();
//        Log.d(TAG,"onCreate called "+bAns);
//        if(bAns == b){
//            Toast.makeText(MainActivity.this,R.string.successInfo,Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(MainActivity.this,R.string.failInfo,Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG,"onStrat called");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG,"onPause called");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG,"onResume called");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG,"onRestart called");
    }

}