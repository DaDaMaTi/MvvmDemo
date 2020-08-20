package com.csmar.mvvmdemo.bean;

import java.io.Serializable;
import java.util.List;

public class CreditCardListBean implements Serializable {

    /**
     * total : 29613.00
     * list : [{"repayAmount":"29613.00","status":"success","bindcard":"1","cardNo":"621661890300000024"},{"repayAmount":"0.00","status":"success","bindcard":"1","cardNo":"621661890200000026"}]
     */
    public String total;
    public List<ListBean> list;

    public static class ListBean implements Serializable{
        /**
         * repayAmount : 29613.00
         * status : success
         * bindcard : 1
         * cardNo : 621661890300000024
         */

        public String repayAmount;
        public String status;
        public String bindcard;
        public String name;
        public String cardNo;

    }
}
