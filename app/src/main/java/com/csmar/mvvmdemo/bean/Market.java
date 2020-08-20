package com.csmar.mvvmdemo.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 理财超市数据类
 */
public class Market {

    /**
     * date : 2020年07月25日
     * list : [{"createUserId":null,"level":1,"updateUserId":null,"updateTime":null,"type":1,"totalAmount":10000000,"startAmount":5000,"earningsYield":3.5,"balance":10000000,"createTime":1595572432000,"platformUserId":"43c89cabcd844bc5a498fa42dae55bae","dateLimit":34,"name":"寂寞如雪","id":"a3b35ad5cd7711ea92b7005056bc797e","page":null,"map":{},"isDel":0},{"createUserId":null,"level":1,"updateUserId":null,"updateTime":null,"type":0,"totalAmount":10000000,"startAmount":5000,"earningsYield":4,"balance":10000000,"createTime":1595572340000,"platformUserId":"43c89cabcd844bc5a498fa42dae55bae","dateLimit":20,"name":"生命管理","id":"6ca9d482cd7711ea92b7005056bc797e","page":null,"map":{},"isDel":0}]
     * balacne : 0
     */
    public String date;
    public List<ListEntity> list;
    public BigDecimal balacne; // 余额

    public static class ListEntity implements Serializable {
        /**
         * createUserId : null
         * level : 1
         * updateUserId : null
         * updateTime : null
         * type : 1
         * totalAmount : 10000000
         * startAmount : 5000
         * earningsYield : 3.5
         * balance : 10000000
         * createTime : 1595572432000
         * platformUserId : 43c89cabcd844bc5a498fa42dae55bae
         * dateLimit : 34
         * name : 寂寞如雪
         * id : a3b35ad5cd7711ea92b7005056bc797e
         * page : null
         * map : {}
         * isDel : 0
         */
        public String createUserId;
        public int level;
        public String updateUserId;
        public String updateTime;
        public int type;
        public BigDecimal totalAmount; // 总额度
        public BigDecimal startAmount;
        public BigDecimal earningsYield;
        public BigDecimal balance; // 理财产品剩余金额，
        public long createTime;
        public String platformUserId;
        public int dateLimit;
        public String name;
        public String id;
        public String page;
        public int isDel;
    }
}
