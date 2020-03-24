package com.ruihua.geshuibao.Base;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.ruihua.geshuibao.CustomWidget.LoadingDialog;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    /***获取TAG的activity名称**/
    protected final String TAG = "yu....."+this.getClass().getSimpleName();
    public static Context context;//需要使用的上下文对象
    public OkHttpUtils okHttpUtils;
    public LoadingDialog loadingDialog;
    private MyReceiver receiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        okHttpUtils = okHttpUtils.newInstance(this);
        loadingDialog = new LoadingDialog(this);
        //设置布局
        setContentView(intiLayout());
        context = this;
        ButterKnife.bind(this);
        //设置数据
        initData();
        //初始化控件
        initView();
        registerBroadcast();
    }
    private void registerBroadcast() {
        // 注册广播接收者
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("exit_app");
        context.registerReceiver(receiver,filter);
    }
    /**
     * 自定义广播    用来退出app
     */
    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("exit_app")){
                finish();
            }
        }
    }
    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();

    /**
     * 显示长toast
     * @param msg
     */
    public void toastLong(String msg){ Toast.makeText(this, msg, Toast.LENGTH_LONG).show(); }

    /**
     * 显示短toast
     * @param msg
     */
    public void toastShort(String msg){ Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
