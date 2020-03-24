package com.ruihua.geshuibao.Bean;

import java.util.List;

public class NotifierProPlusBean {

    /**
     * data : {"list":[{"contentText":"第三方大幅度发大幅度范德萨的","isRead":false,"msgId":"4a5c5ef3900c4f5aa9183eb423807370","classifyType":"2","id":"2392ef82f2514e1c80d984b19572d9ae","classifyPicture":["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/workReimbur/349137773939041.png"],"classifyName":"税务局消息","createDate":"2018-11-09 10:53:45"},{"contentText":"但是但是所所所多多","isRead":false,"msgId":"a5357e1194e840c8b7c4a0108720ad59","classifyType":"1","id":"5264703a3b9047bb852dabfd7b27ed50","classifyPicture":["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/workReimbur/349108582281193.png"],"classifyName":"系统消息","createDate":"2018-11-09 10:53:59"}]}
     * errorcode : 0000
     * errormsg : 查询消息通知分类成功
     */

    private DataBean data;
    private String errorcode;
    private String errormsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public static class DataBean {
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * contentText : 第三方大幅度发大幅度范德萨的
             * isRead : false
             * msgId : 4a5c5ef3900c4f5aa9183eb423807370
             * classifyType : 2
             * id : 2392ef82f2514e1c80d984b19572d9ae
             * classifyPicture : ["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/workReimbur/349137773939041.png"]
             * classifyName : 税务局消息
             * createDate : 2018-11-09 10:53:45
             */

            private String contentText;
            private boolean isRead;
            private String msgId;
            private String classifyType;
            private String id;
            private String classifyName;
            private String createDate;
            private List<String> classifyPicture;

            public String getContentText() {
                return contentText;
            }

            public void setContentText(String contentText) {
                this.contentText = contentText;
            }

            public boolean isIsRead() {
                return isRead;
            }

            public void setIsRead(boolean isRead) {
                this.isRead = isRead;
            }

            public String getMsgId() {
                return msgId;
            }

            public void setMsgId(String msgId) {
                this.msgId = msgId;
            }

            public String getClassifyType() {
                return classifyType;
            }

            public void setClassifyType(String classifyType) {
                this.classifyType = classifyType;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClassifyName() {
                return classifyName;
            }

            public void setClassifyName(String classifyName) {
                this.classifyName = classifyName;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public List<String> getClassifyPicture() {
                return classifyPicture;
            }

            public void setClassifyPicture(List<String> classifyPicture) {
                this.classifyPicture = classifyPicture;
            }
        }
    }
}
