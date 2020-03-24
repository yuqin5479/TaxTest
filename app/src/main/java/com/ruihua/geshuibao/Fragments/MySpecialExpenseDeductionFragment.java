package com.ruihua.geshuibao.Fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Adapter.MySpecialExpenseDeductionAdopter2;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.MonthlySpecialExpenseDeductionBean;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 我的申报---专项附加扣除
 */
public class MySpecialExpenseDeductionFragment extends BaseFragment {

    @BindView(R.id.rv_item_list1)
    RecyclerView rvItemList1;
    @BindView(R.id.rv_item_list2)
    RecyclerView rvItemList2;
    private String userId,token,taxpayerNo;
    private MonthlySpecialExpenseDeductionBean mBean;
    private MySpecialExpenseDeductionAdopter2 specialExpenseDeductionAdopter,elseAdopter;
    DateFormat format = new SimpleDateFormat("yyyy-MM");
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_my_special_expense_deduction;
    }

    @Override
    protected void setUpData() {
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        taxpayerNo = (String) SPUtils.get("taxpayerNo","");
        getData(format.format(new Date(System.currentTimeMillis())));//获取当前月份的 我的申报记录
    }

    public void getData(String date) {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.EXP_DEDUCTION_TYP +
                "?appUserId=" + userId +
                "&token=" + token+
                "&month="+date+
                "&type="+"1"
                +"&taxpayerNo="+taxpayerNo, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("扣除项获取失败");
            }
            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"我的申报----专项附加扣除项........."+response);
                mBean = new Gson().fromJson(response,MonthlySpecialExpenseDeductionBean.class);
                loadingDialog.dismiss();
                if(mBean!=null&&mBean.getErrorcode().equals("0000")){
                    setData();
                }
            }
        });
    }

    private void setData() {
        specialExpenseDeductionAdopter.setDatas(mBean.getData());
        elseAdopter.setDatas(mBean.getData());
    }

    @Override
    protected void setUpView() {
        specialExpenseDeductionAdopter = new MySpecialExpenseDeductionAdopter2(getActivity(),11);
        elseAdopter = new MySpecialExpenseDeductionAdopter2(getActivity(),12);
        rvItemList1.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvItemList1.setNestedScrollingEnabled(false);//解决数据加载不完的问题
        rvItemList1.setAdapter(specialExpenseDeductionAdopter);
        rvItemList2.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvItemList2.setNestedScrollingEnabled(false);//解决数据加载不完的问题
        rvItemList2.setAdapter(elseAdopter);
    }

}
