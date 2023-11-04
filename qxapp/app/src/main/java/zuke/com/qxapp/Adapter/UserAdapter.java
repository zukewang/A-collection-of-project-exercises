package zuke.com.qxapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import zuke.com.qxapp.Bean.User;
import zuke.com.qxapp.R;
import zuke.com.qxapp.activity.ReceiveCommunity;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<User> data;

    private final int N_TYPE = 0;
    private final int F_TYPE = 1;

    private int Max_num = 15; //预加载的数据  一共15条

    private Boolean isfootview = true;
    private UserAdapter.RecyclerViewHolder viewHolder;

    public UserAdapter(Context context, List<User> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item, viewGroup, false);
        if (i == F_TYPE){
            return new UserAdapter.RecyclerViewHolder(footview, F_TYPE);
        }else {
            viewHolder = new UserAdapter.RecyclerViewHolder(view, N_TYPE);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (isfootview && (getItemViewType(i)) == F_TYPE){
            //底部加载提示
            final UserAdapter.RecyclerViewHolder recyclerViewHolder = (UserAdapter.RecyclerViewHolder) viewHolder;
            recyclerViewHolder.Loading.setText("加载中...");
            Handler handler = new Handler(Looper.myLooper());
            handler.postDelayed(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    Max_num += 8;
                    notifyDataSetChanged();
                }
            },2000);
        }else{
            final UserAdapter.RecyclerViewHolder recyclerViewHolder = viewHolder;
            User user = data.get(i);
            recyclerViewHolder.username.setText(user.getUsername());
            recyclerViewHolder.nickname.setText(user.getNickname());
            recyclerViewHolder.time.setText(user.getCreatedAt());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerViewHolder.getAdapterPosition();
                    Intent intent = new Intent(context, ReceiveCommunity.class);
                    intent.putExtra("name", user.getUsername());
                    intent.putExtra("nickname", user.getNickname());
                    intent.putExtra("time", user.getCreatedAt());
                    intent.putExtra("id",data.get(position).getObjectId());
                    context.startActivity(intent);
                }
            });

        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == Max_num-1){
            return F_TYPE;
        }else{
            return N_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() < Max_num){
            return data.size();
        }
        return Max_num;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView username, nickname, time;
        public TextView Loading;

        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE) {
                username = itemview.findViewById(R.id.manager_username);
                nickname = itemview.findViewById(R.id.manager_nickname);
                time = itemview.findViewById(R.id.manager_time);

            } else if (view_type == F_TYPE) {
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }

}