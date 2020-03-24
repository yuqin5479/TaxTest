package com.ruihua.geshuibao.Fragments;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.LocationUtils;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;
import com.ruihua.geshuibao.Util.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 单位认证  碎片
 */
public class CompanyAuthenticationFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.et_company_name)
    ClearEditText etCompanyName;
    @BindView(R.id.et_company_code)
    ClearEditText etCompanyCode;
    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.et_verification_code)
    ClearEditText etVerificationCode;
    @BindView(R.id.tv_get_verification)
    TextView tvGetVerification;
    @BindView(R.id.tv_submit_register)
    TextView tvSubmitRegister;
    @BindView(R.id.tv_service_agreement)
    TextView tvServiceAgreement;
    private String userId,token;
    private Map<String,String> verificationUrlMap;
    private int countdown = 60,TIME = 1000 ;
    private String adminArea = "";//行政区、城市名
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_company_authentication;
    }

    @Override
    protected void setUpData() {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                initLocation();//获取位置
                Looper.loop();
            }
        }.start();
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        verificationUrlMap = new HashMap<>();
    }
    private void initLocation() {
        if(LocationUtils.isLocationEnabled(mContext)){
            LocationUtils.register(mContext, 5000, 100, new LocationUtils.OnLocationChangeListener() {
                @Override
                public void getLastKnownLocation(Location location) {
                    Address address = LocationUtils.getAddress(mContext,location.getLatitude(),location.getLongitude());
                    adminArea = address.getAdminArea();
                }
                @Override
                public void onLocationChanged(Location location) {}
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { }
            });
        }
    }
    @Override
    protected void setUpView() {
        tvHeadTitle.setText(getString(R.string.company_authentication));
    }

    @OnClick({R.id.iv_back,R.id.tv_get_verification,R.id.tv_submit_register ,R.id.tv_service_agreement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回 加入单位 界面
                JoinCompanyActivity.switchContent(JoinCompanyActivity.joinCompanyFragment);
                break;
            case R.id.tv_get_verification://获取验证码
                if(Utils.isMobileNO(etPhone.getText().toString().trim())){
                    tvGetVerification.setClickable(false);
                    handler.postDelayed(runnable, TIME); //每隔1s执行 启动定时器
                    getVerificationCode(etPhone.getText().toString().trim());
                }else toastShort("请输入正确的手机号码");
                break;
            case R.id.tv_submit_register:
                if(dataIsNull()){//执行单位认证 请求
                    companyAuthentication();
                }
                break;
            case R.id.tv_service_agreement://服务协议监听

                break;
        }
    }

    private void companyAuthentication() {
        loadingDialog.show();
        Map<String,String> companyData = new HashMap<>();
        companyData.put("address",adminArea);
        companyData.put("appUserId",userId);
        companyData.put("token ",token);
        companyData.put("code ",etVerificationCode.getText().toString().trim());
        companyData.put("name",etCompanyName.getText().toString().trim());
        companyData.put("number",etCompanyCode.getText().toString().trim());
        companyData.put("phone",etPhone.getText().toString().trim());
        companyData.put("registerMethod","1");//1客户端发起认证
        okHttpUtils.postAsnycData(companyData, BaseUrl.companyAuthentication, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("单位认证失败！");
            }

            @Override
            public void onSucces(Call call, String response) {

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
                 loadingDialog.dismiss();
            }
        });
    }

    private void getVerificationCode(String phoneNumber) {
        loadingDialog.show();
        verificationUrlMap.clear();
        verificationUrlMap.put("mobile", phoneNumber);
        verificationUrlMap.put("type","8");//8 企业申请验证码
        okHttpUtils.postAsnycData(verificationUrlMap, BaseUrl.SEND_MOBILE_CODE2, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastShort("验证码获取失败!");
                loadingDialog.dismiss();
            }
            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                Log.i(TAG,"单位认证获取验证码......."+response);
                try {
                    if(new JSONObject(response).optString("errorcode").equals("0000")){
                        toastShort("验证码已发送");
                    }
                } catch (JSONException e) {
                    loadingDialog.dismiss();
                    toastShort("验证码获取失败!");
                    e.printStackTrace();
                }
            }
        });
    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                tvGetVerification.setTextColor(getResources().getColor(R.color.gray_text));
                tvGetVerification.setText("剩余（" + Integer.toString(--countdown) + "）");
                if (countdown == 0) {
                    handler.removeCallbacks(this);
                    countdown = 60;
                    tvGetVerification.setClickable(true);
                    tvGetVerification.setTextColor(getResources().getColor(R.color.login_btn_background));
                    tvGetVerification.setText("获取验证码");
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }

        }
    };
    private boolean dataIsNull() {
        if (TextUtils.isEmpty(etCompanyName.getText().toString().trim())){
            toastShort("单位名称不可为空");
            return false;
        }
        if(!Utils.isTaxpayer(etCompanyCode.getText().toString().trim())){
            toastShort("请输入正确的纳税人识别号");
            return false;
        }
        if (!Utils.isMobileNO(etPhone.getText().toString().trim())){
            toastShort("请输入正确的手机号码");
            return false;
        }
        if(TextUtils.isEmpty(etVerificationCode.getText().toString().trim())){
            toastShort("验证码不可为空");
            return false;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationUtils.unregister();//定位注销
    }
}
