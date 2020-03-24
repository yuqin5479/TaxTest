package com.ruihua.geshuibao.Acivity;

import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Bean.RegisterBean;
import com.ruihua.geshuibao.Util.LocationUtils;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.R;
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
 * 注册界面
 */

public class RejisterActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBackHeadtitle;
    @BindView(R.id.tv_head_title)
    TextView tvTitleHeadtitle;
    @BindView(R.id.tv_strverification_rejest)
    TextView tvStrverificationRejest;
    @BindView(R.id.et_verification_rejest)
    ClearEditText etVerificationRejest;
    @BindView(R.id.tv_getverification_rejecst)
    TextView tvGetverificationRejecst;
    @BindView(R.id.tv_strpassword_regist)
    TextView tvStrpasswordRegist;
    @BindView(R.id.image_isshow_regist)
    ImageView imageIsshowRegist;
    @BindView(R.id.et_password_regist)
    ClearEditText etPasswordRegist;
    @BindView(R.id.tv_rejistnow_rejist)
    TextView tvRejistnowRejist;
    @BindView(R.id.et_phone_regist)
    ClearEditText etPhoneRegist;
    @BindView(R.id.tv_ismember_rejist)
    TextView tvIsmemberRejist;
    @BindView(R.id.tv_phone)
    TextView mTextView_phone;

    @BindView(R.id.act_register_privacy_policy)
    TextView tv_privacy_policy;


    private Map<String, String> verificationUrlMap,registerUrlMap;
    private String strphone;
    private RegisterBean registerBean;
    private int i = 60;
    private int TIME = 1000;
    private boolean showPassword = false;
    private String adminArea = "";//行政区、城市名
    private double latitude = 0.0,longitude=0.0;//经纬度
    boolean hadRejiste = true;
    @Override
    public int intiLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
            tvTitleHeadtitle.setText("账号注册");
            mTextView_phone.setText("手机号");
            tvRejistnowRejist.setText("立即注册");
    }

    @Override
    public void initData() {
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                initLocation();//获取位置
                Looper.loop();
            }
        }.start();
        verificationUrlMap = new HashMap<>();
        registerUrlMap = new HashMap<>();
    }

    private void initLocation() {
        if(LocationUtils.isLocationEnabled(context)){
            LocationUtils.register(context, 5000, 100, new LocationUtils.OnLocationChangeListener() {
                @Override
                public void getLastKnownLocation(Location location) {
                    Address address = LocationUtils.getAddress(RejisterActivity.context,location.getLatitude(),location.getLongitude());
                    adminArea = address.getAdminArea();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                @Override
                public void onLocationChanged(Location location) {}
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { }
            });
        }
    }


    private void regist() { //执行注册
        loadingDialog.show();
        registerUrlMap.put("mobile",strphone);
        registerUrlMap.put("password",etPasswordRegist.getText().toString().trim());
        registerUrlMap.put("code",etVerificationRejest.getText().toString().trim());
        registerUrlMap.put("IMEI",Utils.getAndroidID());
        registerUrlMap.put("type","2");//1 ios 2 android
        registerUrlMap.put("city",adminArea);
        registerUrlMap.put("longitude",longitude+"");
        registerUrlMap.put("latitude",latitude+"");
        okHttpUtils.postAsnycData(registerUrlMap, BaseUrl.REGISTER, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastShort("注册失败！请稍候重试...");
                loadingDialog.dismiss();
            }
            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                Log.i(TAG,"注册...."+response);
                registerBean = new Gson().fromJson(response,RegisterBean.class);
                //本地保存 用户数据
                if (registerBean!=null&&registerBean.getErrorcode().equals("0000")){
                    saveUserInfor();
                    startActivity(new Intent(RejisterActivity.this,MainActivity.class));
                    RejisterActivity.this.finish();
                }else {
                    toastShort(registerBean.getErrormsg());
                }

            }
        });
    }

    private void saveUserInfor() {
            SPUtils.put("mobile",registerBean.getData().getMobile());
            SPUtils.put("userName",registerBean.getData().getUserName());
            SPUtils.put("userId",registerBean.getData().getUserId());
            SPUtils.put("token",registerBean.getData().getToken());
    }

    private boolean setPhoneNum() {
        if (!TextUtils.isEmpty(etPhoneRegist.getText().toString().trim())) {
            strphone = etPhoneRegist.getText().toString().trim();
            if (!Utils.isMobileNO(strphone)) {
                Utils.showToast(RejisterActivity.this, "请输入正确的手机号");
                return false;
            }
        } else {
            Utils.showToast(RejisterActivity.this, "请输入手机号");
            return false;
        }
        return true;
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                tvGetverificationRejecst.setText("剩余（" + Integer.toString(--i) + "）");
                if (i == 0) {
                    handler.removeCallbacks(this);
                    i = 60;
                    tvGetverificationRejecst.setClickable(true);
                    tvGetverificationRejecst.setTextColor(getResources().getColor(R.color.login_btn_background));
                    tvGetverificationRejecst.setText("获取验证码");
                }
                System.out.println("do...");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }

        }
    };


    @OnClick({R.id.iv_back, R.id.tv_getverification_rejecst, R.id.image_isshow_regist, R.id.tv_rejistnow_rejist, R.id.tv_ismember_rejist,R.id.act_register_privacy_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_getverification_rejecst://获取验证码按钮
                    if (i == 60&&setPhoneNum()) {
                        getVerificationCode();
                        handler.postDelayed(runnable, TIME); //每隔1s执行 启动定时器
                    }
                break;
            case R.id.image_isshow_regist:
                if (showPassword) {
                    imageIsshowRegist.setImageDrawable(getResources().getDrawable(R.drawable.deye_lan));
                    etPasswordRegist.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    imageIsshowRegist.setImageDrawable(getResources().getDrawable(R.drawable.deye_hui));
                    etPasswordRegist.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                showPassword = !showPassword;

                break;
            case R.id.tv_rejistnow_rejist:
                    if (dataIsNull()){
                        regist();
                    }
                break;
            case R.id.act_register_privacy_policy://跳转隐私政策

                break;
        }
    }

    private void getVerificationCode() {//获取验证码
        loadingDialog.show();
        verificationUrlMap.put("mobile", strphone);
        verificationUrlMap.put("type","1");
        okHttpUtils.postAsnycData(verificationUrlMap, BaseUrl.SEND_MOBILE_CODE, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
             toastShort("验证码获取失败!");
             loadingDialog.dismiss();
            }
            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                Log.i(TAG,"获取验证码......."+response);
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

    private boolean dataIsNull(){
        if (TextUtils.isEmpty(etPhoneRegist.getText().toString().trim())) {
            Utils.showToast(RejisterActivity.this, "请输正确的手机号码");
            return false;
        }
        if(TextUtils.isEmpty(etVerificationRejest.getText().toString().trim())){
            Utils.showToast(RejisterActivity.this, "验证码不可为空");
            return false;
        }
        if (TextUtils.isEmpty(etPasswordRegist.getText().toString().trim())){
            Utils.showToast(RejisterActivity.this, "密码不可为空");
            return false;
        }
        if(Utils.isRightPwd(etPasswordRegist.getText().toString().trim())){
            Utils.showToast(RejisterActivity.this, "密码含非法字符");
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.unregister();//定位注销
    }
}
