package com.csmar.mvvmdemo.bean;

import java.math.BigDecimal;

/**
 * 首页理财 bean 类
 */
public class LcBean {
    public BigDecimal total; //

    public BigDecimal zhuanTotalEarning; // 赚赚历史累计

    public BigDecimal zhuanAmount; // 赚赚总钱数

    public BigDecimal licaiHistoryEarnings; // 理财历史累计

    public BigDecimal licaiBuyAmount; // 理财总钱数

    public BigDecimal licaiMouthEarnings; // 理财当月收益

    public BigDecimal zhuanYesterdayEarning; // 赚赚昨日收益

    public BigDecimal licaiBlockAmount; // 定期理财冻结数
}
