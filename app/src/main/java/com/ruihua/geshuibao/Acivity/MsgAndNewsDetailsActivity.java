package com.ruihua.geshuibao.Acivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.MsgDetailsBean;
import com.ruihua.geshuibao.Bean.NewsDetailsBean;
import com.ruihua.geshuibao.Bean.PoliciesRegulationsDetailsBean;
import com.ruihua.geshuibao.Event.Event_MsgAndNewsDetailsActivity_Id;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 新闻、消息、政策法规  详情页 （共用）
 */
public class MsgAndNewsDetailsActivity extends BaseActivity {
    public static int DETAILS_TYPE_MSG = 000;//消息详情
    public static int DETAILS_TYPE_NEWS = 111;//新闻详情
    public static int DETAILS_TYPE_POLICY = 222;//政策法规详情
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.iv_head_right)
    ImageView ivHeadRight;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.wb_content)
    WebView wbContent;
    private String userId, token;
    private String detailsId;
    private int detailsType;
    private MsgDetailsBean msgDetailsBean;
    private NewsDetailsBean newsDetailsBean;
    private PoliciesRegulationsDetailsBean policiesRegulationsDetailsBean;
    @Override
    public int intiLayout() {
        return R.layout.activity_msg_news_details;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void getDetaiilId(Event_MsgAndNewsDetailsActivity_Id event_detailsId) {//获取详情id和详情类型
        detailsId = event_detailsId.detailsId;
        detailsType = event_detailsId.type;
    }

    @Override
    public void initView() {
        if (detailsType == DETAILS_TYPE_MSG){
            tvHeadTitle.setText("消息详情");
            ivHeadRight.setVisibility(View.GONE);
        } else if(detailsType == DETAILS_TYPE_NEWS){
            tvHeadTitle.setText("新闻详情");
            ivHeadRight.setVisibility(View.VISIBLE);
            ivHeadRight.setImageResource(R.drawable.nav_coll_nor);
        }else if(detailsType == DETAILS_TYPE_POLICY){
            tvHeadTitle.setText("政策法规详情");
            ivHeadRight.setVisibility(View.GONE);
        }

    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
        userId = (String) SPUtils.get("userId", "");
        token = (String) SPUtils.get("token", "");
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getData() {
        if (!TextUtils.isEmpty(detailsId)) {
            if (detailsType == DETAILS_TYPE_MSG) {
                getMsgDetaiil();//获取 消息详情
            } else if (detailsType == DETAILS_TYPE_NEWS) {
                getNesDetaiil();//获取 新闻详情
            }else if(detailsType==DETAILS_TYPE_POLICY){
                getPolicyDetaiil();// 获取 政策法规详情
            }
        }

    }

    private void getPolicyDetaiil() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.lawDetails +
                "?Id=" + detailsId, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("政策法规获取失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"政策法规获取..........."+response);
                policiesRegulationsDetailsBean = new Gson().fromJson(response,PoliciesRegulationsDetailsBean.class);
                loadingDialog.dismiss();
                if(policiesRegulationsDetailsBean!=null&&policiesRegulationsDetailsBean.getErrorcode().equals("0000")){
                    setPoliciesDetails();
                }
            }
        });
    }

    private void setPoliciesDetails() {
        tvTitle.setText(policiesRegulationsDetailsBean.getData().getTitle());
        tvDate.setText(policiesRegulationsDetailsBean.getData().getCreateDate());
        wbContent.loadDataWithBaseURL( null, policiesRegulationsDetailsBean.getData().getContent() , "text/html", "UTF-8", null ) ;
    }

    private void getNesDetaiil() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.newDetails +
                "?appUserId=" + userId +
                "&token=" + token +
                "&Id=" + detailsId, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("新闻详情获取失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"新闻详情..........."+response);
                newsDetailsBean = new Gson().fromJson(response,NewsDetailsBean.class);
                loadingDialog.dismiss();
                if(newsDetailsBean!=null&&newsDetailsBean.getErrorcode().equals("0000")){
                    setNewsDetails();
                }
            }
        });
    }

    private void setNewsDetails() {
        if(newsDetailsBean.getData().isCollect()){
            //已收藏过该新闻
            ivHeadRight.setImageResource(R.drawable.nav_coll_pre);
        }
        tvTitle.setText(newsDetailsBean.getData().getTitle());
        tvDate.setText(newsDetailsBean.getData().getCreateDate());
        wbContent.loadDataWithBaseURL( null, newsDetailsBean.getData().getContent() , "text/html", "UTF-8", null ) ;
    }

    private void getMsgDetaiil() {
        loadingDialog.show();
        HashMap<String, String> newsUrlMap = new HashMap<>();
        newsUrlMap.put("appUserId", userId);
        newsUrlMap.put("token", token);
        newsUrlMap.put("id", detailsId);
        okHttpUtils.postAsnycData(newsUrlMap, BaseUrl.queryMsgDetail, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("消息详情获取失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG, "消息详情......" + response);
                msgDetailsBean = new Gson().fromJson(response,MsgDetailsBean.class);
                loadingDialog.dismiss();
                if (msgDetailsBean!=null&&msgDetailsBean.getErrorcode().equals("0000")){
                    setMsgDetaiil();
                }
            }
        });
    }

    private void setMsgDetaiil() {
        tvTitle.setText(msgDetailsBean.getData().getTitle());
        tvDate.setText(msgDetailsBean.getData().getCreateDate());
        wbContent.loadDataWithBaseURL( null, msgDetailsBean.getData().getContent() , "text/html", "UTF-8", null ) ;
    }


    @OnClick({R.id.iv_back, R.id.iv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_head_right://新闻详情 收藏按钮
                    if(newsDetailsBean.getData()!=null && !newsDetailsBean.getData().isCollect()){
                        //收藏
                        executeCollect(newsDetailsBean.getData().isCollect());
                    }else {
                        //取消收藏
                        executeCollect(newsDetailsBean.getData().isCollect());
                    }
                break;
        }
    }

    private void executeCollect(final boolean collect) {
        Map<String,String> collsctData = new HashMap<>();
        collsctData.put("appUserId",userId);
        collsctData.put("Id",detailsId);
        collsctData.put("token",token);
        okHttpUtils.postAsnycData(collsctData, collect ? BaseUrl.delnewscollect:BaseUrl.newCollect, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onSucces(Call call, String response) {
                if (detailsType == DETAILS_TYPE_NEWS)
                    getNesDetaiil();//刷新 新闻详情
                try {
                    if(new JSONObject(response).optString("errorcode").equals("0000")){
                        toastShort(new JSONObject(response).optString("errormsg"));
                        //收藏成功/取消收藏成功
                        if(collect){
                            ivHeadRight.setImageResource(R.drawable.nav_coll_nor);//取消收藏成功
                        }else
                            ivHeadRight.setImageResource(R.drawable.nav_coll_pre);//收藏成功

                    }else {
                        toastShort(new JSONObject(response).optString("errormsg"));
                    }
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
