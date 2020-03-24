package com.ruihua.geshuibao.Bean;

public class ResetPasswordBean {

    /**
     * data : {"mobile":"13718425479"}
     * errormsg : 找回密码成功
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
         * mobile : 13718425479
         */

        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
