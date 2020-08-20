package com.csmar.mvvmdemo.bean;

import java.util.List;

public class CreditBean {
    public int score;
    public String evluateTime;
    public List<Credit> creditList;

    public class Credit {
        public String id;
        public int page;
        public long createTime;
        public long updateTime;
        public String userId;
        public int score;
    }
}
