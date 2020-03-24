package com.ruihua.geshuibao.Fragments;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MsgAndNewsDetailsActivity;
import com.ruihua.geshuibao.Acivity.NotifierProPlusActivity;
import com.ruihua.geshuibao.Adapter.MessageListAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.MessageListBean;
import com.ruihua.geshuibao.Event.Event_MessageListFrgment_MsgTypeId;
import com.ruihua.geshuibao.Event.Event_MsgAndNewsDetailsActivity_Id;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 具体类型消息列表  碎片
 */
public class MessageListFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.tv_head_right)
    TextView tvHeadRight;
    @BindView(R.id.rv_msg_list)
    RecyclerView rvMsgList;
    @BindView(R.id.srl_msg_list)
    SmartRefreshLayout srlMsgList;
    private String userId, token, msgTypeId, msgType;
    private int pageNo = 1;
    private MessageListBean messageListBean;
    private List<MessageListBean.DataBean.ListBean> messageList = new ArrayList<>();
    private MessageListAdapter adapter;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_message_list;
    }

    @Override
    protected void setUpData() {
        EventBus.getDefault().register(this);
        userId = (String) SPUtils.get("userId", "");
        token = (String) SPUtils.get("token", "");
        adapter = new MessageListAdapter(getActivity());
        rvMsgList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMsgList.setAdapter(adapter);
        srlMsgList.autoRefresh();// 进入页面 自动刷新
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            messageList.clear();
            srlMsgList.autoRefresh();// 进入页面 自动刷新
            setTitle();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getMsgTypeId(Event_MessageListFrgment_MsgTypeId event_msgTypeId) {//获取消息类型id和消息类型
        this.msgTypeId = event_msgTypeId.msgTypeId;
        this.msgType = event_msgTypeId.msgType;
    }

    @Override
    protected void setUpView() {
        setTitle();
        tvHeadRight.setText("全部已读");
        srlMsgList.setOnRefreshListener(new OnRefreshListener() {//下拉刷新
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                getMsgListData();
            }
        });
        srlMsgList.setOnLoadMoreListener(new OnLoadMoreListener() {//上拉加载
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNo++;
                getMsgListData();
            }
        });
        adapter.setItemClickListener(new MessageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {//跳转 消息详情
                EventBus.getDefault().postSticky(
                        new Event_MsgAndNewsDetailsActivity_Id(
                                messageList.get(position).getId(),
                                MsgAndNewsDetailsActivity.DETAILS_TYPE_MSG));
                startActivity(new Intent(getActivity(),MsgAndNewsDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            }
        });
    }

    private void setTitle() {
        if (!TextUtils.isEmpty(msgType)){
            switch (msgType){
                case "1":
                    tvHeadTitle.setText("系统消息");
                    break;
                case "2":
                    tvHeadTitle.setText("税务消息");
                    break;
                case "3":
                    tvHeadTitle.setText("单位消息");
                    break;
            }
        }
    }


    private void getMsgListData() {
        okHttpUtils.getAsyncData(BaseUrl.queryMsg
                        + "?appUserId="+userId
                        + "&token="+token
                        + "&pageNo=" + pageNo
                        + "&pageSize=10"
                        + "&id=" + msgTypeId,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        toastShort("消息列表获取失败");
                    }
                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"根据分类id获取消息列表.........."+response);
                        if(messageListBean!=null&&messageList!=null){
                            MessageListBean bean = new Gson().fromJson(response,MessageListBean.class);
                            if(bean.getData().getList().size()!=0){
                                if (pageNo==1)
                                    messageList = bean.getData().getList();
                                else
                                    messageList.addAll(bean.getData().getList());
                            } else srlMsgList.finishLoadMoreWithNoMoreData();//数据加载完成 暂无数据
                        }else {
                            messageListBean = new Gson().fromJson(response,MessageListBean.class);
                            messageList = messageListBean.getData().getList();
                            if(messageList.size()==0){
                                srlMsgList.finishLoadMoreWithNoMoreData();
                            }
                        }
                        adapter.setDatas(messageList);
                        srlMsgList.finishRefresh();
                        srlMsgList.finishLoadMore();
                    }
                }
        );
    }



    @OnClick({R.id.iv_back, R.id.tv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                NotifierProPlusActivity.switchContent(NotifierProPlusActivity.notifierProPlusFragment);
                break;
            case R.id.tv_head_right://全部已读
                allRead();
                break;
        }
    }
    private void allRead() {
        loadingDialog.show();
        //全部消息已读
        Map<String,String> msgData = new HashMap<>();
        msgData.put("appUserId",userId);
        msgData.put("token",token);
        msgData.put("id",msgTypeId);
        okHttpUtils.postAsnycData(msgData, BaseUrl.querytotalMs, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
            }
            @Override
            public void onSucces(Call call, String response) {
                loadingDialog.dismiss();
                srlMsgList.autoRefresh();
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
