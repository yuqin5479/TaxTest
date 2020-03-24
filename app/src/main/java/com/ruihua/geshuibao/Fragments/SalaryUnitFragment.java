package com.ruihua.geshuibao.Fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MyCompanyActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.CompanyInfoBean;
import com.ruihua.geshuibao.Bean.SetLaborContractUnitBean;
import com.ruihua.geshuibao.Event.Event_MyCompanyInforFragment_CompanyInfor;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 授薪单位碎片
 */
public class SalaryUnitFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_representative)
    TextView tvRepresentative;
    @BindView(R.id.tv_ns_id)
    TextView tvNsId;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.iv_company_qr_code)
    ImageView ivCompanyQrCode;
    @BindView(R.id.but_swldht_dw)
    Button butSwldhtDw;
    @BindView(R.id.but_qxdqsx_dw)
    Button butQxdqsxDw;
    @BindView(R.id.tv_hint_text)
    TextView tvHintText;
    private String userId,token,companyId;
    private CompanyInfoBean companyInfoBean;
    private SetLaborContractUnitBean setLaborContractUnitBean;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_award_salary_company;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void getCompanyId(Event_MyCompanyInforFragment_CompanyInfor companyInfor) { //获取传递来的 单位id 请求单位详情
        companyId = companyInfor.companyId;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !TextUtils.isEmpty(companyId)) {
            getCompanyInfo();
        }
    }
    private void getCompanyInfo() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.COMPANY_INFO
                        + "?appUserId=" + userId
                        + "&token=" + token
                        + "&id=" + companyId, new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("获取单位信息失败");
                    }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"获取单位信息........."+response);
                        companyInfoBean = new Gson().fromJson(response,CompanyInfoBean.class);
                        loadingDialog.dismiss();
                        if(companyInfoBean!=null&&companyInfoBean.getErrorcode().equals("0000")){
                            setCompanyInfoData();
                        }else toastShort(companyInfoBean.getErrormsg());
                    }
                }
        );
    }
    private void setCompanyInfoData() {
        tvCompanyName.setText(companyInfoBean.getData().getName());
        tvRepresentative.setText(companyInfoBean.getData().getLegal_person());
        tvNsId.setText(companyInfoBean.getData().getNumber());
        Glide.with(this).load(companyInfoBean.getData().getCompanyQrcode()).into(ivCompanyQrCode);
    }
    @Override
    protected void setUpData() {
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        if (!TextUtils.isEmpty(companyId)){
            getCompanyInfo();
        }
    }

    @Override
    protected void setUpView() {
        tvHeadTitle.setText("单位信息");
    }


    @OnClick({R.id.iv_back, R.id.but_swldht_dw, R.id.but_qxdqsx_dw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MyCompanyActivity.switchContent(MyCompanyActivity.myCompanyFragment);
                break;
            case R.id.but_swldht_dw://设为劳动合同单位
                setLaborContractUnit();
                break;
            case R.id.but_qxdqsx_dw://取消当前授薪单位
                cancelSalaryUnit();
                break;
        }
    }

    private void cancelSalaryUnit() {
        loadingDialog.show();
        Map<String,String> companyDate = new HashMap();
        companyDate.put("appUserId",userId);
        companyDate.put("token",token);
        companyDate.put("id",companyId);
        okHttpUtils.postAsnycData(companyDate, BaseUrl.cancelMoneyCompany, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("取消当前授薪单位失败！");
            }

            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                Log.i(TAG,"取消当前授薪单位......."+response);
                try {
                    if(new JSONObject(response).getJSONObject("data").getInt("success")==1){
                        toastShort("取消授薪单位成功");
                        MyCompanyActivity.switchContent(MyCompanyActivity.myCompanyFragment);
                    }else toastShort("取消授薪单位失败");
                } catch (JSONException e) {
                    try {
                        toastShort(new JSONObject(response).optString("errormsg"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    private void setLaborContractUnit() {
        loadingDialog.show();
        Map<String,String> companyDate = new HashMap();
        companyDate.put("appUserId",userId);
        companyDate.put("token",token);
        companyDate.put("id",companyId);
        okHttpUtils.postAsnycData(companyDate, BaseUrl.setSecurityCompany, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("设为劳动合同单位失败！");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"设为劳动合同单位......."+response);
                setLaborContractUnitBean = new Gson().fromJson(response,SetLaborContractUnitBean.class);
                loadingDialog.dismiss();
                if (setLaborContractUnitBean!=null&&setLaborContractUnitBean.getErrorcode().equals("0000")){
                    toastShort("解除劳动合同单位成功");
                    MyCompanyActivity.switchContent(MyCompanyActivity.myCompanyFragment);
                }else toastShort(setLaborContractUnitBean.getErrormsg());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
