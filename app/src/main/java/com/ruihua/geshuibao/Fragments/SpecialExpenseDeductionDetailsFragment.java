package com.ruihua.geshuibao.Fragments;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.ruihua.geshuibao.Acivity.MonthlySpecialExpenseDeductionActivity;
import com.ruihua.geshuibao.Adapter.GridImageAdapter;
import com.ruihua.geshuibao.Base.BaseFragment;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Base.MyAplication;
import com.ruihua.geshuibao.Bean.ExpenseDeductionDetailsEditBean;
import com.ruihua.geshuibao.Bean.SpecialExpenseDeductionDetailsBean;
import com.ruihua.geshuibao.CustomWidget.FullyGridLayoutManager;
import com.ruihua.geshuibao.Event.Event_SpecialExpenseDeductionDetailsFragment_detailsId;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.OssService;
import com.ruihua.geshuibao.Util.SPUtils;
import com.ruihua.geshuibao.Util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * 月度专项附加扣除 详情上传界面
 */
public class SpecialExpenseDeductionDetailsFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.tv_head_right)
    TextView tvHeadRight;
    @BindView(R.id.tv_current_date)
    TextView tvCurrentDate;
    @BindView(R.id.iv_item_icon)
    ImageView ivItemIcon;
    @BindView(R.id.tv_item_name)
    TextView tvItemName;
    @BindView(R.id.tv_sum)
    TextView tvSum;
    @BindView(R.id.tv_practical_sum)
    TextView tvPracticalSum;
    @BindView(R.id.tv_bottm_hint)
    TextView tvBottmHint;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_employers)
    TextView tvEmployers;
    @BindView(R.id.rv_pics)
    RecyclerView rvPics;
    private String detailsId,iconUrl,itemType;
    private String userId,token;
    private SpecialExpenseDeductionDetailsBean mBean;
    private ExpenseDeductionDetailsEditBean mEditBean;
    private int REQUEST_CODE_CHOOSE = 110;
    private List<MediaEntity> mSelected = new ArrayList<>();//选择的附件图片文件
    private GridImageAdapter selectPicAdapter;
    private Map<String,String> DeductionDetailsUrlData = new HashMap<>();
    private Map<String,String> picUrlData;
    private int picNum = 0;
    private List<String> fileNames;//上传于阿里云的附件文件
    private OssService ossService;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_special_expense_deduction;
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void getDetaiilId(Event_SpecialExpenseDeductionDetailsFragment_detailsId event_detailsId) {//获取item界面的各项参数
        detailsId = event_detailsId.detailsId;
        iconUrl = event_detailsId.iconUrl;
        itemType = event_detailsId.itemType;
    }
    @Override
    protected void setUpData() {
        EventBus.getDefault().register(this);
        //初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数
        ossService = new OssService(MyAplication.context, BaseUrl.AccessKey, BaseUrl.SecretKey, BaseUrl.ENDPOINT, BaseUrl.BucketName);
        //初始化OSSClient
        ossService.initOSSClient();
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        if (!TextUtils.isEmpty(detailsId))
            getDetaiilData();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden&&!TextUtils.isEmpty(detailsId)){
                resetData();
                getDetaiilData();
        }
    }

    private void resetData() {
        //因界面复用所以每次显示该界面时 数据重置
        mSelected.clear();
        selectPicAdapter.notifyDataSetChanged();
        picNum = 0;
    }

    private void getDetaiilData() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.FIND_NOTICE_DETAIL + "?noticeType=" + itemType, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"详情提示语..........."+response);
                try {
                    tvBottmHint.setText(new JSONObject(response).getJSONObject("data").getString("content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        okHttpUtils.getAsyncData(BaseUrl.EXP_DEDUCTION_INFO +
                        "?token=" + token
                        + "&appUserId=" + userId
                        + "&id=" + detailsId, new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("扣除详情获取失败");
                    }

                    @Override
                    public void onSucces(Call call, String response) {
                        mBean = new Gson().fromJson(response,SpecialExpenseDeductionDetailsBean.class);
                        loadingDialog.dismiss();
                        if(mBean!=null&&mBean.getErrorcode().equals("0000")){
                            setDetailsData();
                        }
                        Log.i(TAG,"扣除详情.........."+response);
                    }
                }
        );
    }

    private void setDetailsData() {
        tvCurrentDate.setText(mBean.getData().getDeductionDate());
        Glide.with(getActivity()).load(iconUrl).into(ivItemIcon);
        tvItemName.setText(mBean.getData().getTitle());
        if(mBean.getData().getActualDeductionAmount()>0){
            tvSum.setText(mBean.getData().getActualDeductionAmount()+"");
        }else {
            tvSum.setText("");
            tvSum.setHint("￥0.00");
        }
        tvData.setText(mBean.getData().getDeductionDate());
        if (mBean.getData().getRemarks()!=null){
            etRemark.setText(mBean.getData().getRemarks()+"");
        }else {
            etRemark.setText("");
            etRemark.setHint("说点儿什么吧~");
        }
        tvEmployers.setText(mBean.getData().getCompanyName());

    }

    @Override
    protected void setUpView() {
        tvHeadTitle.setText("专项附加扣除及其他扣除上传");
        tvHeadRight.setTextColor(getResources().getColor(R.color.main_color));
        tvHeadRight.setText(getString(R.string.personal_information_save));
        selectPicAdapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 4, LinearLayoutManager.VERTICAL, false);
        rvPics.setLayoutManager(manager);
        rvPics.setAdapter(selectPicAdapter);
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {//添加图片
                case 0:
                    toSelectPic();
                    break;
                case 1:
                    // 删除图片
                    mSelected.remove(position);
                    selectPicAdapter.notifyItemRemoved(position);
                    break;
            }
        }
    };
    @OnClick({R.id.iv_back,R.id.tv_head_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                MonthlySpecialExpenseDeductionActivity.switchContent(MonthlySpecialExpenseDeductionActivity.monthlySpecialExpenseDeductionFragment);
                break;
            case R.id.tv_head_right://保存按钮
                saveDate();
                break;

        }
    }

    private void saveDate() {
        loadingDialog.show();
        DeductionDetailsUrlData.clear();
        DeductionDetailsUrlData.put("appUserId",userId);
        DeductionDetailsUrlData.put("token",token);
        DeductionDetailsUrlData.put("id",detailsId);
        DeductionDetailsUrlData.put("actualDeductionAmount ",tvSum.getText().toString().trim());
        DeductionDetailsUrlData.put("remarks",etRemark.getText().toString().trim());

        okHttpUtils.postAsnycData(DeductionDetailsUrlData, BaseUrl.DeductionInfoEdit, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("数据上传失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"详情修改成功............."+response);
                mEditBean = new Gson().fromJson(response,ExpenseDeductionDetailsEditBean.class);
                loadingDialog.dismiss();
                if(mEditBean!=null&&mEditBean.getErrorcode().equals("0000")){
                    //参数上传完传图片附件
                    if(mSelected.size()!=0){
                        fileNames = new ArrayList<>();
                        UploadPic();
                    }

                }
            }
        });
    }

    private void UploadPic() {
        loadingDialog.show();
        final String picPath;
        picPath = Utils.getPicPath(mSelected.get(picNum));//获取图片路径
        if (TextUtils.isEmpty(picPath)) {
            toastShort("图片不可为路径为空！");
            loadingDialog.dismiss();
            return;
        }
        PutObjectRequest put = new PutObjectRequest(BaseUrl.BucketName,"app-img/photo/"+new File(picPath).getName(), picPath);
        @SuppressWarnings("rawtypes")
        OSSAsyncTask task = ossService.getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                //上传资源的URL是定死的。http:// + bucketName+ .服务器中心地址 + /你上传的资源objectKey
                fileNames.add("/app-img/photo/"+new File(picPath).getName());
                Log.i(TAG,"阿里云附件上传成功......"+fileNames.get(picNum));
                if (picNum == mSelected.size() - 1) {
                    //  图片附件全部提交 完毕 开始往服务器传参数
                    PushPicDate();
                } else {
                    //继续传剩下的图片
                    picNum++;
                    UploadPic();
                }
            }
            @Override public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                loadingDialog.dismiss();
                toastShort("附件上传失败");
                Log.i(TAG,"阿里云附件图片传送失败...服务错误代码："+serviceException.getErrorCode()+"连接错误："+clientExcepion.getMessage());
            }
        });
    }
    private void PushPicDate() {//图片往阿里云传完 往服务器 发送图片参数
        setPicData();
        okHttpUtils.postAsnycData(picUrlData, BaseUrl.SAVE_FILE, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("附件上传失败");
            }
            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"附件上传成功.........."+response);
                try {
                    if(new JSONObject(response).optString("errorcode").equals("0000")){
                        toastShort("附件上传成功");
                        //保存完毕 返回列表界面
                        MonthlySpecialExpenseDeductionActivity.switchContent(MonthlySpecialExpenseDeductionActivity.monthlySpecialExpenseDeductionFragment);
                    }else toastShort("附件上传失败");
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastShort("附件上传失败");
                }
                loadingDialog.dismiss();
            }
        });
    }
    private void setPicData() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<fileNames.size();i++){
            if(i==fileNames.size()-1)//当循环到最后一个的时候 就不添加逗号,
            {
                sb.append(fileNames.get(i));
            } else {
                sb.append(fileNames.get(i));
                sb.append(",");
            }
        }
        picUrlData = new HashMap<>();
        picUrlData.put("id",userId);
        picUrlData.put("appUserId",userId);
        picUrlData.put("token",token);
        picUrlData.put("urls",sb.toString());
        picUrlData.put("type",itemType);
    }

    public void toSelectPic(){//跳转选择图片
        //发起图片选择
        Phoenix.with()
                .theme(PhoenixOption.THEME_BLUE)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(9)// 最大选择数量
                .minPickNumber(1)// 最小选择数量
                .spanCount(3)// 每行显示个数
                .enablePreview(true)// 是否开启预览
                .enableCamera(true)// 是否开启拍照
                .enableAnimation(true)// 选择界面图片点击效果
                .enableCompress(true)// 是否开启压缩
                .compressPictureFilterSize(1024)//多少kb以下的图片不压缩
                .thumbnailHeight(160)// 选择界面图片高度
                .thumbnailWidth(160)// 选择界面图片宽度
                .enableClickSound(false)// 是否开启点击声音
                //如果是在Activity里使用就传Activity，如果是在Fragment里使用就传Fragment
                .start(this, PhoenixOption.TYPE_PICK_MEDIA, REQUEST_CODE_CHOOSE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {//获得选择的图片
//              获取选择的图片数据
            //界面填充选择的图片数据
            if(data!=null&&Phoenix.result(data).size()!=0){
                mSelected.addAll(Phoenix.result(data));
                selectPicAdapter.setList(mSelected);
                selectPicAdapter.notifyDataSetChanged();
                Log.i(TAG,"已选图片获取完毕.........."+mSelected.size());
            }

        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
