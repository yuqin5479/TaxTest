package com.ruihua.geshuibao.Bean;

import java.util.List;

public class SpecialExpenseDeductionDetailsBean {
    /**
     * data : {"id":"658b6b4f4dea41ea97e1e1bfca769e22","isNewRecord":false,"remarks":null,"createDate":"2018-11-23 02:52:51","updateDate":"2018-11-23 02:52:51","actualDeductionAmount":0,"deductionDate":"2018-11-23 02:52:51","deductionId":"1aa80f0d8add4da4a8c8563ba21904c7","companyId":"726fab21051d4759b1b191c42901488c","taxNumber":null,"policyEffective":null,"policyMoney":null,"monthMoney":null,"deductionAmount":null,"type":"2","title":"继续教育","companyName":"南京港湾","workAttachments":[]}
     * errormsg : 获取专项附加扣除单项详情接口成功
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
         * id : 658b6b4f4dea41ea97e1e1bfca769e22
         * isNewRecord : false
         * remarks : null
         * createDate : 2018-11-23 02:52:51
         * updateDate : 2018-11-23 02:52:51
         * actualDeductionAmount : 0.0
         * deductionDate : 2018-11-23 02:52:51
         * deductionId : 1aa80f0d8add4da4a8c8563ba21904c7
         * companyId : 726fab21051d4759b1b191c42901488c
         * taxNumber : null
         * policyEffective : null
         * policyMoney : null
         * monthMoney : null
         * deductionAmount : null
         * type : 2
         * title : 继续教育
         * companyName : 南京港湾
         * workAttachments : []
         */

        private String id;
        private boolean isNewRecord;
        private Object remarks;
        private String createDate;
        private String updateDate;
        private double actualDeductionAmount;
        private String deductionDate;
        private String deductionId;
        private String companyId;
        private Object taxNumber;
        private Object policyEffective;
        private Object policyMoney;
        private Object monthMoney;
        private Object deductionAmount;
        private String type;
        private String title;
        private String companyName;
        private List<?> workAttachments;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIsNewRecord() {
            return isNewRecord;
        }

        public void setIsNewRecord(boolean isNewRecord) {
            this.isNewRecord = isNewRecord;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public double getActualDeductionAmount() {
            return actualDeductionAmount;
        }

        public void setActualDeductionAmount(double actualDeductionAmount) {
            this.actualDeductionAmount = actualDeductionAmount;
        }

        public String getDeductionDate() {
            return deductionDate;
        }

        public void setDeductionDate(String deductionDate) {
            this.deductionDate = deductionDate;
        }

        public String getDeductionId() {
            return deductionId;
        }

        public void setDeductionId(String deductionId) {
            this.deductionId = deductionId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public Object getTaxNumber() {
            return taxNumber;
        }

        public void setTaxNumber(Object taxNumber) {
            this.taxNumber = taxNumber;
        }

        public Object getPolicyEffective() {
            return policyEffective;
        }

        public void setPolicyEffective(Object policyEffective) {
            this.policyEffective = policyEffective;
        }

        public Object getPolicyMoney() {
            return policyMoney;
        }

        public void setPolicyMoney(Object policyMoney) {
            this.policyMoney = policyMoney;
        }

        public Object getMonthMoney() {
            return monthMoney;
        }

        public void setMonthMoney(Object monthMoney) {
            this.monthMoney = monthMoney;
        }

        public Object getDeductionAmount() {
            return deductionAmount;
        }

        public void setDeductionAmount(Object deductionAmount) {
            this.deductionAmount = deductionAmount;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public List<?> getWorkAttachments() {
            return workAttachments;
        }

        public void setWorkAttachments(List<?> workAttachments) {
            this.workAttachments = workAttachments;
        }
    }
}
