package com.csmar.lib.base.util;

import android.app.Application;

import java.lang.ref.SoftReference;

public class BaseApplication extends Application {

    private static SoftReference<Application> mContext;

    public static Application getContext() {
        LogUtil.e("wsd", "---Application---" + mContext);
        if (mContext == null || mContext.get() == null) {
            throw new NullPointerException("The Context is Null !");
        }
        return mContext.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 在创建应用程序时创建
        mContext = new SoftReference<Application>(this);
        CrashHandler.getInstance().init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mContext = null;
    }
}
