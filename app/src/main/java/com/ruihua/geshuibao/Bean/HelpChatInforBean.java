package com.ruihua.geshuibao.Bean;

public class HelpChatInforBean {

    /**
     * data : {"content":"您好,瑞华个税宝客服人员很高兴为您服务!","userId":"1c80878c057b46e2929058348b0268ed","image":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","userName":"运营客服1号"}
     * errormsg : 发送提示语成功
     * errorcode : 0000
     */

    private DataBean data;
    private String errormsg;
    private String errorcode;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public static class DataBean {
        /**
         * content : 您好,瑞华个税宝客服人员很高兴为您服务!
         * userId : 1c80878c057b46e2929058348b0268ed
         * image : http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png
         * userName : 运营客服1号
         */

        private String content;
        private String userId;
        private String image;
        private String userName;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
