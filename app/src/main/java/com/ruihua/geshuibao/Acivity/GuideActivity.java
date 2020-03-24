package com.ruihua.geshuibao.Acivity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.ChatMessageService;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.SPUtils;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;

/**
 * 启动页、首次进入引导页
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.act_guide_iv_start)
    ImageView mIvStart;
    @BindView(R.id.banner_guide_content)
    BGABanner bannerGuideContent;
    @BindView(R.id.act_guide_iv_default)
    ImageView IvDefault;
    @BindView(R.id.act_guide_cpv)
    TextView tvGuideCpv;
    @BindView(R.id.rl_act_guide_iv_start)
    RelativeLayout rlActGuideIvStart;
    private boolean isFirstStart = false;
    private String userId;
    private static final long COUNT_DOWN = 5000;//倒计时秒数
    private MyCountDownTimer mCountDownTimer;
    @Override
    public int intiLayout() {
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_guide;
    }

    @Override
    public void initData() {
        isFirstStart = (boolean) SPUtils.get("isFirstStart", true);
        userId = (String) SPUtils.get("userId", "");
    }

    @Override
    public void initView() {

        if (isFirstStart) {//首次启动 启动四页引导图
            rlActGuideIvStart.setVisibility(View.GONE);
            bannerGuideContent.setVisibility(View.VISIBLE);
            // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
            BGALocalImageSize localImageSize = new BGALocalImageSize(720, 1280, 320, 640);
            // 设置数据源
            bannerGuideContent.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                    R.drawable.defoult_1,
                    R.drawable.defoult_2,
                    R.drawable.defoult_3,
                    R.drawable.defoult_4);
            bannerGuideContent.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                @Override
                public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                    if (position == 3) {//  点击最后一页引导 进入app登录
                        startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                        GuideActivity.this.finish();
                    }
                }
            });
            SPUtils.put("isFirstStart", false);//启动过后 修改 首次启动标志
        } else {//非首次启动  启用 一页5秒启动图（含广告位）
            rlActGuideIvStart.setVisibility(View.VISIBLE);
            bannerGuideContent.setVisibility(View.GONE);
            tvGuideCpv.setText("5s 跳过");
            //创建倒计时
            mCountDownTimer = new MyCountDownTimer(COUNT_DOWN +1000, 1000);
            mCountDownTimer.start();
//            mIvStart.setBackgroundResource(R.drawable.guide_pic);
            tvGuideCpv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//点击跳过 按钮
                    startSkip();
                }
            });
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startSkip();
                }
            }, COUNT_DOWN);

        }
    }

    class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        public void onFinish() {
            tvGuideCpv.setText("0s 跳过");
        }
        public void onTick(long millisUntilFinished) {
            tvGuideCpv.setText( millisUntilFinished / 1000 + "s 跳过");
        }
    }
    private void startSkip() {
        if (TextUtils.isEmpty(userId))//未登录 跳转登录 已登录跳转主页
            startActivity(new Intent(GuideActivity.this, LoginActivity.class));
        else{
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
            this.startService(new Intent().setClass(this,ChatMessageService.class));//启动 监听IM消息 服务
        }

        finish();
    }

}
