package com.csmar.mvvmdemo.bean;

import java.util.List;

public class ZhuanHomeBean {

    public ZhuanInfoBean zhuanInfo;
    public double totalMoney;
    public String shouyiFafangDate;
    public double yesterdayMoney;
    public List<ListBean> list;

    public static class ZhuanInfoBean {
        /**
         * zhuanzhuanOpen : null
         * zhuanAmount : 2
         * balance : 10098
         * bankCardList : [{"id":"ce90566bcc8f11ea92b7005056bc797e","page":null,"createTime":null,"updateTime":null,"map":{},"userId":"a4487613cc8f11ea92b7005056bc797e","cardNumber":"621661690300000086","phone":"13800000002","isValid":true,"cardType":"A","bankName":"国泰安银行","bindingTime":1595472861000,"isDel":null,"createUserId":null,"updateUserId":null}]
         */

        public Object zhuanzhuanOpen;
        public double zhuanAmount;
        public double balance;
        public List<BankCardListBean> bankCardList;

        public static class BankCardListBean {
            /**
             * id : ce90566bcc8f11ea92b7005056bc797e
             * page : null
             * createTime : null
             * updateTime : null
             * map : {}
             * userId : a4487613cc8f11ea92b7005056bc797e
             * cardNumber : 621661690300000086
             * phone : 13800000002
             * isValid : true
             * cardType : A
             * bankName : 国泰安银行
             * bindingTime : 1595472861000
             * isDel : null
             * createUserId : null
             * updateUserId : null
             */

            public String id;
            public Object page;
            public Object createTime;
            public Object updateTime;
            public MapBean map;
            public String userId;
            public String cardNumber;
            public String phone;
            public boolean isValid;
            public String cardType;     //A 银行卡  B 引用卡  zhuanzhuan 赚赚  yue 余额
            public String bankName;
            public long bindingTime;
            public Object isDel;
            public Object createUserId;
            public Object updateUserId;

            public static class MapBean {
            }
        }
    }

    public static class ListBean {
        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public MapBeanX getMap() {
            return map;
        }

        public void setMap(MapBeanX map) {
            this.map = map;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getUserType() {
            return userType;
        }

        public void setUserType(Object userType) {
            this.userType = userType;
        }

        public int getChargeType() {
            return chargeType;
        }

        public void setChargeType(int chargeType) {
            this.chargeType = chargeType;
        }

        public long getChargeDate() {
            return chargeDate;
        }

        public void setChargeDate(long chargeDate) {
            this.chargeDate = chargeDate;
        }

        public double getMakeMoney() {
            return makeMoney;
        }

        public void setMakeMoney(double makeMoney) {
            this.makeMoney = makeMoney;
        }

        public double getChargeMoney() {
            return chargeMoney;
        }

        public void setChargeMoney(double chargeMoney) {
            this.chargeMoney = chargeMoney;
        }

        public String getChargeDetail() {
            return chargeDetail;
        }

        public void setChargeDetail(String chargeDetail) {
            this.chargeDetail = chargeDetail;
        }

        public int getChargWay() {
            return chargWay;
        }

        public void setChargWay(int chargWay) {
            this.chargWay = chargWay;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getIsDel() {
            return isDel;
        }

        public void setIsDel(Object isDel) {
            this.isDel = isDel;
        }

        public Object getCreateUserId() {
            return createUserId;
        }

        public void setCreateUserId(Object createUserId) {
            this.createUserId = createUserId;
        }

        public Object getUpdateUserId() {
            return updateUserId;
        }

        public void setUpdateUserId(Object updateUserId) {
            this.updateUserId = updateUserId;
        }

        public Object getPlatformUserId() {
            return platformUserId;
        }

        public void setPlatformUserId(Object platformUserId) {
            this.platformUserId = platformUserId;
        }

        /**
         * id : null
         * page : null
         * createTime : 1595474624000
         * updateTime : null
         * map : {}
         * userId : a4487613cc8f11ea92b7005056bc797e
         * userType : null
         * chargeType : 10
         * chargeDate : 1595433600000
         * makeMoney : 2
         * chargeMoney : 1
         * chargeDetail : 向国泰安·赚赚转账出
         * chargWay : 1
         * remark : null
         * isDel : null
         * createUserId : null
         * updateUserId : null
         * platformUserId : null
         */

        public Object id;
        public Object page;
        public long createTime;
        public Object updateTime;
        public MapBeanX map;
        public String userId;
        public Object userType;
        public int chargeType;
        public long chargeDate;
        public double makeMoney;
        public double chargeMoney;
        public String chargeDetail;
        public int chargWay;
        public Object remark;
        public Object isDel;
        public Object createUserId;
        public Object updateUserId;
        public Object platformUserId;

        public static class MapBeanX {
        }
    }
}
