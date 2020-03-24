package com.ruihua.geshuibao.Fragments;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MsgAndNewsDetailsActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.NewsSearchBean;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.Event.Event_MsgAndNewsDetailsActivity_Id;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 新闻搜索页面
 */
public class NewsSeaechFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search_news)
    ClearEditText etSearchNews;
    @BindView(R.id.tv_search_news)
    TextView tvSearchNews;
    @BindView(R.id.lv_company_names)
    ListView lvCompanyNames;
    @BindView(R.id.ll_search_news_not_content)
    LinearLayout llSearchNewsNotContent;
    private NewsSearchBean newsSearchBean;
    private MyAdapter adapter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_news;
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void setUpView() {
        adapter = new MyAdapter(mContext);
        lvCompanyNames.setAdapter(adapter);
        lvCompanyNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //从搜索也跳转到新闻详情页
                EventBus.getDefault().postSticky(
                        new Event_MsgAndNewsDetailsActivity_Id(
                                newsSearchBean.getData().getList().get(position).getId(),
                                MsgAndNewsDetailsActivity.DETAILS_TYPE_NEWS));
                startActivity(new Intent(getActivity(),MsgAndNewsDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
        etSearchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                if (!TextUtils.isEmpty(key)){
                    lvCompanyNames.setVisibility(View.VISIBLE);
                    llSearchNewsNotContent.setVisibility(View.GONE);
                    getNewsData(key);
                } else adapter.upData(null);//输入框无内容 适配器同步清空数据

            }
        });
    }

    private void getNewsData(String key) {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.NEWS_FIND +
                "?pageNo=0" +
                "&pageSize=10" +
                "&problem=" + key, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("搜索失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"新闻搜索结果............."+response);
                newsSearchBean = new Gson().fromJson(response,NewsSearchBean.class);
                loadingDialog.dismiss();
                if(newsSearchBean!=null&&newsSearchBean.getErrorcode().equals("0000")){
                    if(newsSearchBean.getData().getList().size()!=0){
                        adapter.upData(newsSearchBean.getData().getList());
                    }else {//搜索结果0 就显示 相应布局 隐藏listView
                        lvCompanyNames.setVisibility(View.GONE);
                        llSearchNewsNotContent.setVisibility(View.VISIBLE);
                    }
                }else toastShort(newsSearchBean.getErrormsg());
            }
        });
    }


    @OnClick({R.id.iv_back, R.id.tv_search_news})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.tv_search_news:
                break;
        }
    }

    public class MyAdapter extends BaseAdapter {
        List<NewsSearchBean.DataBean.ListBean> list;
        LayoutInflater inflater;
        public MyAdapter(Context context){
            inflater = LayoutInflater.from(context);
        }
        public void upData(List<NewsSearchBean.DataBean.ListBean> list){
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
                convertView = inflater.inflate(R.layout.item_news_profic, null);
                viewHolder = new ViewHolder();
                viewHolder.title = convertView.findViewById(R.id.tv_title_item_news_profic);
                viewHolder.createDate = convertView.findViewById(R.id.tv_date_item_news_profic);
                viewHolder.pic = convertView.findViewById(R.id.iv_item_news_profic);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
                viewHolder.title.setText(newsSearchBean.getData().getList().get(position).getTitle());
                viewHolder.createDate.setText(newsSearchBean.getData().getList().get(position).getCreateDate());
                viewHolder.pic.setVisibility(View.GONE);//暂时搜索item先隐藏图片
            return convertView;
        }
    }
    //辅助类
    class ViewHolder{
        TextView title,createDate;
        ImageView pic;
    }

}
