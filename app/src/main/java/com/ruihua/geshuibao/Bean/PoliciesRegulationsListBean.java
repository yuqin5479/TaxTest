package com.ruihua.geshuibao.Bean;

import java.util.List;

public class PoliciesRegulationsListBean {

    /**
     * data : {"pageNo":"1","pageSize":"10","list":[{"picture":[""],"id":"a8e584fccba946b498c6d79ee41679a4","title":"财政部 税务总局 保监会关于将商业健康保险个人所得税试点政策推广到全国范围实施的通知","createDate":"2018-12-03"},{"picture":[""],"id":"10adb4f3df854b7a9161bcea8eb06db0","title":"财政部 国家税务总局 证监会关于上市公司股息红利差别化个人所得税政策有关问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"0716bed4b476400f915cfc4aadeb6268","title":"财政部 国家税务总局关于个人非货币性资产投资有关个人所得税政策的通知","createDate":"2018-12-03"},{"picture":[""],"id":"015dabb96c944c3ba5a40018dff55a9c","title":"财政部　国家税务总局　证监会关于实施全国中小企业股份转让系统挂牌公司股息红利差别化个人所得税政策有关问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"84c47ff22c3b4c64ad7f90845d48e61e","title":"财政部 国家税务总局 民政部关于调整完善扶持自主就业退役士兵创业就业有关税收政策的通知","createDate":"2018-12-03"},{"picture":[""],"id":"cf01dad16bcf417c8e7c56bcaf7d6840","title":"财政部 人力资源社会保障部 国家税务总局关于企业年金 职业年金个人所得税有关问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"f4c726d88b2b4259a244b38dd01fa55e","title":"财政部 国家税务总局关于棚户区改造有关税收政策的通知","createDate":"2018-12-03"},{"picture":[""],"id":"fcb64df2b0604777acf698e588dcf44d","title":"财政部 海关总署 国家税务总局关于支持芦山地震灾后恢复重建有关税收政策问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"3f8f14dbf62b4c08a6593e8ec8f635b4","title":"财政部 国家税务总局关于地方政府债券利息免征所得税问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"92b6f99bd99a47fbbd3d34c2d800126e","title":"财政部 国家税务总局 证监会关于实施上市公司股息红利差别化个人所得税政策有关问题的通知","createDate":"2018-12-03"}],"maxNum":8}
     * errormsg : 获取我的政策法规列表成功
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
         * pageNo : 1
         * pageSize : 10
         * list : [{"picture":[""],"id":"a8e584fccba946b498c6d79ee41679a4","title":"财政部 税务总局 保监会关于将商业健康保险个人所得税试点政策推广到全国范围实施的通知","createDate":"2018-12-03"},{"picture":[""],"id":"10adb4f3df854b7a9161bcea8eb06db0","title":"财政部 国家税务总局 证监会关于上市公司股息红利差别化个人所得税政策有关问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"0716bed4b476400f915cfc4aadeb6268","title":"财政部 国家税务总局关于个人非货币性资产投资有关个人所得税政策的通知","createDate":"2018-12-03"},{"picture":[""],"id":"015dabb96c944c3ba5a40018dff55a9c","title":"财政部　国家税务总局　证监会关于实施全国中小企业股份转让系统挂牌公司股息红利差别化个人所得税政策有关问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"84c47ff22c3b4c64ad7f90845d48e61e","title":"财政部 国家税务总局 民政部关于调整完善扶持自主就业退役士兵创业就业有关税收政策的通知","createDate":"2018-12-03"},{"picture":[""],"id":"cf01dad16bcf417c8e7c56bcaf7d6840","title":"财政部 人力资源社会保障部 国家税务总局关于企业年金 职业年金个人所得税有关问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"f4c726d88b2b4259a244b38dd01fa55e","title":"财政部 国家税务总局关于棚户区改造有关税收政策的通知","createDate":"2018-12-03"},{"picture":[""],"id":"fcb64df2b0604777acf698e588dcf44d","title":"财政部 海关总署 国家税务总局关于支持芦山地震灾后恢复重建有关税收政策问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"3f8f14dbf62b4c08a6593e8ec8f635b4","title":"财政部 国家税务总局关于地方政府债券利息免征所得税问题的通知","createDate":"2018-12-03"},{"picture":[""],"id":"92b6f99bd99a47fbbd3d34c2d800126e","title":"财政部 国家税务总局 证监会关于实施上市公司股息红利差别化个人所得税政策有关问题的通知","createDate":"2018-12-03"}]
         * maxNum : 8
         */

        private String pageNo;
        private String pageSize;
        private int maxNum;
        private List<ListBean> list;

        public String getPageNo() {
            return pageNo;
        }

        public void setPageNo(String pageNo) {
            this.pageNo = pageNo;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public int getMaxNum() {
            return maxNum;
        }

        public void setMaxNum(int maxNum) {
            this.maxNum = maxNum;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * picture : [""]
             * id : a8e584fccba946b498c6d79ee41679a4
             * title : 财政部 税务总局 保监会关于将商业健康保险个人所得税试点政策推广到全国范围实施的通知
             * createDate : 2018-12-03
             */

            private String id;
            private String title;
            private String createDate;
            private List<String> picture;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public List<String> getPicture() {
                return picture;
            }

            public void setPicture(List<String> picture) {
                this.picture = picture;
            }
        }
    }
}
