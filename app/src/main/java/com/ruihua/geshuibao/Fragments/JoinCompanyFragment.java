package com.ruihua.geshuibao.Fragments;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Event.Event_CompanyInforFragment_CompanyId;
import com.ruihua.geshuibao.R;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * 加入企业 碎片
 */
public class JoinCompanyFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_scan)
    RelativeLayout rlScan;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.tv_applyfor_create_company)
    TextView tvApplyforCreateCompany;

    private int REQUEST_CODE_SCAN = 1;//扫码回传标志

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_join_company;
    }
    @Override
    protected void setUpData() { }
    @Override
    protected void setUpView() { }

    @OnClick({R.id.iv_back,R.id.rl_scan, R.id.rl_search, R.id.tv_applyfor_create_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back://返回
                getActivity().onBackPressed();
                break;
            case R.id.rl_scan://扫一扫加入单位
                Intent intent = new Intent(mContext, CaptureActivity.class);
                ZxingConfig config = new ZxingConfig();
                config.setDecodeBarCode(false);//是否扫描条形码 默认为true
                config.setReactColor(R.color.main_color);//设置扫描框四个角的颜色 默认为白色
                config.setScanLineColor(R.color.main_color);//设置扫描线的颜色 默认白色
                intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);//跳转扫码界面
                break;
            case R.id.rl_search://查询加入单位
                JoinCompanyActivity.switchContent(JoinCompanyActivity.searchJoinCompanyFragment);
                break;
            case R.id.tv_applyfor_create_company://申请创建单位
                //跳转单位认证界面
                JoinCompanyActivity.switchContent(JoinCompanyActivity.companyAuthenticationFragment);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Log.i(TAG,"扫一扫加入单位二维码.........."+content);
                // 二维码格式：826b6192c9184d7b8dde144df661c9d5,无锡港湾,1,管理员
                //传单位id跳转单位详情页面
                EventBus.getDefault().postSticky(new Event_CompanyInforFragment_CompanyId(content.split(",")[0]));
                JoinCompanyActivity.switchContent(JoinCompanyActivity.companyInfoFragment);
            }
        }
    }
}
