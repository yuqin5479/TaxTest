package com.ruihua.geshuibao.Acivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.CustomWidget.MoreWindow;
import com.ruihua.geshuibao.CustomWidget.MyRadioButton;
import com.ruihua.geshuibao.Fragments.ConsultFragment;
import com.ruihua.geshuibao.Fragments.HomeFragment;
import com.ruihua.geshuibao.Fragments.MineFragment;
import com.ruihua.geshuibao.Fragments.NewsFragment;
import com.ruihua.geshuibao.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.act_main_rl)
    RelativeLayout mMain_rl;
    @BindView(R.id.act_main_fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.rb_home)
    MyRadioButton mRbHome;
    @BindView(R.id.rb_consult)
    MyRadioButton mRbConsult;
    @BindView(R.id.rb_news)
    MyRadioButton mRbNews;
    @BindView(R.id.rb_mine)
    MyRadioButton mRbMine;
    @BindView(R.id.iv_declare)
    ImageView mIvDeclare;
    @BindView(R.id.tv_declare)
    TextView mTvDeclare;
    @BindView(R.id.mian_ll_declare)
    LinearLayout mLlDeclare;

    private FragmentManager manager;
    private Fragment currentFragment, homeFragmen,consultFragment,newsFragment,mineFragmen;
    private MoreWindow mMoreWindow;
    private long mExitTime;//声明一个long类型变量：用于存放上一点击“返回键”的时刻
    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        manager = getSupportFragmentManager();
        homeFragmen = new HomeFragment();
        consultFragment = new ConsultFragment();
        newsFragment = new NewsFragment();
        mineFragmen = new MineFragment();
        currentFragment = homeFragmen;
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.act_main_fl_container, currentFragment);
        transaction.commit();
//        Utils.setStatusBarTranslucent(this);//状态栏透明
        mMoreWindow = new MoreWindow(this);
        mMoreWindow.init(mMain_rl);
    }

    @Override
    public void initData() {

    }
    @OnClick({ R.id.rb_home,R.id.rb_consult,R.id.rb_news,R.id.rb_mine,R.id.mian_ll_declare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_home://首页
                switchContent(homeFragmen);
                break;
            case R.id.rb_consult://咨询
                switchContent(consultFragment);
                break;
            case R.id.mian_ll_declare://申报
                //弹出带动画的 申报菜单 弹窗
                mMoreWindow.showMoreWindow(mMain_rl );
                break;
            case R.id.rb_news://新闻
                switchContent(newsFragment);
                break;
            case R.id.rb_mine://我的
                switchContent(mineFragmen);
                break;
        }
    }
    //隐藏和显示对应的fragment
    public void switchContent(Fragment to) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!to.isAdded()) {
            // 先判断是否被add过
            transaction.hide(currentFragment).add(R.id.act_main_fl_container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = to;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                toastShort(getString(R.string.exit_msg));
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
