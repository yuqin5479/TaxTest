package com.ruihua.geshuibao.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruihua.geshuibao.Bean.MessageListBean;
import com.ruihua.geshuibao.R;

import java.util.List;

/**
 * 具体类型消息列表  适配器
 */
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> implements View.OnClickListener{
    private Context context;
    private OnItemClickListener mItemClickListener;
    private List<MessageListBean.DataBean.ListBean> data;
    public MessageListAdapter( Context context) {
        this.context = context;
    }
    public void setDatas(List<MessageListBean.DataBean.ListBean> data){
        this.data = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_message_list, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.time.setText(data.get(position).getCreateDate());
        if (data.get(position).isIsRead())
            holder.redHint.setVisibility(View.GONE);
        else holder.redHint.setVisibility(View.VISIBLE);
        holder.title.setText(data.get(position).getTitle());
        holder.msg.setText(data.get(position).getContentText());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
         return data==null?0:data.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time,title,msg;
        ImageView redHint;

        public MyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tv_date);
            title = itemView.findViewById(R.id.tv_msg_title);
            msg = itemView.findViewById(R.id.tv_msg);
            redHint = itemView.findViewById(R.id.iv_red_hint);
        }
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener!=null){
            mItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
