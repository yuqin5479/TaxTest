package com.ruihua.geshuibao.Fragments;
import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Adapter.SelfHelpContentAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.SelfHelpContentBean;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 自助问答 列表 碎片
 */
@SuppressLint("ValidFragment")
public class SelfHelpContentFragment extends BaseFragment {
    @BindView(R.id.rv_seif_help)
    RecyclerView rvSeifHelp;
    @BindView(R.id.srl_seif_help)
    SmartRefreshLayout srlSeifHelp;
    private int pageNo = 1;
    private String classifyId;//分类id
    private SelfHelpContentBean selfHelpContentBean;
    private SelfHelpContentAdapter adopter;
    private List<SelfHelpContentBean.DataBean.ListBean> ContentList = new ArrayList<>();
    @SuppressLint("ValidFragment")
    public SelfHelpContentFragment(String classifyId) {
        this.classifyId = classifyId;
    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_self_help_content;
    }
    @Override
    protected void setUpView() {
        srlSeifHelp.setOnRefreshListener(new OnRefreshListener() {//下拉刷新
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                getContentData();
            }
        });
        srlSeifHelp.setOnLoadMoreListener(new OnLoadMoreListener() {//上拉加载
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNo++;
                getContentData();
            }
        });
    }
    @Override
    protected void setUpData() {
        adopter = new SelfHelpContentAdapter(getActivity());
        rvSeifHelp.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSeifHelp.setAdapter(adopter);
        srlSeifHelp.autoRefresh();// 进入页面 自动刷新
    }

    private void getContentData() {
        okHttpUtils.getAsyncData(BaseUrl.querySelfManagerById
                        + "?pageNo=" + pageNo
                        + "&pageSize=10"
                        + "&id=" + classifyId,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        toastShort("自助问答获取失败");
                    }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"根据自助问答分类id获取内容.........."+response);
                        if(selfHelpContentBean!=null&&ContentList!=null){
                            SelfHelpContentBean bean = new Gson().fromJson(response,SelfHelpContentBean.class);
                            if(bean.getData().getList().size()!=0){
                                if (pageNo==1)
                                    ContentList = bean.getData().getList();
                                else
                                    ContentList.addAll(bean.getData().getList());
                            } else srlSeifHelp.finishLoadMoreWithNoMoreData();//数据加载完成 暂无数据
                        }else {
                            selfHelpContentBean = new Gson().fromJson(response,SelfHelpContentBean.class);
                            ContentList = selfHelpContentBean.getData().getList();
                            if(ContentList.size()==0){
                                srlSeifHelp.finishLoadMoreWithNoMoreData();
                            }
                        }
                        adopter.setDatas(ContentList);
                        srlSeifHelp.finishRefresh();
                        srlSeifHelp.finishLoadMore();
                    }
                }
        );
    }



}
