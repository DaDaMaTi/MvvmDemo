package com.csmar.mvvmdemo.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.csmar.mvvmdemo.BR;
import com.google.gson.annotations.SerializedName;

public class UserBean extends BaseObservable {
    public String id; // 用户id
    public String createTime;
    public String updateTime;
    public String account; // 账号
    @SerializedName("name")
    private String userName; // 用户名称
    public String idCadrd;
    public String phone;
    public String imageUrl;
    public int type;
    public String platformUserId;
    public String zhuanzhuanOpen;
    public String companyName;
    public int isComplete; // 是否完善了个人信息 0 完成 1 未完成

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }
}
