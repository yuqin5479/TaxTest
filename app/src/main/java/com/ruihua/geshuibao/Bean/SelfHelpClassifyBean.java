package com.ruihua.geshuibao.Bean;

import java.util.List;

public class SelfHelpClassifyBean {
    /**
     * data : {"list":[{"id":"0cee952727fc44cd92ca0716b51778c7","classify_name":"法律"},{"id":"533491ec9e204b42b079fd50cba5ae38","classify_name":"个税"}]}
     * errormsg : 查询咨询自助问答分类成功
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
             * id : 0cee952727fc44cd92ca0716b51778c7
             * classify_name : 法律
             */

            private String id;
            private String classify_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getClassify_name() {
                return classify_name;
            }

            public void setClassify_name(String classify_name) {
                this.classify_name = classify_name;
            }
        }
    }
}
