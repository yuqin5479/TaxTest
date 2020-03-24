package com.ruihua.geshuibao.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ruihua.geshuibao.CustomWidget.LoadingDialog;
import com.ruihua.geshuibao.Util.OkHttpUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment基类
 */
public abstract class BaseFragment extends Fragment {
    /***获取TAG的activity名称**/
    protected final String TAG = "yuqin....."+this.getClass().getSimpleName();
    private View mContentView;
    public Context mContext;
    private Unbinder unbinder;
    public OkHttpUtils okHttpUtils;
    public LoadingDialog loadingDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(setLayoutResourceID(),container,false);
        unbinder = ButterKnife.bind(this,mContentView);
        mContext = getContext();
        okHttpUtils = okHttpUtils.newInstance(mContext);
        loadingDialog = new LoadingDialog(mContext);
        init();
        setUpData();
        setUpView();
        return mContentView;
    }

    /**
     * 此方法用于返回Fragment设置ContentView的布局文件资源ID
     *
     * @return 布局文件资源ID
     */
    protected abstract int setLayoutResourceID();

    /**
     * 一些Data的相关操作
     */
    protected abstract void setUpData();
    /**
     * 一些View的相关操作
     */
    protected abstract void setUpView();


    /**
     * 此方法用于初始化成员变量及获取Intent传递过来的数据
     * 注意：这个方法中不能调用所有的View，因为View还没有被初始化，要使用View在initView方法中调用
     */
    protected void init() {}

    public View getContentView() {
        return mContentView;
    }

    public Context getMContext() {
        return mContext;
    }

    /**
     * 显示长toast
     * @param msg
     */
    public void toastLong(String msg){ Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show(); }

    /**
     * 显示短toast
     * @param msg
     */
    public void toastShort(String msg){ Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show(); }


    /**
     * onDestroyView中进行解绑操作
     * 在Fragment 中必须做解绑操作
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
