package com.ruihua.geshuibao.Fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MsgAndNewsDetailsActivity;
import com.ruihua.geshuibao.Adapter.NewsProficyAdopter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.NewsFindBean;
import com.ruihua.geshuibao.Event.Event_MsgAndNewsDetailsActivity_Id;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 新闻列表 碎片
 */
@SuppressLint("ValidFragment")
public class NewsProficyFragment extends BaseFragment {
    @BindView(R.id.news_proficy_rv)
    RecyclerView mNewsProficyRv;
    @BindView(R.id.news_proficy_srl)
    SmartRefreshLayout mNewsProficySrl;
    private NewsProficyAdopter adopter;
    private String newsId;
    private int pageNo = 1;
    private NewsFindBean newsFindBean;
    private List<NewsFindBean.DataBean.ListBean> newsList = new ArrayList<>();
    @SuppressLint("ValidFragment")
    public NewsProficyFragment(String newsId) {
       this.newsId = newsId;
    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_news_proficy;
    }

    @Override
    protected void setUpData() {
        adopter = new NewsProficyAdopter(getActivity());
        mNewsProficyRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsProficyRv.setAdapter(adopter);
        mNewsProficySrl.autoRefresh();// 进入页面 自动刷新
    }

    @Override
    protected void setUpView() {
        mNewsProficySrl.setOnRefreshListener(new OnRefreshListener() {//下拉刷新
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                getNewsData();
            }
        });
        mNewsProficySrl.setOnLoadMoreListener(new OnLoadMoreListener() {//上拉加载
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNo++;
                getNewsData();
            }
        });
        adopter.setItemClickListener(new NewsProficyAdopter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().postSticky(
                        new Event_MsgAndNewsDetailsActivity_Id(
                                newsList.get(position).getId(),
                                MsgAndNewsDetailsActivity.DETAILS_TYPE_NEWS));
                startActivity(new Intent(getActivity(),MsgAndNewsDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
    }

    private void getNewsData() {
        okHttpUtils.getAsyncData(BaseUrl.NEWS_FIND
                        + "?pageNo=" + pageNo
                        + "&pageSize=10"
                        + "&classifyId=" + newsId,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("新闻获取失败");
                    }
                    @Override
                    public void onSucces(Call call, String response) {
                        if(newsFindBean!=null&&newsList!=null){
                            NewsFindBean bean = new Gson().fromJson(response,NewsFindBean.class);
                            if(bean.getData().getList().size()!=0){
                                if (pageNo==1)
                                    newsList = bean.getData().getList();
                                else
                                    newsList.addAll(bean.getData().getList());
                            } else mNewsProficySrl.finishLoadMoreWithNoMoreData();//数据加载完成 暂无数据
                        }else {
                            newsFindBean = new Gson().fromJson(response,NewsFindBean.class);
                            newsList = newsFindBean.getData().getList();
                            if(newsList.size()==0){
                                mNewsProficySrl.finishLoadMoreWithNoMoreData();
                            }
                        }
                        adopter.setDatas(newsList);
                        mNewsProficySrl.finishRefresh();
                        mNewsProficySrl.finishLoadMore();
                    }
                }
        );
    }

}
