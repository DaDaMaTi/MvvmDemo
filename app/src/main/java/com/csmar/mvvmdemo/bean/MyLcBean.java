package com.csmar.mvvmdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 我的理财数据类
 */
public class MyLcBean {

    public List<MyLc> list;

    public static class MyLc implements Parcelable {
        public double addEarnings; // 累计收益
        public long endDate;
        public int remainDay;
        public String updateTime;
        public int type; // 0 券商， 1 保险
        public String userId;
        public int isRedempt; // isRedempt 1 是已赎回，是置灰的，字写“已赎回”，0 --> rounds == 0 的是第一轮的，也不能赎回   显示“赎回”置灰 。其他的情况可赎回 ，显示 ”赎回“可点。
        public String userFinancialId; // 理财id
        public double buyAmount; // 持有额
        public int startAmount;
        public String createTime;
        public int dateLimit;
        public String name;
        public String id;
        public String page;
        public int rounds;

        protected MyLc(Parcel in) {
            addEarnings = in.readDouble();
            endDate = in.readLong();
            remainDay = in.readInt();
            updateTime = in.readString();
            type = in.readInt();
            userId = in.readString();
            isRedempt = in.readInt();
            userFinancialId = in.readString();
            buyAmount = in.readDouble();
            startAmount = in.readInt();
            createTime = in.readString();
            dateLimit = in.readInt();
            name = in.readString();
            id = in.readString();
            page = in.readString();
            rounds = in.readInt();
        }

        public static final Creator<MyLc> CREATOR = new Creator<MyLc>() {
            @Override
            public MyLc createFromParcel(Parcel in) {
                return new MyLc(in);
            }

            @Override
            public MyLc[] newArray(int size) {
                return new MyLc[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(addEarnings);
            parcel.writeLong(endDate);
            parcel.writeInt(remainDay);
            parcel.writeString(updateTime);
            parcel.writeInt(type);
            parcel.writeString(userId);
            parcel.writeInt(isRedempt);
            parcel.writeString(userFinancialId);
            parcel.writeDouble(buyAmount);
            parcel.writeInt(startAmount);
            parcel.writeString(createTime);
            parcel.writeInt(dateLimit);
            parcel.writeString(name);
            parcel.writeString(id);
            parcel.writeString(page);
            parcel.writeInt(rounds);
        }
    }
}
