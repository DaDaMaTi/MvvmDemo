package com.csmar.mvvmdemo.bean;

import java.util.List;

public class BillHomeBean {
    public Object selectMap;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : null
         * page : null
         * createTime : 1595865900000
         * updateTime : null
         * map : {}
         * userId : 5bbf693dcc8f11ea92b7005056bc797e
         * userType : null
         * chargeType : 13
         * chargeDate : 1595865600000
         * makeMoney : 6000.66
         * chargeMoney : 0.66
         * chargeDetail : 赚赚收益
         * chargWay : 0
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
        public MapBean map;
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

        public static class MapBean {
        }
    }
}
