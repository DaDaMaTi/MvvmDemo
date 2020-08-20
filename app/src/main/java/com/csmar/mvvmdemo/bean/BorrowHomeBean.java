package com.csmar.mvvmdemo.bean;

import java.io.Serializable;
import java.util.List;

public class BorrowHomeBean implements Serializable{
    /**
     * jieJieAvaliable : 499000.00
     * list : [{"id":"bd920158ce2211ea92b7005056bc797e","page":null,"createTime":null,"updateTime":null,"map":{},"userId":"5bbf693dcc8f11ea92b7005056bc797e","borrowAmount":1000,"repaymentPeriods":6,"hasPeriods":0,"repayCount":0,"monthAmount":173.74,"repayAmount":0,"borrowTime":1595645919000,"repayDate":1595606400000,"nextRepayDate":1598284800000,"advanceRepayAmount":0,"state":0}]
     * borrowLimit : 500000
     */

    public String jieJieAvaliable;
    public double borrowLimit;
    public List<ListBean> list;

    public static class ListBean implements Serializable {
        /**
         * id : bd920158ce2211ea92b7005056bc797e
         * page : null
         * createTime : null
         * updateTime : null
         * map : {}
         * userId : 5bbf693dcc8f11ea92b7005056bc797e
         * borrowAmount : 1000
         * repaymentPeriods : 6
         * hasPeriods : 0
         * repayCount : 0
         * monthAmount : 173.74
         * repayAmount : 0
         * borrowTime : 1595645919000
         * repayDate : 1595606400000
         * nextRepayDate : 1598284800000
         * advanceRepayAmount : 0
         * state : 0   state = 0 就是去还款  否则就是无需还款
         */

        public String id;
        public Object page;
        public Object createTime;
        public Object updateTime;
        public MapBean map;
        public String userId;
        public double borrowAmount;
        public int repaymentPeriods;
        public int hasPeriods;
        public int repayCount;
        public double monthAmount;
        public double repayAmount;
        public long borrowTime;
        public long repayDate;
        public long nextRepayDate;
        public double advanceRepayAmount;
        public int state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getPage() {
            return page;
        }

        public void setPage(Object page) {
            this.page = page;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public double getBorrowAmount() {
            return borrowAmount;
        }

        public void setBorrowAmount(double borrowAmount) {
            this.borrowAmount = borrowAmount;
        }

        public int getRepaymentPeriods() {
            return repaymentPeriods;
        }

        public void setRepaymentPeriods(int repaymentPeriods) {
            this.repaymentPeriods = repaymentPeriods;
        }

        public int getHasPeriods() {
            return hasPeriods;
        }

        public void setHasPeriods(int hasPeriods) {
            this.hasPeriods = hasPeriods;
        }

        public int getRepayCount() {
            return repayCount;
        }

        public void setRepayCount(int repayCount) {
            this.repayCount = repayCount;
        }

        public double getMonthAmount() {
            return monthAmount;
        }

        public void setMonthAmount(double monthAmount) {
            this.monthAmount = monthAmount;
        }

        public double getRepayAmount() {
            return repayAmount;
        }

        public void setRepayAmount(double repayAmount) {
            this.repayAmount = repayAmount;
        }

        public long getBorrowTime() {
            return borrowTime;
        }

        public void setBorrowTime(long borrowTime) {
            this.borrowTime = borrowTime;
        }

        public long getRepayDate() {
            return repayDate;
        }

        public void setRepayDate(long repayDate) {
            this.repayDate = repayDate;
        }

        public long getNextRepayDate() {
            return nextRepayDate;
        }

        public void setNextRepayDate(long nextRepayDate) {
            this.nextRepayDate = nextRepayDate;
        }

        public double getAdvanceRepayAmount() {
            return advanceRepayAmount;
        }

        public void setAdvanceRepayAmount(double advanceRepayAmount) {
            this.advanceRepayAmount = advanceRepayAmount;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public static class MapBean implements Serializable{
        }
    }
}
