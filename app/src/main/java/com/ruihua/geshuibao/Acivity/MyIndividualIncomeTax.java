package com.ruihua.geshuibao.Acivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruihua.geshuibao.Adapter.MyIndividualIncomeTaxAdopter;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的个税  界面
 */
public class MyIndividualIncomeTax extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.rv_my_tax)
    RecyclerView rvMyTax;
    @BindView(R.id.srl_my_tax)
    SmartRefreshLayout srlMyTax;
    List<String> mDatas = new ArrayList<>();
    private MyIndividualIncomeTaxAdopter adopter;
    @Override
    public int intiLayout() {
        return R.layout.activity_my_individual_income_tax;
    }

    @Override
    public void initView() {
        tvHeadTitle.setText(getString(R.string.mine_my_individual));
        adopter = new MyIndividualIncomeTaxAdopter(this);
        rvMyTax.setLayoutManager(new LinearLayoutManager(this));
        rvMyTax.setAdapter(adopter);
        adopter.setDatas(mDatas);
    }

    @Override
    public void initData() {
        srlMyTax.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        srlMyTax.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                getNewsData();
                adopter.setDatas(mDatas);
                srlMyTax.finishLoadMore();
            }
        });
        getNewsData();
    }

    private void getNewsData() {
       for (int i=1;i<10;i++){
           mDatas.add("2018年"+i+"月");
       }
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
