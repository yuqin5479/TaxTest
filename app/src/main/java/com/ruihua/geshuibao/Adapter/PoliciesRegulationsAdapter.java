package com.ruihua.geshuibao.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ruihua.geshuibao.Bean.PoliciesRegulationsListBean;
import com.ruihua.geshuibao.R;

import java.util.List;

/**
 * 政策法规 列表 适配器
 */
public class PoliciesRegulationsAdapter
        extends RecyclerView.Adapter<PoliciesRegulationsAdapter.MyViewHolder>
        implements View.OnClickListener{
    private Context context;
    private List<PoliciesRegulationsListBean.DataBean.ListBean> datas;
    private OnItemClickListener mItemClickListener;
    public PoliciesRegulationsAdapter( Context context) {
        this.context = context;
    }
    public void setDatas(List<PoliciesRegulationsListBean.DataBean.ListBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /**
         * 政策法规列表的item与新闻列表itme共用的一个
         */
        View view = View.inflate(context, R.layout.item_news_profic, null);
        PoliciesRegulationsAdapter.MyViewHolder holder = new PoliciesRegulationsAdapter.MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(datas.get(position).getTitle());
        holder.time.setText(datas.get(position).getCreateDate());
        if (datas.get(position).getPicture()!=null&&datas.get(position).getPicture().size()!=0){//如果无图就隐藏图片控件 以便TextVew伸展
                    Glide.with(context).load(datas.get(position).getPicture().get(0))
                .apply(new RequestOptions()
                        .placeholder(R.drawable.new_bitmap_bg)
                        .error(R.drawable.new_bitmap_bg))
                .into(holder.newsImg);
        }else holder.newsImg.setVisibility(View.GONE);

        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        /**
         * 政策法规列表的item与新闻列表itme共用的一个
         */
        TextView title,time;
        ImageView newsImg;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title_item_news_profic);
            time = itemView.findViewById(R.id.tv_date_item_news_profic);
            newsImg = itemView.findViewById(R.id.iv_item_news_profic);
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
