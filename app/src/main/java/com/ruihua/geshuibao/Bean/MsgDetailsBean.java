package com.ruihua.geshuibao.Bean;

import java.util.List;

public class MsgDetailsBean {

    /**
     * data : {"picture":[""],"content":"<p>系统消息<\/p>","title":"系统消息","createDate":"2018-11-26 16:03:01","createBy":"运营"}
     * errormsg : 查询消息详情成功
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
         * picture : [""]
         * content : <p>系统消息</p>
         * title : 系统消息
         * createDate : 2018-11-26 16:03:01
         * createBy : 运营
         */

        private String content;
        private String title;
        private String createDate;
        private String createBy;
        private List<String> picture;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public List<String> getPicture() {
            return picture;
        }

        public void setPicture(List<String> picture) {
            this.picture = picture;
        }
    }
}
