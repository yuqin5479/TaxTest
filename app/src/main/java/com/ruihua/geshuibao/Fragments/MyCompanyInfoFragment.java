package com.ruihua.geshuibao.Fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MyCompanyActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.CompanyInfoBean;
import com.ruihua.geshuibao.Bean.UnbindMainCompanyBean;
import com.ruihua.geshuibao.Event.Event_CompanyInforFragment_CompanyId;
import com.ruihua.geshuibao.Event.Event_MyCompanyInforFragment_CompanyInfor;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 劳动合同单位 和待审核单位 碎片界面
 */
public class MyCompanyInfoFragment extends BaseFragment {
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
    @BindView(R.id.bit_unbind)
    Button bitUnbind;
    @BindView(R.id.tv_hint_text)
    TextView tvHintText;
    private String companyId;
    public static final int LABOR_CONTRACT_COMPANY = 1;//劳动合同单位标志
    public static final int PEBDING_TRIAL_COMPANY = 2;//待审核单位标志
    private int inforType;
    private String userId,token;
    private CompanyInfoBean companyInfoBean;
    private UnbindMainCompanyBean unbindMainCompanyBean;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_mycompany_info;
    }
    @Subscribe(threadMode = ThreadMode.MAIN ,sticky = true)
    public void getCompanyId(Event_MyCompanyInforFragment_CompanyInfor companyInfor) { //获取传递来的 单位id 请求单位详情
        companyId = companyInfor.companyId;
        inforType = companyInfor.inforType;
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && !TextUtils.isEmpty(companyId)) {
            getCompanyInfo();
            if (inforType==LABOR_CONTRACT_COMPANY){
                bitUnbind.setClickable(true);
                bitUnbind.setText("解除绑定劳动合同单位");
                tvHintText.setVisibility(View.VISIBLE);
            }else {
                bitUnbind.setClickable(false);
                bitUnbind.setText("待审核单位");
                tvHintText.setVisibility(View.GONE);
            }
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

    @OnClick({R.id.iv_back, R.id.bit_unbind})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MyCompanyActivity.switchContent(MyCompanyActivity.myCompanyFragment);
                break;
            case R.id.bit_unbind://解除劳动合同单位按钮
                if (!TextUtils.isEmpty(companyId))
                    unbind();
                break;
        }
    }

    private void unbind() {//解除劳动合同单位
        loadingDialog.show();
        Map<String,String> companyDate = new HashMap();
        companyDate.put("appUserId",userId);
        companyDate.put("token",token);
        companyDate.put("id",companyId);
        okHttpUtils.postAsnycData(companyDate, BaseUrl.unbindMainCompany, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("解除绑定失败！");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"解除绑定社保单位......."+response);
                unbindMainCompanyBean = new Gson().fromJson(response,UnbindMainCompanyBean.class);
                loadingDialog.dismiss();
                if (unbindMainCompanyBean!=null&&unbindMainCompanyBean.getErrorcode().equals("0000")){
                    toastShort("解除劳动合同单位成功");
                }else toastShort(unbindMainCompanyBean.getErrormsg());
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
