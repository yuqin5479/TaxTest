package com.ruihua.geshuibao.Fragments;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.CompanyInfoBean;
import com.ruihua.geshuibao.Event.Event_CompanyInforFragment_CompanyId;
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
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 企业信息 碎片
 */
public class CompanyInfoFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_company_info_text)
    TextView tvCompanyInfoText;
    @BindView(R.id.iv_companyname_logo)
    ImageView ivCompanynameLogo;
    @BindView(R.id.tv_company_name)
    TextView tvCompanyName;
    @BindView(R.id.tv_organization_code)
    TextView tvOrganizationCode;
    @BindView(R.id.tv_company_name2)
    TextView tvCompanyName2;
    @BindView(R.id.tv_legal_representative_name)
    TextView tvLegalRepresentativeName;
    @BindView(R.id.iv_company_qr_code)
    ImageView ivCompanyQrCode;
    @BindView(R.id.but_join_company)//设为 劳动合同单位 按钮
    Button butJoinCompany;
    @BindView(R.id.but_join_company2)//设为  授薪单位 按钮
    Button butJoinCompany2;
    private String userId,token,companyId,joinTyoe;
    private CompanyInfoBean companyInfoBean;
    private Map<String,String> joinCompanyData = new HashMap<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_company_info;
    }
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void getCompanyId(Event_CompanyInforFragment_CompanyId event_companyId) { //获取传递来的 单位id 请求单位详情
        companyId = event_companyId.companyId;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!TextUtils.isEmpty(companyId)){
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
        Glide.with(this).load(companyInfoBean.getData().getLogo())
        .apply(RequestOptions.bitmapTransform(new CircleCrop())//按圆形图加载
        .error(R.drawable.join_header_companylogo)//加载错误图
        .placeholder(R.drawable.join_header_companylogo))//占位图
        .into(ivCompanynameLogo);
        tvCompanyName.setText(companyInfoBean.getData().getName());
        tvOrganizationCode.setText(companyInfoBean.getData().getNumber());
        tvCompanyName2.setText(companyInfoBean.getData().getName());
        tvLegalRepresentativeName.setText(companyInfoBean.getData().getLegal_person());
        Glide.with(this).load(companyInfoBean.getData().getCompanyQrcode()).into(ivCompanyQrCode);
        setWindowBrightness(getActivity(),WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL);//填充完二维码 屏幕高亮
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
    protected void setUpView() { }
    @OnClick({R.id.iv_back, R.id.but_join_company,R.id.but_join_company2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                JoinCompanyActivity.switchContent(JoinCompanyActivity.searchJoinCompanyFragment);//左上角 返回键 返回搜索单位页面
                break;
            case R.id.but_join_company://点击按钮执行加入单位(劳动合同单位/社保单位)
                joinTyoe = "1";
                joinCompany();
                break;
            case R.id.but_join_company2://点击按钮执行加入单位（授薪单位）
                joinTyoe = "2";
                joinCompany();
                break;
        }
    }

    private void joinCompany() {
        loadingDialog.show();
        joinCompanyData.clear();
        joinCompanyData.put("id",companyId);
        joinCompanyData.put("token",token);
        joinCompanyData.put("appUserId",userId);
        joinCompanyData.put("type",joinTyoe);
        okHttpUtils.postAsnycData(joinCompanyData, BaseUrl.JOIN_COMPANY, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("加入单位失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                Log.i(TAG,"加入单位........"+response);
                try {
                    if(new JSONObject(response).getJSONObject("data").getBoolean("success")){
                        toastShort(new JSONObject(response).optString("errormsg"));
                        getActivity().finish();
                    }else {
                        toastShort(new JSONObject(response).optString("errormsg"));
                        getActivity().finish();
                    }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        setWindowBrightness(getActivity(),WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE);//恢复屏幕亮度
        super.onDestroy();
    }
    //调整屏幕亮度的方法
    private void setWindowBrightness(Activity activity, float brightness) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = brightness;
        window.setAttributes(lp);
    }

}
