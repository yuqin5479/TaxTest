package com.ruihua.geshuibao.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Acivity.MsgAndNewsDetailsActivity;
import com.ruihua.geshuibao.Acivity.MyIndividualIncomeTax;
import com.ruihua.geshuibao.Acivity.NotifierProPlusActivity;
import com.ruihua.geshuibao.Acivity.PersonalInformationActivity;
import com.ruihua.geshuibao.Acivity.PoliciesRegulationsActivity;
import com.ruihua.geshuibao.Adapter.HomeMessageAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.HomeDataBean;
import com.ruihua.geshuibao.CustomWidget.ScrollTextView;
import com.ruihua.geshuibao.Event.Event_MsgAndNewsDetailsActivity_Id;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;
import com.ruihua.geshuibao.Util.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 首页
 */
@SuppressLint("NewApi")
public class HomeFragment extends BaseFragment implements HomeMessageAdapter.OnItemClickListener, View.OnScrollChangeListener {
    @BindView(R.id.home_srl)
    SwipeRefreshLayout home_srl;
    @BindView(R.id.home_sv_titlebar)
    ScrollView homeSvTitle;
    @BindView(R.id.home_tv_username)
    TextView homeTvUsername;
    @BindView(R.id.home_tv_tel)
    TextView homeTvTel;
    @BindView(R.id.home_tv_complete_infor)
    TextView homeTvCompleteInfor;
    @BindView(R.id.home_tv_bound_company_text)
    TextView homeTvBoundCompanyText;
    @BindView(R.id.home_tv_bound_company)
    TextView homeTvBoundCompany;
    @BindView(R.id.home_iv_header)
    ImageView homeIvHeader;
    @BindView(R.id.home_tv_invite_company)
    TextView homeTvInviteCompany;
    @BindView(R.id.home_tv_roll_msg)
    ScrollTextView homeTvRollMsg;
    @BindView(R.id.home_rl_messages)
    RecyclerView homeRlMsg;
    @BindView(R.id.home_ll_individual_income_tax_query)
    LinearLayout homeLlIndividualIncomeTaxQuery;
    @BindView(R.id.home_ll_policies_regulations)
    LinearLayout homeLlPoliciesRegulations;
    @BindView(R.id.home_iv_pic_invite_company)
    ImageView homeIvPicInviteCompany;
    @BindView(R.id.fragment_home_null_view)
    View fragmentHomeNullView;
    @BindView(R.id.tv_area_home)
    TextView tvAreaHome;
    @BindView(R.id.iv_bell_home)
    ImageView ivBellHome;
    @BindView(R.id.iv_bell_home_hint)
    ImageView ivBellHomeHint;
    @BindView(R.id.view_whlite_line)
    View viewWhliteLine;
    @BindView(R.id.rela_innertop_home)
    RelativeLayout relaInnertopHome;

    private String userId,token;
    private HomeDataBean homeDataBean;
    private HomeMessageAdapter homeMessageAdapter;
    private List<String> scrollTextList = new ArrayList<>();//卡片底部滚动消息集合

    public static String userName,companyName,userHeadUrl="";//用于判断是否已完善信息与加入单位
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setUpData() {
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        home_srl.setColorSchemeResources(R.color.main_color);
        home_srl.setOnRefreshListener(refreshListener);
        home_srl.post(new Runnable() {
            @Override
            public void run() {
                home_srl.setRefreshing(true);
            }
        });
        refreshListener.onRefresh();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            //每次进入界面 自动刷新数据
            home_srl.post(new Runnable() {
                @Override
                public void run() {
                    home_srl.setRefreshing(true);
                }
            });
            refreshListener.onRefresh();
        }
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        public void onRefresh() {
            //TODO
            getHomeDate();
        }
    };
    private void getHomeDate() {
        okHttpUtils.getAsyncData(BaseUrl.MAIN_DATA
                        + "?appUserId=" + userId
                        + "&token=" + token, new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        toastShort("首页信息获取失败");
                        if (home_srl.isRefreshing()) {
                            home_srl.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"首页数据........"+response);
                        homeDataBean = new Gson().fromJson(response,HomeDataBean.class);
//                        loadingDialog.dismiss();
                        if(homeDataBean!=null&&homeDataBean.getErrorcode().equals("0000")){
                            setHomeData();
                        }
                        if (home_srl.isRefreshing()) {
                            home_srl.setRefreshing(false);
                        }
                    }
                }
        );
    }

    private void setHomeData() {
        if(!TextUtils.isEmpty(homeDataBean.getData().getName())){
            homeTvUsername.setText(homeDataBean.getData().getName());
            userName = homeDataBean.getData().getName();
            homeTvCompleteInfor.setVisibility(View.GONE);
        }else {
            homeTvUsername.setHint("您还未完善个人信息");
            homeTvCompleteInfor.setVisibility(View.VISIBLE);
        }
        homeTvTel.setText(homeDataBean.getData().getPhone());
        if(!TextUtils.isEmpty(homeDataBean.getData().getCompanyName())){
            homeTvBoundCompanyText.setText(homeDataBean.getData().getCompanyName());
            companyName = homeDataBean.getData().getCompanyName();
            homeTvBoundCompany.setVisibility(View.GONE);
        }else {
            homeTvBoundCompanyText.setHint("暂未绑定单位");
            homeTvBoundCompany.setVisibility(View.VISIBLE);
        }
        if(homeDataBean.getData().isTotalNotReadMsg()){
            ivBellHomeHint.setVisibility(View.VISIBLE);
        }else ivBellHomeHint.setVisibility(View.GONE);
        Glide.with(this).load(homeDataBean.getData().getPhoto())
                .apply(RequestOptions.bitmapTransform(new CircleCrop())//按圆形图加载
                        .error(R.drawable.home_header)//加载错误图
                        .placeholder(R.drawable.home_header))//占位图
                .into(homeIvHeader);
        userHeadUrl = homeDataBean.getData().getPhoto();
        homeMessageAdapter.setDatas(homeDataBean.getData().getMiddleList());//首页中部 消息展示填充
        //在此开始填充 头部滚动 通知类消息 数据
        if(homeDataBean.getData().getTotalSystemMsgList().size()!=0){
            for (int i=0;i<homeDataBean.getData().getTotalSystemMsgList().size();i++){
                scrollTextList.add(homeDataBean.getData().getTotalSystemMsgList().get(i).getTitle());
            }
            homeTvRollMsg.setTextColor(Color.WHITE);
            homeTvRollMsg.setTextSize(28);
            homeTvRollMsg.setTextContent(scrollTextList);//设置滚动消息内容
            homeTvRollMsg.startTextScroll();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void setUpView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            fragmentHomeNullView.setVisibility(View.VISIBLE);
//        }
        homeSvTitle.setOnScrollChangeListener(this);
        homeMessageAdapter = new HomeMessageAdapter(this.mContext);
        homeRlMsg.setLayoutManager(new LinearLayoutManager(getActivity()));
        homeRlMsg.setAdapter(homeMessageAdapter);
        homeMessageAdapter.setItemClickListener(this);
        homeTvRollMsg.setTextSize(20);//设置首页滚动消息字体大小
    }

    @OnClick({R.id.home_tv_bound_company, R.id.home_iv_header, R.id.home_tv_invite_company,
            R.id.home_tv_roll_msg, R.id.home_ll_individual_income_tax_query,
            R.id.home_ll_policies_regulations, R.id.home_iv_pic_invite_company,
            R.id.tv_area_home, R.id.iv_bell_home,R.id.home_tv_complete_infor})
    public void onViewClicked(View view) {
        //个税宝 邀请 分享地址（带分享userId）
        String shareUrl = "http://gsb-test.sruihua.com:8080/gsb/index.html#/companyApprove?shareId="+userId;
        switch (view.getId()) {
            case R.id.home_tv_bound_company://去绑定
                startActivity(new Intent(getActivity(),JoinCompanyActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.home_tv_complete_infor://去完善
                startActivity(new Intent(getActivity(),PersonalInformationActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.home_iv_header:
                startActivity(new Intent(getActivity(),PersonalInformationActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.home_tv_invite_company://邀请链接 分享
                Utils.shareText("分享到","邀请单位入驻个税宝",shareUrl);
                break;
            case R.id.home_tv_roll_msg:

                break;
            case R.id.home_ll_individual_income_tax_query://查询
                startActivity(new Intent(getActivity(),MyIndividualIncomeTax.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.home_ll_policies_regulations://政策法规
                startActivity(new Intent(getActivity(),PoliciesRegulationsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.home_iv_pic_invite_company://邀请链接 分享
                Utils.shareText("分享到","邀请单位入驻个税宝",shareUrl);
                break;
            case R.id.tv_area_home://城市列表
                break;
            case R.id.iv_bell_home://消息通知
                startActivity(new Intent(getActivity(),NotifierProPlusActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {//中部消息（三种消息）item 点击跳转监听
        EventBus.getDefault().postSticky(
                new Event_MsgAndNewsDetailsActivity_Id(
                        homeDataBean.getData().getMiddleList().get(position).getId(),
                        MsgAndNewsDetailsActivity.DETAILS_TYPE_MSG));
        startActivity(new Intent(getActivity(),MsgAndNewsDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }

    //头部标题栏滑动变色监听
    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (v == homeSvTitle) {
            if (scrollY >= 280) {
                ivBellHome.setImageResource(R.drawable.nav_mass_black);
                tvAreaHome.setTextColor(Color.parseColor("#333333"));
                viewWhliteLine.setVisibility(View.VISIBLE);
                Drawable drawable = getResources().getDrawable(R.mipmap.home_nav_drop_down_black);

                drawable.setBounds(0, 0, drawable.getMinimumWidth() - 14, drawable.getMinimumHeight() - 14);

                tvAreaHome.setCompoundDrawables(null, null, drawable, null);
                relaInnertopHome.setBackgroundColor(getResources().getColor(R.color.white));
                fragmentHomeNullView.setBackgroundColor(getResources().getColor(R.color.text_color_type_gray));
//                Log.e("scrollView: ", x + "   " + y);
            }
            if (scrollY < 320) {
                Drawable drawable = getResources().getDrawable(R.mipmap.home_nav_drop_down_white);
                drawable.setBounds(0, 0, drawable.getMinimumWidth() - 14, drawable.getMinimumHeight() - 14);
                tvAreaHome.setCompoundDrawables(null, null, drawable, null);
                tvAreaHome.setTextColor(Color.parseColor("#ffffff"));
                ivBellHome.setImageResource(R.drawable.m_mass);
                viewWhliteLine.setVisibility(View.INVISIBLE);
                relaInnertopHome.setBackgroundColor(getResources().getColor(R.color.transparent));
                fragmentHomeNullView.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
        }
    }
}
