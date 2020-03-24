package com.ruihua.geshuibao.Acivity;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Base.MyAplication;
import com.ruihua.geshuibao.CustomWidget.IosSwitch;
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
 * 设置
 */
public class SetUpActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.setup_ll_safety)
    LinearLayout mLlSafety;
    @BindView(R.id.setup_switch_new_msg)
    IosSwitch mSwitchNewMsg;
    @BindView(R.id.setup_ll_new_msg)
    LinearLayout mLlNewMsg;
    @BindView(R.id.setu_tv_cache)
    TextView setuTvCache;
    @BindView(R.id.setup_ll_wipe_cache)
    LinearLayout mLlWipeCache;
    @BindView(R.id.setup_ll_about_us)
    LinearLayout mLlAboutUs;
    @BindView(R.id.setup_ll_feedback)
    LinearLayout mLlFeedback;
    @BindView(R.id.setup_but_log_out)
    Button mButLogOut;

    @Override
    public int intiLayout() {
        return R.layout.activity_setup;
    }

    @Override
    public void initView() {
        tvHeadTitle.setText(getString(R.string.mine_set));
        mSwitchNewMsg.setChecked(true);
        try {
            setuTvCache.setText(Utils.getTotalCacheSize(MyAplication.context));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_back, R.id.setup_ll_safety, R.id.setup_ll_new_msg, R.id.setup_ll_wipe_cache, R.id.setup_ll_about_us, R.id.setup_ll_feedback, R.id.setup_but_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.setup_ll_safety:
                break;
            case R.id.setup_ll_new_msg://新消息提醒开关
                break;
            case R.id.setup_ll_wipe_cache://清除缓存
                Utils.clearAllCache(MyAplication.context);
                setuTvCache.setText("0.0M");
                break;
            case R.id.setup_ll_about_us:
                break;
            case R.id.setup_ll_feedback:
                break;
            case R.id.setup_but_log_out:
                LogOut();
                break;
        }
    }

    private void LogOut() {
        loadingDialog.show();
        Map<String,String> logOutData = new HashMap<>();
        logOutData.put("appUserId", (String) SPUtils.get("userId",""));
        logOutData.put("token", (String) SPUtils.get("token",""));
        logOutData.put("IMEI", Utils.getAndroidID());

        okHttpUtils.postAsnycData(logOutData, BaseUrl.LOGOUT, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("登出失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"退出登录...."+response);
                loadingDialog.dismiss();
                try {
                    if(new JSONObject(response).optString("errorcode").equals("0000")){
                        SetUpActivity.this.sendBroadcast(new Intent().setAction("exit_app"));
                        SPUtils.clear();//清除用户所有数据
                        toastShort("退出成功");
                        startActivity(new Intent(SetUpActivity.this,LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                        SetUpActivity.this.finish();
                    }else toastShort(new JSONObject(response).optString("errormsg"));
                } catch (JSONException e) {
                    loadingDialog.dismiss();
                    toastShort("登出异常，请稍后重试");
                    e.printStackTrace();
                }
            }
        });
    }
}
