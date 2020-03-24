package com.ruihua.geshuibao.Fragments;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Acivity.MyCompanyActivity;
import com.ruihua.geshuibao.Adapter.MyCompanyAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.MyCompanysBean;
import com.ruihua.geshuibao.Event.Event_CompanyInforFragment_CompanyId;
import com.ruihua.geshuibao.Event.Event_MyCompanyInforFragment_CompanyInfor;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 我的单位 列表 碎片
 */
public class MyCompanyFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.tv_head_right)
    TextView tvHeadRight;
    @BindView(R.id.mycompany_srl)
    SwipeRefreshLayout mycompanySrl;

    @BindView(R.id.rl_labor_contract_company)
    RelativeLayout rlLaborContractCompany;
    @BindView(R.id.tv_labor_contract_company_type)
    TextView tvLaborContractCompanyType;
    @BindView(R.id.lv_labor_contract_company_list)
    ListView lvLaborContractCompanyList;

    @BindView(R.id.rl_salaried_company)
    RelativeLayout rlSalariedCompany;
    @BindView(R.id.tv_salaried_company_type)
    TextView tvSalariedCompanyType;
    @BindView(R.id.lv_salaried_company_list)
    ListView lvSalariedCompanyList;

    @BindView(R.id.rl_pending_company)
    RelativeLayout rlPendingCompany;
    @BindView(R.id.tv_pending_company_type)
    TextView tvPendingCompanyType;
    @BindView(R.id.lv_pending_company_list)
    ListView lvPendingCompanyList;

    private String userId,token;
    private MyCompanysBean myCompanysBean;
    private MyCompanyAdapter adapter1,adapter2,adapter3;//劳动合同单位、授薪单位、待审核单位
    private List<String> companys1,companys2,companys3;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_my_company;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && mycompanySrl!=null) {
            mycompanySrl.setColorSchemeResources(R.color.main_color);
            mycompanySrl.setOnRefreshListener(refreshListener);
            mycompanySrl.post(new Runnable() {
                @Override
                public void run() {
                    mycompanySrl.setRefreshing(true);
                }
            });
            refreshListener.onRefresh();
        }
    }
    @Override
    protected void setUpData() {
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        companys1 = new ArrayList<>();//劳动合同单位
        companys2 = new ArrayList<>();//授薪单位
        companys3 = new ArrayList<>();//待审核单位
        mycompanySrl.setColorSchemeResources(R.color.main_color);
        mycompanySrl.setOnRefreshListener(refreshListener);
        mycompanySrl.post(new Runnable() {
            @Override
            public void run() {
                mycompanySrl.setRefreshing(true);
            }
        });
        refreshListener.onRefresh();
    }
    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        public void onRefresh() {
            //刷新  获取数据单位数据
            getCompanyData();
        }
    };

    private void getCompanyData() {
        okHttpUtils.getAsyncData(BaseUrl.myJoinCompany +
                "?appUserId=" + userId +
                "&token=" + token, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastShort("我的单位获取失败");
                if (mycompanySrl.isRefreshing()) {
                    mycompanySrl.setRefreshing(false);
                }
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"我的单位.............."+response);
                myCompanysBean = new Gson().fromJson(response,MyCompanysBean.class);
                if (myCompanysBean!=null&&myCompanysBean.getErrorcode().equals("0000")){
                    setCompanyNameData();
                    if (mycompanySrl.isRefreshing()) {
                        mycompanySrl.setRefreshing(false);
                    }
                }else {
                    if (mycompanySrl.isRefreshing()) {
                        mycompanySrl.setRefreshing(false);
                    }
                    toastShort(myCompanysBean.getErrormsg());
                }
            }
        });
    }

    private void setCompanyNameData() {
        companys1.clear();companys2.clear();companys3.clear();
        for (int i=0;i<myCompanysBean.getData().getSecurityCompanylist().size();i++){
            companys1.add(myCompanysBean.getData().getSecurityCompanylist().get(i).getName());
        }
        for (int i=0;i<myCompanysBean.getData().getMoneyCompanylist().size();i++){
            companys2.add(myCompanysBean.getData().getMoneyCompanylist().get(i).getName());
        }
        for (int i=0;i<myCompanysBean.getData().getCompanyNotAduitList().size();i++){
            companys3.add(myCompanysBean.getData().getCompanyNotAduitList().get(i).getName());
        }
        adapter1.setDeta(companys1);
        adapter2.setDeta(companys2);
        adapter3.setDeta(companys3);
    }

    @Override
    protected void setUpView() {
        tvHeadTitle.setText("我的单位");
        tvHeadRight.setText("添加");
        adapter1 = new MyCompanyAdapter(getActivity());
        adapter2 = new MyCompanyAdapter(getActivity());
        adapter3 = new MyCompanyAdapter(getActivity());
        lvLaborContractCompanyList.setAdapter(adapter1);
        lvSalariedCompanyList.setAdapter(adapter2);
        lvPendingCompanyList.setAdapter(adapter3);
        lvLaborContractCompanyList.setOnItemClickListener(this);
        lvSalariedCompanyList.setOnItemClickListener(this);
        lvPendingCompanyList.setOnItemClickListener(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.tv_head_right:
                startActivity(new Intent(getActivity(),JoinCompanyActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.lv_labor_contract_company_list://劳动合同单位列表
                //传单位id跳转单位详情页面 (劳动合同单位详情页面)
                EventBus.getDefault().postSticky(new Event_MyCompanyInforFragment_CompanyInfor(
                        myCompanysBean.getData().getSecurityCompanylist().get(position).getId(),
                        MyCompanyInfoFragment.LABOR_CONTRACT_COMPANY));
                MyCompanyActivity.switchContent(MyCompanyActivity.myCompanyInfoFragment);
                break;
            case R.id.lv_salaried_company_list://授薪单位列表
                EventBus.getDefault().postSticky(new Event_MyCompanyInforFragment_CompanyInfor(
                        myCompanysBean.getData().getMoneyCompanylist().get(position).getId(),
                        0));
                MyCompanyActivity.switchContent(MyCompanyActivity.salaryUnitFragment);
                break;
            case R.id.lv_pending_company_list://待审核单位列表
                EventBus.getDefault().postSticky(new Event_MyCompanyInforFragment_CompanyInfor(
                        myCompanysBean.getData().getCompanyNotAduitList().get(position).getId(),
                        MyCompanyInfoFragment.PEBDING_TRIAL_COMPANY));
                MyCompanyActivity.switchContent(MyCompanyActivity.myCompanyInfoFragment);
                break;
        }
    }
}
