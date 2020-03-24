package com.ruihua.geshuibao.Fragments;
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
import com.ruihua.geshuibao.Adapter.SelfHelpContentAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.SelfHelpContentBean;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 咨询搜索  碎片
 */
public class ConsultSearchFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_search_consult)
    ClearEditText etSearchConsult;
    @BindView(R.id.tv_search_consult)
    TextView tvSearchConsult;
    @BindView(R.id.rl_company_consults)
    RecyclerView rlCompanyConsults;
    @BindView(R.id.ll_search_consult_not_content)
    LinearLayout llSearchConsultNotContent;
    private SelfHelpContentAdapter adapter;
    private SelfHelpContentBean selfHelpContentBean;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_consult_search;
    }

    @Override
    protected void setUpData() {
        adapter = new SelfHelpContentAdapter(getActivity());
        rlCompanyConsults.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlCompanyConsults.setAdapter(adapter);
    }

    @Override
    protected void setUpView() {
        etSearchConsult.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String key = s.toString().trim();
                if (!TextUtils.isEmpty(key)){
                    rlCompanyConsults.setVisibility(View.VISIBLE);
                    llSearchConsultNotContent.setVisibility(View.GONE);
                    getConsultData(key);
                } else adapter.setDatas(null);//输入框无内容 适配器同步清空数据

            }
        });
    }

    private void getConsultData(String key) {
        okHttpUtils.getAsyncData(BaseUrl.querySelfManagerById
                        + "?pageNo=1"
                        + "&pageSize=20"
                        + "&search=" + key,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("搜索自助问答失败");
                    }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"自助问答搜索获取内容.........."+response);
                        selfHelpContentBean = new Gson().fromJson(response,SelfHelpContentBean.class);
                        if(selfHelpContentBean!=null&&selfHelpContentBean.getErrorcode().equals("0000")){
                            if(selfHelpContentBean.getData().getList().size()!=0){
                                adapter.setDatas(selfHelpContentBean.getData().getList());
                            }else {//搜索结果0 就显示 相应布局 隐藏listView
                                rlCompanyConsults.setVisibility(View.GONE);
                                llSearchConsultNotContent.setVisibility(View.VISIBLE);
                            }
                        }else toastShort(selfHelpContentBean.getErrormsg());

                    }
                }
        );
    }

    @OnClick({R.id.iv_back, R.id.tv_search_consult})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.tv_search_consult:
                break;
        }
    }
}
