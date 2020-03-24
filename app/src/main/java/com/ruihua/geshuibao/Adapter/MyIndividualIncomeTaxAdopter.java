package com.ruihua.geshuibao.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruihua.geshuibao.R;

import java.util.List;

public class MyIndividualIncomeTaxAdopter extends RecyclerView.Adapter<MyIndividualIncomeTaxAdopter.MyViewHolder> {
    private Context context;
    private List mListDate;

    public MyIndividualIncomeTaxAdopter( Context context) {
        this.context = context;
    }


    public void setDatas(List<String> datas){
        mListDate = datas;
        notifyDataSetChanged();

    }
    @Override
    public MyIndividualIncomeTaxAdopter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyIndividualIncomeTaxAdopter.MyViewHolder holder = new MyIndividualIncomeTaxAdopter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_tax, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.date.setText(mListDate.get(position).toString());
        holder.sum.setText("￥300");
    }

    @Override
    public int getItemCount() {
        return mListDate==null?0:mListDate.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date,sum;

        public MyViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_my_tax_tv_date);
            sum = itemView.findViewById(R.id.item_my_tax_tv_sum);
        }
    }
}
