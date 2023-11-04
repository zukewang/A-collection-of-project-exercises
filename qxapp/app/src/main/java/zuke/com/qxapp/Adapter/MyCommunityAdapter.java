package zuke.com.qxapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.bmob.v3.BmobUser;
import zuke.com.qxapp.Bean.Community;
import zuke.com.qxapp.Bean.Post;
import zuke.com.qxapp.R;
import zuke.com.qxapp.activity.Login;
import zuke.com.qxapp.activity.Receive;
import zuke.com.qxapp.activity.ReceiveCommunity;

public class MyCommunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Community> data;
    private Context context;

    private final int N_TYPE = 0;
    private final int F_TYPE = 1;

    private int Max_num = 15;  //预加载的数据  一共15条

    private Boolean isfootview = true;  //是否有footview

    private RecyclerViewHolder viewHolder;

    public MyCommunityAdapter(Context context, List<Community> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycommunity_item,viewGroup,false);
        View footview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if (viewType == F_TYPE){
            return new MyCommunityAdapter.RecyclerViewHolder(footview,F_TYPE);
        }else {
            return new MyCommunityAdapter.RecyclerViewHolder(view,N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewHolder) {
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            if (isfootview && holder.getItemViewType() == F_TYPE) {
                // 底部加载提示
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
                // 这是mycommunity_item的内容
                final Community community = data.get(position);
                recyclerViewHolder.communityname.setText(community.getName());
                recyclerViewHolder.info.setText(community.getInfo());
                recyclerViewHolder.owner.setText(community.getOwner());

                recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = recyclerViewHolder.getAdapterPosition();
                        if (BmobUser.getCurrentUser(BmobUser.class) != null) {
                            // 需要改动
                            Intent in = new Intent(context, ReceiveCommunity.class);
                            in.putExtra("name", community.getName());
                            in.putExtra("info", community.getInfo());
                            in.putExtra("owner", community.getOwner());
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
    }

//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
//        if (isfootview && (getItemViewType(i)) == F_TYPE) {
//            //底部加载提示
//            final MyCommunityAdapter.RecyclerViewHolder recyclerViewHolder = viewHolder;
//            recyclerViewHolder.Loading.setText("加载中...");
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Max_num += 8;
//                    notifyDataSetChanged();
//                }
//            }, 2000);
//        } else {
//            //这是mycommunity_item的内容
//            final MyCommunityAdapter.RecyclerViewHolder recyclerViewHolder = viewHolder;
//            final Community community = data.get(i);
//            recyclerViewHolder.communityname.setText(community.getName());
//            recyclerViewHolder.info.setText(community.getInfo());
//            recyclerViewHolder.owner.setText(community.getOwner());
//
//            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int position = recyclerViewHolder.getAdapterPosition();
//                    if (BmobUser.getCurrentUser(BmobUser.class) != null) {
//
//                        Intent in = new Intent(context, ReceiveCommunity.class);
//                        in.putExtra("name", community.getName());
//                        in.putExtra("info", community.getInfo());
//                        in.putExtra("owner", community.getOwner());
//                        in.putExtra("id", data.get(position).getObjectId());
//                        context.startActivity(in);
//                    } else {
//                        Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, Login.class));
//                    }
//                }
//            });
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        if (position == Max_num - 1){
            return F_TYPE;  //底部type
        }else {
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

        public TextView communityname,info,owner; //mypush_item的TextView
        public TextView Loading;


        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE){
                communityname = itemview.findViewById(R.id.myColComunityName);
                info = itemview.findViewById(R.id.myColComunityInfo);
                owner = itemview.findViewById(R.id.myColComunityUser);
            }else if(view_type == F_TYPE){
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }



}
