package com.ruihua.geshuibao.Fragments;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.NotifierProPlusActivity;
import com.ruihua.geshuibao.Adapter.NotifierProPlusAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.NotifierProPlusBean;
import com.ruihua.geshuibao.Event.Event_MessageListFrgment_MsgTypeId;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 全部消息（消息分类） 碎片
 */
public class NotifierProPlusFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.lv_notifis)
    ListView lvNotifis;
    private String userId,token,companyId;
    private NotifierProPlusBean notifierProPlusBean;
    private NotifierProPlusAdapter adapter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_notifierpro_plus;
    }

    @Override
    protected void setUpData() {
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        getNotifierProPlus();
    }

    private void getNotifierProPlus() {//获取消息通知（类型列表）
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.MSG_CLASSIFY +
                        "?appUserId=" + userId +
                        "&token=" + token, new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("获取消息分类失败");
                    }

                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"消息通知分类列表.........."+response);
                        notifierProPlusBean = new Gson().fromJson(response,NotifierProPlusBean.class);
                        loadingDialog.dismiss();
                        if(notifierProPlusBean!=null&&notifierProPlusBean.getErrorcode().equals("0000")){
                            adapter = new NotifierProPlusAdapter(mContext,R.layout.item_notifierpro_plaus,notifierProPlusBean.getData().getList());
                            lvNotifis.setAdapter(adapter);
                            Log.i(TAG,"消息分类界面适配器填充完毕.........");
                        }
                    }
                }
        );
    }
    @Override
    protected void setUpView() {
        tvHeadTitle.setText("消息通知");
        tvRight.setText("全部已读");
        lvNotifis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().postSticky(new
                        Event_MessageListFrgment_MsgTypeId(
                                notifierProPlusBean.getData().getList().get(position).getId(),
                                notifierProPlusBean.getData().getList().get(position).getClassifyType()));//传递具体消息类型id消息类型
                NotifierProPlusActivity.switchContent(NotifierProPlusActivity.messageListFragment);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.tv_right://全部已读 按钮
                allRead();
                break;
        }
    }

    private void allRead() {
        loadingDialog.show();
        //全部消息 全部已读
        Map<String,String> msgData = new HashMap<>();
        msgData.put("appUserId",userId);
        msgData.put("token",token);
        msgData.put("id","");// 全部消息就传id:""
        okHttpUtils.postAsnycData(msgData, BaseUrl.querytotalMs, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
            }
            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                getNotifierProPlus();
            }
        });
    }
}
