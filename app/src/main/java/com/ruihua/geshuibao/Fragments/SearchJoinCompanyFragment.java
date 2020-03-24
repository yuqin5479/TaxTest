package com.ruihua.geshuibao.Fragments;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.SearchJoinCompanyBean;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.Event.Event_CompanyInforFragment_CompanyId;
import com.ruihua.geshuibao.Event.Event_SpecialExpenseDeductionDetailsFragment_detailsId;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 查询加入单位  碎片
 */
public class SearchJoinCompanyFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.ev_search_company)
    ClearEditText mEvSearchCompany;
    @BindView(R.id.tv_search_company)
    TextView mTvSearchCompany;
    @BindView(R.id.lv_company_names)
    ListView mLvCompanyNames;

    private SearchJoinCompanyBean searchJoinCompanyBean;
    private MyAdapter adapter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_join_company;
    }

    @Override
    protected void setUpData() {
        mEvSearchCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                if (!TextUtils.isEmpty(key)){
                    getCompanyData(key);
                }else adapter.upData(null);//输入框无内容 适配器同步清空数据

            }
        });
    }

    private void getCompanyData(String key) {
        okHttpUtils.getAsyncData(BaseUrl.QUERV_JOIN_COMPANY
                        + "?name=" + key, new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) { }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"搜索加入企业.........."+response);
                       searchJoinCompanyBean = new Gson().fromJson(response,SearchJoinCompanyBean.class);
                       if(searchJoinCompanyBean!=null&&searchJoinCompanyBean.getErrorcode().equals("0000")){
                           if(searchJoinCompanyBean.getData().getList().size()==0){
                               toastShort("暂无此单位信息");
                           }
                           adapter.upData(searchJoinCompanyBean.getData().getList());
                       }
                    }
                }
        );
    }
    @Override
    protected void setUpView() {
        adapter = new MyAdapter(mContext);
        mLvCompanyNames.setAdapter(adapter);
        mLvCompanyNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转公司详情页面  传递单位id
                if(searchJoinCompanyBean!=null&&searchJoinCompanyBean.getErrorcode().equals("0000")){
                    EventBus.getDefault().postSticky(new Event_CompanyInforFragment_CompanyId(searchJoinCompanyBean.getData().getList().get(position).getId()));
                    JoinCompanyActivity.switchContent(JoinCompanyActivity.companyInfoFragment);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_search_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                JoinCompanyActivity.switchContent(JoinCompanyActivity.joinCompanyFragment);
                break;
            case R.id.tv_search_company:
                break;
        }
    }

    public class MyAdapter extends BaseAdapter {
        List<SearchJoinCompanyBean.DataBean.ListBean> list;
        LayoutInflater inflater;
        public MyAdapter(Context context){
            inflater = LayoutInflater.from(context);
        }
        public void upData(List<SearchJoinCompanyBean.DataBean.ListBean> list){
            this.list = list;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {return list==null?0:list.size();}
        @Override
        public Object getItem(int position) { return list.get(position);}
        @Override
        public long getItemId(int position) { return position; }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            ViewHolder viewHolder;
            if(convertView==null){
                convertView = inflater.inflate(R.layout.item_search_join_company, null);
                viewHolder = new ViewHolder();
                viewHolder.companyName = (TextView) convertView.findViewById(R.id.item_tv_company_name);
                convertView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.companyName.setText(list.get(position).getName());
            return convertView;
        }
    }
    //辅助类
    class ViewHolder{
        TextView companyName;
    }
}
