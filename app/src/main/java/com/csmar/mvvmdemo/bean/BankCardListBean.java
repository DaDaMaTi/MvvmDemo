package com.csmar.mvvmdemo.bean;


import java.util.List;

/**
 * 我的银行卡列表Bean  可能含有信用卡
 */
public class BankCardListBean {
    public List<CardListBean> cardList;

    public static class CardListBean {
        /**
         * name : 张无忌
         * idCard : 342422198809207001
         * balance : 25351.78
         * zhuanZhuan : 6000.32
         * cardId : 9566632bcc8f11ea92b7005056bc797e
         * bankName : 国泰安银行
         * cardNumber : 621661690300000085
         * cardType : A
         * isValid : 1
         * context : 国泰安银行储蓄卡(尾号0085)
         * cardNumberContext : 0085
         */

        public String name;
        public String idCard;
        public double balance;
        public double zhuanZhuan;
        public String cardId;
        public String bankName;
        public String cardNumber;
        public String cardType;   //A 银行卡  B 引用卡  zhuanzhuan 赚赚  yue 余额
        public int isValid;
        public String context;
        public String cardNumberContext;
    }
}
