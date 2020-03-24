package com.ruihua.geshuibao.Acivity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mylhyl.circledialog.CircleDialog;
import com.ruihua.geshuibao.Adapter.VpfagmentAdapter;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.NewsTypeBean;
import com.ruihua.geshuibao.Fragments.MyNewsProficyFragment;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;
import com.ruihua.geshuibao.Util.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 我的收藏  界面
 */
public class MyCollectActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.iv_head_right)
    ImageView ivHeadRight;
    @BindView(R.id.tab_my_news_title)
    TabLayout tabMyNewsTitle;
    @BindView(R.id.vp_my_news)
    ViewPager vpMyNews;

    private List<String> mTitle = new ArrayList();
    private List<Fragment> fragments = new ArrayList<>();//具体类型的新闻碎片
    private NewsTypeBean newsTypeBean;
    private VpfagmentAdapter vpAdapter;
    private String userId, token;
    @Override
    public int intiLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    public void initView() {
        tvHeadTitle.setText(getString(R.string.mine_my_collect));
        ivHeadRight.setVisibility(View.VISIBLE);
        ivHeadRight.setImageResource(R.drawable.nav_del);
    }

    @Override
    public void initData() {
        userId = (String) SPUtils.get("userId", "");
        token = (String) SPUtils.get("token", "");
        getTitleDate();
    }
    private void getTitleDate() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.NEWS_TYPE, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("新闻类型获取失败");
            }
            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"获取新闻类型...."+response);
                newsTypeBean = new Gson().fromJson(response,NewsTypeBean.class);
                loadingDialog.dismiss();
                if(newsTypeBean!=null && newsTypeBean.getErrorcode().equals("0000")){
                    setViewPager();
                }else toastShort(newsTypeBean.getErrormsg());
            }
        });
    }
    private void setViewPager() {
        for (int i=0;i<newsTypeBean.getData().getList().size();i++){
            mTitle.add(newsTypeBean.getData().getList().get(i).getClassifyName());
            fragments.add(new MyNewsProficyFragment(newsTypeBean.getData().getList().get(i).getId()));//创建我的收藏新闻列表碎片时带新闻类型id
        }
        vpAdapter = new VpfagmentAdapter(getSupportFragmentManager(), mTitle, fragments);
        vpMyNews.setAdapter(vpAdapter);
        //为TabLayout设置ViewPager
        tabMyNewsTitle.setupWithViewPager(vpMyNews);
        tabMyNewsTitle.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabMyNewsTitle, 50, 50);
            }
        });//改头部下划线的长度
    }
    @OnClick({R.id.iv_back, R.id.iv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_head_right://清空我的收藏
                showDialog();
                break;
        }
    }

    private void showDialog() {
        new CircleDialog.Builder(this)
                .setTitle("清空")
                .setText("确认要清空所有收藏的新闻吗？")
                .setTextColor(getResources().getColor(R.color.black_text))
                .setCanceledOnTouchOutside(false)//触摸外部关闭，默认 true
                .setCancelable(true)//返回键关闭，默认 true
                .setPositive("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delCollect();
                    }
                })
                .setNegative("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {}
                }).show();
    }

    private void delCollect() {
        loadingDialog.show();
        Map<String,String> delData = new HashMap<>();
        delData.put("appUserId",userId);
        delData.put("token",token);
        okHttpUtils.postAsnycData(delData, BaseUrl.delcollect, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("清空失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                //刷新数据
                mTitle.clear();
                fragments.clear();
                vpMyNews.clearAnimation();
                tabMyNewsTitle.clearAnimation();
                vpAdapter.notifyDataSetChanged();
                getTitleDate();
            }
        });
    }
}
