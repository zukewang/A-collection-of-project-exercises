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

import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.R;
import zuke.com.qxapp.activity.Receive;
import zuke.com.qxapp.activity.ReceiveCommunity;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Community> data;

    private final int N_TYPE = 0;
    private final int F_TYPE = 1;

    private int Max_num = 15; //预加载的数据  一共15条

    private Boolean isfootview = true;
    private ChatAdapter.RecyclerViewHolder viewHolder;

    public ChatAdapter(Context context, List<Community> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comunity_item, viewGroup, false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item, viewGroup, false);
        if (i == F_TYPE){
            return new ChatAdapter.RecyclerViewHolder(footview, F_TYPE);
        }else {
            viewHolder = new ChatAdapter.RecyclerViewHolder(view, N_TYPE);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (isfootview && (getItemViewType(i)) == F_TYPE){
            //底部加载提示
            final ChatAdapter.RecyclerViewHolder recyclerViewHolder = (ChatAdapter.RecyclerViewHolder) viewHolder;
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
            final ChatAdapter.RecyclerViewHolder recyclerViewHolder = viewHolder;
            Community community = data.get(i);
            recyclerViewHolder.comunityName.setText(community.getName());
            recyclerViewHolder.comunityInfo.setText(community.getInfo());
            recyclerViewHolder.comunityUser.setText(community.getOwner());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerViewHolder.getAdapterPosition();
                    Intent intent = new Intent(context, ReceiveCommunity.class);
                    intent.putExtra("name", community.getName());
                    intent.putExtra("info", community.getInfo());
                    intent.putExtra("owner", community.getOwner());
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
        public TextView comunityName, comunityInfo, comunityUser;
        public TextView Loading;

        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE) {
                comunityName = itemview.findViewById(R.id.comunityName);
                comunityInfo = itemview.findViewById(R.id.comunityInfo);
                comunityUser = itemview.findViewById(R.id.comunityUser);

            } else if (view_type == F_TYPE) {
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }

}
