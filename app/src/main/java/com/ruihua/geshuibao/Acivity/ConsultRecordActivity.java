package com.ruihua.geshuibao.Acivity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.mylhyl.circledialog.CircleDialog;
import com.ruihua.geshuibao.Adapter.ConsultRecordAdapter;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.ConsultRecordBean;
import com.ruihua.geshuibao.Fragments.HomeFragment;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 咨询记录  页面
 */
public class ConsultRecordActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.iv_head_right)
    ImageView ivHeadRight;
    @BindView(R.id.consult_record_rv)
    RecyclerView consultRecordRv;
    @BindView(R.id.consult_record_srl)
    SmartRefreshLayout consultRecordSrl;
//    private int pageNo = 1;
    private String userId,token,companyId;
    private ConsultRecordBean consultRecordBean;
    private ConsultRecordAdapter adapter;
    private List<ConsultRecordBean.DataBean.ListBean> consultRecordList = new ArrayList<>();
    @Override
    public int intiLayout() {
        return R.layout.activity_consult_record;
    }

    @Override
    public void initView() {
        tvHeadTitle.setText("资讯记录");
        ivHeadRight.setVisibility(View.VISIBLE);
        ivHeadRight.setImageResource(R.drawable.nav_del);
        consultRecordRv.setLayoutManager(new LinearLayoutManager(this));
        consultRecordRv.setAdapter(adapter);
        consultRecordSrl.setOnRefreshListener(new OnRefreshListener() {//下拉刷新
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                pageNo = 1;
                getConsultRecord();
            }
        });
        consultRecordSrl.setOnLoadMoreListener(new OnLoadMoreListener() {//上拉加载
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
//                pageNo++;
                getConsultRecord();
            }
        });
        adapter.setItemClickListener(new ConsultRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               //跳转 咨询IM聊天窗口界面
                if(consultRecordList.get(position).getType().equals("1")){
                    //跳转 平台客服
                    startActivity(new Intent(ConsultRecordActivity.this,ChatActivity.class).putExtra("chatType",ChatActivity.CHAT_TYPE_PLATFOR).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                }else {
                    //跳转 联系单位
                    if(!TextUtils.isEmpty(HomeFragment.companyName)){
                        startActivity(new Intent(ConsultRecordActivity.this,ChatActivity.class).putExtra("chatType",ChatActivity.CHAT_TYPE_COMPANY).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
                    }else toastShort("您还未加入单位");
                }
            }
        });
        consultRecordSrl.autoRefresh();// 进入页面 自动刷新
    }

    @Override
    public void initData() {
        userId = (String) SPUtils.get("userId", "");
        token = (String) SPUtils.get("token", "");
        companyId = (String) SPUtils.get("companyId", "");
        adapter = new ConsultRecordAdapter(this);
    }

    private void getConsultRecord() {
        okHttpUtils.getAsyncData(BaseUrl.imChatHistoryType
                +"?token="+token
                +"&appUserId="+userId
                +"&companyId="+companyId, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                toastShort("咨询记录获取失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"获取咨询记录.........."+response);
//                if(consultRecordBean!=null&&consultRecordList!=null){
//                    ConsultRecordBean bean = new Gson().fromJson(response,ConsultRecordBean.class);
//                    if(bean.getData().getList().size()!=0){
//                        if (pageNo==1)
//                            consultRecordList = bean.getData().getList();
//                        else
//                            consultRecordList.addAll(bean.getData().getList());
//                    } else consultRecordSrl.finishLoadMoreWithNoMoreData();//数据加载完成 暂无数据
//                }else {
//                    consultRecordBean = new Gson().fromJson(response,ConsultRecordBean.class);
//                    consultRecordList = consultRecordBean.getData().getList();
//                    if(consultRecordList.size()==0){
//                        consultRecordSrl.finishLoadMoreWithNoMoreData();
//                    }
//                }
                consultRecordBean = new Gson().fromJson(response,ConsultRecordBean.class);
                if(consultRecordBean!=null&&consultRecordBean.getData().getList().size()!=0){
                    consultRecordList = consultRecordBean.getData().getList();
                    adapter.setDatas(consultRecordList);
                    consultRecordSrl.finishRefresh();
                    consultRecordSrl.finishLoadMore();
                }else{
                    consultRecordList.clear();
                    adapter.setDatas(consultRecordList);
                    consultRecordSrl.finishRefresh();
                    consultRecordSrl.finishLoadMore();
                    consultRecordSrl.finishLoadMoreWithNoMoreData();
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                this.finish();
                break;
            case R.id.iv_head_right://清空咨询记录
                new CircleDialog.Builder(this)
                        .setTitle("清空")
                        .setText("确认要清空所有咨询记录吗？")
                        .setTextColor(getResources().getColor(R.color.black_text))
                        .setCanceledOnTouchOutside(false)//触摸外部关闭，默认 true
                        .setCancelable(true)//返回键关闭，默认 true
                        .setPositive("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                emptyConsultRecord();
                            }
                        })
                        .setNegative("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {}
                        }).show();
                break;
        }
    }

    private void emptyConsultRecord() {
        loadingDialog.show();
        Map<String,String > data = new HashMap<>();
        data.put("token",token);
        data.put("appUserId",userId);
        okHttpUtils.postAsnycData(data, BaseUrl.delImChatHistory, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("清空咨询记录失败");
            }
            @Override
            public void onSucces(Call call, String response) {
                try {
                    if(new JSONObject(response).optString("errorcode").equals("0000")){
                        toastShort("清空咨询记录成功");
                        consultRecordSrl.autoRefresh();//刷新
                    }else toastShort(new JSONObject(response).optString("errormsg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    try {
                        toastShort(new JSONObject(response).optString("errormsg"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                loadingDialog.dismiss();
            }
        });
    }
}
