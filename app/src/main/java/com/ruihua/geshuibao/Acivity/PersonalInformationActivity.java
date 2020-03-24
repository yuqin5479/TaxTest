package com.ruihua.geshuibao.Acivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.PhoenixOption;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.core.model.MimeType;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.ruihua.geshuibao.Base.BaseActivity;
import com.ruihua.geshuibao.Base.BaseUrl;
import com.ruihua.geshuibao.Bean.PersonalInformationBean;
import com.ruihua.geshuibao.Bean.UpdatePersonalInformationBean;
import com.ruihua.geshuibao.Fragments.HomeFragment;
import com.ruihua.geshuibao.R;
import com.ruihua.geshuibao.Util.OkHttpUtils;
import com.ruihua.geshuibao.Util.OssService;
import com.ruihua.geshuibao.Util.SPUtils;
import com.ruihua.geshuibao.Util.Utils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
/**
 * 个人信息
 */
public class PersonalInformationActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_head_title)
    TextView tvHeadTitle;
    @BindView(R.id.tv_head_right)
    TextView tvHeadRight;
    @BindView(R.id.personal_information_iv_haed_img)
    ImageView mIvHaedImg;
    @BindView(R.id.personal_information_ll_haed_img)
    LinearLayout mHaedImg;
    @BindView(R.id.personal_information_tv_username)
    EditText mTvUsername;
    @BindView(R.id.personal_information_ll_user_name)
    LinearLayout mLlUserName;
    @BindView(R.id.personal_information_tv_tel)
    EditText mTvTel;
    @BindView(R.id.personal_information_ll_tel)
    LinearLayout mLlTel;
    @BindView(R.id.personal_information_tv_sex)
    TextView mTvSex;
    @BindView(R.id.personal_information_ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.personal_information_tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.personal_information_ll_birthday)
    LinearLayout mLlBirthday;
    @BindView(R.id.personal_information_tv_ns_id)
    EditText mTvNsId;
    @BindView(R.id.personal_information_ll_ns_id)
    LinearLayout mLlNsId;
    @BindView(R.id.personal_information_tv_id)
    EditText mTvId;
    @BindView(R.id.personal_information_ll_id)
    LinearLayout mLlId;

//    @BindView(R.id.personal_information_cb_is_disability)
//    CheckBox mCbIsDisability;
//    @BindView(R.id.personal_information_ll_disability_id)
//    LinearLayout mLlDisability;
//    @BindView(R.id.personal_information_et_disability_id)
//    EditText mEtDisabilityId;
//
//    @BindView(R.id.personal_information_cb_is_martyred)
//    CheckBox mCbIsMartyred;
//    @BindView(R.id.personal_information_ll_martyred_id)
//    LinearLayout mLlMartyred;
//    @BindView(R.id.personal_information_et_martyred_id)
//    EditText mEtMartyredId;
//
//    @BindView(R.id.personal_information_cb_is_old_people)
//    CheckBox mCbIsOldPeople;

//    private TimePickerView pvTime;//日期选择器
    private String userId,token;
    private PersonalInformationBean personalInformationBean;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private int REQUEST_CODE_CHOOSE = 1;
    private List<MediaEntity> mSelected;//选择的 头像文件
//    private static String cachePath = "/sdcard/myHead/";// sd路径
    private String haedAliYunUrl;
    private OssService ossService;
    private UpdatePersonalInformationBean updatePersonalInformationBean;
    @Override
    public int intiLayout() {
        return R.layout.activity_personal_information;
    }

    @Override
    public void initView() {
        tvHeadTitle.setText(getString(R.string.mine_personal_information));
        tvHeadRight.setVisibility(View.VISIBLE);
        tvHeadRight.setText(getString(R.string.personal_information_save));
//        mCbIsDisability.setOnCheckedChangeListener(this);
//        mCbIsMartyred.setOnCheckedChangeListener(this);
//        mCbIsOldPeople.setOnCheckedChangeListener(this);
        //只有头像、姓名、身份证号可编辑  其他数据从已有数据提取
        //如果已经加入单位便不可编辑姓名与身份证号只可编辑头像
        if(!TextUtils.isEmpty(HomeFragment.companyName))
            setEditData(false);
        else setEditData(true);
    }

    private void setEditData(boolean b) {
        mTvUsername.setEnabled(b);
        mTvId.setEnabled(b);
        if (b)//如不可编辑 隐藏保存按钮
            tvHeadRight.setVisibility(View.VISIBLE);
        else
            tvHeadRight.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        //初始化OssService类，参数分别是Content，accessKeyId，accessKeySecret，endpoint，bucketName（后4个参数是您自己阿里云Oss中参数
        ossService = new OssService(this, BaseUrl.AccessKey, BaseUrl.SecretKey, BaseUrl.ENDPOINT, BaseUrl.BucketName);
        //初始化OSSClient
        ossService.initOSSClient();
        userId = (String) SPUtils.get("userId","");
        token = (String) SPUtils.get("token","");
        getMyInforData();
//        pvTime = new TimePickerBuilder(this,onTimeSetListener)
//                //只显示年月日  三个
//                .setType(new boolean[]{true, true, true, false, false, false}).build();
    }
//    OnTimeSelectListener onTimeSetListener = new OnTimeSelectListener() {
//        @Override
//        public void onTimeSelect(Date date, View v) {
//            mTvBirthday.setText(format.format(date));
//        }
//    };
    private void getMyInforData() {
        loadingDialog.show();
        okHttpUtils.getAsyncData(BaseUrl.PERSONAL_INFORMATION
                        + "?appUserId=" + userId
                        + "&token=" + token,
                new OkHttpUtils.OnReusltListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        loadingDialog.dismiss();
                        toastShort("个人信息获取失败");
                    }

                    @Override
                    public void onSucces(Call call, String response) {
                        Log.i(TAG,"获取个人信息........."+response);
                        personalInformationBean = new Gson().fromJson(response,PersonalInformationBean.class);
                        loadingDialog.dismiss();
                        if(personalInformationBean!=null&&personalInformationBean.getErrorcode().equals("0000")){
                            setData();
                        }
                    }
                }
        );
    }

    private void setData() {
        Glide.with(this).load(personalInformationBean.getData().getImage()).apply(
                RequestOptions.bitmapTransform(new CircleCrop())
                        .error(R.drawable.m_header_nor)
                        .placeholder(R.drawable.m_header_nor)).into(mIvHaedImg);
        if(!TextUtils.isEmpty(personalInformationBean.getData().getName())){
            mTvUsername.setText(personalInformationBean.getData().getName());
            mTvUsername.setEnabled(false);
        }
        mTvTel.setText(personalInformationBean.getData().getMobile());
        //性别 和生日改为从身份证号识别提取显示
//        mTvSex.setText(personalInformationBean.getData().getSex());
//        mTvBirthday.setText(personalInformationBean.getData().getBirth());
        mTvSex.setText(Utils.getSexFromId(personalInformationBean.getData().getIdCard()));//身份证号码 提取性别
        mTvBirthday.setText(Utils.getBirthFromId(personalInformationBean.getData().getIdCard()));//身份证号码提取生日
        mTvNsId.setText(personalInformationBean.getData().getTaxpayerNo());
        mTvId.setText(personalInformationBean.getData().getIdCard());
    }


    @OnClick({R.id.iv_back,R.id.tv_head_right,R.id.personal_information_ll_haed_img, R.id.personal_information_ll_user_name,
            R.id.personal_information_ll_tel, R.id.personal_information_ll_sex,R.id.personal_information_ll_birthday,
            R.id.personal_information_ll_ns_id,R.id.personal_information_ll_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_head_right://保存按钮
                if(verifyData()){
                    updateInformation();
                }
                break;
            case R.id.personal_information_ll_haed_img:
//                checkPermissionRequestEachCombined(this);//动态获取权限后执行后续操作
                showSelectPopwindow();
                break;
//            case R.id.personal_information_ll_user_name:
//                break;
//            case R.id.personal_information_ll_tel:
//                break;
//            case R.id.personal_information_ll_sex:
//                showSexChooseDialog();
//                break;
//            case R.id.personal_information_ll_birthday://出生日期选择
//                pvTime.show();
//                break;
//            case R.id.personal_information_ll_ns_id:
//                break;
//            case R.id.personal_information_ll_id:
//                break;
        }
    }

    private boolean verifyData() {//校验必要数据是否符合规则
        if(!TextUtils.isEmpty(mTvNsId.getText().toString().trim())){
            if (!Utils.isTaxpayer(mTvNsId.getText().toString().trim())){
                toastShort("请输入正确的纳税人识别号");
                return false;
            }
        }
        if (!TextUtils.isEmpty(mTvId.getText().toString().trim())){
            if (!Utils.isIDCard(mTvId.getText().toString().trim())){
                toastShort("请输入正确的身份证号");
                return false;
            }
        }
        return true;
    }

    private void updateInformation() {
        loadingDialog.show();
        Map<String,String> informationData = new HashMap<>();
        informationData.put("appUserId",userId);
        informationData.put("token",token);
        informationData.put("idType","1");
        informationData.put("sex",mTvSex.getText().toString().trim());
        informationData.put("birth",mTvBirthday.getText().toString().trim());
        informationData.put("taxpayerNo",mTvNsId.getText().toString().trim());
        informationData.put("idCard",mTvId.getText().toString().trim());
        informationData.put("name",mTvUsername.getText().toString().trim());
        informationData.put("nationality","中国");
        okHttpUtils.postAsnycData(informationData, BaseUrl.UPDATA_PERSONAL_INFORMATION, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("信息修改失败");
            }

            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"个人信息修改...."+response);
                updatePersonalInformationBean = new Gson().fromJson(response,UpdatePersonalInformationBean.class);
                loadingDialog.dismiss();
                if (updatePersonalInformationBean!=null&&updatePersonalInformationBean.getErrorcode().equals("0000")){
                    getMyInforData();
                    toastShort(updatePersonalInformationBean.getErrormsg());
                }
            }
        });
    }

    private void showSexChooseDialog() {
        final String[] sexArry = new String[]{"女", "男"};// 性别选择
        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                mTvSex.setText(sexArry[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 显示选择头像popupWindow
     */
    private void showSelectPopwindow() {
        // 利用layoutInflater获得View
        final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.chooseheadimage, null);
        backgroundAlpha(0.5f);
        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(000000000);
        window.setBackgroundDrawable(dw);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(PersonalInformationActivity.this.findViewById(R.id.personal_information_ll_id),
                Gravity.BOTTOM, 0, 0);
        Button btn_paizhao = (Button) view.findViewById(R.id.btn_paizhao);
        Button btn_choose = (Button) view.findViewById(R.id.btn_choose);
        //本地选择图片
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSelectPic();
                window.dismiss();
            }
        });
        // 拍照
        btn_paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSelectPic();
                window.dismiss();
            }
        });

        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });

    }

    public void toSelectPic(){//跳转选择图片
        //发起图片选择
        Phoenix.with()
                .theme(PhoenixOption.THEME_BLUE)// 主题
                .fileType(MimeType.ofImage())//显示的文件类型图片、视频、图片和视频
                .maxPickNumber(1)// 最大选择数量
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

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {//获得选择的图片
//              获取选择的图片数据
            mSelected = Phoenix.result(data);
            UploadPic();
        }
    }

    private void UploadPic() {
        loadingDialog.show();
        final String picPath;
        if (!TextUtils.isEmpty(mSelected.get(0).getEditPath())){//如果用户编辑过就拿编辑后的图 没有编辑过就是压缩后的图
           picPath = mSelected.get(0).getEditPath();
        }else if(!TextUtils.isEmpty(mSelected.get(0).getCompressPath())){
            picPath = mSelected.get(0).getCompressPath();
        } else picPath = mSelected.get(0).getLocalPath();

        Log.i(TAG,"选择的图片路径...."+picPath);
        PutObjectRequest put = new PutObjectRequest(BaseUrl.BucketName,"app-img/photo/"+new File(picPath).getName(), picPath);
        @SuppressWarnings("rawtypes")
        OSSAsyncTask task = ossService.getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    //上传资源的URL是定死的。http:// + bucketName+ .服务器中心地址 + /你上传的资源objectKey
                    haedAliYunUrl = "/app-img/photo/"+new File(picPath).getName();
                    Log.i(TAG,"阿里云头像上传成功......"+haedAliYunUrl);
                    PushPicDate();//  图片阿里云上传 完毕 开始往自己服务器传参数

            }
            @Override public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    loadingDialog.dismiss();
                    toastShort("头像上传失败");
                Log.i(TAG,"阿里云头像图片传送失败...服务错误代码："+serviceException.getErrorCode()+"连接错误："+clientExcepion.getMessage());
            }
        });
    }

    private void PushPicDate() {//往阿里云传完图片  传送参数
        Map<String,String> urlData = new HashMap<>();
        urlData.put("id",userId);
        urlData.put("appUserId",userId);
        urlData.put("token",token);
        urlData.put("urls",haedAliYunUrl);
        urlData.put("type","20");//20 上传用户头像
        okHttpUtils.postAsnycData(urlData, BaseUrl.SAVE_FILE, new OkHttpUtils.OnReusltListener() {
            @Override
            public void onFailure(Call call, IOException e) {
                loadingDialog.dismiss();
                toastShort("头像上传失败");
            }
            @Override
            public void onSucces(Call call, String response) {
                Log.i(TAG,"头像上传成功......"+haedAliYunUrl+"...."+response);
                try {
                    if(new JSONObject(response).optString("errorcode").equals("0000")){
                        toastShort("头像上传成功");
                        Glide.with(PersonalInformationActivity.this).load("http://pitax.oss-cn-beijing.aliyuncs.com/"+haedAliYunUrl).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mIvHaedImg);
                    }else toastShort("头像上传失败");
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastShort("头像上传失败");
                }
                loadingDialog.dismiss();
            }
        });
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {//残疾、烈属、孤老 判断
//        switch (buttonView.getId()){
//            case R.id.personal_information_cb_is_disability:
//                if(isChecked)
//                    mLlDisability.setVisibility(View.VISIBLE);
//                else
//                    mLlDisability.setVisibility(View.GONE);
//                break;
//            case R.id.personal_information_cb_is_martyred:
//                if(isChecked)
//                    mLlMartyred.setVisibility(View.VISIBLE);
//                else
//                    mLlMartyred.setVisibility(View.GONE);
//                break;
//            case R.id.personal_information_cb_is_old_people:
//                break;
//        }
//    }
}
