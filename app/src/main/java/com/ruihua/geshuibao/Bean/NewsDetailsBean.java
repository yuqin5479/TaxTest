package com.ruihua.geshuibao.Bean;

public class NewsDetailsBean {

    /**
     * data : {"content":"<p>咨询新闻咨询新闻<\/p>","picture":null,"title":"咨询新闻","createDate":"2018-11-27 18:11:19","createBy":"运营客服1号","collect":false}
     * errormsg : 获取我的新闻详情成功
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
         * content : <p>咨询新闻咨询新闻</p>
         * picture : null
         * title : 咨询新闻
         * createDate : 2018-11-27 18:11:19
         * createBy : 运营客服1号
         * collect : false
         */

        private String content;
        private Object picture;
        private String title;
        private String createDate;
        private String createBy;
        private boolean collect;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getPicture() {
            return picture;
        }

        public void setPicture(Object picture) {
            this.picture = picture;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public boolean isCollect() {
            return collect;
        }

        public void setCollect(boolean collect) {
            this.collect = collect;
        }
    }
}
