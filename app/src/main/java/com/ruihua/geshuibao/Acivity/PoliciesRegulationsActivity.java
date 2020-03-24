package com.ruihua.geshuibao.Acivity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Adapter.VpfagmentAdapter;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.PoliciesRegulationsTypeBean;
import com.ruihua.geshuibao.Fragments.PoliciesRegulationsFragment;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 政策法规
 */
public class PoliciesRegulationsActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.iv_head_right)
    ImageView ivHeadRight;
    @BindView(R.id.tab_policies_egulations_title)
    TabLayout mTabTitle;
    @BindView(R.id.vp_policies_egulations)
    ViewPager mViewPager;

    private List<String> mTitle = new ArrayList();
    private List<Fragment> fragments = new ArrayList<Fragment>();
//    private Fragment policiesRegulationsFragment,hotWordAnalysisFragment;
    private PoliciesRegulationsTypeBean policiesRegulationsTypeBean;
    private VpfagmentAdapter vpAdapter;
    @Override
    public int intiLayout() {
        return R.layout.activity_policies_egulations;
    }
    @Override
    public void initData() {
        getTitleDate();
    }

    @Override
    public void initView() {
        tvHeadTitle.setText(getString(R.string.home_policies_regulations));
        ivHeadRight.setVisibility(View.VISIBLE);
        ivHeadRight.setImageResource(R.drawable.search_black);

        VpfagmentAdapter adapter = new VpfagmentAdapter(getSupportFragmentManager(), mTitle, fragments);
        mViewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        mTabTitle.setupWithViewPager(mViewPager);

        mTabTitle.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(mTabTitle, 50, 50);
            }
        });
    }

    private void getTitleDate() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.lawType, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("政策法规类型获取失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"政策法规类型获取....."+response);
                policiesRegulationsTypeBean = new Gson().fromJson(response,PoliciesRegulationsTypeBean.class);
                loadingDialog.dismiss();
                if (policiesRegulationsTypeBean!=null&&policiesRegulationsTypeBean.getErrorcode().equals("0000")){
                    setViewPager();
                }else toastShort(policiesRegulationsTypeBean.getErrormsg());
            }
        });

    }

    private void setViewPager() {
        for (int i=0;i<policiesRegulationsTypeBean.getData().getList().size();i++){
            mTitle.add(policiesRegulationsTypeBean.getData().getList().get(i).getClassifyName());
            fragments.add(new  PoliciesRegulationsFragment(policiesRegulationsTypeBean.getData().getList().get(i).getId()));
        }
        vpAdapter = new VpfagmentAdapter(getSupportFragmentManager(), mTitle, fragments);
        mViewPager.setAdapter(vpAdapter);
        //为TabLayout设置ViewPager
        mTabTitle.setupWithViewPager(mViewPager);
        mTabTitle.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(mTabTitle, 50, 50);
            }
        });//改头部下划线的长度
    }

    @OnClick({R.id.iv_back, R.id.iv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_head_right://政策法规搜索跳转
                //跳转搜索页面
                startActivity(new Intent(this,SearchActivity.class)
                        .putExtra("PoliciesRegulationsSearchFragment","PoliciesRegulationsSearchFragment")
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
    }
}
