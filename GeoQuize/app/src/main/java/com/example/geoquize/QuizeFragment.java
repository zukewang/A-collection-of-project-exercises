package com.example.geoquize;

import static com.example.geoquize.QuestionBank.mQuestions;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    // TODO: Rename and change types of parameters
    int op;
    Button mTrueBtn;
    Button mFalseBtn;
    Button mLastBtn;
    Button mNextBtn;
    Button mPlayBtn;
    Button mStopBtn;
    TextView mTextView;

    int mIndex = 0;
    Intent mMusicIntent = null;
    private String TAG="QuizeFragment";

    public QuizeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizeFragment newInstance(int param1){
        QuizeFragment fragment = new QuizeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static QuizeFragment newInstance(String param1, String param2) {
        QuizeFragment fragment = new QuizeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.playButton:
                op = 1;
                break;
            case R.id.stopButton:
                op = 2;
                break;
        }
        mMusicIntent = new Intent(getActivity(), MusicService.class);
        mMusicIntent.putExtra("op", op);
        getActivity().startService(mMusicIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMusicIntent !=null){
            getActivity().stopService(mMusicIntent);
            mMusicIntent = null;
        }
    }

    private void checkAnswer(boolean b) {
        boolean bAns = mQuestions[mIndex].isAnswerTrue();
        Log.d(TAG,"onCreate called "+bAns);
        if(bAns == b){
            Toast.makeText(getActivity(),R.string.successInfo,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),R.string.failInfo,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quize, container, false);

        mPlayBtn = v.findViewById(R.id.playButton);
        mPlayBtn.setOnClickListener(this);
        mStopBtn = v.findViewById(R.id.stopButton);
        mStopBtn.setOnClickListener(this);

        Spinner mSpinner = v.findViewById(R.id.spinner);
        mTextView = v.findViewById(R.id.question);
        String[] mHuman = getResources().getStringArray(R.array.human);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mHuman);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String current = mHuman[position];
                Toast.makeText(getActivity(), current, Toast.LENGTH_SHORT).show();
                Log.d("wyz", current);
                for (int i=0;i<mQuestions.length;i++){
                    int resId = mQuestions[i].getResId();
                    String ques = getString(resId);
                    if (ques.contains(current)){
                        mIndex = i;
                        mTextView.setText(resId);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        mTrueBtn = v.findViewById(R.id.trueButton);
        mTrueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick called"+mIndex);
                checkAnswer(true);
//                Toast.makeText(getActivity(),R.string.successInfo, Toast.LENGTH_SHORT).show();
            }
        });



        mFalseBtn = v.findViewById(R.id.falseButton);
        mFalseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onClick called"+mIndex);
                checkAnswer(false);
//                Toast.makeText(getActivity(),R.string.failInfo,Toast.LENGTH_SHORT).show();
            }
        });

//        mNextBtn = findViewById(R.id.nextButton);
//        mNextBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,MainActivity2.class);
//                startActivity(intent);
//            }
//        });

        mTextView = v.findViewById(R.id.question);
        int strId = mQuestions[mIndex].getResId();
        mTextView.setText(strId);

        mNextBtn = v.findViewById(R.id.nextButton);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIndex = (mIndex + 1) % mQuestions.length;
                int strId = mQuestions[mIndex].getResId();
                mTextView.setText(strId);
            }
        });

        mLastBtn = v.findViewById(R.id.lastButton);
        mLastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIndex = (mIndex - 1) % mQuestions.length;
                int strId = mQuestions[mIndex].getResId();
                mTextView.setText(strId);
            }
        });
        return v;
    }


}

