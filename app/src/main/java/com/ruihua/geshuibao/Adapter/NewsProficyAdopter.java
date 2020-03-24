package com.ruihua.geshuibao.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ruihua.geshuibao.Bean.NewsFindBean;
import com.ruihua.geshuibao.R;
import java.util.List;

/**
 * 新闻列表 适配器
 */
public class NewsProficyAdopter extends RecyclerView.Adapter<NewsProficyAdopter.MyViewHolder> implements View.OnClickListener{
    private Context context;
    private List<NewsFindBean.DataBean.ListBean> datas;
    private OnItemClickListener mItemClickListener;
    public NewsProficyAdopter( Context context) {
        this.context = context;
    }

    public void setDatas(List<NewsFindBean.DataBean.ListBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_news_profic, null);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(datas.get(position).getTitle());
        holder.time.setText(datas.get(position).getCreateDate());
        if(datas.get(position).getPicture()!=null&&datas.get(position).getPicture().size()!=0){
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
