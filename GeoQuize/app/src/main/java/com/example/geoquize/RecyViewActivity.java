package com.example.geoquize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RecyViewActivity extends AppCompatActivity {
    RecyclerView mRecview;
    private QuestionAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy_view);

        mRecview = findViewById(R.id.recView);
        mRecview.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuestionAdapter(RecyViewActivity.this, QuestionBank.mQuestions);
        mRecview.setAdapter(mAdapter);
    }

    class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mQuestionView;
        int mIndex;
        Question mQues;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            mQuestionView = itemView.findViewById(R.id.quesView);
            mQuestionView.setOnClickListener(this);
        }

        public void bindData(Question q,int index){
            mIndex = index;
            mQues = q;
            mQuestionView.setText(q.getResId());
        }

        public void onClick(View v){
            Intent it = QuizePagerActivity.newIntent(RecyViewActivity.this,mIndex);
            startActivity(it);
        }
    }

    class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{
        private final LayoutInflater mLayoutInflater;
        Question[] mQuestions;

        public QuestionAdapter(Context context, Question[] questions) {
            mLayoutInflater = LayoutInflater.from(context);
            mQuestions = questions;
        }

        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mLayoutInflater.inflate(R.layout.list_item_question, parent, false);
            return new QuestionHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            Question q = mQuestions[position];
            holder.bindData(q, position);
        }

        @Override
        public int getItemCount() {
            return mQuestions.length;
        }

    }
}