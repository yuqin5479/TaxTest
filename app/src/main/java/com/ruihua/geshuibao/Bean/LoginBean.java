package com.ruihua.geshuibao.Bean;

public class LoginBean {

    /**
     * data : {"taxpayerNo":"110119120100861554652","image":"http://pitax.oss-cn-beijing.aliyuncs.com//app-img/photo/DCIM1542535855653.png","companyId":"","mobile":"13718425479","id":"d4be961e616d4ee3934a6854971ef55b","userName":"13718425479","token":"7d048431c434d0e05f84d2a34a98f99d"}
     * errorcode : 0000
     * errormsg : 登录成功
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
         * taxpayerNo : 110119120100861554652
         * image : http://pitax.oss-cn-beijing.aliyuncs.com//app-img/photo/DCIM1542535855653.png
         * companyId :
         * mobile : 13718425479
         * id : d4be961e616d4ee3934a6854971ef55b
         * userName : 13718425479
         * token : 7d048431c434d0e05f84d2a34a98f99d
         */

        private String taxpayerNo;
        private String image;
        private String companyId;
        private String mobile;
        private String id;
        private String userName;
        private String token;

        public String getTaxpayerNo() {
            return taxpayerNo;
        }

        public void setTaxpayerNo(String taxpayerNo) {
            this.taxpayerNo = taxpayerNo;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
