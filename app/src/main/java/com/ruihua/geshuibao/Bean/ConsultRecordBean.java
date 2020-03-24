package com.ruihua.geshuibao.Bean;

import java.util.List;

public class ConsultRecordBean {
    /**
     * data : {"list":[{"status":"已回复","name":"平台客服","image":"http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png","createDate":"2018-12-20 10:02:49","type":"1","msg":"大青蛙让我去"}]}
     * errormsg : 获取历史记录接口成功
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
             * status : 已回复
             * name : 平台客服
             * image : http://gsb2018.oss-cn-hangzhou.aliyuncs.com/pitax-resource-data/static/im/imLogo.png
             * createDate : 2018-12-20 10:02:49
             * type : 1
             * msg : 大青蛙让我去
             */

            private String status;
            private String name;
            private String image;
            private String createDate;
            private String type;
            private String msg;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMsg() {
                return msg;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }
        }
    }
}
