package com.ruihua.geshuibao.Acivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Fragments.CompanyAuthenticationFragment;
import com.ruihua.geshuibao.Fragments.CompanyInfoFragment;
import com.ruihua.geshuibao.Fragments.JoinCompanyFragment;
import com.ruihua.geshuibao.Fragments.SearchJoinCompanyFragment;
import com.ruihua.geshuibao.R;
import butterknife.BindView;

/**
 * 加入单位
 */
public class JoinCompanyActivity extends BaseActivity {
    @BindView(R.id.join_company_fl)
    FrameLayout joinCompanyFl;
    public static FragmentManager fragmentManager;
    public static Fragment currentFragment,//默认界面
            joinCompanyFragment, searchJoinCompanyFragment,
            companyInfoFragment, companyAuthenticationFragment;
    @Override
    public int intiLayout() {
        return R.layout.activity_join_company;
    }

    @Override
    public void initView() {
        joinCompanyFragment = new JoinCompanyFragment();
        searchJoinCompanyFragment = new SearchJoinCompanyFragment();
        companyInfoFragment = new CompanyInfoFragment();
        companyAuthenticationFragment = new CompanyAuthenticationFragment();
        currentFragment = joinCompanyFragment;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.join_company_fl, currentFragment);
        transaction.commit();
    }

    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
    }
    //隐藏和显示对应的fragment
    public static void switchContent(Fragment to) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            // 先判断是否被add过
            transaction.hide(currentFragment).add(R.id.join_company_fl, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = to;
    }
}
