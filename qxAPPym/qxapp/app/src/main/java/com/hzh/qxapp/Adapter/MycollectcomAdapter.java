package com.hzh.qxapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hzh.qxapp.Bean.Comunity;
import com.hzh.qxapp.Bean.Post;
import com.hzh.qxapp.R;
import com.hzh.qxapp.activity.Login;
import com.hzh.qxapp.activity.RecieveCom;
import com.hzh.qxapp.activity.Recive;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class MycollectcomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Comunity> data;

    private final int N_TYPE = 0;
    private final int F_TYPE = 1;

    private int Max_num = 15;  //预加载的数据  一共15条

    private Boolean isfootview = true;  //是否有footview

    public MycollectcomAdapter(Context context, List<Comunity> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycollect_com, viewGroup, false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item, viewGroup, false);
        if (i == F_TYPE) {
            return new MycollectcomAdapter.RecyclerViewHolder(footview, F_TYPE);
        } else {
            return new MycollectcomAdapter.RecyclerViewHolder(view, N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isfootview && (getItemViewType(i)) == F_TYPE) {
            //底部加载提示
            final MycollectcomAdapter.RecyclerViewHolder recyclerViewHolder = (MycollectcomAdapter.RecyclerViewHolder) viewHolder;
            recyclerViewHolder.Loading.setText("加载中...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Max_num += 8;
                    notifyDataSetChanged();
                }
            }, 2000);
        } else {
            //这是ord_item的内容
            final MycollectcomAdapter.RecyclerViewHolder recyclerViewHolder = (MycollectcomAdapter.RecyclerViewHolder) viewHolder;
            final Comunity com = data.get(i);
            recyclerViewHolder.username.setText(com.getName());
            recyclerViewHolder.info.setText(com.getInfo());
            recyclerViewHolder.onw.setText(com.getOnwer());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerViewHolder.getAdapterPosition();
                    if (BmobUser.getCurrentUser(BmobUser.class) != null) {
                        Intent in = new Intent(context, RecieveCom.class);
                        in.putExtra("username", com.getName());
                        in.putExtra("info", com.getInfo());
                        in.putExtra("onw", com.getOnwer());
                        in.putExtra("id", data.get(position).getObjectId());
                        context.startActivity(in);
                    } else {
                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == Max_num - 1) {
            return F_TYPE;  //底部type
        } else {
            return N_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() < Max_num) {
            return data.size();
        }
        return Max_num;
    }


    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView username, info, onw; //ord_item的TextView
        public TextView Loading;


        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE) {
                username = itemview.findViewById(R.id.com_name);
                info = itemview.findViewById(R.id.com_info);
                onw = itemview.findViewById(R.id.com_user);
            } else if (view_type == F_TYPE) {
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }
}
