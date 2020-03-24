package com.ruihua.geshuibao.Acivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.widget.FrameLayout;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Fragments.ConsultSearchFragment;
import com.ruihua.geshuibao.Fragments.NewsSeaechFragment;
import com.ruihua.geshuibao.Fragments.PoliciesRegulationsSearchFragment;
import com.ruihua.geshuibao.R;
import butterknife.BindView;
/**
 * 搜索页面（含多个搜索页面碎片）
 * 新闻搜索、咨询搜索、政策法规搜索  碎片
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.act_search_fl)
    FrameLayout actSearchFl;
    public static FragmentManager fragmentManager;
    public static Fragment currentFragment,//默认界面
            newsSeaechFragment,consultSearchFragment,policiesRegulationsSearchFragment;
    @Override
    public int intiLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        if(getIntent()!=null){
            if(!TextUtils.isEmpty(getIntent().getStringExtra("NewsSeaechFragment"))){
                newsSeaechFragment = new NewsSeaechFragment();//新闻搜索界面
                currentFragment = newsSeaechFragment;
            }else if (!TextUtils.isEmpty(getIntent().getStringExtra("ConsultSearchFragment"))){
                consultSearchFragment = new ConsultSearchFragment();//咨询搜索界面
                currentFragment = consultSearchFragment;
            }else if (!TextUtils.isEmpty(getIntent().getStringExtra("PoliciesRegulationsSearchFragment"))){
                policiesRegulationsSearchFragment = new PoliciesRegulationsSearchFragment();//政策法规搜索界面
                currentFragment = policiesRegulationsSearchFragment;
            }
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.act_search_fl, currentFragment);
            transaction.commit();
        }
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
    }

}
