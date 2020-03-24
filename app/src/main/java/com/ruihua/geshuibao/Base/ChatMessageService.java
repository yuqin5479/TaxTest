package com.ruihua.geshuibao.Base;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Acivity.MainActivity;
import com.ruihua.geshuibao.Event.MessageEvent_chatMessageMap;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.StreamError;

import java.util.HashMap;
import java.util.Map;
/**
 * 监听 IM聊天消息的  服务
 */
public class ChatMessageService extends Service {
    private ChatManager chatManager;
    private Map messageBody;
    public static int CHAT_MESSAGE_SERVICE_NOTIFY_ID = 101;
//    private List<ChatMessage> msgList = new ArrayList<>();//消息列表
    Notification notification;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void initConnectionListener() {
        XmppConnection.getConnection().addConnectionListener(new ConnectionListener() {
            @Override
            public void connectionClosed() {}
            @Override
            public void connectionClosedOnError(Exception e) {
                if (e instanceof XMPPException) {
                    XMPPException xe = (XMPPException) e;
                    final StreamError error = xe.getStreamError();
                    String errorCode = "";
                    if (error != null) {
                        errorCode = error.getCode();// larosn 0930
                        Log.v("IMXmppManager", "连接断开，错误码" + errorCode);
                        if (errorCode.equalsIgnoreCase("conflict")) {// 被踢下线

                        }
                    }
                }
            }
            @Override
            public void reconnectingIn(int i) { }
            @Override
            public void reconnectionSuccessful() { }
            @Override
            public void reconnectionFailed(Exception e) { }
        });
    }

    private void loginChateService() {
        //        连接IM服务器，用户登录
            XmppConnection.closeConnection();
                    try {
                        XmppConnection.getConnection().login(
                                (String)SPUtils.get("userId",""),
                                XmppConnection.DEFAULT_PASSWORD);
//                      连接服务器成功，更改在线状态
                        Presence presence = new Presence(Presence.Type.available);
                        XmppConnection.getConnection().sendPacket(presence);
                        Log.i("yu","聊天服务器登录成功...........");
                        initConnectionListener();//登录成功后 监听 连接状态
                    } catch (Exception e) {
                        Log.i("yu","聊天服务器登录失败..........."+e.getMessage());
                        e.printStackTrace();
                    }
    }

    private void initChatListener() {
        XmppConnection.getConnection().addPacketListener(new PacketListener() {
            @Override
            public void processPacket(Packet packet) {
                Message message = (Message) packet;
                messageBody = new Gson().fromJson(message.getBody(),Map.class);
                if(!messageBody.get("xmppChatType").toString().equals("sys_me")){//过滤后台间传递的系统消息  xmppChatType="sys_me"
                    messageBody.put("from",message.getFrom().split("@")[0]);
                    messageBody.put("to",message.getTo().split("@")[0]);
                    Log.i("yu","消息服务收到消息...已过滤系统消息....................."+messageBody.toString());
                    popupnotification(messageBody);
                    EventBus.getDefault().postSticky(new MessageEvent_chatMessageMap(messageBody));
                    }
            }
        },null);
    }

    /**收到消息  弹出通知 */
    private void popupnotification(Map messageBody) {

    }

    private void activateNotification() {
        notification = new Notification.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentTitle(getApplicationContext().getString(R.string.app_name))
                .setContentText("你收到1条新消息")
                .build();

        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        notification.contentIntent = pendingIntent;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(CHAT_MESSAGE_SERVICE_NOTIFY_ID, notification);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        messageBody = new HashMap();
        new Thread(){
            @Override
            public void run() {
                loginChateService();
                initChatListener();
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
