package com.ruihua.geshuibao.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruihua.geshuibao.Bean.MonthlySpecialExpenseDeductionBean;
import com.ruihua.geshuibao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 月度专项附加扣除及其他扣除 列表界面 适配器
 */
public class MySpecialExpenseDeductionAdopter extends RecyclerView.Adapter implements View.OnClickListener {
    private static final int ITEM_VIEW_TYPE_ELSE = 0;
    private static final int ITEM_VIEW_TYPE_HOUSING = 1;
    private  int LIST_TYPE = 11;//填充哪个list数据的标志   deductions others 两个list  非11便是其他扣除项的list
    private Context context;
    List<CheckBox> checkBoxList = new ArrayList<>();
    private MonthlySpecialExpenseDeductionBean.DataBean datas;
    private OnItemClickListener mItemClickListener;
    public MySpecialExpenseDeductionAdopter( Context context,int listType) {
        this.context = context;
        this.LIST_TYPE = listType;
    }

    public void setDatas(MonthlySpecialExpenseDeductionBean.DataBean datas){
        this.datas = datas;
        checkBoxList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener!=null){
            mItemClickListener.onItemClick((Integer) v.getTag());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(LIST_TYPE==11){
            if (viewType == ITEM_VIEW_TYPE_HOUSING) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_special_expense_deduction_housing, parent, false);
                view.setOnClickListener(this);
                return new HousingHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_special_expense_deduction, parent, false);
                view.setOnClickListener(this);
                return new ElseHolder(view);
            }
        }else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_special_expense_deduction, parent, false);
            view.setOnClickListener(this);
            return new ElseHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (LIST_TYPE==11){
            if(getItemViewType(position)==ITEM_VIEW_TYPE_HOUSING){
                final HousingHolder housingHolder = (HousingHolder)holder;
                checkBoxList.add(housingHolder.cbHousing);
                if (datas.getType().equals("1")&&datas.getDeductions().get(position).getType().equals("13")){
                    if(!housingHolder.cbHousing.isChecked()){
                        housingHolder.cbHousing.setChecked(true);
                    }

                }else if (datas.getType().equals("2")&&datas.getDeductions().get(position).getType().equals("14")){
                    if(!housingHolder.cbHousing.isChecked()){
                        housingHolder.cbHousing.setChecked(true);
                    }
                }

                housingHolder.cbHousing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i=0;i<checkBoxList.size();i++){
                            if (checkBoxList.get(i).equals(housingHolder.cbHousing)){
                                //如果住房贷款公积金利息 或 住房租金两个item只可被选中一个
                                checkBoxList.get(i).setChecked(true);
                            }else checkBoxList.get(i).setChecked(false);
                        }
                    }
                });
                Glide.with(context).load(datas.getDeductions().get(position).getImage()).into(housingHolder.icon);
                housingHolder.tag.setText(datas.getDeductions().get(position).getName());
                housingHolder.sum.setText("￥"+datas.getDeductions().get(position).getMoney());
                if (Double.parseDouble(datas.getDeductions().get(position).getMoney())>0)
                    housingHolder.sum.setTextColor(context.getResources().getColor(R.color.orange));
                housingHolder.itemView.setTag(position);
            }else {

                ElseHolder elseHolder = (ElseHolder)holder;
                if(datas.getDeductions().get(position).getType().equals("12")){//type = 12 大病医疗 致灰 按需求暂不可操作
                    elseHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.main_bg_gray));
                    Log.i("yu","大病医疗致灰................");
                }
                Glide.with(context).load(datas.getDeductions().get(position).getImage()).into(elseHolder.icon);
                elseHolder.tag.setText(datas.getDeductions().get(position).getName());
                elseHolder.sum.setText("￥"+datas.getDeductions().get(position).getMoney());
                if (Double.parseDouble(datas.getDeductions().get(position).getMoney())>0)
                    elseHolder.sum.setTextColor(context.getResources().getColor(R.color.orange));
                elseHolder.itemView.setTag(position);
            }
        }else {
            ElseHolder elseHolder = (ElseHolder)holder;
            Glide.with(context).load(datas.getOthers().get(position).getImage()).into(elseHolder.icon);
            elseHolder.tag.setText(datas.getOthers().get(position).getName());
            elseHolder.sum.setText("￥"+datas.getOthers().get(position).getMoney());
            if (Double.parseDouble(datas.getOthers().get(position).getMoney())>0)
                elseHolder.sum.setTextColor(context.getResources().getColor(R.color.orange));
            elseHolder.itemView.setTag(position);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (LIST_TYPE==11)
             return datas ==null?0:datas.getDeductions().size();
        else
            return datas ==null?0:datas.getOthers().size();
    }

    @Override
    public int getItemViewType(int position) {
        return isHousing(position) ? ITEM_VIEW_TYPE_HOUSING : ITEM_VIEW_TYPE_ELSE;
    }

    private boolean isHousing(int position) {//是否为 住房贷款公积金利息 "type": "13"   住房租金 "type": "14"
        if(datas.getDeductions().get(position).getType().equals("13")||
           datas.getDeductions().get(position).getType().equals("14")){
            return true;
        }else
            return false;
    }


    public class HousingHolder extends RecyclerView.ViewHolder {
        private CheckBox cbHousing;
        private ImageView icon;
        private TextView tag,sum;

        public HousingHolder(View itemView) {
            super(itemView);
            cbHousing = itemView.findViewById(R.id.cb_housing);
            icon = itemView.findViewById(R.id.iv_icon_housing);
            tag = itemView.findViewById(R.id.tv_deducion_housing);
            sum = itemView.findViewById(R.id.tv_sum_housing);
        }
    }
    public class ElseHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView tag,sum;
        public ElseHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_icon);
            tag = itemView.findViewById(R.id.tv_deducion_item);
            sum = itemView.findViewById(R.id.tv_sum);
        }
    }
}
