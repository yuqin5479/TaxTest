package com.ruihua.geshuibao.Bean;

import java.util.List;

public class NewsFindBean {
    /**
     * data : {"pageNo":"1","pageSize":"10","maxNum":1,"list":[{"id":"c5e393cba73046c0a77ef24d21f6d676","title":"21-2","picture":["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/logo/20661890416560.png"],"createDate":"2018-11-12"},{"id":"7cd6234dc7b640499228c71b388e7006","title":"21-1","picture":["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/logo/20650348408130.png"],"createDate":"2018-11-12"}]}
     * errorcode : 0000
     * errormsg : 获取我的新闻列表
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
        /**
         * pageNo : 1
         * pageSize : 10
         * maxNum : 1
         * list : [{"id":"c5e393cba73046c0a77ef24d21f6d676","title":"21-2","picture":["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/logo/20661890416560.png"],"createDate":"2018-11-12"},{"id":"7cd6234dc7b640499228c71b388e7006","title":"21-1","picture":["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/logo/20650348408130.png"],"createDate":"2018-11-12"}]
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
             * id : c5e393cba73046c0a77ef24d21f6d676
             * title : 21-2
             * picture : ["http://pitax.oss-cn-beijing.aliyuncs.com/app-img/logo/20661890416560.png"]
             * createDate : 2018-11-12
             */

            private String id;
            private String title;
            private String createDate;
            private List<String> picture;

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

            public List<String> getPicture() {
                return picture;
            }

            public void setPicture(List<String> picture) {
                this.picture = picture;
            }
        }
    }
}
