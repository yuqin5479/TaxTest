package com.ruihua.geshuibao.Acivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Base.ChatMessageService;
import com.ruihua.geshuibao.Bean.LoginBean;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.R;
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
 * 登录界面
 */

public class LoginActivity extends BaseActivity{

    /**
     * 头部
     */
    @BindView(R.id.iv_back)
    ImageView tvBackHead;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.rl_login_pwd)
    RelativeLayout rlLoginPwd;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @BindView(R.id.view_pwd_line)
    View tvPwdLine;

    @BindView(R.id.rl_login_phone)
    RelativeLayout rlLoginPhone;
    @BindView(R.id.tv_login_phone)
    TextView tvLoginPhone;
    @BindView(R.id.view_phone_line)
    View tvPhoneLine;

    @BindView(R.id.ll_left_pwd)
    LinearLayout llLeftPwd;
    @BindView(R.id.ll_right_phone)
    LinearLayout llRightPhone;

    /**
     * 账号密码登录
     */
    @BindView(R.id.et_pwd_login_name)
    ClearEditText etPwdLoginName;
    @BindView(R.id.text_pwd)
    ClearEditText textPwd;
    @BindView(R.id.image_isshow_login)
    ImageView imageIsshowLogin;
    @BindView(R.id.tv_login_btn)
    TextView tv_login_btn;

    /**
     * 手机验证码登录
     */
    @BindView(R.id.et_sms_login_name)
    ClearEditText etSMSLoginName;
    @BindView(R.id.et_sms_login_code)
    ClearEditText etSMSLoginCode;
    @BindView(R.id.tv_phoneCode_btn)
    TextView etPhoneCodeBtn;
    @BindView(R.id.tv_sms_login_btn)
    TextView tvSMSLoginBtn;

    //    act_login_privacy_policy
    @BindView(R.id.act_login_privacy_policy)
    TextView tv_privacy_policy;

    private boolean showPassword = false;
    private String strphone, strPassword;//电话和密码
    private Map<String,String> idLoginData,phoneNuberLoginData,verificationUrlMap;
    private LoginBean loginBean;
    private int i = 60;
    private int TIME = 1000;
    private String imei = "";
    ImageView image;



    @Override
    public int intiLayout() {
        return R.layout.activity_login;
    }


    @Override
    public void initView() {
        tvBackHead.setVisibility(View.GONE);
        tvHeadTitle.setText("登录");
        imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        image = (ImageView) findViewById(R.id.image);
        SharedPreferences shared = getSharedPreferences("user", MODE_PRIVATE);
        String phoneNum = shared.getString("phone", "");
        etPwdLoginName.setText(phoneNum);
        pwdLogin();
        SMSLogin();
    }

    @Override
    public void initData() {

    }


    private void pwdLogin() {
        etPwdLoginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && textPwd.getText().length() > 0) {
                    tv_login_btn.setBackgroundResource(R.drawable.login_click);
                } else {
                    tv_login_btn.setBackgroundResource(R.drawable.login_click_login_gray);
                }
            }
        });


        textPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && etPwdLoginName.getText().length() > 0) {
                    tv_login_btn.setBackgroundResource(R.drawable.login_click);
                } else {
                    tv_login_btn.setBackgroundResource(R.drawable.login_click_login_gray);
                }
            }
        });
    }

    private void SMSLogin() {
        verificationUrlMap = new HashMap<>();
        etSMSLoginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && etSMSLoginCode.getText().length() > 0) {
                    tvSMSLoginBtn.setBackgroundResource(R.drawable.login_click);
                } else {
                    tvSMSLoginBtn.setBackgroundResource(R.drawable.login_click_login_gray);
                }
            }
        });

        etSMSLoginCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && etSMSLoginName.getText().length() > 0) {
                    tvSMSLoginBtn.setBackgroundResource(R.drawable.login_click);
                } else {
                    tvSMSLoginBtn.setBackgroundResource(R.drawable.login_click_login_gray);
                }
            }
        });
    }

    private boolean setSMSNum() {
        if (!TextUtils.isEmpty(etSMSLoginName.getText().toString().trim())) {
            strphone = etSMSLoginName.getText().toString().trim();
            if (!Utils.isMobileNO(strphone)) {
                Utils.showToast(this,"请输入正确的手机号码");
                return false;
            }

        } else {
            Utils.showToast(this,"请输入手机号");
            return false;
        }
        return true;
    }

    public void getSMSCode(String phone) {//获取手机验证码
        loadingDialog.show();
        verificationUrlMap.put("mobile", phone);
        verificationUrlMap.put("type","7");//7 快速登录验证码
        okHttpUtils.postAsnycData(verificationUrlMap, BaseUrl.SEND_MOBILE_CODE, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastShort("验证码获取失败!");
                loadingDialog.dismiss();
            }
            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                Log.i(TAG,"快速登录获取验证码......."+response);
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
                etPhoneCodeBtn.setTextColor(getResources().getColor(R.color.gray_text));
                etPhoneCodeBtn.setText("剩余（" + Integer.toString(--i) + "）");
                if (i == 0) {
                    handler.removeCallbacks(this);
                    i = 60;
                    etPhoneCodeBtn.setClickable(true);
                    etPhoneCodeBtn.setTextColor(getResources().getColor(R.color.login_btn_background));
                    etPhoneCodeBtn.setText("获取验证码");
                }
                System.out.println("do...");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }

        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @OnClick({R.id.image_isshow_login, R.id.tv_forget_password_login, R.id.tv_quick_register_login,
            R.id.tv_login_btn, R.id.rl_login_pwd, R.id.rl_login_phone, R.id.tv_phoneCode_btn, R.id.tv_sms_login_btn,R.id.act_login_privacy_policy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_login_pwd:
                tvLoginPwd.setTextColor(getResources().getColor(R.color.text_color_rb_press));
                tvPwdLine.setVisibility(View.VISIBLE);
                tvLoginPhone.setTextColor(getResources().getColor(R.color.black));
                tvPhoneLine.setVisibility(View.INVISIBLE);
                llLeftPwd.setVisibility(View.VISIBLE);
                llRightPhone.setVisibility(View.GONE);
                break;
            case R.id.rl_login_phone:
                tvLoginPwd.setTextColor(getResources().getColor(R.color.black));
                tvPwdLine.setVisibility(View.INVISIBLE);
                tvLoginPhone.setTextColor(getResources().getColor(R.color.text_color_rb_press));
                tvPhoneLine.setVisibility(View.VISIBLE);
                llLeftPwd.setVisibility(View.GONE);
                llRightPhone.setVisibility(View.VISIBLE);
                break;

            case R.id.image_isshow_login:
                if (showPassword) {
                    imageIsshowLogin.setImageDrawable(getResources().getDrawable(R.drawable.deye_lan));
                    textPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    imageIsshowLogin.setImageDrawable(getResources().getDrawable(R.drawable.deye_hui));
                    textPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                showPassword = !showPassword;
                break;
            case R.id.tv_forget_password_login:
                //忘记密码  找回密码
                Intent intentForget = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intentForget);
                break;
            case R.id.tv_quick_register_login:
                Intent intentRejist = new Intent(LoginActivity.this, RejisterActivity.class);
                startActivity(intentRejist);
                break;

            case R.id.tv_login_btn:
                if (checkLogin()) {//账密登录
                    idLoginData = new HashMap<>();
                    idLoginData.put("userName",strphone);
                    idLoginData.put("password",strPassword);
                    idLoginData.put("IMEI",Utils.getAndroidID());
                    idLoginData.put("type","2");//1 ios 2安卓
                    Login(idLoginData);
                }
                break;

            case R.id.tv_phoneCode_btn://获取验证码
                    if (i == 60&&setSMSNum()) {
                        handler.postDelayed(runnable, TIME); //每隔1s执行 启动定时器
                        getSMSCode(etSMSLoginName.getText().toString().trim());
                    }

                break;

            case R.id.tv_sms_login_btn:
                if(checkLogin2()){//验证码登录
                    phoneNuberLoginData = new HashMap<>();
                    phoneNuberLoginData.put("mobile",strphone);
                    phoneNuberLoginData.put("code",etSMSLoginCode.getText().toString().trim());
                    phoneNuberLoginData.put("type","2");
                    phoneNuberLoginData.put("IMEI",Utils.getAndroidID());
                    Login(phoneNuberLoginData);
                }
                break;

            case R.id.act_login_privacy_policy:

                break;
        }
    }



    private void Login(final Map loginData) {
        loadingDialog.show();
        okHttpUtils.postAsnycData(loginData, loginData.equals(idLoginData)?BaseUrl.LOGIN:BaseUrl.LOGIN_QUICKLY, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("登录失败，请稍后重试");
            }

            @Override
            public void onSucces(Call call, String response) {
                    loginBean = new Gson().fromJson(response,LoginBean.class);
                    loadingDialog.dismiss();
                    if (loginBean!=null&&loginBean.getErrorcode().equals("0000")){
                        saveUserInfor();
                        toastShort(loginBean.getErrormsg());
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        LoginActivity.this.startService(new Intent().setClass(LoginActivity.this,ChatMessageService.class));//启动 监听IM消息 服务
                        LoginActivity.this.finish();
                    }else toastShort(loginBean.getErrormsg());
                    Log.i(TAG,"登录............."+response);
            }
        });
    }

    private void saveUserInfor() {
        SPUtils.put("mobile",loginBean.getData().getMobile());
        SPUtils.put("userName",loginBean.getData().getUserName());
        SPUtils.put("userId",loginBean.getData().getId());
        SPUtils.put("token",loginBean.getData().getToken());
        SPUtils.put("taxpayerNo",loginBean.getData().getTaxpayerNo());
        SPUtils.put("companyId",loginBean.getData().getCompanyId());
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean checkLogin() {
        if (!TextUtils.isEmpty(etPwdLoginName.getText().toString().trim())
                &&Utils.isMobileNO(etPwdLoginName.getText().toString().trim())) {
            strphone = etPwdLoginName.getText().toString().trim();
        } else {
            toastShort("请输入正确的手机号");
            return false;
        }
        if (!TextUtils.isEmpty(textPwd.getText().toString().trim())) {
            if (textPwd.getText().toString().length() > 5) {
                strPassword = textPwd.getText().toString().trim();
            } else {
                toastShort("密码不小于六位数");// TODO 密码是否必须大于六位
                return false;
            }
        } else {
            toastShort("密码不能为空");
            return false;
        }

        return true;
    }
    private boolean checkLogin2() {
        if(TextUtils.isEmpty(etSMSLoginName.getText().toString().trim())
                &&Utils.isMobileNO(etSMSLoginName.getText().toString().trim())){
            toastShort("请输入正确的手机号");
            return false;
        }
        if(TextUtils.isEmpty(etSMSLoginCode.getText().toString().trim())){
            toastShort("验证码不可为空");
            return false;
        }
        return true;
    }

}

