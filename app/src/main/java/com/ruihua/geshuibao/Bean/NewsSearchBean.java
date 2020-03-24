package com.ruihua.geshuibao.Bean;

import java.util.List;

public class NewsSearchBean {

    /**
     * data : {"pageNo":"0","pageSize":"10","list":[{"picture":null,"id":"3dfa0692a46943d88ab2b6f858c92afc","title":"新闻你好","createDate":"2018-11-27","contentText":"新闻你好"},{"picture":null,"id":"baab013fcbc64a0dbb1d90e6f821b4c9","title":"咨询新闻","createDate":"2018-11-27","contentText":"咨询新闻咨询新闻"}],"maxNum":1}
     * errormsg : 获取我的新闻列表成功
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
         * pageNo : 0
         * pageSize : 10
         * list : [{"picture":null,"id":"3dfa0692a46943d88ab2b6f858c92afc","title":"新闻你好","createDate":"2018-11-27","contentText":"新闻你好"},{"picture":null,"id":"baab013fcbc64a0dbb1d90e6f821b4c9","title":"咨询新闻","createDate":"2018-11-27","contentText":"咨询新闻咨询新闻"}]
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
             * picture : null
             * id : 3dfa0692a46943d88ab2b6f858c92afc
             * title : 新闻你好
             * createDate : 2018-11-27
             * contentText : 新闻你好
             */

            private Object picture;
            private String id;
            private String title;
            private String createDate;
            private String contentText;

            public Object getPicture() {
                return picture;
            }

            public void setPicture(Object picture) {
                this.picture = picture;
            }

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
        }
    }
}
