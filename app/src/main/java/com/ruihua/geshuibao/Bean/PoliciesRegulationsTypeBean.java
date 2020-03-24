package com.ruihua.geshuibao.Bean;

import java.util.List;

public class PoliciesRegulationsTypeBean {
    /**
     * data : {"list":[{"id":"bb50b25fa9934fe08b723624405a9a80","type":"2","classifyName":"行业法规"},{"id":"bf645fe3a4f34fb18308966cf9f16a3b","type":"1","classifyName":"热词解析"}]}
     * errormsg : 获取我的政策法规类型成功
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : bb50b25fa9934fe08b723624405a9a80
             * type : 2
             * classifyName : 行业法规
             */

            private String id;
            private String type;
            private String classifyName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
