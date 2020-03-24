package com.ruihua.geshuibao.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.ruihua.geshuibao.Acivity.LoginActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.key;

/**
 * 简易封装网络请求（含链接请求过期）
 */
public class OkHttpUtils {
    public static OkHttpUtils mokhttpUtils;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
    public static final MediaType FORM_CONTENT_TYPE
            = MediaType.parse("application/json; charset=utf-8");
    public String TAG = "OkHttpUtils";
    public Context mContext;
    public Call call_get, call_post;
    public boolean cancle=true;
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Utils.showLongToast(mContext, "您的登录信息已过期，请重新登录");
                    Log.i(TAG, "handleMessage: " + "您的登录信息已过期，请重新登录");
                    break;
                case 500:
                    Utils.showToast(mContext, "数据异常500");
                    break;
                case 501:
                    Utils.showToast(mContext, "获取数据失败");
                    break;
                case 404:
                    Utils.showToast(mContext, "数据异常404");
                    break;
                case 405:
                    Utils.showToast(mContext, "请求超时");
                    break;
                case 406:
                    Log.i(TAG, "onFailure:++++++++++++-----");
                    Utils.showToast(mContext, "服务器遇到点问题,请稍后再试");
                    break;
            }
        }
    };

    public OkHttpUtils(Context context) {
        mHandler = new Handler(Looper.getMainLooper());
        int cacheSize = 10 * 1024 * 1024; //10M
        int cache = 10 << 20; //10M
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS) //连接超时时间
                .readTimeout(10, TimeUnit.SECONDS) //读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS) //写入超时时间
                .cache(new Cache(context.getCacheDir(), cache))//开启缓存
                .build();
        mContext = context;
    }

    /**
     * 单例化
     *
     * @return
     */
    public static OkHttpUtils newInstance(Context context) {
        if (mokhttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (mokhttpUtils == null) {
                    mokhttpUtils = new OkHttpUtils(context);
                }
            }
        }
        return mokhttpUtils;
    }

    public void postAsnycData(Map<String, String> map, String url, final OnReusltListener listener) {
        Log.i("url", "postAsnycData: " + url);
        Log.i("map", "postAsnycData: " + map.toString());

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry + "=" + map.get(key) + "&");
        }
        RequestBody body = RequestBody.create(FORM_CONTENT_TYPE, sb.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        call_post = mOkHttpClient.newCall(request);

        call_post.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                cancle=false;
                Log.i(TAG, "onFailure: " + e.getClass());
                if (e.getClass().getName().equals(SocketTimeoutException.class.getName())) {
                    handler.sendEmptyMessage(405);// 请求超时
                } else if (e.getClass().getName().equals(ConnectException.class.getName())) {
                    handler.sendEmptyMessage(406);
                }

                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onFailure(call, e);
                        }
                    }
                });

            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                cancle=false;
                final String result = response.body().string();
                if (response.code() == 500) {
                    listener.onFailure(call, new IOException("500"));
                    handler.sendEmptyMessage(500);
                } else if (response.code() == 404) {
                    listener.onFailure(call, new IOException("404"));
                    handler.sendEmptyMessage(404);
                } else if (response.code() == 200) {
                    try {
                        String errorcode = new JSONObject(result).optString("errorcode");
                        if (errorcode.equals("2012")) {//登录信息过期  需重新登录
                            mContext.sendBroadcast(new Intent().setAction("exit_app"));
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            SPUtils.clear();
                            handler.sendEmptyMessage(0);
                        } else if (errorcode.equals("101")) {
                            listener.onFailure(call, new IOException("501"));
                            handler.sendEmptyMessage(501);

                        } else {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (listener != null) {
                                        listener.onSucces(call, result);
                                    }
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        if(!isNetworkConnected(mContext)){
            Utils.showToast(mContext,"似乎已断开与互联网的连接");
        }
    }
    public void cannclehttp() {

        if (call_post != null&&!cancle) {

            call_post.cancel();
            Log.i(TAG, "cannclehttp: " + "请求已取消");
        }
        if (call_get != null&& !cancle) {

            Log.i(TAG, "cannclehttp: " + "get请求已取消");
            call_get.cancel();
        }
    }

//    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//        @Override
//        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs;
//            switch (code) {
//                case 0:
//                    logs = "Set tag and alias success极光推送别名设置成功";
//                    Log.i("llll", "gotResult: " + logs);
//                    break;
//                case 6002:
//                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.极光推送别名设置失败，60秒后重试";
//                    Log.i("llll", "gotResult: " + logs);
//                    break;
//                default:
//                    logs = "极光推送设置失败，Failed with errorCode = " + code;
//                    Log.i("llll", "gotResult: " + logs);
//                    break;
//            }
//        }
//    };

    public void getAsyncData(String url, final OnReusltListener listener) {
        Log.i("url", "getAsyncData: " + url);

        final Request request = new Request.Builder()
                .url(url)
                .build();
        call_get = mOkHttpClient.newCall(request);

        call_get.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                Log.i(TAG, "onFailure:取消请求 "+call.isCanceled());
                Log.i(TAG, "onFailure:获取数据失败--- "+e.getMessage()+"+++"+e.toString());
                Log.i(TAG, "onFailure:-- " + SocketTimeoutException.class.getName().equals(e.getClass().getName()));
                if (e.getClass().getName().equals(SocketTimeoutException.class.getName())) {
                    handler.sendEmptyMessage(405);// 请求超时
                } else if (e.getClass().getName().equals(ConnectException.class.getName())) {
                    handler.sendEmptyMessage(406);// 服务器无响应
                }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onFailure(call, e);
                            }
                        }
                    });
            }

            @Override
            public void onResponse(final Call call, Response response) throws IOException {
                final String result;
                result = response.body().string();

                if (response.code() == 500) {
                    listener.onFailure(call, new IOException("500"));
                    handler.sendEmptyMessage(500);
                } else if (response.code() == 404) {
                    listener.onFailure(call, new IOException("404"));
                    handler.sendEmptyMessage(404);
                } else if (response.code() == 200) {
                    try {
                        String errorCode = new JSONObject(result).optString("errorcode");
                        if (errorCode.equals("2012")) {
//                            JPushInterface.setAliasAndTags(mContext, "", null, mAliasCallback);
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                            SPUtils.clear(); //清除已保存用户数据
                            handler.sendEmptyMessage(0);
                        } else if (errorCode.equals("101")) {
                            onFailure(call, new IOException("501"));
                            handler.sendEmptyMessage(501);
                        } else {
                            Log.i(TAG, "onResponse: 取消" + call_get.isCanceled());
                                    mHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (listener != null)
                                                listener.onSucces(call, result);
                                        }
                                    });
                                }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        if(!isNetworkConnected(mContext)){
            Utils.showToast(mContext,"似乎已断开与互联网的连接");
        }
    }

    private class LogInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.v(TAG, "request:" + request.toString());
            long t1 = System.nanoTime();
            okhttp3.Response response = chain.proceed(chain.request());
            long t2 = System.nanoTime();
            Log.v("headers", String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            okhttp3.MediaType mediaType = response.body().contentType();
            String content = response.body().string();
            Log.i("okhttp", "response body:" + content);
            Log.i("responsecode", "intercept: " + response.isSuccessful() + response.code());
            return response.newBuilder()
                    .body(okhttp3.ResponseBody.create(mediaType, content))
                    .build();
        }
    }

    //返回给调用者
    public interface OnReusltListener {
        void onFailure(Call call, IOException e);
        void onSucces(Call call, String response);

    }

    /**
     * 判断是否有网络连接
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
         if (context != null) {
             ConnectivityManager mConnectivityManager = (ConnectivityManager) context
             .getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
             if (mNetworkInfo != null) {
                 return mNetworkInfo.isAvailable();
                 }
             }
         return false;
    }

}
