package com.ruihua.geshuibao.Bean;

public class UpdatePersonalInformationBean {

    /**
     * data : {"taxpayerNo":"","image":"http://pitax.oss-cn-beijing.aliyuncs.com//app-img/photo/DCIM1542338188243.png","companyId":"","read":"0","idCard":"","name":"","mobile":"","id":"d4be961e616d4ee3934a6854971ef55b","userName":"13718425479"}
     * errorcode : 0000
     * errormsg : 修改用户信息成功
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
         * image : http://pitax.oss-cn-beijing.aliyuncs.com//app-img/photo/DCIM1542338188243.png
         * companyId :
         * read : 0
         * idCard :
         * name :
         * mobile :
         * id : d4be961e616d4ee3934a6854971ef55b
         * userName : 13718425479
         */

        private String taxpayerNo;
        private String image;
        private String companyId;
        private String read;
        private String idCard;
        private String name;
        private String mobile;
        private String id;
        private String userName;

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

        public String getRead() {
            return read;
        }

        public void setRead(String read) {
            this.read = read;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
    }
}
