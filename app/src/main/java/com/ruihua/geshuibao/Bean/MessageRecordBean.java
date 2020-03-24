package com.ruihua.geshuibao.Bean;

import java.util.List;

public class MessageRecordBean {
    /**
     * data : {"list":[{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:47:31","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:29:46","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:28:41","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:26:57","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:23:47","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:21:50","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:18:52","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:17:59","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:16:35","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:11:41","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"}],"maxNum":76}
     * errormsg : 分页获取历史记录成功
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
         * list : [{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:47:31","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:29:46","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:28:41","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:26:57","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:23:47","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:21:50","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:18:52","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:17:59","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"45cd05b4b34e44d5a5d0bd7a2c305534","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"平台客服2","createDate":"2018-12-19 16:16:35","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"},{"fromPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","fromId":"1c80878c057b46e2929058348b0268ed","toName":"杨测试","toId":"13d5f3d797844933b15183cacc648921","fromName":"运营客服1号","createDate":"2018-12-19 16:11:41","msg":"您好,瑞华个税宝客服人员很高兴为您服务!","toPhoto":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png"}]
         * maxNum : 76
         */

        private int maxNum;
        private List<ListBean> list;

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * fromPhoto : http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png
             * fromId : 45cd05b4b34e44d5a5d0bd7a2c305534
             * toName : 杨测试
             * toId : 13d5f3d797844933b15183cacc648921
             * fromName : 平台客服2
             * createDate : 2018-12-19 16:47:31
             * msg : 您好,瑞华个税宝客服人员很高兴为您服务!
             * toPhoto : http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png
             */

            private String fromPhoto;
            private String fromId;
            private String toName;
            private String toId;
            private String fromName;
            private String createDate;
            private String msg;
            private String toPhoto;

            public String getFromPhoto() {
                return fromPhoto;
            }

            public void setFromPhoto(String fromPhoto) {
                this.fromPhoto = fromPhoto;
            }

            public String getFromId() {
                return fromId;
            }

            public void setFromId(String fromId) {
                this.fromId = fromId;
            }

            public String getToName() {
                return toName;
            }

            public void setToName(String toName) {
                this.toName = toName;
            }

            public String getToId() {
                return toId;
            }

            public void setToId(String toId) {
                this.toId = toId;
            }

            public String getFromName() {
                return fromName;
            }

            public void setFromName(String fromName) {
                this.fromName = fromName;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public String getToPhoto() {
                return toPhoto;
            }

            public void setToPhoto(String toPhoto) {
                this.toPhoto = toPhoto;
            }
        }
    }
}
