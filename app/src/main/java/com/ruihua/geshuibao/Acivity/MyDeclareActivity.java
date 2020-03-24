package com.ruihua.geshuibao.Acivity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ruihua.geshuibao.Adapter.VpfagmentAdapter;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Fragments.MySpecialExpenseDeductionFragment;
import com.ruihua.geshuibao.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的申报
 */
public class MyDeclareActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.my_declare_tab_title)
    TabLayout myDeclareTabTitle;
    @BindView(R.id.my_declare_date)
    TextView myDeclareDate;
    @BindView(R.id.my_declare_vp)
    ViewPager myDeclareVp;
    private TimePickerView pvTime;//日期选择器
    private List<String> mTitle = new ArrayList();
    private List<Fragment> fragments = new ArrayList();
    DateFormat format = new SimpleDateFormat("yyyy年MM月");
    private MySpecialExpenseDeductionFragment mySpecialExpenseDeductionFragment;
    @Override
    public int intiLayout() {
        return R.layout.activity_my_declare;
    }

    @Override
    public void initView() {
        tvHeadTitle.setText(getString(R.string.mine_my_declare));
        VpfagmentAdapter adapter = new VpfagmentAdapter(this.getSupportFragmentManager(), mTitle, fragments);
        myDeclareVp.setAdapter(adapter);
        myDeclareTabTitle.setupWithViewPager(myDeclareVp);
        myDeclareDate.setText(format.format(new Date(System.currentTimeMillis())));
    }

    @Override
    public void initData() {
        pvTime = new TimePickerBuilder(this,onTimeSetListener)
                //只显示年月  两个
                .setType(new boolean[]{true, true, false, false, false, false}).build();
        mTitle.add("专项附加扣除");
        mySpecialExpenseDeductionFragment = new MySpecialExpenseDeductionFragment();
        fragments.add(mySpecialExpenseDeductionFragment);
    }

    OnTimeSelectListener onTimeSetListener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            myDeclareDate.setText(format.format(date));
            mySpecialExpenseDeductionFragment.getData(new SimpleDateFormat("yyyy-MM").format(date));//选择年份 刷新数据
        }
    };

    @OnClick({R.id.iv_back, R.id.my_declare_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.my_declare_date://弹出年月选框
                pvTime.show();
                break;
        }
    }
}
