package com.ruihua.geshuibao.Base;
public class BaseUrl {
//    public static String BASE_URL = "";正式
    public static String BASE_URL = "http://gsb-test.sruihua.com:8080/PITax/f/";//测试
//    阿里云存储对象  配置信息
    public static String AccessKey = "LTAIxDFOKr4FIs3j";
    public static String SecretKey = "dcouzJWhit8DjNabl9kvlitSxIWBvQ";
    public static String BucketName = "gsb2018";
    public static String ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
//    public static String ENDPOINT2 = ".oss-cn-hangzhou.aliyuncs.com";

    public static String xieyiUrl = "http://cdn.gangwaninfo.com/jeeplus-resource-data/static/JiangjiangProcotal/xieyi.html";

    public static String MAIN_DATA = BASE_URL + "/api/company/indexInfo";//获取首页数据
    public static String REGISTER = BASE_URL+ "api/register/registerMember";//注册接口  mobile=15878900387&password=123456
    public static String LOGIN = BASE_URL +"api/login/getLoginMap";//  登录接口 userName=13900997878&password=123456&
    public static String RETRIEVE_PASSWORD = BASE_URL +"api/register/retrievePassword";//重置密码接口 mobile=2&newPassword=good
    public static String LOGOUT = BASE_URL +"api/login/logout";//退出登录接口
    public static String LOGIN_QUICKLY = BASE_URL+"api/login/getLoginMapQuickly";//验证码快速登录接口
    public static String SEND_MOBILE_CODE = BASE_URL+ "api/register/sendMobileCode";//获取验证码接口  mobile=159****2452&type=1
    public static String SEND_MOBILE_CODE2 = BASE_URL+ "api/company/sendMobileCode";//单位认证获取验证码接口  mobile=159****2452&type=8
    public static String NEWS_TYPE = BASE_URL +"api/MsgnoticeApi/newType";//获取新闻类型接口 无参数
    public static String NEWS_FIND = BASE_URL +"api/MsgnoticeApi/newFind";//获取新闻列表接口/新闻搜索接口 pageNo=0&pageSize=10&classifyId=
    public static String PERSONAL_INFORMATION = BASE_URL + "api/login/getAppUserMap";//获取个人信息接口 appUserId=69E7EF597E234002AE149DA85F150271&token=123456
    public static String UPDATA_PERSONAL_INFORMATION = BASE_URL + "api/login/updateAppUserMap";//修改个人信息
    public static String SAVE_FILE = BASE_URL + "api/utils/saveFile";//保存附件接口 appUserId=69E7EF597E234002AE149DA85F150271&token=123456&id=&urls=&type=
    public static String IS_IMPROVE_PERSONAL_INFO = BASE_URL + "api/company/isImprovePersonalInfo";//用来查询是否已经完善个人信息
    public static String QUERV_JOIN_COMPANY = BASE_URL + "api/company/queryJoinCompany";//搜索加入企业接口 name=无锡
    public static String COMPANY_INFO = BASE_URL + "api/company/companyInfo";//获取单位详情接口  appUserId=69E7EF597E234002AE149DA85F150271&token=123456&id=431111c0a7bb4b60bdeacfac9cda621b
    public static String JOIN_COMPANY = BASE_URL + "api/company/joinCompany";//加入单位接口 appUserId=69E7EF597E234002AE149DA85F150271&token=123456&id=431111c0a7bb4b60bdeacfac9cda621b
    public static String MSG_CLASSIFY = BASE_URL + "api/company/queryMsgClassify";//查询消息通知分类接口 appUserId=69E7EF597E234002AE149DA85F150271&token=123456
    public static String EXP_DEDUCTION_TYP = BASE_URL + "api/gs/gsExpDeductionType";//获取月度专项附加扣除及其他扣除类型列表 token=123456&appUserId=69E7EF597E234002AE149DA85F150271
    public static String EXP_DEDUCTION_INFO = BASE_URL + "api/gs/gsExpDeductionInfo";//获取月度专项附加扣除及其他扣除项详情 token=123456&appUserId=69E7EF597E234002AE149DA85F150271&id=123456
    public static String FIND_NOTICE_DETAIL = BASE_URL + "api/counsel/findNoticeDetail";//获取月度专项附加扣除及其他扣除项详情中的提示  noticeType=10
    public static String DeductionInfoEdit = BASE_URL + "api/gs/gsExpDeductionInfoEdit";//专项附加扣除修改接口
    public static String uploadGs = BASE_URL + "api/gs/uploadGs";//月度专项附加扣除及其他扣除上传接口
    public static String isPurchase = BASE_URL + "api/gs/isPurchase";//获取单位是否已采购
    public static String insertAppUserInfoMap = BASE_URL + "api/login/updateAppUserMap";//完善个人信息接口
    public static String queryMsgDetail = BASE_URL + "api/company/queryMsgDetail";//获取消息详情接口 appUserId=69E7EF597E234002AE149DA85F150271&token=123456&id=f20019d72f6f4f33a5e21e2b37d22e9b
    public static String newDetails = BASE_URL + "api/MsgnoticeApi/newDetails";//获取新闻详情
    public static String myJoinCompany = BASE_URL + "api/company/myJoinCompany";//获取我的单位  appUserId=69E7EF597E234002AE149DA85F150271&token=123456
    public static String unbindMainCompany = BASE_URL + "api/company/unbindMainCompany";//解除绑定社保单位接口appUserId=69E7EF597E234002AE149DA85F150271&token=123456&id=431111c0a7bb4b60bdeacfac9cda621b
    public static String setSecurityCompany = BASE_URL + "api/company/setSecurityCompany";//设为劳动合同单位（设为社保单位）
    public static String cancelMoneyCompany = BASE_URL + "api/company/cancelMoneyCompany";//取消当前授薪单位
    public static String querySelfClassify = BASE_URL + "api/company/querySelfClassify";//自助问答分类接口
    public static String querySelfManagerById = BASE_URL + "api/company/querySelfManagerById";//根据咨询自助问答分类id查询自助问答
    public static String lawType = BASE_URL + "api/MsgnoticeApi/lawType";//政策法规类型接口  无参数
    public static String lawFind = BASE_URL + "api/MsgnoticeApi/lawFind";//获取政策法规列表 pageNo=0&pageSize=10&classifyId=
    public static String lawDetails = BASE_URL + "api/MsgnoticeApi/lawDetails";//政策法规详情 Id=380ccb9dfa2045d3bbe6f74
    public static String companyAuthentication = BASE_URL + "api/company/add";//单位认证接口
    public static String queryMsg = BASE_URL + "api/company/queryMsg";//根据分类id获取消息通知列表   appUserId=69E7EF597E234002AE149DA85F150271&token=123456&id=5264703a3b9047bb852dabfd7b27ed50&pageNo=1&pageSize=10
    public static String querytotalMs = BASE_URL + "api/company/querytotalMsg";//消息全部已读 appUserId=69E7EF597E234002AE149DA85F150271&token=123456&id=""
    public static String newCollect = BASE_URL + "api/MsgnoticeApi/newCollect";//新闻收藏接口
    public static String delnewscollect = BASE_URL + "api/MsgnoticeApi/delnewscollect";//新闻取消收藏接口
    public static String MynewList = BASE_URL + "api/MsgnoticeApi/newList";//我的收藏 新闻资讯列表接口
    public static String delcollect = BASE_URL + "api/MsgnoticeApi/delcollect";//清空我的收藏接口
    public static String myGsByMonth = BASE_URL + "api/gs/myGsByMonth";//获取我的申报列表接口
    public static String helpImInfo = BASE_URL + "api/im/helpImInfo";//获取IM详情 联系单位、平台客服 token=2ab82cb6c2fe7c1e5421731ff60af149&appUserId=1985DBC441B64A86B54ECF2050504F63&type=1&companyId=8852fa2f4b024e5e9ab8601b882e9367
    public static String imChatHistoryPage = BASE_URL + "api/im/imChatHistoryPage";//获取IM消息记录
    public static String imChatHistoryType = BASE_URL + "api/im/imChatHistoryType";//获取咨询记录
    public static String delImChatHistory = BASE_URL + "api/im/ delImChatHistory";//清空咨询记录


}
