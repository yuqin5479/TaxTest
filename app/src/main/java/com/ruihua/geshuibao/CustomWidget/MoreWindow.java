package com.ruihua.geshuibao.CustomWidget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.ms_square.etsyblur.BlurringView;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.view.listener.OnCreateBodyViewListener;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Acivity.MonthlySpecialExpenseDeductionActivity;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.UpdatePersonalInformationBean;
import com.ruihua.geshuibao.Fragments.HomeFragment;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * 首页home键弹窗菜单
 */
public class MoreWindow extends PopupWindow implements OnClickListener {

    private BaseActivity mContext;
    private RelativeLayout layout;
    private ImageView close;
    private View bgView;
    private BlurringView blurringView;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Handler mHandler = new Handler();
    private DialogFragment dialog;
    String userName,nsId;
    EditText etUserName,etNsId;
    public MoreWindow(BaseActivity context) {
        mContext = context;
    }
    private int menuType = 1;//菜单跳转类型
    /**
     * 初始化
     * @param view 要显示的模糊背景View,选择跟布局layout
     */
    public void init(View view) {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);

        layout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.more_window, null);

        setContentView(layout);
        close = (ImageView) layout.findViewById(R.id.iv_close);
        close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    closeAnimation();
                }
            }
        });

        blurringView = (BlurringView) layout.findViewById(R.id.blurring_view);
        blurringView.blurredView(view);//模糊背景

        bgView = layout.findViewById(R.id.rel);
        setOutsideTouchable(true);
        setFocusable(true);
        setClippingEnabled(false);//使popupwindow全屏显示
    }

    public int getNavigationBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        //获取NavigationBar的高度
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 显示window动画
     *
     * @param anchor
     */
    public void showMoreWindow(View anchor) {

        showAtLocation(anchor, Gravity.TOP | Gravity.START, 0, 0);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //圆形扩展的动画
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        int x = mWidth / 2;
                        int y = (int) (mHeight - fromDpToPx(25));
                        Animator animator = ViewAnimationUtils.createCircularReveal(bgView, x,
                                y, 0, bgView.getHeight());
                        animator.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationStart(Animator animation) {
//                                bgView.setVisibility(View.VISIBLE);
                            }
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                //							layout.setVisibility(View.VISIBLE);
                            }
                        });
                        animator.setDuration(300);
                        animator.start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        showAnimation(layout);
    }

    private void showAnimation(ViewGroup layout) {
        try {
            LinearLayout linearLayout = layout.findViewById(R.id.lin);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //＋ 旋转动画
                    close.animate().rotation(90).setDuration(400);
                }
            });
            //菜单项弹出动画
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                final View child = linearLayout.getChildAt(i);
                child.setOnClickListener(this);
                child.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        child.setVisibility(View.VISIBLE);
                        ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                        fadeAnim.setDuration(200);
                        KickBackAnimator kickAnimator = new KickBackAnimator();
                        kickAnimator.setDuration(150);
                        fadeAnim.setEvaluator(kickAnimator);
                        fadeAnim.start();
                    }
                }, i * 50 + 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 关闭window动画
     */
    private void closeAnimation() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                close.animate().rotation(-90).setDuration(400);
            }
        });
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {

                int x = mWidth / 2;
                int y = (int) (mHeight - fromDpToPx(25));
                Animator animator = ViewAnimationUtils.createCircularReveal(bgView, x,
                        y, bgView.getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
//						  layout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        bgView.setVisibility(View.GONE);
                        dismiss();
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        } catch (Exception e) {
        }
    }

    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (isShowing()) {
            closeAnimation();
        }

        switch (v.getId()) {
            case R.id.tv_ietm1:

                /**
                 * 根据新出需求  在进入申报前
                 *  1先判断个人信息是否完善, (未完善去完善信息,完善信息后进行2,已完善进行2)
                    2再判断是否加入单位(未加入去加入,已加入进行3)
                    3再判断是否单位采购(目前是全部已采购)
                    4 展示协议 (同意后下次不在展示此协议)  只展示一次
                 */

                    if(TextUtils.isEmpty(HomeFragment.userName)){
                        //请完善个人信息 填写窗口
                        showGetInforDialog();
                        return;
                    }
                    if(TextUtils.isEmpty(HomeFragment.companyName)){
                        showDialog("您还未加入单位","去加入");
                        menuType = 1;//跳转 加入单位界面的 标志
                        return;
                    }
                    isSurchase();//单位是否采购
                break;
//            case R.id.tv_ietm2:
//                Utils.showToast(mContext,"月度其他扣除上传");
//                Utils.showToast(mContext,"该功能暂未完成，请等待");
//                break;
            case R.id.tv_ietm3:
                menuType = 3;
                showDialog("该功能暂未开通","好的");
                break;
            case R.id.tv_ietm4:
                menuType = 4;
                showDialog("该功能暂未开通","好的");
                break;
            case R.id.tv_ietm5:
                menuType = 5;
                showDialog("该功能暂未开通","好的");
                break;
        }
    }

    private void showGetInforDialog() {
        //弹窗 完善个人信息
        new CircleDialog.Builder(mContext)
                .setBodyView(R.layout.dialog_get_infor, new OnCreateBodyViewListener() {
                    @Override
                    public void onCreateBodyView(View view) {
                        etUserName = view.findViewById(R.id.et_username);
                        etNsId = view.findViewById(R.id.et_ns_id);
                    }
                }).setPositive("保存", new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etUserName.getText().toString().trim())&&
                   !TextUtils.isEmpty(etNsId.getText().toString().trim())){
                    userName = etUserName.getText().toString().trim();
                    nsId = etNsId.getText().toString().trim();
                    saveInfor();
                }
            }
        }).setTitle("请完善个人信息").show();

    }

    private void saveInfor() {
        //上传保存  个人信息
        mContext.loadingDialog.show();
        Map<String,String> infoDta = new HashMap<>();
        infoDta.put("appUserId",(String) SPUtils.get("userId", ""));
        infoDta.put("token",(String) SPUtils.get("token", ""));
        infoDta.put("taxpayerNo",nsId);
        infoDta.put("name",userName);
        mContext.okHttpUtils.postAsnycData(infoDta, BaseUrl.insertAppUserInfoMap, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.loadingDialog.dismiss();
                mContext.toastShort("完善个人信息失败");
            }
            @Override
            public void onSucces(Call call, String response) {
                Log.i("yu","个人信息完善.........."+response);
                UpdatePersonalInformationBean bean = new Gson().fromJson(response,UpdatePersonalInformationBean.class);
                mContext.loadingDialog.dismiss();
                if (bean!=null&&bean.getErrorcode().equals("0000")) {
                    mContext.toastShort(bean.getErrormsg());
                    HomeFragment.userName = bean.getData().getUserName();
                }else  mContext.toastShort(bean.getErrormsg());

            }
        });
    }

    private void isSurchase() {
        //获取是否单位已采购 结果
        mContext.loadingDialog.show();
        mContext.okHttpUtils.getAsyncData(BaseUrl.isPurchase +
                "?appUserId=" + (String) SPUtils.get("userId", "") +
                "&token=" + (String) SPUtils.get("token", ""), new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                mContext.loadingDialog.dismiss();
                mContext.toastShort("获取单位采购失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                mContext.loadingDialog.dismiss();
                try {
                    if(new JSONObject(response).getJSONObject("data").getString("status").equals("1")){
                        //单位已采购 展示协议
                        if(!(boolean)SPUtils.get("sign_an_agreement",false)){
                            showAgreement();
                            //已同意过协议 直接跳转
                        }else mContext.startActivity(new Intent(mContext,MonthlySpecialExpenseDeductionActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                    }else {
                        //单位未采购  提示
                        showDialog("该单位暂未采购","通知单位采购");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAgreement() {
        //弹窗展示服务协议
        final Dialog bgSetDialog = new Dialog(mContext, R.style.BottomDialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_xieyi, null);
        WebView webView = view.findViewById(R.id.wb_xieyi);
        Button button = view.findViewById(R.id.but_xieyi);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bgSetDialog.dismiss();
                SPUtils.put("sign_an_agreement",true);//存下 同意协议的标志
                mContext.startActivity(new Intent(mContext,MonthlySpecialExpenseDeductionActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
        webView.loadUrl(BaseUrl.xieyiUrl);
        //将布局设置给Dialog
        bgSetDialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = bgSetDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        bgSetDialog.show();//显示对话框
        //设置宽度全屏
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.gravity=Gravity.BOTTOM;
        layoutParams.width= LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height= LinearLayout.LayoutParams.WRAP_CONTENT;
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setAttributes(layoutParams);
    }

    public void showDialog(String text,String butText){
        dialog = new CircleDialog.Builder(mContext)
                .setText(text)//内容
                .setCanceledOnTouchOutside(false)//触摸外部关闭，默认true
                .setTextColor(mContext.getResources().getColor(R.color.black_text))//内容字体色值
                .setPositive(butText, PositiveonClickListener)
                .show();
    };
    View.OnClickListener PositiveonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (menuType==1){
                mContext.startActivity(new Intent(mContext,JoinCompanyActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }else dialog.dismiss();
        }
    };
    float fromDpToPx(float dp) {
        return dp * Resources.getSystem().getDisplayMetrics().density;
    }
}
