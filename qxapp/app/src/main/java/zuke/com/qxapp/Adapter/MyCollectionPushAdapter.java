package zuke.com.qxapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobUser;
import zuke.com.qxapp.Bean.Post;
import zuke.com.qxapp.R;
import zuke.com.qxapp.activity.Login;
import zuke.com.qxapp.activity.Receive;

public class MyCollectionPushAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Post> data;
    private Context context;

    //是否要进行底部加载
    private final int N_TYPE = 0;  //normal
    private final int F_TYPE = 1;  //foot

    private int Max_num = 15; //预加载的数据  一共15条

    private Boolean isfootview = true;

    private RecyclerViewHolder viewHolder;

    public MyCollectionPushAdapter(List<Post> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycollectionpush, viewGroup, false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item, viewGroup, false);
        if (i == F_TYPE){
            viewHolder = new RecyclerViewHolder(footview, F_TYPE);
            return viewHolder;
        }else {
            viewHolder = new RecyclerViewHolder(view, N_TYPE);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (isfootview && (getItemViewType(i)) == F_TYPE){
            //底部加载提示
            final RecyclerViewHolder recyclerViewHolder = viewHolder;
            recyclerViewHolder.Loading.setText("加载中...");
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(()->{
                Max_num += 8;
                notifyDataSetChanged();
            },2000);
        }else{

            //ord_item的内容
            final RecyclerViewHolder recyclerViewHolder = viewHolder;
            Post post = data.get(i);
            recyclerViewHolder.username.setText(post.getName());
            recyclerViewHolder.nickname.setText(post.getNickname());
            recyclerViewHolder.content.setText(post.getContent());
            recyclerViewHolder.title.setText(post.getTitle());
            recyclerViewHolder.time.setText(post.getCreatedAt());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerViewHolder.getAdapterPosition();
                    if (BmobUser.getCurrentUser(BmobUser.class) != null){
                        Intent intent = new Intent(context, Receive.class);
                        intent.putExtra("username", post.getName());
                        intent.putExtra("content", post.getContent());
                        intent.putExtra("time", post.getCreatedAt());
                        intent.putExtra("title", post.getTitle());
                        intent.putExtra("id",data.get(position).getObjectId());
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }
                }
            });
        }
    }

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
        public TextView username, nickname, content, time, title;
        public TextView Loading;

        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE) {
                username = itemview.findViewById(R.id.mycolpush_username);
                nickname = itemview.findViewById(R.id.mycolpush_nickname);
                content = itemview.findViewById(R.id.mycolpush_content);
                time = itemview.findViewById(R.id.mycolpush_time);
                title = itemview.findViewById(R.id.mycolpush_title);
            }else if(view_type == F_TYPE) {
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }
}
