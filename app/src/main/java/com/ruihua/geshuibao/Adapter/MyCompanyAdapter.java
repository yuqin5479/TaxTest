package com.ruihua.geshuibao.Adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ruihua.geshuibao.Bean.MyCompanysBean;
import com.ruihua.geshuibao.R;

import java.util.List;

/**
 * 我的单位 适配器
 */
public class MyCompanyAdapter extends BaseAdapter {
    private Context context = null;
    private LayoutInflater inflater;
    private List<String> companyNames;
    public MyCompanyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    public void setDeta(List<String> data){
        this.companyNames = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return companyNames==null?0:companyNames.size();
    }


    @Override
    public Object getItem(int position) {
        return companyNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_my_companys,parent,false);
        TextView companyName = convertView.findViewById(R.id.tv_company_name);
        companyName.setText(companyNames.get(position));
        return convertView;
    }
}
