package com.csmar.lib.base.util;

import android.app.Application;

import java.lang.ref.SoftReference;

public class BaseApplication extends Application {

    private static Application mContext;

    public static Application getContext() {
       return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 在创建应用程序时创建
        mContext = this;
        CrashHandler.getInstance().init(this);
        LogUtil.e("wsd", "---BaseApplication---" + mContext);
    }
}
