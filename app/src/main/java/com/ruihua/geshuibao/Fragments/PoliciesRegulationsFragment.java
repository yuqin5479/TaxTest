package com.ruihua.geshuibao.Fragments;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MsgAndNewsDetailsActivity;
import com.ruihua.geshuibao.Adapter.NewsProficyAdopter;
import com.ruihua.geshuibao.Adapter.PoliciesRegulationsAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.PoliciesRegulationsListBean;
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
 * 政策法规 列表 内容碎片
 */
@SuppressLint("ValidFragment")
public class PoliciesRegulationsFragment extends BaseFragment {
    @BindView(R.id.policies_regulations_proficy_rv)
    RecyclerView policiesRegulationsProficyRv;
    @BindView(R.id.policies_regulations_proficy_srl)
    SmartRefreshLayout policiesRegulationsProficySrl;
    private String policiesRegulationsId;
    private int pageNo = 1;
    private PoliciesRegulationsAdapter adopter;
    private PoliciesRegulationsListBean policiesRegulationsListBean;
    private List<PoliciesRegulationsListBean.DataBean.ListBean> policiesRegulationsList = new ArrayList<>();
    @SuppressLint("ValidFragment")
    public PoliciesRegulationsFragment(String policiesRegulationsId) {
        this.policiesRegulationsId = policiesRegulationsId;
    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_policies_regulations;
    }

    @Override
    protected void setUpData() {
        adopter = new PoliciesRegulationsAdapter(getActivity());
        policiesRegulationsProficyRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        policiesRegulationsProficyRv.setAdapter(adopter);
        policiesRegulationsProficySrl.autoRefresh();// 进入页面 自动刷新
    }

    @Override
    protected void setUpView() {
        policiesRegulationsProficySrl.setOnRefreshListener(new OnRefreshListener() {//下拉刷新
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                getData();
            }
        });
        policiesRegulationsProficySrl.setOnLoadMoreListener(new OnLoadMoreListener() {//上拉加载
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNo++;
                getData();
            }
        });
        adopter.setItemClickListener(new PoliciesRegulationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().postSticky(
                        new Event_MsgAndNewsDetailsActivity_Id(
                                policiesRegulationsList.get(position).getId(),
                                MsgAndNewsDetailsActivity.DETAILS_TYPE_POLICY));
                startActivity(new Intent(getActivity(),MsgAndNewsDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });

    }

    private void getData() {
        okHttpUtils.getAsyncData(BaseUrl.lawFind
                        + "?pageNo=" + pageNo
                        + "&pageSize=10"
                        + "&classifyId=" + policiesRegulationsId,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        toastShort("政策法规列表获取失败");
                    }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"政策法规列表获取......"+response);
                        if(policiesRegulationsListBean!=null&&policiesRegulationsList!=null){
                            PoliciesRegulationsListBean bean = new Gson().fromJson(response,PoliciesRegulationsListBean.class);
                            if(bean.getData().getList().size()!=0){
                                if (pageNo==1)
                                    policiesRegulationsList = bean.getData().getList();
                                else
                                    policiesRegulationsList.addAll(bean.getData().getList());
                            } else policiesRegulationsProficySrl.finishLoadMoreWithNoMoreData();//数据加载完成 暂无数据
                        }else {
                            policiesRegulationsListBean = new Gson().fromJson(response,PoliciesRegulationsListBean.class);
                            policiesRegulationsList = policiesRegulationsListBean.getData().getList();
                            if(policiesRegulationsList.size()==0){
                                policiesRegulationsProficySrl.finishLoadMoreWithNoMoreData();
                            }
                        }
                        adopter.setDatas(policiesRegulationsList);
                        policiesRegulationsProficySrl.finishRefresh();
                        policiesRegulationsProficySrl.finishLoadMore();
                    }
                }
        );
    }

}


