package com.ruihua.geshuibao.Fragments;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.mylhyl.circledialog.CircleDialog;
import com.ruihua.geshuibao.Acivity.ConsultRecordActivity;
import com.ruihua.geshuibao.Acivity.JoinCompanyActivity;
import com.ruihua.geshuibao.Acivity.MyCollectActivity;
import com.ruihua.geshuibao.Acivity.MyCompanyActivity;
import com.ruihua.geshuibao.Acivity.MyDeclareActivity;
import com.ruihua.geshuibao.Acivity.MyIndividualIncomeTax;
import com.ruihua.geshuibao.Acivity.NotifierProPlusActivity;
import com.ruihua.geshuibao.Acivity.PersonalInformationActivity;
import com.ruihua.geshuibao.Acivity.SetUpActivity;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.PersonalInformationBean;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 我的
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.mine_iv_msg)
    ImageView mIvMsg;
    @BindView(R.id.mine_iv_msg_hint)
    ImageView mIvMsgHint;
    @BindView(R.id.mine_iv_head)
    ImageView mIvHead;
    @BindView(R.id.mine_tv_username)
    TextView mTvUserName;
    @BindView(R.id.mine_tv_tel)
    TextView mTvTel;
    @BindView(R.id.mine_tv_not_approve)
    TextView mTvNotApprove;
    @BindView(R.id.mine_ll_personal_information)
    LinearLayout mLlPersonalInformation;
    @BindView(R.id.mine_ll_join_company)
    LinearLayout mLlJoinCompany;
    @BindView(R.id.mine_ll_my_company)
    LinearLayout mLlMyCompany;
    @BindView(R.id.mine_ll_my_collect)
    LinearLayout mLlMyCollect;
    @BindView(R.id.mine_ll_my_individual)
    LinearLayout mLlMyIndividual;
    @BindView(R.id.mine_ll_my_declare)
    LinearLayout mLlMyDeclare;
    @BindView(R.id.mine_ll_consult_record)
    LinearLayout mLlConsultRecord;
    @BindView(R.id.mine_ll_set)
    LinearLayout mLlSet;
    @BindView(R.id.mine_srl)
    SwipeRefreshLayout mSrl;
    private String userId,token;
    private PersonalInformationBean personalInformationBean;
    private int startType;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void setUpView() { }

    @Override
    protected void setUpData() {
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        mSrl.setColorSchemeResources(R.color.main_color);
        mSrl.setOnRefreshListener(refreshListener);
        mSrl.post(new Runnable() {
            @Override
            public void run() {
                mSrl.setRefreshing(true);
            }
        });
        refreshListener.onRefresh();
    }

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        public void onRefresh() {
            //TODO
            getMyInforData();
            getIsImprovePersonalInfo();//查询个人信息是否已完善
        }
    };
    private void getIsImprovePersonalInfo() {
        okHttpUtils.getAsyncData(BaseUrl.IS_IMPROVE_PERSONAL_INFO
                        + "?token=" + token
                        + "&appUserId=" + userId, new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) { }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"个人信息是否已完善...."+response);
                        try {
                            if(new JSONObject(response).getJSONObject("data").getBoolean("success")){
                                mTvNotApprove.setText(getString(R.string.mine_yet_approve));
                            }else mTvNotApprove.setText(getString(R.string.mine_not_approve));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }

    private void getMyInforData() {
        okHttpUtils.getAsyncData(BaseUrl.PERSONAL_INFORMATION
                        + "?appUserId=" + userId
                        + "&token=" + token,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        toastShort("个人信息获取失败");
                        if (mSrl.isRefreshing()) {
                            mSrl.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"获取个人信息........."+response);
                        personalInformationBean = new Gson().fromJson(response,PersonalInformationBean.class);
                        if(personalInformationBean!=null&&personalInformationBean.getErrorcode().equals("0000")){
                            setData();
                        }
                        if (mSrl.isRefreshing()) {
                            mSrl.setRefreshing(false);
                        }
                    }
                }
        );
    }

    private void setData() {
        if(!TextUtils.isEmpty(personalInformationBean.getData().getImage()))
            Glide.with(this).load(personalInformationBean.getData().getImage()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mIvHead);
        else Glide.with(this).load(R.drawable.m_header_nor).into(mIvHead);
        mTvUserName.setText(personalInformationBean.getData().getName());
        mTvTel.setText(personalInformationBean.getData().getMobile());
        if(personalInformationBean.getData().getRead().equals("0"))//Read 读取状态 1已读 0未读
            mIvMsgHint.setVisibility(View.VISIBLE);
        else mIvMsgHint.setVisibility(View.GONE);
    }

    @OnClick({R.id.mine_iv_msg, R.id.mine_iv_head, R.id.mine_ll_personal_information, R.id.mine_ll_join_company,R.id.mine_ll_my_company,
            R.id.mine_ll_my_collect, R.id.mine_ll_my_individual, R.id.mine_ll_my_declare, R.id.mine_ll_consult_record, R.id.mine_ll_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_iv_msg:
                startActivity(new Intent(getActivity(),NotifierProPlusActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.mine_iv_head:
                startActivity(new Intent(getActivity(),PersonalInformationActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.mine_ll_personal_information:
                startActivity(new Intent(getActivity(),PersonalInformationActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.mine_ll_join_company://加入单位
                startActivity(new Intent(getActivity(),JoinCompanyActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.mine_ll_my_company://我的单位
                toMyCompanyActivity();
                break;
            case R.id.mine_ll_my_collect://我的收藏
                startActivity(new Intent(getActivity(),MyCollectActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.mine_ll_my_individual:
                startActivity(new Intent(getActivity(),MyIndividualIncomeTax.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
            case R.id.mine_ll_my_declare://我的申报
                if(!TextUtils.isEmpty(HomeFragment.companyName)){
                    startActivity(new Intent(getActivity(),MyDeclareActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                }else toastShort("您还未加入单位");
                break;
            case R.id.mine_ll_consult_record://咨询记录
                if(!TextUtils.isEmpty(HomeFragment.companyName)){
                    startActivity(new Intent(getActivity(),ConsultRecordActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                }else toastShort("您还未加入单位");
                break;
            case R.id.mine_ll_set:
                startActivity(new Intent(getActivity(),SetUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                break;
        }
    }

    private void toMyCompanyActivity() {
        if(TextUtils.isEmpty(HomeFragment.userName)){
            startType = 1;
            showDialog("您还未完善个人信息","去完善");
            return;
        }
//        if(TextUtils.isEmpty(HomeFragment.companyName)){
//            startType = 2;
//            showDialog("您还未加入单位","去加入");
//            return;
//        }
        startActivity(new Intent(getActivity(),MyCompanyActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
    }
    public void showDialog(String text,String butText){
        DialogFragment dialog = new CircleDialog.Builder(getActivity())
                .setText(text)//内容
                .setCanceledOnTouchOutside(false)//触摸外部关闭，默认true
                .setTextColor(mContext.getResources().getColor(R.color.black_text))//内容字体色值
                .setPositive(butText, PositiveonClickListener)
                .show();
    };
    View.OnClickListener PositiveonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (startType==1){
                startActivity(new Intent(getActivity(),PersonalInformationActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }else startActivity(new Intent(getActivity(),JoinCompanyActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
        }
    };
}
