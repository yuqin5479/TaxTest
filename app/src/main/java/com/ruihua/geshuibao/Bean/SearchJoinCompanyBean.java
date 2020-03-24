package com.ruihua.geshuibao.Bean;

import java.util.List;

public class SearchJoinCompanyBean {

    /**
     * data : {"list":[{"name":"无锡华彩网络科技有限公司","id":"431111c0a7bb4b60bdeacfac9cda621b"},{"name":"无锡港湾","id":"826b6192c9184d7b8dde144df661c9d5"}]}
     * errorcode : 0000
     * errormsg : 查询加入单位成功
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
             * name : 无锡华彩网络科技有限公司
             * id : 431111c0a7bb4b60bdeacfac9cda621b
             */

            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }
    }
}
