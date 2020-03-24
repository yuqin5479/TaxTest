package com.ruihua.geshuibao.Util;

import android.content.Context;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;

public class OssService {
    private OSS oss;
    private String accessKeyId;
    private String bucketName;
    private String accessKeySecret;
    private String endpoint;
    private Context context;
    public OssService(Context context, String accessKeyId, String accessKeySecret, String endpoint, String bucketName) {
        this.context = context;
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
    }
    public void initOSSClient() {
        //OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider("<StsToken.AccessKeyId>", "<StsToken.SecretKeyId>", "<StsToken.SecurityToken>");
        // 这个初始化安全性没有Sts安全，如需要很高安全性建议用OSSStsTokenCredentialProvider创建（上一行创建方式）多出的参数SecurityToken为临时授权参数
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);// 连接超时，默认15秒
         conf.setSocketTimeout(15 * 1000);// socket超时，默认15秒
         conf.setMaxConcurrentRequest(8);// 最大并发请求数，默认5个
         conf.setMaxErrorRetry(2);// 失败后最大重试次数，默认2次
        // oss为全局变量，endpoint是一个OSS区域地址
        oss = new OSSClient(context, endpoint, credentialProvider, conf);
    }

    public OSS getOss(){
        if(oss!=null){
            return oss;
        }
        return null;
    }

}
