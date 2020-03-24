package com.ruihua.geshuibao.Acivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Adapter.MessageAdapter;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Base.XmppConnection;
import com.ruihua.geshuibao.Bean.ChatMessage;
import com.ruihua.geshuibao.Bean.HelpChatInforBean;
import com.ruihua.geshuibao.Bean.MessageRecordBean;
import com.ruihua.geshuibao.Event.MessageEvent_chatMessageMap;
import com.ruihua.geshuibao.Fragments.HomeFragment;
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
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * IM  聊天界面 （咨询 联系单位、平台客服）
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.msg_list_view)
    ListView msgListView;
    @BindView(R.id.input_text)
    EditText inputText;
    @BindView(R.id.send)
    Button butSend;
    @BindView(R.id.msg_proficy_srl)
    SmartRefreshLayout msgProficySrl;
    private Map messageBody;
    private int pageNo = 1;
    private String toUserName, toHeadUrl, toUserId, chatType;
    private List<ChatMessage> msgList = new ArrayList<>();//消息列表
    private ChatManager chatManager;
    private MessageAdapter msgAdapter;
    private Chat chat;
    private Intent intent;
    MessageRecordBean messageRecordBean;
    private List<MessageRecordBean.DataBean.ListBean> messageRecordList = new ArrayList<>();
    HelpChatInforBean helpChatInforBean;
    private String userId, token, companyId,userName;
    private String content;// 消息输入框 输入的消息内容
    public static int CHAT_TYPE_PLATFOR = 1, CHAT_TYPE_COMPANY = 2;//IM聊天咨询类型 联系单位、平台客服

    @Override
    public int intiLayout() {
        EventBus.getDefault().register(this);//订阅
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        if (intent!=null){
            if(intent.getIntExtra("chatType", 0)==CHAT_TYPE_PLATFOR){
                tvHeadTitle.setText("平台客服");
            }else if (intent.getIntExtra("chatType", 0)==CHAT_TYPE_COMPANY){
                tvHeadTitle.setText("联系单位");
            }
        }
        msgProficySrl.setOnRefreshListener(new OnRefreshListener() {//下拉刷新
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNo = 1;
                getChatData();
            }
        });
        msgProficySrl.setOnLoadMoreListener(new OnLoadMoreListener() {//上拉加载
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                pageNo++;
                getChatData();
            }
        });
        msgAdapter = new MessageAdapter(this, R.layout.item_custom_chat, msgList);
        msgListView.setAdapter(msgAdapter);
        msgListView.setSelection(msgAdapter.getCount());//显示到最后的聊天记录
    }

    private void getChatData() {//获取消息记录
        okHttpUtils.getAsyncData(BaseUrl.imChatHistoryPage
                +"?token="+token
                +"&appUserId="+userId
                +"&companyId="+companyId
                +"&pageSize=10"
                +"&pageNo="+pageNo, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastShort("消息记录获取失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                messageRecordBean = new Gson().fromJson(response,MessageRecordBean.class);
                Log.i(TAG,"IM消息记录......"+response);
                if(messageRecordBean!=null&&messageRecordList!=null){
                    MessageRecordBean bean = new Gson().fromJson(response,MessageRecordBean.class);
                    if(bean.getData().getList().size()!=0){
                        if (pageNo==1)
                            messageRecordList = bean.getData().getList();
                        else
                            messageRecordList.addAll(bean.getData().getList());
                    } else msgProficySrl.finishLoadMoreWithNoMoreData();//数据加载完成 暂无数据
                }else {
                    messageRecordBean = new Gson().fromJson(response,MessageRecordBean.class);
                    messageRecordList = messageRecordBean.getData().getList();
                    if(messageRecordList.size()==0){
                        msgProficySrl.finishLoadMoreWithNoMoreData();
                    }
                }
                setMsgList();
                msgProficySrl.finishRefresh();
                msgProficySrl.finishLoadMore();
            }
        });
    }

    private void setMsgList() {
        msgList.clear();
        if (messageRecordList.size()!=0){
            for (int i=0;i<messageRecordList.size();i++){
                msgList.add(new ChatMessage(
                        messageRecordList.get(i).getMsg(),
                        ChatMessage.TYPE_RECEIVED,
                        messageRecordList.get(i).getFromPhoto()));
            }
           msgAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void initData() {
        userId = (String) SPUtils.get("userId", "");
        userName = (String) SPUtils.get("userName", "");
        token = (String) SPUtils.get("token", "");
        companyId = (String) SPUtils.get("companyId", "");
        messageBody = new HashMap();
        chatManager = XmppConnection.getConnection().getChatManager();
        intent = getIntent();
        if (intent != null) {
            getServes(intent.getIntExtra("chatType", 0));
        }
    }

    /**
     * 接收到消息    在ui线程执行
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MessageEvent_chatMessageMap message) {
        messageBody = message.messageBody;
        messageToUi(messageBody);
        Log.i("yu", "聊天界面收到消息.............");
    }

    private void messageToUi(Map messageMap) {
        //先判断 单聊/群聊消息体
        if (!TextUtils.isEmpty(messageMap.get("xmppChatType").toString())) {
            //单聊消息 消息接收人是自己
            if (messageMap.get("to").equals(userId.toLowerCase())) {//本人ID转小写 需与IM服务格式一致
                msgList.add(new ChatMessage(messageMap.get("msg").toString(), ChatMessage.TYPE_RECEIVED, toHeadUrl));
                msgAdapter.notifyDataSetChanged();
                msgListView.setSelection(msgList.size());
            }
        }
    }

    private void getServes(int chatType) {//获取客服详情
        loadingDialog.show();
        if (chatType == CHAT_TYPE_PLATFOR)
            companyId = "8852fa2f4b024e5e9ab8601b882e9367";//平台客服  ID固定
        okHttpUtils.getAsyncData(BaseUrl.helpImInfo
                        + "?token=" + token
                        + "&appUserId=" + userId
                        + "&type=" + chatType
                        + "&companyId=" + companyId
                , new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("获取IM详情失败");
                    }

                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG, "IM详情......." + response);
                        helpChatInforBean = new Gson().fromJson(response, HelpChatInforBean.class);
                        loadingDialog.dismiss();
                        if (helpChatInforBean != null && helpChatInforBean.getErrorcode().equals("0000")) {
                            toUserId = helpChatInforBean.getData().getUserId();
                            toHeadUrl = helpChatInforBean.getData().getImage();
                            toUserName = helpChatInforBean.getData().getUserName();
                            chat = chatManager.createChat(toUserId.toLowerCase()+"@"+XmppConnection.SERVER_NAME, null);//单聊会话通道
                        } else {
                            toastShort(helpChatInforBean.getErrormsg());
                            ChatActivity.this.finish();
                        }
                    }
                });
    }

    @OnClick({R.id.iv_back, R.id.send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.send://消息 发送按钮
                sendMsg();
                break;
        }
    }

    private void sendMsg() {
        content = inputText.getText().toString();//消息内容
        if(!TextUtils.isEmpty(content)){
            ChatMessage myMsg = new ChatMessage(content, ChatMessage.TYPE_SEND, HomeFragment.userHeadUrl);
            final Message msg = new Message();
            msg.setFrom(userId+"@"+XmppConnection.SERVER_NAME+"/mobileClient");
            msg.setType(Message.Type.chat);
            messageBody.clear();
            messageBody.put("bodyType","txt");
            messageBody.put("fromPortrait",HomeFragment.userHeadUrl);
            messageBody.put("fromUserName",userName);
            messageBody.put("toPortrait",toHeadUrl);
            messageBody.put("toUserName",toUserName);
            messageBody.put("xmppChatType","friend");
            messageBody.put("msg",content);
            messageBody.put("timeStamp",System.currentTimeMillis());
            //自定义消息体 装填
            msg.setBody(new Gson().toJson(messageBody));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 发送消息
                        chat.sendMessage(msg);
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            msgList.add(myMsg);
            msgAdapter.notifyDataSetChanged();
            msgListView.setSelection(msgList.size());
            inputText.setText("");
        }else toastShort("发送内容不可为空");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
