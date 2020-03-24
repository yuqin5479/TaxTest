package com.ruihua.geshuibao.Fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MsgAndNewsDetailsActivity;
import com.ruihua.geshuibao.Adapter.PoliciesRegulationsAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.PoliciesRegulationsListBean;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.Event.Event_MsgAndNewsDetailsActivity_Id;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
/**
 * 政策法规搜索 界面 碎片
 */
public class PoliciesRegulationsSearchFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search_policies)
    ClearEditText etSearchPolicies;
    @BindView(R.id.tv_search_policies)
    TextView tvSearchPolicies;
    @BindView(R.id.rl_policies)
    RecyclerView rlPolicies;
    @BindView(R.id.ll_search_policies_not_content)
    LinearLayout llSearchPoliciesNotContent;
    private PoliciesRegulationsAdapter adapter;
    private PoliciesRegulationsListBean policiesRegulationsListBean;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_policies_regulations_search;
    }

    @Override
    protected void setUpData() {
        adapter = new PoliciesRegulationsAdapter(getActivity());
        rlPolicies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlPolicies.setAdapter(adapter);
    }

    @Override
    protected void setUpView() {
        etSearchPolicies.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                if (!TextUtils.isEmpty(key)){
                    rlPolicies.setVisibility(View.VISIBLE);
                    llSearchPoliciesNotContent.setVisibility(View.GONE);
                    getPoliciesData(key);
                } else adapter.setDatas(null);//输入框无内容 适配器同步清空数据

            }
        });
        adapter.setItemClickListener(new PoliciesRegulationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转政策法规详情
                EventBus.getDefault().postSticky(
                        new Event_MsgAndNewsDetailsActivity_Id(
                                policiesRegulationsListBean.getData().getList().get(position).getId(),
                                MsgAndNewsDetailsActivity.DETAILS_TYPE_POLICY));
                startActivity(new Intent(getActivity(),MsgAndNewsDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
    }
    private void getPoliciesData(String key) {
        okHttpUtils.getAsyncData(BaseUrl.lawFind
                        + "?pageNo=1"
                        + "&pageSize=20"
                        + "&problem=" + key,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("搜索政策法规失败");
                    }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"政策法规搜索获取内容.........."+response);
                        policiesRegulationsListBean = new Gson().fromJson(response,PoliciesRegulationsListBean.class);
                        if(policiesRegulationsListBean!=null&&policiesRegulationsListBean.getErrorcode().equals("0000")){
                            if(policiesRegulationsListBean.getData().getList().size()!=0){
                                adapter.setDatas(policiesRegulationsListBean.getData().getList());
                            }else {//搜索结果0 就显示 相应布局 隐藏listView
                                rlPolicies.setVisibility(View.GONE);
                                llSearchPoliciesNotContent.setVisibility(View.VISIBLE);
                            }
                        }else toastShort(policiesRegulationsListBean.getErrormsg());

                    }
                }
        );
    }
    @OnClick({R.id.iv_back, R.id.tv_search_policies})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.tv_search_policies:
                break;
        }
    }
}
