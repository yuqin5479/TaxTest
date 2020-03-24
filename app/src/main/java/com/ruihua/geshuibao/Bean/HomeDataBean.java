package com.ruihua.geshuibao.Bean;

import java.util.List;

public class HomeDataBean {

    /**
     * data : {"phone":"13718425479","companyName":"","name":"玉琴","photo":"http://pitax.oss-cn-beijing.aliyuncs.com//app-img/photo/IMG_1542348201985.jpg","middleList":[{"isRead":false,"classifyType":"1","id":"e077739088ad490cac7172816417920d","title":"系统消息第三条","gsMsgClassify":{"classifyId":"5264703a3b9047bb852dabfd7b27ed50","classifyName":"系统消息"},"createDate":"2018-11-16 09:48:23"},{"isRead":false,"classifyType":"2","id":"01807f9e96b042279688b763f85800e0","title":"税务局第三条","gsMsgClassify":{"classifyId":"2392ef82f2514e1c80d984b19572d9ae","classifyName":"税务局消息"},"createDate":"2018-11-16 09:46:49"}],"totalSystemMsgList":[{"id":"46e2a1bd46ee45acaddfa785a2656696","title":"系统消息第一条"},{"id":"5191b119b1f84b72ab744f335befeabd","title":"系统消息第二条"},{"id":"e077739088ad490cac7172816417920d","title":"系统消息第三条"}],"totalNotReadMsg":true}
     * errorcode : 0000
     * errormsg : 查询首页成功
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
         * phone : 13718425479
         * companyName :
         * name : 玉琴
         * photo : http://pitax.oss-cn-beijing.aliyuncs.com//app-img/photo/IMG_1542348201985.jpg
         * middleList : [{"isRead":false,"classifyType":"1","id":"e077739088ad490cac7172816417920d","title":"系统消息第三条","gsMsgClassify":{"classifyId":"5264703a3b9047bb852dabfd7b27ed50","classifyName":"系统消息"},"createDate":"2018-11-16 09:48:23"},{"isRead":false,"classifyType":"2","id":"01807f9e96b042279688b763f85800e0","title":"税务局第三条","gsMsgClassify":{"classifyId":"2392ef82f2514e1c80d984b19572d9ae","classifyName":"税务局消息"},"createDate":"2018-11-16 09:46:49"}]
         * totalSystemMsgList : [{"id":"46e2a1bd46ee45acaddfa785a2656696","title":"系统消息第一条"},{"id":"5191b119b1f84b72ab744f335befeabd","title":"系统消息第二条"},{"id":"e077739088ad490cac7172816417920d","title":"系统消息第三条"}]
         * totalNotReadMsg : true
         */

        private String phone;
        private String companyName;
        private String name;
        private String photo;
        private boolean totalNotReadMsg;
        private List<MiddleListBean> middleList;
        private List<TotalSystemMsgListBean> totalSystemMsgList;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public boolean isTotalNotReadMsg() {
            return totalNotReadMsg;
        }

        public void setTotalNotReadMsg(boolean totalNotReadMsg) {
            this.totalNotReadMsg = totalNotReadMsg;
        }

        public List<MiddleListBean> getMiddleList() {
            return middleList;
        }

        public void setMiddleList(List<MiddleListBean> middleList) {
            this.middleList = middleList;
        }

        public List<TotalSystemMsgListBean> getTotalSystemMsgList() {
            return totalSystemMsgList;
        }

        public void setTotalSystemMsgList(List<TotalSystemMsgListBean> totalSystemMsgList) {
            this.totalSystemMsgList = totalSystemMsgList;
        }

        public static class MiddleListBean {
            /**
             * isRead : false
             * classifyType : 1
             * id : e077739088ad490cac7172816417920d
             * title : 系统消息第三条
             * gsMsgClassify : {"classifyId":"5264703a3b9047bb852dabfd7b27ed50","classifyName":"系统消息"}
             * createDate : 2018-11-16 09:48:23
             */

            private boolean isRead;
            private String classifyType;
            private String id;
            private String title;
            private GsMsgClassifyBean gsMsgClassify;
            private String createDate;

            public boolean isIsRead() {
                return isRead;
            }

            public void setIsRead(boolean isRead) {
                this.isRead = isRead;
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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public GsMsgClassifyBean getGsMsgClassify() {
                return gsMsgClassify;
            }

            public void setGsMsgClassify(GsMsgClassifyBean gsMsgClassify) {
                this.gsMsgClassify = gsMsgClassify;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public static class GsMsgClassifyBean {
                /**
                 * classifyId : 5264703a3b9047bb852dabfd7b27ed50
                 * classifyName : 系统消息
                 */

                private String classifyId;
                private String classifyName;

                public String getClassifyId() {
                    return classifyId;
                }

                public void setClassifyId(String classifyId) {
                    this.classifyId = classifyId;
                }

                public String getClassifyName() {
                    return classifyName;
                }

                public void setClassifyName(String classifyName) {
                    this.classifyName = classifyName;
                }
            }
        }

        public static class TotalSystemMsgListBean {
            /**
             * id : 46e2a1bd46ee45acaddfa785a2656696
             * title : 系统消息第一条
             */

            private String id;
            private String title;

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
        }
    }
}
