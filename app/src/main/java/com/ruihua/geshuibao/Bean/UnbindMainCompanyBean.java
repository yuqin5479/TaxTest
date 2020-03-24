package com.ruihua.geshuibao.Bean;

public class UnbindMainCompanyBean {
    /**
     * data : {"companyId":"431111c0a7bb4b60bdeacfac9cda621b","success":true}
     * errormsg : 取消绑定主单位成功
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
         * companyId : 431111c0a7bb4b60bdeacfac9cda621b
         * success : true
         */

        private String companyId;
        private boolean success;

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }
}
