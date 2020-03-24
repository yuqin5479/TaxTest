package com.ruihua.geshuibao.Fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ruihua.geshuibao.R;

/**
 * 全局使用 加载中... 弹窗
 */
public class LoadingDialogFragment  extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //去掉默认的title
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉白色边角
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view = inflater.inflate(R.layout.loading_dialog, container);
        return view;
    }
}
