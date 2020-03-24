package com.ruihua.geshuibao.Bean;

import java.util.List;

public class MessageListBean {
    /**
     * data : {"pageNo":"1","pageSize":"10","list":[{"picture":[],"id":"b78346563eee412c97ada46c6623dafe","title":"税务消息","createDate":"2018-11-26 16:03:28","contentText":"税务消息","isRead":true}],"maxNum":1}
     * errormsg : 查询消息通知成功
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
         * pageNo : 1
         * pageSize : 10
         * list : [{"picture":[],"id":"b78346563eee412c97ada46c6623dafe","title":"税务消息","createDate":"2018-11-26 16:03:28","contentText":"税务消息","isRead":true}]
         * maxNum : 1
         */

        private String pageNo;
        private String pageSize;
        private int maxNum;
        private List<ListBean> list;

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

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
             * picture : []
             * id : b78346563eee412c97ada46c6623dafe
             * title : 税务消息
             * createDate : 2018-11-26 16:03:28
             * contentText : 税务消息
             * isRead : true
             */

            private String id;
            private String title;
            private String createDate;
            private String contentText;
            private boolean isRead;
            private List<?> picture;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public List<?> getPicture() {
                return picture;
            }

            public void setPicture(List<?> picture) {
                this.picture = picture;
            }
        }
    }
}
