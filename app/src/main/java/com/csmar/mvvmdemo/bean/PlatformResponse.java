package com.csmar.mvvmdemo.bean;

public class PlatformResponse {
    public int result; // 返回的code
    public String data; // 平台用户
    public String explain; // explain 可用来返回接口的说明

    public class PlatformUser {
        public String accountName; // 平台账号
        public String id; // 平台账号id
        public String userName; // 平台用户名

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public boolean isSuccess() {
        return result == 200;
    }
}


