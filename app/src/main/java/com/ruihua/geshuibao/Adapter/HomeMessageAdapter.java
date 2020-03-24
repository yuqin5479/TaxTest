package com.ruihua.geshuibao.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruihua.geshuibao.Bean.HomeDataBean;
import com.ruihua.geshuibao.R;

import java.util.List;

/**
 * 首页 消息 适配器
 */
public class HomeMessageAdapter extends RecyclerView.Adapter<HomeMessageAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<HomeDataBean.DataBean.MiddleListBean> data;
    private OnItemClickListener mItemClickListener;
    public HomeMessageAdapter( Context context) {
        this.context = context;
    }

    public void setDatas(List<HomeDataBean.DataBean.MiddleListBean> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_home_message, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        switch (data.get(position).getClassifyType()){
            case "1":
                holder.msgType.setText(context.getString(R.string.home_terrace_msg));
                break;
            case "2":
                holder.msgType.setText(context.getString(R.string.home_revenue_msg));
                break;
            case "3":
                holder.msgType.setText(context.getString(R.string.home_company_msg));
                break;
        }
        if(data.get(position).isIsRead())
            holder.redHint.setVisibility(View.GONE);
        else holder.redHint.setVisibility(View.VISIBLE);
        holder.title.setText(data.get(position).getTitle());
        holder.time.setText(data.get(position).getCreateDate());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView msgType,more,title,time;
        ImageView redHint;

        public MyViewHolder(View itemView) {
            super(itemView);
            msgType = itemView.findViewById(R.id.home_tv_msg_text);
            more = itemView.findViewById(R.id.home_tv_msg_more);
            title = itemView.findViewById(R.id.home_tv_msg_title);
            time = itemView.findViewById(R.id.home_tv_msg_time);
            redHint = itemView.findViewById(R.id.home_iv_msg_title_hint);
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
