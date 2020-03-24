package com.ruihua.geshuibao.Acivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Adapter.VpfagmentAdapter;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.SelfHelpClassifyBean;
import com.ruihua.geshuibao.Fragments.HomeFragment;
import com.ruihua.geshuibao.Fragments.SelfHelpContentFragment;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 自助问答  界面
 */
public class SelfHelpActivity extends BaseActivity {
    @BindView(R.id.null_view)
    View nullView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.iv_head_right)
    ImageView ivHeadRight;
    @BindView(R.id.tab_self_help_title)
    TabLayout tabSelfHelpTitle;
    @BindView(R.id.vp_self_help)
    ViewPager vpSelfHelp;
    @BindView(R.id.but_contact_company)
    Button butContactCompany;
    @BindView(R.id.but_contact_service)
    Button butContactService;
    private List<String> mTitle = new ArrayList();
    private List<Fragment> fragments = new ArrayList<>();//具体类型的咨询碎片
    private VpfagmentAdapter vpAdapter;
    private SelfHelpClassifyBean selfHelpClassifyBean;
    @Override
    public int intiLayout() {
        return R.layout.activity_self_help;
    }

    @Override
    public void initView() {
        tvHeadTitle.setText("咨询");
        ivHeadRight.setVisibility(View.VISIBLE);
        ivHeadRight.setImageResource(R.drawable.search_black);
    }

    @Override
    public void initData() {
        getTitleDate();
    }
    private void getTitleDate() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.querySelfClassify, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("新闻类型获取失败");
            }
            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"获取自助问答类型...."+response);
                selfHelpClassifyBean = new Gson().fromJson(response,SelfHelpClassifyBean.class);
                loadingDialog.dismiss();
                if(selfHelpClassifyBean!=null && selfHelpClassifyBean.getErrorcode().equals("0000")){
                    setViewPager();
                }else toastShort(selfHelpClassifyBean.getErrormsg());
            }
        });
    }
    private void setViewPager() {
        for (int i=0;i<selfHelpClassifyBean.getData().getList().size();i++){
            mTitle.add(selfHelpClassifyBean.getData().getList().get(i).getClassify_name());
            fragments.add(new SelfHelpContentFragment(selfHelpClassifyBean.getData().getList().get(i).getId()));//创建自助问答列表碎片时带分类id
        }
        vpAdapter = new VpfagmentAdapter(getSupportFragmentManager(), mTitle, fragments);
        vpSelfHelp.setAdapter(vpAdapter);
        //为TabLayout设置ViewPager
        tabSelfHelpTitle.setupWithViewPager(vpSelfHelp);
        tabSelfHelpTitle.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabSelfHelpTitle, 50, 50);
            }
        });//改头部下划线的长度
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_back, R.id.iv_head_right, R.id.but_contact_company, R.id.but_contact_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_head_right:
                //跳转搜索页面
                startActivity(new Intent(this,SearchActivity.class)
                        .putExtra("ConsultSearchFragment","ConsultSearchFragment")
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.but_contact_company://联系单位按钮
                if(!TextUtils.isEmpty(HomeFragment.companyName)){
                    startActivity(new Intent(this,ChatActivity.class).putExtra("chatType",ChatActivity.CHAT_TYPE_COMPANY).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                }else toastShort("您还未加入单位");
                break;
            case R.id.but_contact_service://平台客服按钮
                startActivity(new Intent(this,ChatActivity.class).putExtra("chatType",ChatActivity.CHAT_TYPE_PLATFOR).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
    }
}
