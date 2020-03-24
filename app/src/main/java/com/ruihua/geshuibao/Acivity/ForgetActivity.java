package com.ruihua.geshuibao.Acivity;

import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.ResetPasswordBean;
import com.ruihua.geshuibao.CustomWidget.ClearEditText;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import butterknife.OnClick;
import okhttp3.Call;

import com.ruihua.geshuibao.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 忘记密码
 * Created by yuqin on 2018/10/22.
 */

public class ForgetActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBackHeadtitle;
    @BindView(R.id.tv_head_title)
    TextView tvTitleHeadtitle;
    @BindView(R.id.et_phone_forget)
    ClearEditText etPhoneForget;
    @BindView(R.id.tv_strverification_forget)
    TextView tvStrverificationForget;
    @BindView(R.id.et_verification_forget)
    ClearEditText etVerificationForget;
    @BindView(R.id.tv_getverification_forget)
    TextView tvGetverificationForget;
    @BindView(R.id.tv_strpassword_forget)
    TextView tvStrpasswordForget;
    @BindView(R.id.image_isshow_forget)
    ImageView imageIsshowForget;
    @BindView(R.id.et_password_forget)
    ClearEditText etPasswordForget;
    @BindView(R.id.tv_strensurepassword_forget)
    TextView tvStrensurepasswordForget;
    @BindView(R.id.et_ensurepassword_forget)
    ClearEditText etEnsurepasswordForget;
    @BindView(R.id.tv_commit_forget)
    TextView tvCommitForget;
    private String strphone;
    private Map<String, String> verificationUrlMap;
    private Map<String, String> retrievePassswordUrlMap;//
    private boolean showPassword = false;
    private ResetPasswordBean resetPasswordBean;
    //时间
    private int i = 60;
    private int TIME = 1000;
    private String newPassword,ensurePassword;//新密码和确认新密码

@Override
public int intiLayout() { return R.layout.activity_forget; }

    @Override
    public void initView() {
        tvTitleHeadtitle.setText("忘记密码");
    }

    @Override
    public void initData() {
        verificationUrlMap = new HashMap<>();
        retrievePassswordUrlMap = new HashMap<>();
    }

    @OnClick({R.id.iv_back, R.id.tv_getverification_forget, R.id.image_isshow_forget, R.id.tv_commit_forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_getverification_forget:
                if (i == 60) {
                    handler.postDelayed(runnable, TIME); //每隔1s执行 启动定时器
                    if (checkPhoneNum()) {
                        //获取验证码
                        getVerification();
                    } else {
                        handler.removeCallbacks(runnable);
                        i = 60;
                        tvGetverificationForget.setText("获取验证码");
                    }
                }
                break;
            case R.id.image_isshow_forget:

                if (showPassword) {

                    imageIsshowForget.setImageDrawable(getResources().getDrawable(R.drawable.deye_lan));
                    etPasswordForget.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {

                    imageIsshowForget.setImageDrawable(getResources().getDrawable(R.drawable.deye_hui));
                    etPasswordForget.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                showPassword = !showPassword;
                break;
            case R.id.tv_commit_forget:
                //检查是否信息并提交
                if(checketdit()){
                    resetPassword();
                }
                break;
        }
    }

    private void resetPassword() {//执行重置密码
        loadingDialog.show();
        retrievePassswordUrlMap.clear();
        retrievePassswordUrlMap.put("mobile",strphone);
        retrievePassswordUrlMap.put("newPassword",newPassword);
        retrievePassswordUrlMap.put("code",etVerificationForget.getText().toString().trim());
        okHttpUtils.postAsnycData(retrievePassswordUrlMap, BaseUrl.RETRIEVE_PASSWORD, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("重置密码失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"重置密码........."+response);
                resetPasswordBean = new Gson().fromJson(response,ResetPasswordBean.class);
                loadingDialog.dismiss();
                if(resetPasswordBean!=null && resetPasswordBean.getErrorcode().equals("0000")){
                    toastShort(resetPasswordBean.getErrormsg());
                    ForgetActivity.this.finish();
                }else toastShort(resetPasswordBean.getErrormsg());
            }
        });

    }

    private void getVerification() {
        loadingDialog.show();
        verificationUrlMap.clear();
        verificationUrlMap.put("mobile", strphone);
        verificationUrlMap.put("type","2");//找回密码验证码 type = 2
        okHttpUtils.postAsnycData(verificationUrlMap, BaseUrl.SEND_MOBILE_CODE, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastShort("验证码获取失败!");
                loadingDialog.dismiss();
            }
            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                Log.i(TAG,"忘记密码获取验证码......."+response);
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


    private boolean checketdit() {
        newPassword = etPasswordForget.getText().toString().trim();
        ensurePassword = etEnsurepasswordForget.getText().toString().trim();
        if (!TextUtils.isEmpty(newPassword)) {
            if (newPassword.length() < 6) {
                Utils.showToast(ForgetActivity.this, "请输入大于六位的密码");
                return false;
            }
        } else {
            Utils.showToast(ForgetActivity.this, "新密码不能为空");
            return false;
        }
        if (!TextUtils.isEmpty(ensurePassword)) {
            if (ensurePassword.length() < 6) {
                Utils.showToast(ForgetActivity.this, "请输入大于六位的密码");
                return false;
            }
        } else {
            Utils.showToast(ForgetActivity.this, "确认密码不能为空");
            return false;
        }
        if (newPassword.equals(ensurePassword)){
            return true;
        }else {
            Utils.showToast(ForgetActivity.this, "两次密码不一致");
            return false;
        }

    }

    private boolean checkPhoneNum() {
        strphone = etPhoneForget.getText().toString().trim();
        if (!TextUtils.isEmpty(strphone)) {
            if (!Utils.isMobileNO(strphone)) {
                Utils.showToast(ForgetActivity.this, "请输入正确的手机号码");
                return false;
            }
        } else {
            Utils.showToast(ForgetActivity.this, "请输入手机号");
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
                tvGetverificationForget.setClickable(false);
                tvGetverificationForget.setTextColor(getResources().getColor(R.color.gray_text));
                tvGetverificationForget.setText("剩余（" + Integer.toString(--i) + "）");
                if (i == 0) {
                    handler.removeCallbacks(this);
                    i = 60;
                    tvGetverificationForget.setClickable(true);
                    tvGetverificationForget.setTextColor(getResources().getColor(R.color.main_color));
                    tvGetverificationForget.setText("获取验证码");
                }
                System.out.println("do...");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("exception...");
            }
        }
    };


}
