package com.ruihua.geshuibao.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ruihua.geshuibao.Bean.ConsultRecordBean;
import com.ruihua.geshuibao.R;

import java.util.List;

/**
 * 咨询记录  列表 适配器
 */
public class ConsultRecordAdapter extends RecyclerView.Adapter<ConsultRecordAdapter.MyViewHolder> implements View.OnClickListener{
    private Context context;
    private List<ConsultRecordBean.DataBean.ListBean> datas;
    private OnItemClickListener mItemClickListener;
    public ConsultRecordAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<ConsultRecordBean.DataBean.ListBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_consult_record, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.date.setText(datas.get(position).getCreateDate());
        if(datas.get(position).getStatus().equals("未回复"))
            holder.state.setTextColor(context.getResources().getColor(R.color.orange1));
        else
            holder.state.setTextColor(context.getResources().getColor(R.color.gray_text));
        holder.state.setText(datas.get(position).getStatus());
        holder.mag.setText(datas.get(position).getMsg());
        Glide.with(context).load(datas.get(position).getImage())
                .apply(RequestOptions.bitmapTransform(new CircleCrop())
                        .placeholder(R.drawable.home_header)
                        .error(R.drawable.home_header))
                .into(holder.ivHead);
        holder.servesType.setText(datas.get(position).getType().equals("1")?"平台客服":"企业客服");
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date,state,mag,servesType;
        ImageView ivHead;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.tv_data);
            state = itemView.findViewById(R.id.tv_state);
            mag = itemView.findViewById(R.id.tv_msg);
            servesType = itemView.findViewById(R.id.tv_serves_type);
            ivHead = itemView.findViewById(R.id.iv_head);
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
