package com.csmar.mvvmdemo.bean;

import java.util.List;

/**
 * 信用维护
 */
public class CreditArrowBean {

    public String imageUrl;
    public int lastMonth; // 上个月
    public String name;

    public CreditCount creaditLastMonthDto;

    public List<CreditArrow> list; // 信用维护列表

    public class CreditCount {
        public int lastMonth; // 上个月守约次数
        public int total; // 总守约次数
    }

    public class CreditArrow{
        public String id;
        public String page;
        public long createTime;
        public long updateTime;
        public String userId;
        public String borrowId;
        public String isComplete;  // 0 是未守约，点了可以带上借借id 去还款，1是按时还款，2是逾期后还款
        public long borrowTime;
    }
}
