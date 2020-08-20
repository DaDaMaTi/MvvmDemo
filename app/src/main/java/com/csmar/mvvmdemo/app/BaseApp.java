package com.csmar.mvvmdemo.app;

import com.csmar.lib.base.util.BaseApplication;
import com.csmar.lib.net.NetworkManager;
import com.squareup.leakcanary.LeakCanary;

public class BaseApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化网络
        NetworkManager.getInstance().init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        LeakCanary.install(this);
    }
}
