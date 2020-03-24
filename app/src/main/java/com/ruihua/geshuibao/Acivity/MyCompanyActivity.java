package com.ruihua.geshuibao.Acivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Fragments.MyCompanyFragment;
import com.ruihua.geshuibao.Fragments.MyCompanyInfoFragment;
import com.ruihua.geshuibao.Fragments.SalaryUnitFragment;
import com.ruihua.geshuibao.R;
import butterknife.BindView;

/**
 * 我的单位
 */
public class MyCompanyActivity extends BaseActivity {
    @BindView(R.id.act_my_company_fl)
    FrameLayout actMyCompanyFl;
    public static FragmentManager fragmentManager;
    public static Fragment currentFragment,myCompanyFragment,myCompanyInfoFragment,salaryUnitFragment;
    @Override
    public int intiLayout() {
        return R.layout.activity_my_company;
    }

    @Override
    public void initView() {
        myCompanyFragment = new MyCompanyFragment();
        myCompanyInfoFragment = new MyCompanyInfoFragment();
        salaryUnitFragment = new SalaryUnitFragment();
        currentFragment = myCompanyFragment;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.act_my_company_fl, currentFragment);
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
            transaction.hide(currentFragment).add(R.id.act_my_company_fl, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = to;
    }
}
