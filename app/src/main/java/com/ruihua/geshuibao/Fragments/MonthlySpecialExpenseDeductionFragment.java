package com.ruihua.geshuibao.Fragments;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MonthlySpecialExpenseDeductionActivity;
import com.ruihua.geshuibao.Adapter.MySpecialExpenseDeductionAdopter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.MonthlySpecialExpenseDeductionBean;
import com.ruihua.geshuibao.Event.Event_SpecialExpenseDeductionDetailsFragment_detailsId;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 月度专项附加扣除及其他扣除上传  列表  碎片
 */
public class MonthlySpecialExpenseDeductionFragment extends BaseFragment{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.tv_head_right)
    TextView tvHeadRight;
    @BindView(R.id.tv_title_hint)
    TextView tvTitleHint;
    @BindView(R.id.iv_hint_clear)
    ImageView ivHintClear;
    @BindView(R.id.rl_title_hint)
    RelativeLayout rlTitleHint;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.rv_item_list1)
    RecyclerView rvItemList1;
    @BindView(R.id.rv_item_list2)
    RecyclerView rvItemList2;
    private String userId,token;
    private MonthlySpecialExpenseDeductionBean mBean;
    private Map<String,String> mData;
    private MySpecialExpenseDeductionAdopter specialExpenseDeductionAdopter,elseAdopter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_monthly_special_expense_deduction;
    }

    @Override
    protected void setUpData() {
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        getData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            getData();
        }
    }

    private void getData() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.EXP_DEDUCTION_TYP +
                        "?appUserId=" + userId +
                        "&token=" + token, new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("扣除项获取失败");
                    }

                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"专项附加扣除项........."+response);
                        mBean = new Gson().fromJson(response,MonthlySpecialExpenseDeductionBean.class);
                        loadingDialog.dismiss();
                        if(mBean!=null&&mBean.getErrorcode().equals("0000")){
                            setData();
                        }
                    }
                });

    }

    private void setData() {
        if(!TextUtils.isEmpty(mBean.getData().getUploadTime())){
            tvDate.setText(mBean.getData().getUploadTime());
        }else tvDate.setText("暂无上传记录");

        specialExpenseDeductionAdopter.setDatas(mBean.getData());
        elseAdopter.setDatas(mBean.getData());

    }

    @Override
    protected void setUpView() {
        tvHeadTitle.setText(getString(R.string.special_expense_deduction));
        tvHeadRight.setTextColor(getResources().getColor(R.color.main_color));
        tvHeadRight.setText(getString(R.string.special_expense_deduction_upload));
        specialExpenseDeductionAdopter = new MySpecialExpenseDeductionAdopter(getActivity(),11);
        elseAdopter = new MySpecialExpenseDeductionAdopter(getActivity(),12);
        rvItemList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvItemList1.setNestedScrollingEnabled(false);//解决数据加载不完的问题
        rvItemList1.setAdapter(specialExpenseDeductionAdopter);
        rvItemList2.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvItemList2.setNestedScrollingEnabled(false);//解决数据加载不完的问题
        rvItemList2.setAdapter(elseAdopter);

        specialExpenseDeductionAdopter.setItemClickListener(new MySpecialExpenseDeductionAdopter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              if(!mBean.getData().getDeductions().get(position).getType().equals("12")){// 扣除项item点击监听   跳转 扣除项详情编辑界面 带参 详情id、icon的Url、扣除项Type
                  EventBus.getDefault().postSticky(new Event_SpecialExpenseDeductionDetailsFragment_detailsId(
                          mBean.getData().getDeductions().get(position).getId(),
                          mBean.getData().getDeductions().get(position).getImage(),
                          mBean.getData().getDeductions().get(position).getType()
                  ));
                  MonthlySpecialExpenseDeductionActivity.switchContent(MonthlySpecialExpenseDeductionActivity.specialExpenseDeductionDetailsFragment);
              }else toastShort("此项暂不可操作");
            }
        });
        elseAdopter.setItemClickListener(new MySpecialExpenseDeductionAdopter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {// 扣除项item点击监听   跳转 扣除项详情编辑界面
                EventBus.getDefault().postSticky(
                        new Event_SpecialExpenseDeductionDetailsFragment_detailsId(
                                mBean.getData().getOthers().get(position).getId(),
                                mBean.getData().getOthers().get(position).getImage(),
                                mBean.getData().getOthers().get(position).getType()
                                ));
                if(mBean.getData().getOthers().get(position).getType().equals("17"))// 如果是商业健康保险 则跳转专有特殊类型界面
                    MonthlySpecialExpenseDeductionActivity.switchContent(MonthlySpecialExpenseDeductionActivity.commercialHealthInsuranceFragment);
                else
                    MonthlySpecialExpenseDeductionActivity.switchContent(MonthlySpecialExpenseDeductionActivity.specialExpenseDeductionDetailsFragment);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_head_right, R.id.iv_hint_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.tv_head_right://上传总表
                if (mBean!=null&&mBean.getErrorcode().equals("0000"))
                     UploadData();
                break;
            case R.id.iv_hint_clear:
                rlTitleHint.setVisibility(View.GONE);
                break;
        }
    }

    private void UploadData() {
        loadingDialog.show();
        mData = new HashMap<>();
        mData.put("appUserId",userId);
        mData.put("token",token);
        mData.put("id",mBean.getData().getId());
        mData.put("type","1");//选中哪个传哪个状态（住房租金和住房贷款公积金利息）
        okHttpUtils.postAsnycData(mData, BaseUrl.uploadGs, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("上传失败！");
            }
            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"申报总表上传............"+response);
                try {
                    if(new JSONObject(response).optString("errorcode").equals("0000")){
                        toastShort("上传成功！");
                        getActivity().finish();
                    }else toastShort("上传失败！");
                } catch (JSONException e) {
                    try {
                        toastShort(new JSONObject(response).optString("errormsg"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        });
    }

}
