package com.ruihua.geshuibao.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ruihua.geshuibao.Bean.SelfHelpContentBean;
import com.ruihua.geshuibao.R;

import java.util.List;

/**
 * 自助问答  列表 适配器
 */
public class SelfHelpContentAdapter extends RecyclerView.Adapter<SelfHelpContentAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private NewsProficyAdopter.OnItemClickListener mItemClickListener;
    private List<SelfHelpContentBean.DataBean.ListBean> datas;
    public SelfHelpContentAdapter( Context context) {
        this.context = context;
    }

    public void setDatas(List<SelfHelpContentBean.DataBean.ListBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_self_help_content, null);
        SelfHelpContentAdapter.MyViewHolder holder = new SelfHelpContentAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.topic.setText(datas.get(position).getTitle());
        holder.answer.setText(datas.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView topic,answer;
        public MyViewHolder(View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.tv_topic);
            answer = itemView.findViewById(R.id.tv_answer);
        }
    }
    @Override
    public void onClick(View v) {
        if(mItemClickListener!=null){
            mItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }
    public void setItemClickListener(NewsProficyAdopter.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
}
