package com.ruihua.geshuibao.Bean;

import java.util.List;

public class MonthlySpecialExpenseDeductionBean {

    /**
     * data : {"id":"1aa80f0d8add4da4a8c8563ba21904c7","type":"0","deductions":[{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/childrenEducation.png","money":"0.0","name":"子女教育","id":"68e6af2b86d04ab1b84e68f0c9d131dd","type":"10"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/continuingEducation.png","money":"0.0","name":"继续教育","id":"658b6b4f4dea41ea97e1e1bfca769e22","type":"11"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/houseLoan.png","money":"0.0","name":"住房贷款公积金利息","id":"71aa4063b01a47a78e95bc93c418c2bd","type":"13"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/houseRent.png","money":"0.0","name":"住房租金","id":"2be957aa1c5742f1abac39cb46946659","type":"14"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/supportOld.png","money":"0.0","name":"赡养老人","id":"207344ac9e634b74a24bb7237f9e12aa","type":"15"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/illnessMedical.png","money":"0.0","name":"大病医疗","id":"526d0e6c75ad4dcb9260f4493aa8c0fd","type":"12"}],"uploadTime":"","others":[{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/annuity.png","money":"0.0","name":"年金","id":"45edc85599984056b7ba8e6cffeb99ea","type":"16"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/businessHealth.png","money":"0.0","name":"商业健康保险","id":"43c2d68302334d0386fa070930b0c1aa","type":"17"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/otherDeduction.png","money":"0.0","name":"其他扣除","id":"286595c5d13c4a9998c69ef1eacaac53","type":"18"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/pensionInsurance.png","money":"0.0","name":"税延养老保险","id":"6e44cf016fde41559940151f083990ef","type":"19"}]}
     * errorcode : 0000
     * errormsg : 获取专项附加扣除接口成功
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
         * id : 1aa80f0d8add4da4a8c8563ba21904c7
         * type : 0
         * deductions : [{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/childrenEducation.png","money":"0.0","name":"子女教育","id":"68e6af2b86d04ab1b84e68f0c9d131dd","type":"10"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/continuingEducation.png","money":"0.0","name":"继续教育","id":"658b6b4f4dea41ea97e1e1bfca769e22","type":"11"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/houseLoan.png","money":"0.0","name":"住房贷款公积金利息","id":"71aa4063b01a47a78e95bc93c418c2bd","type":"13"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/houseRent.png","money":"0.0","name":"住房租金","id":"2be957aa1c5742f1abac39cb46946659","type":"14"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/supportOld.png","money":"0.0","name":"赡养老人","id":"207344ac9e634b74a24bb7237f9e12aa","type":"15"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/illnessMedical.png","money":"0.0","name":"大病医疗","id":"526d0e6c75ad4dcb9260f4493aa8c0fd","type":"12"}]
         * uploadTime :
         * others : [{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/annuity.png","money":"0.0","name":"年金","id":"45edc85599984056b7ba8e6cffeb99ea","type":"16"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/businessHealth.png","money":"0.0","name":"商业健康保险","id":"43c2d68302334d0386fa070930b0c1aa","type":"17"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/otherDeduction.png","money":"0.0","name":"其他扣除","id":"286595c5d13c4a9998c69ef1eacaac53","type":"18"},{"image":"http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/pensionInsurance.png","money":"0.0","name":"税延养老保险","id":"6e44cf016fde41559940151f083990ef","type":"19"}]
         */

        private String id;
        private String type;
        private String uploadTime;
        private List<DeductionsBean> deductions;
        private List<OthersBean> others;

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

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public List<DeductionsBean> getDeductions() {
            return deductions;
        }

        public void setDeductions(List<DeductionsBean> deductions) {
            this.deductions = deductions;
        }

        public List<OthersBean> getOthers() {
            return others;
        }

        public void setOthers(List<OthersBean> others) {
            this.others = others;
        }

        public static class DeductionsBean {
            /**
             * image : http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/childrenEducation.png
             * money : 0.0
             * name : 子女教育
             * id : 68e6af2b86d04ab1b84e68f0c9d131dd
             * type : 10
             */

            private String image;
            private String money;
            private String name;
            private String id;
            private String type;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public static class OthersBean {
            /**
             * image : http://pitax.oss-cn-beijing.aliyuncs.com/app-img/gsExpDeduction/annuity.png
             * money : 0.0
             * name : 年金
             * id : 45edc85599984056b7ba8e6cffeb99ea
             * type : 16
             */

            private String image;
            private String money;
            private String name;
            private String id;
            private String type;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
