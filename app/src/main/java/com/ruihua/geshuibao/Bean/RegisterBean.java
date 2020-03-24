package com.ruihua.geshuibao.Bean;

import java.util.List;

public class RegisterBean {

    /**
     * data : {"taxpayerNo":"","image":[],"companyId":"","mobile":"13766666666","userName":"13766666666","userId":"B1C934D9E48F461D938F4595DD9ACF32","token":"7ae037d7cb519aff2825323c866a6e3b"}
     * errorcode : 0000
     * errormsg : 注册前端会员成功
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
         * taxpayerNo :
         * image : []
         * companyId :
         * mobile : 13766666666
         * userName : 13766666666
         * userId : B1C934D9E48F461D938F4595DD9ACF32
         * token : 7ae037d7cb519aff2825323c866a6e3b
         */

        private String taxpayerNo;
        private String companyId;
        private String mobile;
        private String userName;
        private String userId;
        private String token;
        private List<?> image;

        public String getTaxpayerNo() {
            return taxpayerNo;
        }

        public void setTaxpayerNo(String taxpayerNo) {
            this.taxpayerNo = taxpayerNo;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public List<?> getImage() {
            return image;
        }

        public void setImage(List<?> image) {
            this.image = image;
        }
    }
}
