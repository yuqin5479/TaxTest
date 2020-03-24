package com.ruihua.geshuibao.Bean;

public class CompanyInfoBean {

    /**
     * data : {"number":"123456789GGG596314","legal_person":"张三三","phone":"15210510743","name":"无锡华彩网络科技有限公司","logo":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/logo/70239859015516.png","companyQrcode":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/rqcode/1540432409794431111c0a7bb4b60bdeacfac9cda621b.png"}
     * errorcode : 0000
     * errormsg : 查询单位信息成功
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
         * number : 123456789GGG596314
         * legal_person : 张三三
         * phone : 15210510743
         * name : 无锡华彩网络科技有限公司
         * logo : http://pitax.oss-cn-beijing.aliyuncs.com/app-img/logo/70239859015516.png
         * companyQrcode : http://pitax.oss-cn-beijing.aliyuncs.com/app-img/rqcode/1540432409794431111c0a7bb4b60bdeacfac9cda621b.png
         */

        private String number;
        private String legal_person;
        private String phone;
        private String name;
        private String logo;
        private String companyQrcode;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getLegal_person() {
            return legal_person;
        }

        public void setLegal_person(String legal_person) {
            this.legal_person = legal_person;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getCompanyQrcode() {
            return companyQrcode;
        }

        public void setCompanyQrcode(String companyQrcode) {
            this.companyQrcode = companyQrcode;
        }
    }
}
