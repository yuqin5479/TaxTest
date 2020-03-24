package com.ruihua.geshuibao.Acivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Fragments.MessageListFragment;
import com.ruihua.geshuibao.Fragments.NotifierProPlusFragment;
import com.ruihua.geshuibao.R;
import butterknife.BindView;
/**
 * 消息通知
 */
public class NotifierProPlusActivity extends BaseActivity {
    @BindView(R.id.fl_noifierpro_plus)
    FrameLayout flNoifierproPlus;
    public static FragmentManager fragmentManager;
    public static Fragment currentFragment,//默认界面
            notifierProPlusFragment,//全部消息（分类界面）
            messageListFragment;//具体类型消息列表界面

    @Override
    public int intiLayout() {
        return R.layout.activity_notifierpro_plus;
    }
    @Override
    public void initData() {
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initView() {
        notifierProPlusFragment = new NotifierProPlusFragment();
        messageListFragment = new MessageListFragment();
        currentFragment = notifierProPlusFragment;
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_noifierpro_plus, currentFragment);
        transaction.commit();
    }

    //隐藏和显示对应的fragment
    public static void switchContent(Fragment to) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            // 先判断是否被add过
            transaction.hide(currentFragment).add(R.id.fl_noifierpro_plus, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        currentFragment = to;
    }
}
