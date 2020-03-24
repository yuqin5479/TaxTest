package com.ruihua.geshuibao.Bean;

import java.util.List;

public class NewsTypeBean {

    /**
     * data : {"list":[{"id":"48c28b02eee944029e35bf6131a0cbf9","classifyName":"策划案例"},{"id":"5f0aa26c0d874cb4b69634206303858d","classifyName":"税务要闻"}]}
     * errorcode : 0000
     * errormsg : 获取我的新闻类型
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
             * id : 48c28b02eee944029e35bf6131a0cbf9
             * classifyName : 策划案例
             */

            private String id;
            private String classifyName;

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
        }
    }
}
