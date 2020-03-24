package com.ruihua.geshuibao.Acivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Fragments.CommercialHealthInsuranceFragment;
import com.ruihua.geshuibao.Fragments.MonthlySpecialExpenseDeductionFragment;
import com.ruihua.geshuibao.Fragments.SpecialExpenseDeductionDetailsFragment;
import com.ruihua.geshuibao.R;
import butterknife.BindView;
/**
 * 月度专项附加扣除及其他扣除上传
 */
public class MonthlySpecialExpenseDeductionActivity extends BaseActivity {
    @BindView(R.id.fl_details)
    FrameLayout mFlDetails;
    public static FragmentManager fragmentManager;
    public static Fragment currentFragment,//默认界面
            monthlySpecialExpenseDeductionFragment,//专项附加扣除项界面
            specialExpenseDeductionDetailsFragment,//专项附加扣除项编辑详情界面
            commercialHealthInsuranceFragment;//专项附加扣除项编辑详情界面（商业健康保险  特殊类型碎片）

    @Override
    public int intiLayout() {
        return R.layout.activity_monthly_special_expense_deduction;
    }

    @Override
    public void initView() {
        monthlySpecialExpenseDeductionFragment = new MonthlySpecialExpenseDeductionFragment();
        specialExpenseDeductionDetailsFragment = new SpecialExpenseDeductionDetailsFragment();
        commercialHealthInsuranceFragment = new CommercialHealthInsuranceFragment();
        currentFragment = monthlySpecialExpenseDeductionFragment;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_details, currentFragment);
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
            transaction.hide(currentFragment).add(R.id.fl_details, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = to;
    }

}
