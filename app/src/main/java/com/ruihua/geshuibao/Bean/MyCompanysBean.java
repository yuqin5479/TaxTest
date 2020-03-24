package com.ruihua.geshuibao.Bean;

import java.util.List;

public class MyCompanysBean {

    /**
     * data : {"companyNotAduitList":[{"id":"726fab21051d4759b1b191c42901488c","name":"南京港湾"}],"MoneyCompanylist":[{"id":"826b6192c9184d7b8dde144df661c9d5","name":"无锡港湾"}],"securityCompanylist":[{"id":"431111c0a7bb4b60bdeacfac9cda621b","name":"无锡华彩网络科技有限公司"}]}
     * errormsg : 查询我加入的单位成功
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
        private List<CompanyNotAduitListBean> companyNotAduitList;
        private List<MoneyCompanylistBean> MoneyCompanylist;
        private List<SecurityCompanylistBean> securityCompanylist;

        public List<CompanyNotAduitListBean> getCompanyNotAduitList() {
            return companyNotAduitList;
        }

        public void setCompanyNotAduitList(List<CompanyNotAduitListBean> companyNotAduitList) {
            this.companyNotAduitList = companyNotAduitList;
        }

        public List<MoneyCompanylistBean> getMoneyCompanylist() {
            return MoneyCompanylist;
        }

        public void setMoneyCompanylist(List<MoneyCompanylistBean> MoneyCompanylist) {
            this.MoneyCompanylist = MoneyCompanylist;
        }

        public List<SecurityCompanylistBean> getSecurityCompanylist() {
            return securityCompanylist;
        }

        public void setSecurityCompanylist(List<SecurityCompanylistBean> securityCompanylist) {
            this.securityCompanylist = securityCompanylist;
        }

        public static class CompanyNotAduitListBean {
            /**
             * id : 726fab21051d4759b1b191c42901488c
             * name : 南京港湾
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class MoneyCompanylistBean {
            /**
             * id : 826b6192c9184d7b8dde144df661c9d5
             * name : 无锡港湾
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class SecurityCompanylistBean {
            /**
             * id : 431111c0a7bb4b60bdeacfac9cda621b
             * name : 无锡华彩网络科技有限公司
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
