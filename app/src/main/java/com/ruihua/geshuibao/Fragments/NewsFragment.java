package com.ruihua.geshuibao.Fragments;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.SearchActivity;
import com.ruihua.geshuibao.Adapter.VpfagmentAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.NewsTypeBean;
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
 * 新闻资讯
 */
public class NewsFragment extends BaseFragment {
    @BindView(R.id.null_view)
    View nullView;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.iv_head_right)
    ImageView ivHeadRight;
    @BindView(R.id.tab_news_title)
    TabLayout tabNewsTitle;
    @BindView(R.id.vp_news)
    ViewPager vpNews;

    private List<String> mTitle = new ArrayList();
    private List<Fragment> fragments = new ArrayList<>();//具体类型的新闻碎片
    private NewsTypeBean newsTypeBean;
    private VpfagmentAdapter vpAdapter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_news;
    }

    @Override
    protected void setUpData() {
        getTitleDate();
    }

    @Override
    protected void setUpView() {
        tvHeadTitle.setText(getString(R.string.bottom_bar_news));
        ivBack.setVisibility(View.GONE);
        ivHeadRight.setVisibility(View.VISIBLE);
        ivHeadRight.setImageResource(R.drawable.search_black);
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
            fragments.add(new NewsProficyFragment(newsTypeBean.getData().getList().get(i).getId()));//创建新闻列表碎片时带新闻类型id
        }
        vpAdapter = new VpfagmentAdapter(getFragmentManager(), mTitle, fragments);
        vpNews.setAdapter(vpAdapter);
        //为TabLayout设置ViewPager
        tabNewsTitle.setupWithViewPager(vpNews);
        tabNewsTitle.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabNewsTitle, 50, 50);
            }
        });//改头部下划线的长度
    }

    @OnClick({ R.id.iv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_right://新闻搜索按钮
                //跳转搜索页面
                startActivity(new Intent(getActivity(),SearchActivity.class)
                        .putExtra("NewsSeaechFragment","NewsSeaechFragment")
                        .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
    }
}
